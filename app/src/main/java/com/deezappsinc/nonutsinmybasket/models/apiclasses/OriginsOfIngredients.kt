package com.deezappsinc.nonutsinmybasket.models.apiclasses

data class OriginsOfIngredients(
    val aggregated_origins: List<AggregatedOrigin>,
    val epi_score: Float,
    val epi_value: Float,
    val origins_from_origins_field: List<String>,
    val transportation_score_be: Float,
    val transportation_score_ch: Float,
    val transportation_score_de: Float,
    val transportation_score_es: Float,
    val transportation_score_fr: Float,
    val transportation_score_ie: Float,
    val transportation_score_it: Float,
    val transportation_score_lu: Float,
    val transportation_score_nl: Float,
    val transportation_value_be: Float,
    val transportation_value_ch: Float,
    val transportation_value_de: Float,
    val transportation_value_es: Float,
    val transportation_value_fr: Float,
    val transportation_value_ie: Float,
    val transportation_value_it: Float,
    val transportation_value_lu: Float,
    val transportation_value_nl: Float,
    val value_be: Float,
    val value_ch: Float,
    val value_de: Float,
    val value_es: Float,
    val value_fr: Float,
    val value_ie: Float,
    val value_it: Float,
    val value_lu: Float,
    val value_nl: Float,
    val warning: String
)