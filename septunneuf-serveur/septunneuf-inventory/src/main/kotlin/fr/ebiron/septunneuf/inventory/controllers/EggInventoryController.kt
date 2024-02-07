package fr.ebiron.septunneuf.inventory.controllers

import fr.ebiron.septunneuf.inventory.dto.EggIdsResponse
import fr.ebiron.septunneuf.inventory.services.EggInventoryService
import jakarta.validation.constraints.NotBlank
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inventory/eggs")
class EggInventoryController(private val service: EggInventoryService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseBody
    fun getEggsInInventory(
        @RequestHeader(required = true, name = "heroName")
        @NotBlank
        heroName: String
    ): EggIdsResponse {
        log.info("GET /inventory/eggs - heroName='$heroName'")
        val eggInventory = service.getEggInventory(heroName)
        return EggIdsResponse(eggInventory.eggIds.toList())
    }
}