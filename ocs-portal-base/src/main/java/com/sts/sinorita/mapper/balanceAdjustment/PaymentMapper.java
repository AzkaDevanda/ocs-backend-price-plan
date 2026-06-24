package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.PaymentDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectPaymentProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
  PaymentDto toPaymentDto (SelectPaymentProjection projection);
}
