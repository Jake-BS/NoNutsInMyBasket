package com.example.nonutsinmybasket.apiclasses

data class Source(
    val fields: List<String>,
    val id: String,
    val images: List<Any>,
    val import_t: Int,
    val manufacturer: String,
    val name: String,
    val source_licence: String,
    val source_licence_url: String,
    val url: String
)