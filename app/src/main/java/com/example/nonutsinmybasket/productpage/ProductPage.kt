package com.example.nonutsinmybasket.productpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nonutsinmybasket.R
import com.example.nonutsinmybasket.productpage.api.MainViewModel
import com.example.nonutsinmybasket.productpage.api.MainViewModelFactory
import com.example.nonutsinmybasket.productpage.api.Repository
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_page.*

class ProductPage : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var db: FirebaseFirestore

    companion object {
        const val placeholderImage = "https://www.ecpgr.cgiar.org/fileadmin/templates/ecpgr.org/Assets/images/No_Image_Available.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        db = FirebaseFirestore.getInstance()

        val intent = getIntent()

        val barcodeData = intent.getStringExtra("Barcode")
        val email = intent.getStringExtra("email_id")
        getUserData(email, barcodeData)


    }

    private fun getUserData(email: String?, barcodeData: String?) {
        if (email != null) {
            val user = db.collection("USERS").document(email)
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
                    val actionBar = supportActionBar
                    actionBar!!.title = "Ingredient Data"
                    actionBar.setDisplayHomeAsUpEnabled(true)
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
                }

                val imageURL = response.body()?.product?.image_front_url
                if(imageURL==null)
                    Picasso.get().load(placeholderImage).into(imageView)
                else {
                    val into = Picasso.get().load(imageURL).into(imageView)
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
                    }
                } else if (productIngredients.contains(ingredient.lowercase())) {
                    if(foundSomethingSwitch || maybeFoundSomethingSwitch) {
                        displayString = "$displayString, very small chance of $ingredient (may be part of another word)"
                    } else if (maybeFoundSomethingSwitch) {
                        displayString = "Maybe avoid buying - small chance of $ingredient (may be part of another word)"
                    }
                }
            }
        }
        if (foundSomethingSwitch) output.text = displayString + "."
        else output.text = "This product fits your dietry requirements, enjoy!"
    }

}