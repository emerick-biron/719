package fr.ebiron.septunneuf.incubators.dto

import com.fasterxml.jackson.annotation.JsonInclude
import fr.ebiron.septunneuf.incubators.models.IncubatorStatus
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GetIncubatorStatusResponse(
    val incubatorStatus: IncubatorStatus,
    val durability: Int,
    val eggId: Long? = null,
    val hatchingDateTime: LocalDateTime? = null
)