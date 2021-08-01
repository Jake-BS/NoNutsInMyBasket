package com.example.nonutsinmybasket.models

import java.util.*

data class Diet (
    val ingredientList: List<String>,
    val name: String,
    var isChecked: Boolean = false
    )