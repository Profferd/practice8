package com.intern.controller;

import com.intern.dto.RestResponse;
import com.intern.exception.EmptyFileException;
import com.intern.exception.InvalidFileTypeException;
import com.intern.exception.InvalidJsonException;
import com.intern.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors().forEach(e -> errorMessage
                .append(e.getField())
                .append(" - ")
                .append(e.getDefaultMessage() == null ? e.getCode() : e.getDefaultMessage())
                .append("; "));
        throw new ValidationException(errorMessage.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private RestResponse handleException(ValidationException e) {
        return new RestResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private RestResponse handleException(InvalidJsonException ignoredE) {
        return new RestResponse("File reading failure: Invalid JSON");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private RestResponse handleException(InvalidFileTypeException ignoredE) {
        return new RestResponse("File reading failure: Acceptable types: .zip, .json");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private RestResponse handleException(EmptyFileException ignoredE) {
        return new RestResponse("File reading failure: Unable to locate");
    }
}
