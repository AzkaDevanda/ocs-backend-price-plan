package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookDto;
import com.sts.sinorita.dto.response.balanceAdjustment.RechargeRecentDto;
import com.sts.sinorita.projection.balanceAdjustment.FindAcctBookByPartnerSnProjection;
import com.sts.sinorita.projection.balanceAdjustment.QryRechargePaymentInfoProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectLastRecentValidAcctBookProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AcctBookMapper {
  @Mapping(target = "acctBookId", ignore = true)
  @Mapping(target = "acctBookType", ignore = true)
  @Mapping(target = "acctResId", ignore = true)
  @Mapping(target = "preBalance", ignore = true)
  @Mapping(target = "charge", ignore = true)
  @Mapping(target = "preExpDate", ignore = true)
  @Mapping(target = "seconds", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  RechargeRecentDto toRechargeRecentDto (QryRechargePaymentInfoProjection projection);

  @Mapping(target = "refAttr", ignore = true)
  @Mapping(target = "orgId", ignore = true)
  @Mapping(target = "glCode", ignore = true)
  @Mapping(target = "partId", ignore = true)
  @Mapping(target = "eventPaymentData", ignore = true)
  @Mapping(target = "modBal", ignore = true)
  @Mapping(target = "reconcileDto", ignore = true)
  @Mapping(target = "preEffDate", ignore = true)
  @Mapping(target = "effSeconds", ignore = true)
  @Mapping(target = "benefitSecondsAcctBookDto", ignore = true)
  @Mapping(target = "reCcInstDataList", ignore = true)
  AcctBookData toAcctBookData (SelectLastRecentValidAcctBookProjection projection);

  @Mapping(target = "eventPaymentId", ignore = true)
  @Mapping(target = "glCode", ignore = true)
  @Mapping(target = "partId", ignore = true)
  @Mapping(target = "preEffDate", ignore = true)
  @Mapping(target = "effSeconds", ignore = true)
  @Mapping(target = "refAttr", ignore = true)
  @Mapping(target = "eventInstId", ignore = true)
  AcctBookDto toAcctBookDtoForFindAcctBookByPartnerSn(FindAcctBookByPartnerSnProjection projection);


}
