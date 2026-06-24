package com.sts.sinorita.validation;

public class ValidationHandler extends RuntimeException {
    public ValidationHandler(String message){
        super(message);
    }
}
