package com.ocs.portal.dto.response.offer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OfferApplyStaffResponseDto {

    private Integer offerId;

    private Integer staffId;

    private Integer spId;

    private Character excludeFlag;

    private String userCode;

    private String staffName;

    private Character state;

    private LocalDate sateDate;

    private Integer userId;

}
