package com.example.nonutsinmybasket.apiclasses

data class Packaging(
    val non_recyclable_and_non_biodegradable_materials: Int,
    val packagings: List<PackagingX>,
    val score: Int,
    val value: Int
)