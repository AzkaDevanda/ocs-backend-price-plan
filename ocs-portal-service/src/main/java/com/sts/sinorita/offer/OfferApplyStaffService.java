package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferApplyStaffMapper;
import com.sts.sinorita.repository.offer.OfferApplyStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OfferApplyStaffService {

    @Autowired
    private OfferApplyStaffRepository offerApplyStaffRepository;

    @Autowired
    private OfferApplyStaffMapper offerApplyStaffMapper;

    public ResponseEntity<CustomeResponse> getOfferApplyStaff(Integer offerId, Integer spId) {
        var data = offerApplyStaffRepository.qryOfferApplyStaff(offerId, spId)
                .stream()
                .map(offerApplyStaffMapper::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));

    }

}
