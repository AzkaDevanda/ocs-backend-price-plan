package com.sts.sinorita.offer;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;

import com.sts.sinorita.dto.request.common.PagingRequestDto;

import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;

import com.sts.sinorita.mapper.offer.OfferSubPlanMapper;
import com.sts.sinorita.projection.offer.OfferSubsPlanProjection;

import com.sts.sinorita.repository.PricePlanRepository;
import com.sts.sinorita.repository.offer.SubsPlanRepository;
import com.sts.sinorita.validation.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OfferSubsPlanService {
    @Autowired
    MessageService messageService;

    @Autowired
    SubsPlanRepository subsPlanRepository;

    @Autowired
    OfferSubPlanMapper offerSubPlanMapper;

    Logger log = LoggerFactory.getLogger(OfferSubsPlanService.class);

    public ResponseEntity<CustomeResponse> getListSubsPlanByOfferGroupId(Integer offerGroupId, Integer spId, PagingRequestDto pagingRequest) {
//        BaseResponseDto baseResponseDto = new BaseResponseDto();

        int page = Math.max(pagingRequest.getPage() - 1, 0);
        if (pagingRequest.getSortBy().equalsIgnoreCase("id")){
            pagingRequest.setSortBy("subsPlanId");
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), new String[]{pagingRequest.getSortBy()});
        Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

        Page<OfferSubsPlanProjection> data  = subsPlanRepository.getListSubsPlanByOfferGroupId(offerGroupId,spId, pageable);
        if (data.isEmpty()){
            throw new ValidationHandler(messageService.getMessage("CC-S-SALES-00433"));
        }
        var result = data.getContent().stream().map(offerSubPlanMapper::toDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
//        baseResponseDto.setMessage(HttpStatusConstant.SUCCESS_MESSAGE);
//        baseResponseDto.setCode("200");
//        baseResponseDto.setData(data);
//        return baseResponseDto;
    }

}
