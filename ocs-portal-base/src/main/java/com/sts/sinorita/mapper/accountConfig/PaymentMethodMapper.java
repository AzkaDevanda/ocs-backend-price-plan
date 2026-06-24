package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.request.accountConfig.PaymentMethodDto;
import com.sts.sinorita.dto.request.accountConfig.PaymentTypeDto;
import com.sts.sinorita.projection.acct.PaymentMethodProjection;
import com.sts.sinorita.projection.acct.PaymentTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethodDto dto(PaymentMethodProjection projection);
    PaymentTypeDto dto2(PaymentTypeProjection projection2);
}
