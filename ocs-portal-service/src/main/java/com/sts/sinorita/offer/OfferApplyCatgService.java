package com.sts.sinorita.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferApplyCatgMapper;
import com.sts.sinorita.repository.offer.OfferApplyCatgRepository;
import com.sts.sinorita.constant.HttpStatusConstant;

@Service
public class OfferApplyCatgService {
  @Autowired
  OfferApplyCatgRepository offerApplyCatgRepository;

  @Autowired
  OfferApplyCatgMapper offerApplyCatgMapper;

  public ResponseEntity<CustomeResponse> getOfferApplyCategory(Integer offerId, Integer spId) {

    Character state = 'A';
    var data = offerApplyCatgRepository.findOfferApplyCategory(state, offerId, spId)
        .map(offerApplyCatgMapper::toDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }
}
