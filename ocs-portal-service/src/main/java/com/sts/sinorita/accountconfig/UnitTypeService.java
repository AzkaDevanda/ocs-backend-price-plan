package com.sts.sinorita.accountconfig;

import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.accountconfig.UnitTypeResponse;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.repository.UnitTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UnitTypeService {

    @Autowired
    private UnitTypeRepository unitTypeRepository;

    public BaseResponseDto getAllUnitType() {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        List<Object[]> list = unitTypeRepository.getUnitType();

        List<UnitTypeResponse> responseList = list.stream().map(obj -> {
            UnitTypeResponse response = new UnitTypeResponse();
            response.setUnitTypeId((Integer) obj[0]);
            response.setUnitTypeName((String) obj[1]);
            return response;
        }).toList();

        baseResponseDto.setData(responseList);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

        return baseResponseDto;
    }

}
