package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferRebateMapper;
import com.sts.sinorita.repository.OfferRebateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OfferRebateService {

    @Autowired
    private OfferRebateRepository offerRebateRepository;

    @Autowired
    private OfferRebateMapper offerRebateMapper;


    public ResponseEntity<CustomeResponse> getListOfferRebate(Integer offerId, Integer offerVerId, Integer spId) {
        var data = offerRebateRepository.qryOfferRebate(offerId, offerVerId, spId)
                .stream()
                .map(offerRebateMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

}
