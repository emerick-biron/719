package fr.ebiron.septunneuf.incubators.controllers

import fr.ebiron.septunneuf.incubators.dto.*
import fr.ebiron.septunneuf.incubators.models.IncubatorStatus
import fr.ebiron.septunneuf.incubators.services.IncubatorService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/incubators")
class IncubatorController(private val service: IncubatorService) {

    @GetMapping
    @ResponseBody
    fun getHeroIncubators(@RequestHeader(required = true, name = "heroName") @NotBlank heroName: String): GetHeroIncubatorsResponse {
        return GetHeroIncubatorsResponse(service.getHeroIncubators(heroName).map { it.id }.toList())
    }

    @GetMapping("/{incubatorId}/status")
    @ResponseBody
    fun getIncubatorStatus(@PathVariable incubatorId: Long): GetIncubatorStatusResponse {
        val incubator = service.getIncubatorById(incubatorId)
        return when (incubator.eggId) {
            null -> GetIncubatorStatusResponse(IncubatorStatus.EMPTY, incubator.durability)
            else -> GetIncubatorStatusResponse(
                IncubatorStatus.FULL,
                incubator.durability,
                incubator.eggId,
                incubator.hatchingDateTime
            )
        }
    }

    @PutMapping("/{incubatorId}/fill")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun fillIncubator(@PathVariable incubatorId: Long, @Valid @RequestBody req: FillIncubatorRequest){
        service.fillIncubator(incubatorId, req.eggId, req.incubationTime)
    }

}