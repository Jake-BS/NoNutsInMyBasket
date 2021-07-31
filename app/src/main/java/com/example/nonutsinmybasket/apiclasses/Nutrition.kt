package com.example.nonutsinmybasket.apiclasses

data class Nutrition(
    val display: DisplayXX,
    val small: SmallXX,
    val thumb: ThumbXX
)

data class SmallXX (
    val en: String
)

data class ThumbXX (
    val en: String
)
