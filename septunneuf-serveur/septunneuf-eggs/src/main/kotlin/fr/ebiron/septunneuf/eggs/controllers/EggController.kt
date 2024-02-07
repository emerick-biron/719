package fr.ebiron.septunneuf.eggs.controllers

import fr.ebiron.septunneuf.eggs.dto.GenerateEggRequest
import fr.ebiron.septunneuf.eggs.dto.GenerateEggResponse
import fr.ebiron.septunneuf.eggs.models.Egg
import fr.ebiron.septunneuf.eggs.services.EggService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/eggs")
class EggController(private val eggService: EggService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("generate")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun generateEgg(@RequestBody @Valid req: GenerateEggRequest?): GenerateEggResponse {
        log.info("GET /eggs/generate - quantity=${req?.quantity ?: 1}")
        val eggs = eggService.generateEgg(req?.quantity ?: 1)
        return GenerateEggResponse(eggs.map { it.id })
    }

    @GetMapping("{eggId}/details")
    @ResponseBody
    fun getEggById(@PathVariable eggId: Long): Egg {
        log.info("GET /eggs/$eggId/details")
        return eggService.getEggById(eggId)
    }
}