package fr.ebiron.septunneuf.eggs.controllers

import fr.ebiron.septunneuf.eggs.dto.GenerateEggRequest
import fr.ebiron.septunneuf.eggs.dto.IdResponse
import fr.ebiron.septunneuf.eggs.dto.IdsResponse
import fr.ebiron.septunneuf.eggs.models.Egg
import fr.ebiron.septunneuf.eggs.services.EggService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/eggs")
class EggController(private val eggService: EggService) {

    @PostMapping("generate")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun generateEgg(@RequestBody @Valid req: GenerateEggRequest?): IdsResponse {
        val eggs = eggService.generateEgg(req?.quantity ?: 1)
        return IdsResponse(eggs.map { it.id })
    }

    @GetMapping("{eggId}/details")
    @ResponseBody
    fun getEggById(@PathVariable eggId: Long): Egg {
        return eggService.getEggById(eggId)
    }

    @DeleteMapping("{eggId}/remove")
    @ResponseBody
    fun deleteEggById(@PathVariable eggId: Long): IdResponse {
        return IdResponse(eggService.deleteEggById(eggId).id)
    }
}