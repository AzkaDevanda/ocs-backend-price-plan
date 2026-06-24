package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferApplyChannelMapper;
import com.sts.sinorita.repository.offer.OfferApplyChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OfferApplyChannelService {

    @Autowired
    private OfferApplyChannelRepository offerApplyChannelRepository;

    @Autowired
    private OfferApplyChannelMapper offerApplyChannelMapper;

    public ResponseEntity<CustomeResponse> getListOfferApplyChannel(Integer offerId, Integer spId) {
        var data = offerApplyChannelRepository.qryOfferApplyChannel(offerId, spId)
                .stream()
                .map(offerApplyChannelMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

}
