package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.request.accountConfig.PaymentMethodDto;
import com.ocs.portal.dto.request.accountConfig.PaymentTypeDto;
import com.ocs.portal.projection.acct.PaymentMethodProjection;
import com.ocs.portal.projection.acct.PaymentTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethodDto dto(PaymentMethodProjection projection);
    PaymentTypeDto dto2(PaymentTypeProjection projection2);
}
