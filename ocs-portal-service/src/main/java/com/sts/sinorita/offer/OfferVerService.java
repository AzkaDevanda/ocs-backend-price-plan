package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.offerver.OfferVerListResponse;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.mapper.offer.OfferVerMapper;
import com.sts.sinorita.mapper.pricePlan.PricePlanVerByPricePlanIdMapper;
import com.sts.sinorita.repository.OfferRepository;
import com.sts.sinorita.repository.OfferVerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferVerService {

    // =====> Repository <=====
    @Autowired
    OfferVerRepository offerVerRepository;

    @Autowired
    OfferRepository offerRepository;

    // =====> Mapper <=====
    @Autowired
    OfferVerMapper offerVerMapper;

    @Autowired
    private PricePlanVerByPricePlanIdMapper pricePlanVerByPricePlanIdMapper;

    public BaseResponseDto doGetOfferVerList() {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        List<OfferVerListResponse> list = offerVerRepository.findOfferVerListAndOffer();
        baseResponseDto.setCode(EnumRC.DATA_NOT_FOUND.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.DATA_NOT_FOUND.getMessage());

        if (!list.isEmpty()) {
            baseResponseDto.setData(list);
            baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
            baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        }

        return baseResponseDto;
    }


    public ResponseEntity<CustomeResponse> getOfferVerByOfferId(Integer offerId, Integer onlyValid, Integer seq, Integer spId) {
        var data = offerVerRepository.findOfferVerByOfferId(offerId, onlyValid, seq, spId).map(offerVerMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> getPricePlanVerByPricePlanId(Integer offerId, Integer spId) {
        var data = offerVerRepository.qryPricePlanVerListByPricePlanId(offerId, spId)
                .stream()
                .map(pricePlanVerByPricePlanIdMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

}
