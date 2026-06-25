package com.ocs.portal.priceplan;

import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.re.GetSubscriptionEvent;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.repository.ReRepository;
import com.ocs.portal.validation.ValidationHandler;
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
