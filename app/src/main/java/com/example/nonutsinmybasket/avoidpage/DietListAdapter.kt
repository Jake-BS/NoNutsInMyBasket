package com.example.nonutsinmybasket.avoidpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nonutsinmybasket.models.Diet
import com.example.nonutsinmybasket.models.Ingredient
import com.example.nonutsinmybasket.R

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
                    //sqliteHelper.insertDiet(curDiet.name)
                }
                else {
                    //Removes diet items when unchecked
                    var curIngredientsList = avoidListAdapter.getIngredients()
                    for (dietIngredient in curDiet.ingredientList) {
                        for (avoidIngredient in curIngredientsList) {
                            if (avoidIngredient.name == dietIngredient) avoidIngredient.delete = true
                            break
                        }
                    }
                    avoidListAdapter.setIngredientsList(curIngredientsList)
                    avoidListAdapter.removeIngredients()
                    //sqliteHelper.deleteDiet(curDiet.name)
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

    fun getDiets() : MutableList<Diet>{
        return diets
    }
}