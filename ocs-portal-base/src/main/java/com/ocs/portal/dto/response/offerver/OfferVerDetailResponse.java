package com.ocs.portal.dto.response.offerver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferVerDetailResponse {
    private Integer offerVerId;
    private LocalDate effDate;
    private LocalDate expDate;
}
