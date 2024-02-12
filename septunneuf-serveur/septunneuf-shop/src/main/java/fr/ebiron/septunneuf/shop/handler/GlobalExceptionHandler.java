package fr.ebiron.septunneuf.shop.handler;


import fr.ebiron.septunneuf.shop.dto.HttpExceptionDto;
import fr.ebiron.septunneuf.shop.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public HttpExceptionDto handleNotFoundException(NotFoundException e) {
        log.error("NotFoundException: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public HttpExceptionDto handleConflictException(ConflictException e) {
        log.error("ConflictException: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(NotEnoughtMoney.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public HttpExceptionDto handleNotEnoughtMoneyException(NotEnoughtMoney e) {
        log.error("NotEnoughtMoney: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(NotOwned.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpExceptionDto handleEggNotOwnedException(NotOwned e) {
        log.error("NotOwned: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(TooManyIncubator.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpExceptionDto handleTooManyIncubatorException(TooManyIncubator e) {
        log.error("TooManyIncubator: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(EggsGenerationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public HttpExceptionDto handleEggsGenerationException(EggsGenerationException e) {
        log.error("EggsGenerationException: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(GetHeroEggsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public HttpExceptionDto handleGetHeroEggsException(GetHeroEggsException e) {
        log.error("GetHeroEggsException: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(GetHeroMonstersException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public HttpExceptionDto handleGetHeroMonsters(GetHeroMonstersException e) {
        log.error("GetHeroMonstersException: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(GetHeroIncubatorsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public HttpExceptionDto handleGetHeroIncubatorsException(GetHeroIncubatorsException e) {
        log.error("GetHeroIncubatorsException: {}", e.getMessage());
        return new HttpExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
    }
}