package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcctResRequestDto {

    private String balanceTypeName;
    private Integer balanceCatalog;
    private String stdCode;
    private Character isCurrency;
    private Character isFreeUnit;
    private Character paymentForce;
    private Character balanceAggregation;
    private Character rewardFlag;
    private Character unlimitedFlag;
    private Character rolloverFlag;
    private String freeFlag;
    private Character balCategory;
    private Character isOverdraft;
//    private Character isCustomer;
    private Character clearFlag;

}
