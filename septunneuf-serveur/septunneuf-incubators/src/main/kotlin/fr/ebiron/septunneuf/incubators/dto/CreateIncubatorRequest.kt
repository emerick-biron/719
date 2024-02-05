package fr.ebiron.septunneuf.incubators.dto

import jakarta.validation.constraints.NotBlank

data class CreateIncubatorRequest(
    @NotBlank(message = "heroName is mandatory")
    val heroName: String
)
