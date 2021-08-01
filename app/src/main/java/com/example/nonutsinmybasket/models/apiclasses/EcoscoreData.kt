package com.example.nonutsinmybasket.models.apiclasses

data class EcoscoreData(
    val adjustments: Adjustments,
    val agribalyse: Agribalyse,
    val missing: Missing,
    val missing_agribalyse_match_warning: Float,
    val status: String
)