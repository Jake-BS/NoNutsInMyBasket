package com.deezappsinc.nonutsinmybasket.models

data class Diet (
    val ingredientList: List<String>,
    val name: String,
    var isChecked: Boolean = false
    )