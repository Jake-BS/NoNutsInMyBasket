package com.example.nonutsinmybasket
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AvoidListAdapter (
    private var ingredients: MutableList<Ingredient>
    ) : RecyclerView.Adapter<AvoidListAdapter.IngredientViewHolder>()
{
    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_ingredient,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val curIngredient = ingredients[position]
        holder.itemView.apply {
            val tvIngredient = findViewById<TextView>(R.id.tvIngredient)
            tvIngredient.text = curIngredient.name
            val btnRemoveIngredient = findViewById<ImageButton>(R.id.btnRemoveIngredient)
            btnRemoveIngredient.setOnClickListener {
                curIngredient.delete = true
                ingredients.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(0, ingredient)
        notifyItemInserted(0)
    }

    fun getIngredients(): MutableList<Ingredient> {
        return ingredients
    }

    fun removeIngredients() {
        ingredients.removeAll{ ingredient ->  ingredient.delete}
        notifyDataSetChanged()
    }

    fun setIngredientsList(newIngredients: MutableList<Ingredient>) {
        ingredients = newIngredients
        notifyDataSetChanged()
    }

}