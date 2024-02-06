package fr.ebiron.septunneuf.inventory.controllers

import fr.ebiron.septunneuf.inventory.dto.EggIdsResponse
import fr.ebiron.septunneuf.inventory.services.EggInventoryService
import jakarta.validation.constraints.NotBlank
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inventory/eggs")
class EggInventoryController(private val service: EggInventoryService) {

    @GetMapping
    @ResponseBody
    fun getEggsInInventory(
        @RequestHeader(required = true, name = "heroName")
        @NotBlank
        heroName: String
    ): EggIdsResponse {
        val eggInventory = service.getEggsInInventory(heroName)
        return EggIdsResponse(eggInventory.eggIds.toList())
    }
}