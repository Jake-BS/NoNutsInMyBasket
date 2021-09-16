package com.deezappsinc.nonutsinmybasket.models.apiclasses

data class Adjustments(
    val origins_of_ingredients: OriginsOfIngredients,
    val packaging: Packaging,
    val production_system: ProductionSystem,
    val threatened_species: ThreatenedSpecies
)