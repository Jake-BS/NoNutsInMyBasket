package com.example.nonutsinmybasket.apiclasses

data class ProductModel(
    val code: String,
    val product: Product,
    val status: Int,
    val status_verbose: String
)