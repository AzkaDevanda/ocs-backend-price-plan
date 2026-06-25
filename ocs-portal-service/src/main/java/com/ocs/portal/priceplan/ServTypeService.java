package com.ocs.portal.priceplan;

import com.ocs.portal.constant.HttpStatusConstant;
import com.ocs.portal.dto.request.AllServTypeDto;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.mapper.pricePlan.price.QryServTypeMapper;
import com.ocs.portal.repository.AllServTypeRepository;
import com.ocs.portal.validation.ValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServTypeService {

    @Autowired
    private AllServTypeRepository allServTypeRepository;

    @Autowired
    private QryServTypeMapper qryServTypeMapper;

    public BaseResponseDto getAllServType() {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        List<Object[]> list = allServTypeRepository.getServiceType();

        if (list.isEmpty()) {
            throw new ValidationHandler(EnumRC.DATA_NOT_FOUND.getMessage());
        }

        List<AllServTypeDto> responseList = list.stream().map(obj -> {
            AllServTypeDto response = new AllServTypeDto();
            response.setId((Integer) obj[0]);
            response.setServTypeName((String) obj[1]);
            response.setNetworkType((Character) obj[2]);
            response.setNetworkTypeName((String) obj[3]);
            return response;
        }).toList();

        baseResponseDto.setData(responseList);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

        log.info("Completed getAllServType with response code: {}", baseResponseDto.getCode());

        return baseResponseDto;
    }

    public ResponseEntity<CustomeResponse> getServType(String servTypeName, Integer paging, Integer size) {
        int page = Math.max(paging - 1, 0);
        Pageable pageable = PageRequest.of(page, size);

        var data = allServTypeRepository.qryServType(servTypeName, pageable);

        var result = data.getContent().stream().map(qryServTypeMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));

    }


}
