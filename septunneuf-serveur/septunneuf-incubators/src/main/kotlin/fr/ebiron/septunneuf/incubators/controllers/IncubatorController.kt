package fr.ebiron.septunneuf.incubators.controllers

import fr.ebiron.septunneuf.incubators.dto.FillIncubatorRequest
import fr.ebiron.septunneuf.incubators.dto.GetHeroIncubatorsResponse
import fr.ebiron.septunneuf.incubators.dto.GetIncubatorStatusResponse
import fr.ebiron.septunneuf.incubators.models.IncubatorStatus
import fr.ebiron.septunneuf.incubators.services.IncubatorService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/incubators")
class IncubatorController(private val service: IncubatorService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseBody
    fun getHeroIncubators(
        @RequestHeader(
            required = true,
            name = "heroName"
        ) @NotBlank heroName: String
    ): GetHeroIncubatorsResponse {
        log.info("GET /incubators - heroName='$heroName'")
        return GetHeroIncubatorsResponse(service.getHeroIncubators(heroName).map { it.id }.toList())
    }

    @GetMapping("/{incubatorId}/status")
    @ResponseBody
    fun getIncubatorStatus(@PathVariable incubatorId: Long): GetIncubatorStatusResponse {
        log.info("GET /incubators/$incubatorId/status")
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
    fun fillIncubator(
        @RequestHeader(
            required = true,
            name = "heroName"
        ) @NotBlank heroName: String,
        @PathVariable incubatorId: Long,
        @Valid @RequestBody req: FillIncubatorRequest) {
        log.info("PUT /incubators/$incubatorId/fill - eggId=${req.eggId}, incubationTime=${req.incubationTime}")
        service.fillIncubator(incubatorId, req.eggId, req.incubationTime, heroName)
    }
}