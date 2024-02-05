package fr.ebiron.septunneuf.incubators.dto

import jakarta.validation.constraints.NotBlank
import kotlin.time.Duration

data class FillIncubatorRequest(
    @NotBlank(message = "eggId is mandatory")
    val eggId: Long,
    @NotBlank(message = "incubationTime is mandatory")
    val incubationTime: Duration
)
