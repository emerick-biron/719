package fr.ebiron.septunneuf.heroes.handler;

import fr.ebiron.septunneuf.heroes.dto.HttpExceptionDto;
import fr.ebiron.septunneuf.heroes.exception.ConflictException;
import fr.ebiron.septunneuf.heroes.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public HttpExceptionDto handleNotFoundException(NotFoundException e) {
        return new HttpExceptionDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public HttpExceptionDto handleConflictException(ConflictException e) {
        return new HttpExceptionDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage());
    }
}