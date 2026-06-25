package com.ocs.portal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumRC {

    SUCCESS("SUCCESS","SUCCESS",200),
    CREATE_FAILED("FAILED","CREATE FAILED",500),
    CREATE_SUCCESS("CREATE_SUCCESS","CREATED",201),
    BAD_REQUEST("BAD_REQUEST","BAD REQUEST",401),
    NOT_FOUND("NOT_FOUND","NOT FOUND",404),
    DATA_NOT_FOUND("DATA_NOT_FOUND","DATA_NOT_FOUND",404),
    ALREADY_EXIST("ALREADY_EXIST","ALREADY EXIST",409),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR","INTERNAL ERROR", 500),
    JWT_EXPIRED("JWT","JWT",403);




    private final String key;
    private final String message;
    private final Integer RESPONSE_CODE;


    public static EnumRC getByMessage(String message) {
        for (EnumRC errorCode : values()) {
            if (errorCode.getMessage().equalsIgnoreCase(message)) {
                return errorCode;
            }
        }
        return EnumRC.getByMessage(INTERNAL_SERVER_ERROR.getMessage());  // Or you can throw an exception if not found
    }

}
