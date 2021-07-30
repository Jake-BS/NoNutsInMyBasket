package com.example.nonutsinmybasket

import java.util.*

data class Diet (
    val ingredientList: List<String>,
    val name: String,
    var isChecked: Boolean = false
    )