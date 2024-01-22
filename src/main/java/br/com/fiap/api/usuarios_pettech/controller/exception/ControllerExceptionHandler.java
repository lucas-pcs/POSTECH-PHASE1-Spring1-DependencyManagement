package br.com.fiap.api.usuarios_pettech.controller.exception;

import br.com.fiap.api.usuarios_pettech.entities.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    private StandardError err = new StandardError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ControllerNotFoundException e, HttpServletRequest request){
        err.setError("Entity Not Found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        HttpStatus status = HttpStatus.NOT_FOUND;
        err.setStatus(status.value());
        err.setTimestamp(Instant.now());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
        ValidateError validateError = new ValidateError();

        validateError.setError("Erro de validação");
        validateError.setMessage(e.getMessage());
        validateError.setPath(request.getRequestURI());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        validateError.setStatus(status.value());
        validateError.setTimestamp(Instant.now());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            validateError.addMessages(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(validateError);
    }

}
