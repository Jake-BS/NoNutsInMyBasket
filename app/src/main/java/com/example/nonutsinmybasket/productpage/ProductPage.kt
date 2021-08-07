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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        db = FirebaseFirestore.getInstance()

        val intent = getIntent()

        val barcodeData = intent.getStringExtra("Barcode")
        val email = intent.getStringExtra("email_id")

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
                        viewModel.getPostDynamic(barcodeData)
                        viewModel.myResponseDynamic.observe(this, Observer { response ->
                            if (response.isSuccessful) {
                                var displayString = ""
                                var foundSomethingSwitch = false
                                val productIngredients = response.body()?.product?.ingredients_text?.lowercase()
                                for(ingredient in ingredientsText) {
                                    if (productIngredients != null) {
                                        if (productIngredients.contains(ingredient.lowercase()) ) {
                                            if (foundSomethingSwitch) {
                                                displayString = "$displayString, $ingredient"
                                            }
                                            else {
                                                displayString = "Avoid buying - detected $ingredient"
                                                foundSomethingSwitch = true
                                            }
                                        }
                                    }
                                }
                                if (foundSomethingSwitch == true) output.text = displayString + "."
                                else output.text = "This product fits your dietry requirements, enjoy!"

                                val imageURL = response.body()?.product?.image_front_url
                                val placeholderImage = "https://www.ecpgr.cgiar.org/fileadmin/templates/ecpgr.org/Assets/images/No_Image_Available.jpg"
                                if(imageURL==null)
                                    Picasso.get().load(placeholderImage).into(imageView)
                                else {
                                    val into = Picasso.get().load(imageURL).into(imageView)
                                }



                            } else {
                                response.errorBody()?.let { Log.d("Response", it.string()) }
                                output.text = response.code().toString()
                            }
                        })
                    }


                    val actionBar = supportActionBar
                    actionBar!!.title = "Ingredient Data"
                    actionBar.setDisplayHomeAsUpEnabled(true)
                }
            }
        }

    }

}