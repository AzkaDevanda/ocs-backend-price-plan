package com.sts.sinorita.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferApplyAreaMapper;
import com.sts.sinorita.repository.offer.OfferApplyAreaRepository;

@Service
public class OfferApplyAreaService {
  // ==== Repository
  @Autowired
  OfferApplyAreaRepository offerApplyAreaRepository;

  // ==== Mapper
  @Autowired
  OfferApplyAreaMapper offerApplyAreaMapper;

  public ResponseEntity<CustomeResponse> getOfferApplyArea(Integer offerId, Integer spId) {
    System.out.println("offerId: " + offerId + " spId : " + spId);
    var data = offerApplyAreaRepository.findOfferApplyArea(offerId, spId)
        .map(offerApplyAreaMapper::toOfferApplyAreaResponseDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }
}
