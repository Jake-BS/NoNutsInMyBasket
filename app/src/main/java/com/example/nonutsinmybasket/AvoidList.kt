package com.example.nonutsinmybasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AvoidList : AppCompatActivity() {

    private lateinit var avoidListAdapter: AvoidListAdapter
    private lateinit var dietListAdapter: DietListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avoid_list)

        val actionBar = supportActionBar

        actionBar!!.title = "Avoid List"
        actionBar.setDisplayHomeAsUpEnabled(true)

        avoidListAdapter = AvoidListAdapter(mutableListOf())
        var rvAvoidList = findViewById<RecyclerView>(R.id.rvAvoidList)
        rvAvoidList.adapter = avoidListAdapter
        rvAvoidList.layoutManager = LinearLayoutManager(this)

        var btnAddIngredient = findViewById<Button>(R.id.btnAdd)
        btnAddIngredient.setOnClickListener {
            var etEnterIngredient = findViewById<EditText>(R.id.etEnterIngredient)
            val ingredientName = etEnterIngredient.text.toString()
            if(ingredientName.isNotEmpty()) {
                val ingredient = Ingredient(ingredientName)
                avoidListAdapter.addIngredient(ingredient)
                etEnterIngredient.text.clear()
            }
        }

        dietListAdapter = DietListAdapter(mutableListOf(), this.avoidListAdapter)
        var rvDietList = findViewById<RecyclerView>(R.id.rvDietsList)
        rvDietList.adapter = dietListAdapter
        rvDietList.layoutManager = LinearLayoutManager(this)

        val veggieBanned = listOf<String>("Chicken", "Beef", "Pork", "Lamb", "Meat")
        val name = "Vegetarian"
        val veggieDiet = Diet(veggieBanned, name, false)
        dietListAdapter.addDiet(veggieDiet)
    }
}