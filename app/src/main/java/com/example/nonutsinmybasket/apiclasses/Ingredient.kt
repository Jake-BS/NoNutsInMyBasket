package com.example.nonutsinmybasket.apiclasses

data class Ingredient(
    val from_palm_oil: String,
    val has_sub_ingredients: String,
    val id: String,
    val percent_estimate: Float,
    val percent_max: Float,
    val percent_min: Float,
    val rank: Int,
    val text: String,
    val vegan: String,
    val vegetarian: String
)