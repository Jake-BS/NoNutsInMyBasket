package com.example.nonutsinmybasket.models.apiclasses

import com.google.gson.annotations.SerializedName

data class NutrientLevels(
    val fat: String,
    val salt: String,
    @SerializedName("saturated-fat") val saturated_fat: String,
    val sugars: String
)