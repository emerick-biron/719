package fr.ebiron.septunneuf.incubators.handlers

import fr.ebiron.septunneuf.incubators.dto.HttpExceptionDto
import fr.ebiron.septunneuf.incubators.exceptions.EggAlreadyInIncubatorException
import fr.ebiron.septunneuf.incubators.exceptions.NotFoundException
import fr.ebiron.septunneuf.incubators.exceptions.TooManyIncubatorException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(TooManyIncubatorException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleTooManyIncubatorException(e: TooManyIncubatorException): HttpExceptionDto {
        log.error("TooManyIncubatorException: ${e.message}")
        return HttpExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.reasonPhrase, e.message)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleNotFoundException(e: NotFoundException): HttpExceptionDto {
        log.error("NotFoundException: ${e.message}")
        return HttpExceptionDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.reasonPhrase, e.message)
    }

    @ExceptionHandler(EggAlreadyInIncubatorException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    fun handleEggAlreadyInIncubatorException(e: EggAlreadyInIncubatorException): HttpExceptionDto {
        log.error("EggAlreadyInIncubatorException: ${e.message}")
        return HttpExceptionDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.reasonPhrase, e.message)
    }
}