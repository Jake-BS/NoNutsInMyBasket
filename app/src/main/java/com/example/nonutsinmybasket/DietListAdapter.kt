package com.example.nonutsinmybasket

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DietListAdapter (
    private val diets: MutableList<Diet>,
    private val avoidListAdapter: AvoidListAdapter
) : RecyclerView.Adapter<DietListAdapter.DietViewHolder>() {

    class DietViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        return DietViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_diet,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        val curDiet = diets[position]
        holder.itemView.apply {
            val tvDietTitle = findViewById<TextView>(R.id.tvDietTitle)
            val cbDietSelected = findViewById<CheckBox>(R.id.cbDietSelected)
            tvDietTitle.text = curDiet.name
            cbDietSelected.isChecked = curDiet.isChecked
            cbDietSelected.setOnCheckedChangeListener { _, isChecked ->
                curDiet.isChecked = !curDiet.isChecked
                if(curDiet.isChecked) {
                    //Adds diet items when checked
                    for (ingredient in curDiet.ingredientList) avoidListAdapter.addIngredient(
                        Ingredient(ingredient)
                    )
                }
                else {
                    //Removes diet items when unchecked
                    var curIngredientsList = avoidListAdapter.getIngredients()
                    for (avoidIngredient in curIngredientsList) {
                        for (dietIngredient in curDiet.ingredientList) {
                            if (avoidIngredient.name == dietIngredient) avoidIngredient.delete = true
                        }
                    }
                    avoidListAdapter.setIngredientsList(curIngredientsList)
                    avoidListAdapter.removeIngredients()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return diets.size
    }

    fun addDiet(diet: Diet) {
        diets.add(diet)
        notifyItemInserted(diets.size - 1)
    }
}