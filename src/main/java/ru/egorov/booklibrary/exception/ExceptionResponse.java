package ru.egorov.booklibrary.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private Map<String, String> exceptions;

    public ExceptionResponse(String message) {
        this.message = message;
    }
}
