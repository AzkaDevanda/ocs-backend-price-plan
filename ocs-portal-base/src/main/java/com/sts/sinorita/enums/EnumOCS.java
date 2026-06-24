package com.sts.sinorita.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumOCS {

    DATA_NOT_FOUND("DATA_NOT_FOUND", "DATA_NOT_FOUND", "S-PRD-00003"),
    ALREADY_EXIST("ALREADY_EXIST", "ALREADY EXIST", "S-PRD-00003"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "INTERNAL SERVER ERROR", "500"),
    OFFER_VER_EXIST("OFFER_VER_EXIST", "OFFER VERSION ALREADY EXIST", "S-PRD-01008"),
    RE_PRICE_PLAN_IS_REFERENCED("RE_PRICE_PLAN_IS_REFERENCED", "RE PRICE PLAN IS REFERENCED", "S-PRD-01024"),
    DP_IS_REFERENCED("DP_IS_REFERENCED", "DP IS REFERENCED", "S-PRD-01025"),
    ACM_TRIGGER_IS_REFERENCED("ACM_TRIGGER_IS_REFERENCED", "ACM TRIGGER IS REFERENCED", "S-PRD-01027"),
    BAL_TRIGGER_IS_REFERENCED("BAL_TRIGGER_IS_REFERENCED", "BAL TRIGGER IS REFERENCED", "S-PRD-01028"),
    OFFER_VER_IS_REFERENCED("OFFER_VER_IS_REFERENCED", "OFFER VER IS REFERENCED", "S-PRD-51105"),
    PRIORITY_EQUALS("PRIORITY_EQUALS", "OLD PRIORITY IS EQUALS TO NEW PRIORITY", "S-RES-70005"),
    SUBS_UPP_INST_REFERENCED("SUBS_UPP_INST_REF", "SUBS_UPP_INST REFERENCED", "S-PRD-01014"),
    UNKNOWNEXCEPTION("UNKNOWN_EXCEPTION", "UNKNOWN EXCEPTION", "S-PRD-01052"),
    QUERY_ERROR("QUERY_ERROR", "QUERY ERROR", "S-DAT-00003");

    private final String key;
    private final String message;
    private final String RESPONSE_CODE;

    public static EnumOCS getByMessage(String message) {
        for (EnumOCS errorCode : values()) {
            if (errorCode.getMessage().equals(message)) {
                return errorCode;
            }
        }
        return EnumOCS.getByMessage(INTERNAL_SERVER_ERROR.getMessage()); // Or you can throw an exception if not found
    }
}
