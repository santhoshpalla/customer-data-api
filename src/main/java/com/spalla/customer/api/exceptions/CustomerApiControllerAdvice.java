package com.spalla.customer.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomerApiControllerAdvice {

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String customerNotFoundHandler(CustomerNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String customerNotFoundHandler(NoSuchElementException ex){
        return "Customer not found with given parameters";
    }

    @ExceptionHandler(CustomerRequestInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String requestValidationErrorHandler(CustomerRequestInvalidException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String emailIdConflictErrorHandler(DataIntegrityViolationException ex){
        return "email id already exists";
    }
}
