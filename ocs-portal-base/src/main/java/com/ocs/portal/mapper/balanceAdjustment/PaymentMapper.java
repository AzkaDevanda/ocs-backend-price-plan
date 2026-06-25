package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.PaymentDto;
import com.ocs.portal.projection.balanceAdjustment.SelectPaymentProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
  PaymentDto toPaymentDto (SelectPaymentProjection projection);
}
