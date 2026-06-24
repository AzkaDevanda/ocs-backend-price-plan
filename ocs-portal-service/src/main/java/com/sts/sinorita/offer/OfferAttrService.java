package com.sts.sinorita.offer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.common.PagingRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.offer.OfferAttrMapper;
import com.sts.sinorita.repository.offer.OfferAttrRepository;

@Service
public class OfferAttrService {
  @Autowired
  OfferAttrRepository offerAttrRepository;

  @Autowired
  OfferAttrMapper offerAttrMapper;

  public ResponseEntity<CustomeResponse> getOfferAttr(PagingRequestDto pagingRequest, Integer offerId, Integer spId, List<String> attrCode, List<String> attrName) {
    int page = Math.max(pagingRequest.getPage() - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
    Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

    System.out.println("ini hasil dari pageabel : " + pageable);

    var data = offerAttrRepository.findOfferAttr(offerId, spId, pageable);
    var result = data.getContent().stream().map(offerAttrMapper::toDtoOfferAttr);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }
}
