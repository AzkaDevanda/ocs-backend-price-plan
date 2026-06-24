package com.sts.sinorita.priceplan;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.constant.SortDirection;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.pricePlan.priceplanpackage.PricePlanPackageMapper;
import com.sts.sinorita.repository.PricePlanPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PricePlanPackageService {

    @Autowired
    private PricePlanPackageRepository pricePlanPackageRepository;

    @Autowired
    private PricePlanPackageMapper pricePlanPackageMapper;


    public ResponseEntity<CustomeResponse> getPricePlanJoinPackage(Integer pricePlanId, Integer spId, Integer paging, Integer size, String order_field, SortDirection order_direction) {
        int page = Math.max(paging - 1, 0);
        Sort sort = Sort.by(Sort.Direction.fromString(order_direction.toString()), order_field);
        Pageable pageable = PageRequest.of(page, size, sort);

        var data = pricePlanPackageRepository.qryPricePlanJoinPackage(pricePlanId, spId, pageable);

        var result = data.getContent().stream().map(pricePlanPackageMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));

    }

}
