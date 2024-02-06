package fr.ebiron.septunneuf.inventory.handlers

import fr.ebiron.septunneuf.inventory.dto.HttpExceptionDto
import fr.ebiron.septunneuf.inventory.exceptions.AlreadyInInventoryException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

}