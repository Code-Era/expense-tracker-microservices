package com.financeservice.exception.common;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public class OperationFailedException extends RuntimeException{

    public OperationFailedException(String message) {
        super(message);
    }
}
