package br.com.elo7.explorer.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

    private HttpStatus status;
    private Map<String, String> errors;
}
