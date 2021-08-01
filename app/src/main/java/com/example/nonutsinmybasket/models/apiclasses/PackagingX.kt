package com.example.nonutsinmybasket.models.apiclasses

data class PackagingX(
    val ecoscore_material_score: String,
    val ecoscore_shape_ratio: String,
    val material: String,
    val non_recyclable_and_non_biodegradable: String,
    val shape: String
)