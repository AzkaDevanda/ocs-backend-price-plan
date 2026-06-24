package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingBaseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BalHelperExt;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.ConfigItemRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class ValidateBeforeDataStoreService {
  private final Logger log = LoggerFactory.getLogger(AddAdjustBalInitAndValidateService.class);

  private final BillingTransactionStoreService billingTransactionStoreService;

  private final ConfigItemRepository configItemRepository;

  private final BalHelperExt balHelperExt;

  private final BalHelper balHelper;

  private AcctDto acct;

  private SubsDto subs;

  private BillingBaseDataBus dataBus;

  public void invoke() {
    log.info("validateBeforeDataStore invoked.");
    AcctBookData acctBookData = this.dataBus.getAcctBookData();
    balHelper.checkCreditBalUpdate(this.acct.getUpdateBalList());
    if (acctBookData != null)
      balHelperExt.checkBalDeductLimit(acctBookData.getReCcInstDataList());
    if (this.subs != null) {
      balHelperExt.validateProductLevelBasicBalMaxLimit(this.subs.getSubsId(), this.acct.getNewBalList());
    } else {
      balHelperExt.validateProductLevelBasicBalMaxLimit(null, this.acct.getNewBalList());
    }
    checkBalByCheckMode(this.acct);
    Optional<ConfigItemParamProjection> findIsSupportValidateBalFromOtherAcct = configItemRepository
        .findConfigItem("ACCT", "ACCT_BILLING", "IS_SUPPORT_VALIDATE_BAL_FROM_OTHER_ACCT");
    String isSupportValidateBalFromOtherAcct = findIsSupportValidateBalFromOtherAcct
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse(null);
    if ("Y".equals(isSupportValidateBalFromOtherAcct)) {
      List<AcctDto> otherAcctList = this.dataBus.getOtherAcctList();
      if (!otherAcctList.isEmpty())
        for (AcctDto tmp : otherAcctList)
          checkBalByCheckMode(tmp);
    }
    log.info("validateBeforeDataStore passed.");
    getDataBus().setAcctBookType("O");
    getDataBus().setAcct(acct);
    billingTransactionStoreService.setDataBus(dataBus);
    billingTransactionStoreService.invoke();
  }

  private void checkBalByCheckMode(AcctDto acct) {
    if (!"Y".equals(acct.getPostpaid())) {
      Long subsCredictLimit = Long.valueOf(0L);
      if (this.subs != null && this.subs.getPpsCreditLimit() != null)
        subsCredictLimit = this.subs.getPpsCreditLimit();
      String acctBookType = this.dataBus.getAcctBookType();
      balHelper.checkBalByCheckMode(acct.getUpdateBalList(), acct.getNewBalList(), subsCredictLimit, acctBookType);
    } else {
      balHelper.checkPostpaidBalUpdate(acct.getUpdateBalList(), acct.getNewBalList());
    }
  }
}
