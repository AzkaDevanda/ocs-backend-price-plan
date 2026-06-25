package com.ocs.portal.validation;

public class ValidationHandler extends RuntimeException {
    public ValidationHandler(String message){
        super(message);
    }
}
