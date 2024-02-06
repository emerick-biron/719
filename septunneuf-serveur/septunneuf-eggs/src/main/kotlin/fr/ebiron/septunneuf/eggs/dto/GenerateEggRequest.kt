package fr.ebiron.septunneuf.eggs.dto

import jakarta.validation.constraints.Min

data class GenerateEggRequest(
    @field:Min(value = 1, message = "quantity must be > 0")
    val quantity: Int = 1,
)
