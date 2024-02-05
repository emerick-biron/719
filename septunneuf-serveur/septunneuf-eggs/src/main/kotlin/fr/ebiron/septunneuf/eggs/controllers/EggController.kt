package fr.ebiron.septunneuf.eggs.controllers

import fr.ebiron.septunneuf.eggs.dto.GenerateEggResponse
import fr.ebiron.septunneuf.eggs.models.Egg
import fr.ebiron.septunneuf.eggs.services.EggService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/eggs")
class EggController(private val eggService: EggService) {

    @PostMapping("generate")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun createEgg(): GenerateEggResponse {
        return GenerateEggResponse(eggService.generateEgg().id)
    }

    @GetMapping("{eggId}/details")
    @ResponseBody
    fun getEggById(@PathVariable eggId: Long): Egg {
        return eggService.getEggById(eggId)
    }
}