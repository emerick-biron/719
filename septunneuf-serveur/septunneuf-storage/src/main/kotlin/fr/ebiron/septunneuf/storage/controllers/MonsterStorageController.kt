package fr.ebiron.septunneuf.storage.controllers

import fr.ebiron.septunneuf.storage.dto.GetStoredMonsterResponse
import fr.ebiron.septunneuf.storage.services.MonsterStorageService
import jakarta.validation.constraints.NotBlank
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/storage")
class MonsterStorageController(private val service: MonsterStorageService) {

    @GetMapping
    @ResponseBody
    fun getStoredMonsters(
        @RequestHeader(required = true, name = "heroName")
        @NotBlank
        heroName: String
    ): GetStoredMonsterResponse {
        val monsterStorage = service.getStoredMonsters(heroName)
        return GetStoredMonsterResponse(monsterStorage.monsterIds.toList())
    }
}