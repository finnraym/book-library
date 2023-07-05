package ru.egorov.booklibrary.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private String message;
    private Map<String, String> exceptions;

    public ExceptionResponse(String message) {
        this.message = message;
    }
}
