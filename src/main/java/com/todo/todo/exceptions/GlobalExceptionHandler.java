package com.todo.todo.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Map<String, List<String>> createErrorMap(List<String> errorList) {
        Map<String, List<String>> errorMap = new HashMap<>();
        errorMap.put("errors", errorList);
        return errorMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, List<String>>> handleBindErrors(MethodArgumentNotValidException exception) {
        List<String> errorList = exception.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(createErrorMap(errorList));
    }

}
