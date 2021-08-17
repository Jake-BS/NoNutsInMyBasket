package com.example.nonutsinmybasket.models

data class Diet (
    val ingredientList: List<String>,
    val name: String,
    var isChecked: Boolean = false
    )