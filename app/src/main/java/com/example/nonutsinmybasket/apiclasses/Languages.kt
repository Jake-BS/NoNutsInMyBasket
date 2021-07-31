package com.example.nonutsinmybasket.apiclasses

import com.google.gson.annotations.SerializedName

data class Languages(
    @SerializedName("en:english") val en_english: Int,
    @SerializedName("en:french") val en_french: Int
)