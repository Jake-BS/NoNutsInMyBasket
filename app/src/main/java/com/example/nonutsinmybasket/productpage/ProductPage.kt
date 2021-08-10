package com.example.nonutsinmybasket.productpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nonutsinmybasket.R
import com.example.nonutsinmybasket.productpage.api.MainViewModel
import com.example.nonutsinmybasket.productpage.api.MainViewModelFactory
import com.example.nonutsinmybasket.productpage.api.Repository
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_product_page.*
import kotlinx.android.synthetic.main.toolbar.*

class ProductPage : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var db: FirebaseFirestore

    companion object {
        const val placeholderImage = "https://www.ecpgr.cgiar.org/fileadmin/templates/ecpgr.org/Assets/images/No_Image_Available.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        backButton.setOnClickListener { super.onBackPressed() }

        db = FirebaseFirestore.getInstance()

        val intent = getIntent()

        val barcodeData = intent.getStringExtra("Barcode")
        val userId = intent.getStringExtra("user_id")
        getUserData(userId, barcodeData)


    }

    private fun getUserData(userId: String?, barcodeData: String?) {
        if (userId != null) {
            val user = db.collection("USERS").document(userId)
            user.get().addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    val document = task.result
                    val ingredientsText = document?.get("custom_ingredients") as ArrayList<String>
                    val repository = Repository()
                    val viewModelFactory = MainViewModelFactory(repository)
                    viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
                    if (barcodeData != null) {
                        getProductData(barcodeData, ingredientsText)
                    }
                    //Action bar stuff was here
                }
            }
        }
    }

    private fun getProductData(barcodeData: String, ingredientsText: ArrayList<String>) {
        viewModel.getPostDynamic(barcodeData)
        viewModel.myResponseDynamic.observe(this, Observer { response ->
            if (response.isSuccessful) {

                val productName = response.body()?.product?.product_name?.lowercase()
                val productIngredients = response.body()?.product?.ingredients_text?.lowercase()
                if (productIngredients != null) {
                    detectAndDisplayBannedIngredients(productIngredients, ingredientsText)
                } else output.text = "Nothing to display"

                val imageURL = response.body()?.product?.image_front_url
                if(imageURL==null)
                    Glide.with(this).load(R.drawable.jake).into(imageView)
                else {
                    val into = Picasso.get().load(imageURL).into(imageView)
                    noImagePrompt.visibility=View.INVISIBLE
                }
            } else {
                response.errorBody()?.let { Log.d("Response", it.string()) }
                output.text = response.code().toString()
                Picasso.get().load(placeholderImage).into(imageView)
            }
        })
    }

    private fun detectAndDisplayBannedIngredients(productIngredients: String, ingredientsText: ArrayList<String>){
        var displayString = ""
        var foundSomethingSwitch = false
        var maybeFoundSomethingSwitch = false
        for(ingredient in ingredientsText) {
            if (productIngredients != null) {
                if (
                    //for middle ingredient scenario
                    productIngredients.contains(" "+ingredient.lowercase()+",") ||
                    productIngredients.contains(" "+ingredient.lowercase()+" ") ||

                    //for starting ingredient scenario
                    productIngredients.substring(0, ingredient.length+1) == (ingredient.lowercase()+",") ||
                    productIngredients.substring(0, ingredient.length+1) == (ingredient.lowercase()+" ") ||
                    //for ending ingredient scenario
                    productIngredients.substring(productIngredients.length-(ingredient.length)) == ingredient.lowercase() ||
                    //ordered like this because we're using shortcut if statement that skip the rest once one scenario is true
                    //less likely scenarios
                    productIngredients.contains("("+ingredient.lowercase()+")") ||
                    productIngredients.contains("("+ingredient.lowercase()+" ") ||
                    productIngredients.contains(" "+ingredient.lowercase()+")") ||

                    productIngredients.contains(" "+ingredient.lowercase()+"-") ||
                    productIngredients.contains("-"+ingredient.lowercase()+" ") ||
                    productIngredients.contains(" "+ingredient.lowercase()+":") ||

                    //very unlikely scenarios
                    productIngredients.contains(":"+ingredient.lowercase()+" ") ||
                    productIngredients.contains(":"+ingredient.lowercase()+":") ||
                    productIngredients.contains("-"+ingredient.lowercase()+"-") ||
                    productIngredients.contains(" "+ingredient.lowercase()+":")

                        ) {
                    if (foundSomethingSwitch) {
                        displayString = "$displayString, $ingredient"
                    }
                    else if (!maybeFoundSomethingSwitch){
                        displayString = "Avoid buying - detected $ingredient"
                        foundSomethingSwitch = true
                    } else {
                        val previouslyDetected = displayString.substring(7)
                        displayString = "A$previouslyDetected"
                        foundSomethingSwitch = true
                        maybeFoundSomethingSwitch = false
                    }
                } else if (productIngredients.contains(ingredient.lowercase())) {
                    if(foundSomethingSwitch || maybeFoundSomethingSwitch) {
                        displayString = "$displayString, $ingredient is a sub-word of an ingredient"
                    } else{
                        displayString = "Maybe avoid buying - $ingredient is a sub-word of an ingredient"
                        maybeFoundSomethingSwitch = true
                    }
                }
            }
        }
        if (foundSomethingSwitch || maybeFoundSomethingSwitch) output.text = displayString + "."
        else output.text = "This product fits your dietry requirements, enjoy!"
    }

}