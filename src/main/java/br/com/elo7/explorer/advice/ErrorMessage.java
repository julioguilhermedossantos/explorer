package br.com.elo7.explorer.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

    private HttpStatus status;

    private String error;

}
