package com.ocs.portal.dto.response.offerver;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferVerListResponse {
    private Integer offerId;
    private Integer offerVerId;
    private String offerName;
    private String offerCode;
    private LocalDate effDate;
    private LocalDate expDate;
}
