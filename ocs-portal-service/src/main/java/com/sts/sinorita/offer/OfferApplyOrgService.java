package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferApplyOrgMapper;
import com.sts.sinorita.repository.offer.OfferApplyOrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OfferApplyOrgService {

    @Autowired
    private OfferApplyOrgRepository offerApplyOrgRepository;

    @Autowired
    private OfferApplyOrgMapper offerApplyOrgMapper;

    public ResponseEntity<CustomeResponse> getListOfferApplyOrg(Integer offerId, Integer spId) {
        var data = offerApplyOrgRepository.qryOfferApplyOrg(offerId, spId)
                .stream()
                .map(offerApplyOrgMapper::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

}
