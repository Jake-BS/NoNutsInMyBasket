package com.example.nonutsinmybasket.apiclasses

data class Ingredient(
    val from_palm_oil: String,
    val has_sub_ingredients: String,
    val id: String,
    val percent_estimate: Int,
    val percent_max: Int,
    val percent_min: Int,
    val rank: Int,
    val text: String,
    val vegan: String,
    val vegetarian: String
)