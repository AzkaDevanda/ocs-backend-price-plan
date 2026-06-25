package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.request.accountConfig.BillingCycleListDto;
import com.ocs.portal.dto.request.accountConfig.BillingCycleTypeDto;
import com.ocs.portal.dto.request.accountConfig.CustTypeDto;
import com.ocs.portal.dto.response.accountconfig.BillingCycleDto;
import com.ocs.portal.entity.BillingCycle;
import com.ocs.portal.projection.acct.BillingCycleListProjection;
import com.ocs.portal.projection.acct.BillingCycleTypeListProjection;
import com.ocs.portal.projection.acct.CustTypeProjection;
import com.ocs.portal.projection.balanceAdjustment.SelectCurBillingCycleIDByAcctIdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillingCycleMapper {
  static BillingCycle toEntity (BillingCycleDto dto) {
    BillingCycle entity = new BillingCycle();
    entity.setBillingCycleId(dto.getBillingCycleId());
    entity.setBillingCycleTypeId(dto.getBillingCycleTypeId());
    entity.setCycleBeginDate(dto.getCycleBeginDate());
    entity.setCycleEndDate(dto.getCycleEndDate());
    entity.setDebtDate(dto.getDebtDate());
    entity.setState(dto.getState());
    entity.setSpId(dto.getSpId());
    entity.setRunDate(dto.getRunDate());
    // posting_date, invoice_date, origin_date bisa di-set default ke cycleEndDate (sesuai legacy)
    entity.setPostingDate(dto.getCycleEndDate());
    entity.setInvoiceDate(dto.getCycleEndDate());
    entity.setOriginDate(dto.getCycleEndDate());
    return entity;
  }

  BillingCycleListDto dto (BillingCycleListProjection projection);

  BillingCycleTypeDto dto2 (BillingCycleTypeListProjection projection2);

  CustTypeDto dto3 (CustTypeProjection projection3);

  BillingCycleDto selectCurBillingCycleIDByAcctIdToDto (SelectCurBillingCycleIDByAcctIdProjection projection);
}
