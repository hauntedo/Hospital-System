package ru.itis.hauntedo.simbirtest.controller.handler;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.hauntedo.simbirtest.dto.response.ExceptionResponse;
import ru.itis.hauntedo.simbirtest.exception.GlobalServiceException;
import ru.itis.hauntedo.simbirtest.exception.unauthorized.AuthenticationHeaderException;
import ru.itis.hauntedo.simbirtest.validation.ValidationError;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> onAllExceptions(Exception exception) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .message(exception.getMessage())
                        .exceptionName(exception.getClass().getSimpleName())
                        .build());

    }

    @ExceptionHandler({AuthenticationException.class, AuthenticationHeaderException.class})
    public final ResponseEntity<ExceptionResponse> onAuthenticationExceptions(AuthenticationException authenticationException) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .message(authenticationException.getMessage())
                        .exceptionName(authenticationException.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(GlobalServiceException.class)
    public final ResponseEntity<ExceptionResponse> onGlobalException(GlobalServiceException globalServiceException) {
        return ResponseEntity
                .status(globalServiceException.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .message(globalServiceException.getMessage())
                                .exceptionName(globalServiceException.getClass().getSimpleName())
                                .build());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public final ResponseEntity<ExceptionResponse> handleIllegalException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .message(exception.getMessage())
                                .exceptionName(exception.getClass().getSimpleName())
                                .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String ,String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }
        ValidationError error =
                new ValidationError(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(
                ex, error, headers, error.getStatus(), request);
    }

    @ExceptionHandler(JwtException.class)
    public final ResponseEntity<ExceptionResponse> onJwtExceptions(JwtException jwtException) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .message(jwtException.getMessage())
                        .exceptionName(jwtException.getClass().getSimpleName())
                        .build());
    }
}
