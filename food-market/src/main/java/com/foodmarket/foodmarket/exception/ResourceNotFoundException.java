package com.foodmarket.foodmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public final String resourceName;
    public final String fieldName;
    public final Long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue){
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName =resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
