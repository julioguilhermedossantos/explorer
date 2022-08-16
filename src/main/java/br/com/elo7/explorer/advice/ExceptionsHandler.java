package br.com.elo7.explorer.advice;

import br.com.elo7.explorer.advice.excepion.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ConstraintValidationMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("[ExceptionsHandler] MethodArgumentNotValidException");
        var errors = new HashMap<String, String>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage())
                );
        return ResponseEntity.badRequest().body(new ConstraintValidationMessage(HttpStatus.BAD_REQUEST, errors));
    }

    @ExceptionHandler(value = {CollisionExpection.class})
    public ResponseEntity<ErrorMessage> handleCollision(CollisionExpection ex) {
        var exceptionMessage = ex.getMessage();
        log.error("[ExceptionsHandler] CollisionExpection: {}", exceptionMessage);
        return ResponseEntity.unprocessableEntity()
                .body(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, exceptionMessage));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {ProbeNotFoundException.class})
    public ErrorMessage handleProbeNotFound(ProbeNotFoundException ex) {
        var exceptionMessage = ex.getMessage();
        log.error("[ExceptionsHandler] ProbeNotFoundException: {}", exceptionMessage);
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = {OrbitalLimitExceededException.class})
    public ResponseEntity<ErrorMessage> handleOrbitalLimitExceeded(OrbitalLimitExceededException ex) {
        var exceptionMessage = ex.getMessage();
        log.error("[ExceptionsHandler] OrbitalLimitExceededException: {}", exceptionMessage);
        return ResponseEntity.unprocessableEntity()
                .body(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()));
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        var exceptionMessage = Objects.requireNonNull(ex.getMessage());

        if(exceptionMessage.contains("br.com.elo7.explorer.probe.enums.AllowedActions")){
            return ResponseEntity.unprocessableEntity()
                    .body(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, "Instrução válida!"));
        }

        log.error("[ExceptionsHandler] HttpMessageNotReadableException: {}", exceptionMessage);
        return ResponseEntity.unprocessableEntity()
                .body(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {UnknownPlanetException.class})
    public ErrorMessage handleUnknownPlanet(UnknownPlanetException ex) {
        var exceptionMessage = ex.getMessage();
        log.error("[ExceptionsHandler] UnknownPlanetException: {}", exceptionMessage);
        return new ErrorMessage(HttpStatus.NOT_FOUND, exceptionMessage);
    }

    @ExceptionHandler(value = {InvalidActionException.class})
    public ResponseEntity<ErrorMessage> handleInvalidAction(InvalidActionException ex) {
        var exceptionMessage = ex.getMessage();
        log.error("[ExceptionsHandler] InvalidActionException: {}", exceptionMessage);
        return ResponseEntity.unprocessableEntity()
                .body(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, exceptionMessage));
    }

}
