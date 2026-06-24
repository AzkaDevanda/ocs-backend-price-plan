package com.sts.sinorita.priceplan;

import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.re.GetSubscriptionEvent;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.repository.ReRepository;
import com.sts.sinorita.validation.ValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubscriptionEventService {

    @Autowired
    private ReRepository reRepository;

    public BaseResponseDto getAllSubscriptionEvent(Character reType) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        List<Object[]> list = reRepository.getEvent(reType);

        if (list.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        List<GetSubscriptionEvent> responseList = list.stream().map(obj -> {
            GetSubscriptionEvent response = new GetSubscriptionEvent();
            response.setId((Integer) obj[0]);
            response.setReName((String) obj[1]);
            response.setReType((Character) obj[2]);
            return response;
        }).toList();

        baseResponseDto.setData(responseList);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

        return baseResponseDto;
    }

}
