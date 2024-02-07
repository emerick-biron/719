package fr.ebiron.septunneuf.inventory.controllers

import fr.ebiron.septunneuf.inventory.dto.MonsterIdsResponse
import fr.ebiron.septunneuf.inventory.services.MonsterInventoryService
import jakarta.validation.constraints.NotBlank
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inventory/monsters")
class MonsterInventoryController(private val service: MonsterInventoryService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/{monsterId}/store")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun storeMonster(
        @RequestHeader(required = true, name = "heroName")
        @NotBlank
        heroName: String,
        @PathVariable monsterId: Long
    ) {
        log.info("POST /inventory/monsters/$monsterId/store - heroName='$heroName'")
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
        log.info("POST /inventory/monsters/$monsterId/release - heroName='$heroName'")
        service.releaseMonster(heroName, monsterId)
    }

    @GetMapping
    @ResponseBody
    fun getMonstersInInventory(
        @RequestHeader(required = true, name = "heroName")
        @NotBlank
        heroName: String
    ): MonsterIdsResponse {
        log.info("GET /inventory/monsters - heroName='$heroName'")
        val monsterInventory = service.getMonsterInventory(heroName)
        return MonsterIdsResponse(monsterInventory.monsterIds.toList())
    }
}