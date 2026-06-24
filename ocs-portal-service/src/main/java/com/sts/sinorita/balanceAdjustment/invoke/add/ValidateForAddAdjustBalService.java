package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.balanceAdjustment.AcctResService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BalHelperExt;
import com.sts.sinorita.helper.BillingHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class ValidateForAddAdjustBalService {
  private final Logger log = LoggerFactory.getLogger(ValidateForAddAdjustBalService.class);

  private final AddAdjustBalDataFillService addAdjustBalDataFillService;

  private  BalHelperExt balHelperExt;

  private  BalHelper balHelper;

  private final AcctResService acctResService;

  private BalDto modBal;

  private String partyType;

  private String partyCode;

  private AcctResDto acctResDto;

  private BalDto[] newBalList;

  public Information getInformation() {
    return null;
  }

  public void invoke(AddBalanceAccountRequestDto requestDto) {
    this.partyCode = requestDto.getPartyCode();
    this.partyType = requestDto.getPartyType();
    log.info("validateForAddAdjustBalService invoked.");
    if (this.modBal != null && "M".equals(this.modBal.getOperationType())) {
      AcctResDto acctResDto = acctResService.getAcctResById(this.modBal.getAcctResId());
      if (acctResDto == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00074"));
      String refillable = String.valueOf(acctResDto.getRefillable());
      if ("Y".equals(refillable)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00126"));
      } else if ("L".equals(refillable)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00032"));
      } else if ("M".equals(refillable)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00031"));
      }
    }
    validateBalExpDateAndCharge();
    validateMaxAdjustCharge();
    validateStaffMaxAdjustAmount();
    
    log.info("validateForAddAdjustBalService passed.");
    addAdjustBalDataFillService.invoke(requestDto);
  }

  private void validateBalExpDateAndCharge() {
    if (this.modBal == null || this.acctResDto == null)
      return;
    BalHelperExt.validateMaxChgValue(this.modBal.getCharge(), this.acctResDto.getMaxChgValue());
    BalDto newBal = BalHelper.getBalFromListByBalId(this.newBalList, this.modBal.getBalId());
    if (newBal == null)
      return;
    BalHelperExt.validateMaxValue(this.acctResDto.getMaxValue(), newBal.getRealBal());
    BalHelperExt.validateMaxExpDate(this.acctResDto.getMaxExpDate(), newBal.getExpDate(), LocalDateTime.now());
  }

  private void validateMaxAdjustCharge() {
    if (this.modBal == null || this.acctResDto == null)
      return;
    Long maxAdjustValue = this.acctResDto.getMaxAdjustValue();
    BalHelperExt.validateMaxChgValue(this.modBal.getCharge(), maxAdjustValue);
  }

  private void validateStaffMaxAdjustAmount() {
    if (this.modBal == null)
      return;
    String isLimitAdjustBal = BillingHelper.getStringFromConfig("ACCT.BALAJUST.IS_LIMIT_ADJUST_BAL_OF_JOB_PRIV", "N");
    if ("N".equals(isLimitAdjustBal))
      return;
    if (!"A".equals(this.partyType))
      return;
    Map<Long, Long> map = new HashMap<>();
    map.put(this.modBal.getAcctResId(), this.modBal.getCharge());
    BillingHelper.checkStaffMaxAdjustAmount(this.partyCode, map);
  }

}
