package com.example.nonutsinmybasket

data class Diet (
    val ingredientList: List<String>,
    val name: String,
    var isChecked: Boolean
    )