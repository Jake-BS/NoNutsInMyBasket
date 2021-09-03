package com.deezappsinc.nonutsinmybasket.models.apiclasses

data class Packaging(
    val non_recyclable_and_non_biodegradable_materials: Float,
    val packagings: List<PackagingX>,
    val score: Float,
    val value: Float
)