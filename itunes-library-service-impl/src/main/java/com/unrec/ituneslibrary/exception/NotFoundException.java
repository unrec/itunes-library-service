package com.unrec.ituneslibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Class entityClass) {
        this(String.format("%s with %s not found", entityClass.getSimpleName(), message));
    }
}
