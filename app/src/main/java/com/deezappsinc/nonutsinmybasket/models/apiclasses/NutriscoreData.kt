package com.deezappsinc.nonutsinmybasket.models.apiclasses

data class NutriscoreData(
    val energy: Float,
    val energy_poFloats: Float,
    val energy_value: Float,
    val fiber: Float,
    val fiber_poFloats: Float,
    val fiber_value: Float,
    val fruits_vegetables_nuts_colza_walnut_olive_oils: Float,
    val fruits_vegetables_nuts_colza_walnut_olive_oils_poFloats: Float,
    val fruits_vegetables_nuts_colza_walnut_olive_oils_value: Float,
    val grade: String,
    val is_beverage: Float,
    val is_cheese: Float,
    val is_fat: Float,
    val is_water: Float,
    val negative_poFloats: Float,
    val positive_poFloats: Float,
    val proteins: Double,
    val proteins_poFloats: Float,
    val proteins_value: Double,
    val saturated_fat: Float,
    val saturated_fat_poFloats: Float,
    val saturated_fat_ratio: Double,
    val saturated_fat_ratio_poFloats: Float,
    val saturated_fat_ratio_value: Double,
    val saturated_fat_value: Float,
    val score: Float,
    val sodium: Float,
    val sodium_poFloats: Float,
    val sodium_value: Float,
    val sugars: Float,
    val sugars_poFloats: Float,
    val sugars_value: Float
)