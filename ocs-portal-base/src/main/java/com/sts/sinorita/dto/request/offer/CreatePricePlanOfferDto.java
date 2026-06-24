package com.sts.sinorita.dto.request.offer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePricePlanOfferDto {
	private Integer staffId;

	@NotNull
	private Long offerCatgId;

	// OFFER_NAME
	@NotNull
	private String pricePlanName;

	// OFFER_TYPE
	@NotNull
	private Character offerType;

	// OFFER_CODE
	@NotNull
	private String pricePlanCode;

	// SERV_TYPE
	private Integer serviceType;

	// APPLY_LEVEL
	@NotNull
	private Character applyLevel;

	// EFF_DATE
	@NotNull
	private LocalDate effDate;

	// EXP_DATE
	private LocalDate expDate;

	// PRICE_PLAN_TYPE
	private Character pricePlanType;

	// PACKAGE_FLAG
	private Character isPackage;

	// GROUP_TYPE
	private Character packageMode;

	// LOWER_LIMIT
	private Integer lowerLimit;

	// UPPER_LIMIT
	private Integer upperLimit;

	// SALE_LIST_PRICE
	private Long salePrice;

	// RENT_LIST_PRICE
	private Long rentPrice;

	// EFF_TYPE
	@NotNull
	private String effType;

	// AGREEMENT PERIOD
	// CYCLE_QUANTITY
	private Integer cycleTimeLimitL;
	// TIME_UNIT
	private Character cycleTimeLimitR;

	// ORDER TIME LIMIT
	// EXP_OFF
	private Integer agreementPeriodL;
	// EXP_TIME_UNIT
	private Character agreementPeriodR;

	// AUTO_CONTINUE_FLAG
	private Character automaticRenewal;

	// DUPLICATE_FLAG
	private Character duplicateOrder;

	// AGREEMENT_EFF_TYPE
	private Character agreementEffType;

	// COMMENT
	private String remarks;
}
