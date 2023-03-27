package io.vendas.application.controller;

import io.vendas.application.exceptions.CustomerNotFoundException;
import io.vendas.application.exceptions.GenericErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public GenericErrorResponse handleCustomerException(CustomerNotFoundException e) {
        return new GenericErrorResponse(LocalDateTime.now(),404, e.getMessage());
    }

}
