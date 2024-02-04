package com.foodmarket.foodmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Long fieldValue;

    public BadRequestException(String resourceName, String fieldName, Long fieldValue){
        super(String.format("%s not matches with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName =resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
