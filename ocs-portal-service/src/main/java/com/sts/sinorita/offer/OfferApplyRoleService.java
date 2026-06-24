package com.sts.sinorita.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferApplyRoleMapper;
import com.sts.sinorita.repository.offer.OfferApplyRoleRepository;

@Service
public class OfferApplyRoleService {
  // ==== Repository
  @Autowired
  OfferApplyRoleRepository offerApplyRoleRepository;

  // ==== Mapper
  @Autowired
  OfferApplyRoleMapper offerApplyRoleMapper;

  public ResponseEntity<CustomeResponse> getOfferApplyRoleForFish(Integer offerId, Integer spId) {
    var data = offerApplyRoleRepository.findOfferApplyRoleForFish(offerId, spId)
        .map(offerApplyRoleMapper::toOfferApplyRoleForFishResponseDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }
}
