package fr.ebiron.septunneuf.inventory.handlers

import fr.ebiron.septunneuf.inventory.dto.HttpExceptionDto
import fr.ebiron.septunneuf.inventory.exceptions.MonsterInventoryNotFound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(MonsterInventoryNotFound::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleNotFoundException(e: MonsterInventoryNotFound): HttpExceptionDto {
        log.error("NotFoundException: ${e.message}")
        return HttpExceptionDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.reasonPhrase, e.message)
    }
}