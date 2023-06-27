package ru.egorov.booklibrary.web.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.egorov.booklibrary.exception.DataAlreadyExistsException;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.exception.ExceptionResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MainControllerExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleDataNotFoundException(DataNotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleDataAlreadyExistsException(DataAlreadyExistsException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlePropertyReferenceException(PropertyReferenceException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleIllegalStateException(IllegalStateException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ExceptionResponse response = new ExceptionResponse("validation failed.");
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        response.setExceptions(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));

        return response;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex) {
        ExceptionResponse response = new ExceptionResponse("validation failed.");

        response.setExceptions(ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage)));

        return response;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleOtherExceptions(Exception ex) {
        return new ExceptionResponse(ex.getMessage());
    }

}
