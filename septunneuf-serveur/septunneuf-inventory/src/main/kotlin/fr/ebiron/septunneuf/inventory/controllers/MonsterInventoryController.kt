package fr.ebiron.septunneuf.inventory.controllers

import fr.ebiron.septunneuf.inventory.dto.EggIdsResponse
import fr.ebiron.septunneuf.inventory.dto.MonsterIdsResponse
import fr.ebiron.septunneuf.inventory.services.MonsterInventoryService
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inventory/monsters")
class MonsterInventoryController(private val service: MonsterInventoryService) {

    @PostMapping("/{monsterId}/store")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun storeMonster(
        @RequestHeader(required = true, name = "heroName")
        @NotBlank
        heroName: String,
        @PathVariable monsterId: Long
    ) {
        service.storeMonster(heroName, monsterId)
    }

    @PostMapping("/{monsterId}/release")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun releaseMonster(
        @RequestHeader(required = true, name = "heroName")
        @NotBlank
        heroName: String,
        @PathVariable monsterId: Long
    ) {
        service.releaseMonster(heroName, monsterId)
    }

    @GetMapping
    @ResponseBody
    fun getMonstersInInventory(
        @RequestHeader(required = true, name = "heroName")
        @NotBlank
        heroName: String
    ): MonsterIdsResponse {
        val monsterInventory = service.getMonsterInventory(heroName)
        return MonsterIdsResponse(monsterInventory.monsterIds.toList())
    }
}