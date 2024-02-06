package fr.ebiron.septunneuf.shop.handler;


import fr.ebiron.septunneuf.shop.dto.HttpExceptionDto;
import fr.ebiron.septunneuf.shop.exception.*;
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

    @ExceptionHandler(NotEnoughtMoney.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public HttpExceptionDto handleNotEnoughtMoneyException(NotEnoughtMoney e) {
        return new HttpExceptionDto(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(NotOwned.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpExceptionDto handleEggNotOwnedException(NotOwned e) {
        return new HttpExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(TooManyIncubator.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpExceptionDto handleTooManyIncubatorException(TooManyIncubator e) {
        return new HttpExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
    }
}