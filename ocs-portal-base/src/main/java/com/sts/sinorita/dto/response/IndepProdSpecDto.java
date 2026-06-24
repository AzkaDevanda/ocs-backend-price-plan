package com.sts.sinorita.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndepProdSpecDto {
    private Long IndepProdSpecId;

    private Long BrandPricePlanId;

    private Integer ServType;

    private Character PaidFlag;

    private Character NetworkType;

    private Character ServPaidFlag;

    private Long OfferId;

    private String OfferName;

    private String OfferCode;

    private Character SaleListPrice;

    private Character RentListPrice;

    private LocalDate EffDate;

    private LocalDate ExpDate;

    private LocalDate CreatedDate;

    private Character State;

    private LocalDate StateDate;

    private String EffType;

    private Character AutoContinueFlag;

    private Integer CycleQuantity;

    private String TimeUnit;

    private Character DuplicateFlag;

    private String Comments;

    private String ProdType;

    private String LifecycleType;
}

