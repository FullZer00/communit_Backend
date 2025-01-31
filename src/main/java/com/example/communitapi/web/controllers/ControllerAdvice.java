package com.example.communitapi.web.controllers;

import com.example.communitapi.entities.exceptions.AccessDeniedException;
import com.example.communitapi.entities.exceptions.ExceptionBody;
import com.example.communitapi.entities.exceptions.ResourceMappingException;
import com.example.communitapi.entities.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException ex) {
        return new ExceptionBody(ex.getMessage());
    }

    @ExceptionHandler(ResourceMappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleResourceMapping(ResourceMappingException ex) {
        ExceptionBody exceptionBody = new ExceptionBody(ex.getMessage());
        exceptionBody.setErrors(ex.getErrorLocation());
        return exceptionBody;
    }

    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied(){
        return new ExceptionBody("Access denied");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ExceptionBody exceptionBody = new ExceptionBody(ex.getMessage());
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleException(ConstraintViolationException ex) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        exceptionBody.setErrors(ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ))
        );
        return exceptionBody;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleRuntimeException(Exception ex) {
        return new ExceptionBody(ex.getMessage());
    }

//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ExceptionBody handleAuthenticationException(AuthenticationException ex) {
//        ExceptionBody exBody = new ExceptionBody(ex.getMessage());
//        exBody.setErrors(Map.of("Ошибка авторизации:", "Вы не имеете доступа к данному объекту"));
//        return exBody;
//    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleAuthenticationException(Exception ex) {
        return new ExceptionBody("Authentication failed. " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception ex) {
        return new ExceptionBody("Iternal error \n " + ex.getMessage());
    }
}
