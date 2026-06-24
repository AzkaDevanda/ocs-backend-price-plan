package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.constant.SortDirection;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferGroupMemByOfferIdMapper;
import com.sts.sinorita.repository.OfferGroupMemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OfferGroupMemService {

    @Autowired
    private OfferGroupMemRepository offerGroupMemRepository;

    @Autowired
    private OfferGroupMemByOfferIdMapper offerGroupMemByOfferIdMapper;

    public ResponseEntity<CustomeResponse> getOfferGroupMemByOfferId(Integer offerId, Integer spId, String order_field, SortDirection order_direction, Integer paging, Integer size) {
        int page = Math.max(paging - 1, 0);
        Sort sort = Sort.by(Sort.Direction.fromString(order_direction.toString()), order_field);
        Pageable pageable = PageRequest.of(page, size, sort);

        var data = offerGroupMemRepository.qryOfferGroupMemByOfferId(offerId, spId, pageable);

        var result = data.getContent().stream().map(offerGroupMemByOfferIdMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));

    }

}
