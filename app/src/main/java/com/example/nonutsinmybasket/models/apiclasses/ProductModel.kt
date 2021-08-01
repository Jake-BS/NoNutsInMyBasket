package com.example.nonutsinmybasket.models.apiclasses

data class ProductModel(
    val code: String,
    val product: Product,
    val status: Float,
    val status_verbose: String
)