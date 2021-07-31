package com.example.nonutsinmybasket.apiclasses

data class EcoscoreData(
    val adjustments: Adjustments,
    val agribalyse: Agribalyse,
    val missing: Missing,
    val missing_agribalyse_match_warning: Int,
    val status: String
)