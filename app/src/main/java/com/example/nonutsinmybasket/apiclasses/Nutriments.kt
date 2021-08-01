package com.example.nonutsinmybasket.apiclasses

import com.google.gson.annotations.SerializedName

data class Nutriments(
    val carbohydrates: Int,
    val carbohydrates_100g: Int,
    val carbohydrates_unit: String,
    val carbohydrates_value: Int,
    val energy: Int,
    @SerializedName("energy-kj") val energy_kj: Int,
    @SerializedName("energy-kj_100g") val energy_kj_100g: Int,
    @SerializedName("energy-kj_unit") val energy_kj_unit: String,
    @SerializedName("energy-kj_value") val energy_kj_value: Int,
    val energy_100g: Int,
    val energy_unit: String,
    val energy_value: Int,
    val fat: Int,
    val fat_100g: Int,
    val fat_unit: String,
    val fat_value: Int,
    @SerializedName("fruits-vegetables-nuts-estimate-from-ingredients_100g") val fruits_vegetables_nuts_estimate_from_ingredients_100g: Int,
    @SerializedName("nova-group") val nova_group: Int,
    @SerializedName("nova-group_100g") val nova_group_100g: Int,
    @SerializedName("nova-group_serving") val nova_group_serving: Int,
    @SerializedName("nutrition-score-fr") val nutrition_score_fr: Int,
    @SerializedName("nutrition-score-fr_100g") val nutrition_score_fr_100g: Int,
    val proteins: Double,
    val proteins_100g: Double,
    val proteins_unit: String,
    val proteins_value: Double,
    val salt: Double,
    val salt_100g: Double,
    val salt_unit: String,
    val salt_value: Double,
    @SerializedName("saturated-fat") val saturated_fat: Int,
    @SerializedName("saturated-fat_100g") val saturated_fat_100g: Int,
    @SerializedName("saturated-fat_unit") val saturated_fat_unit: String,
    @SerializedName("saturated-fat_value") val saturated_fat_value: Int,
    val sodium: Double,
    val sodium_100g: Double,
    val sodium_unit: String,
    val sodium_value: Double,
    val sugars: Int,
    val sugars_100g: Int,
    val sugars_unit: String,
    val sugars_value: Int
)