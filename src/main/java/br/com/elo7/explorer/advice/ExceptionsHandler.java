package br.com.elo7.explorer.advice;

import br.com.elo7.explorer.advice.excepion.CollisionExpection;
import br.com.elo7.explorer.advice.excepion.OrbitalLimitExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.HashMap;

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
        log.error("[ExceptionsHandler] CollisionExpection");
        return ResponseEntity.badRequest().body(new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {OrbitalLimitExceededException.class})
    public ResponseEntity<ErrorMessage> handleOrbitalLimitExceeded(OrbitalLimitExceededException ex) {
        log.error("[ExceptionsHandler] OrbitalLimitExceededException");
        return ResponseEntity.badRequest().body(new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("[ExceptionsHandler] HttpMessageNotReadableException");
        return ResponseEntity.badRequest().body(new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}
