package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingBaseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingRequest;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BalHelperExt;
import com.sts.sinorita.repository.AcctResRepository;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class ValidateForAcctResMaxValueService {

  private final BalChangeTriggerSubsEventService balChangeTriggerSubsEventService;

  private final AcctResRepository acctResRepository;

  private BalDto[] updateBalList;

  private BalDto[] newBalList;

  private BillingBaseDataBus dataBus;

  public void invoke() {
    log.debug("AcctResMaxValueValidate start");
    if (CommonUtil.isNotEmpty((Object[]) this.updateBalList)) {
      Map<String, Object> extMap = null;
      Map<Long, Long> currentAmountMap = null;
      String acctBookType = this.dataBus.getAcctBookType();
      Boolean isValidateAdjustChargeValue = Boolean.FALSE;
      if ("H".equals(acctBookType)) {
        BillingRequest request = this.dataBus.getRequest();
        extMap = request.getExtMap();
        isValidateAdjustChargeValue = Boolean.TRUE;
        if (extMap != null)
          currentAmountMap = (Map<Long, Long>) extMap.get("CURRENT_AMOUNT_MAP");
      }
      List<BalDto> operatorList = BalHelper.mergeAddList(this.updateBalList);
      for (BalDto updateBal : operatorList) {
        Long acctResId = updateBal.getAcctResId();
        AcctResDto acctResDto = acctResRepository.selectAcctRes(updateBal.getAcctResId()).get();
        if (acctResDto == null)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00074"));
        // ExceptionHandler.publish("BL-S-ACT-00074", 0);
        Long charge = updateBal.getCharge();
        BalHelperExt.validateMaxChgValue(charge, acctResDto.getMaxChgValue());
        if (isValidateAdjustChargeValue.booleanValue()) {
          BalHelperExt.validateMaxChgValue(charge, acctResDto.getMaxAdjustValue());
          Long totalAdjustCharge = getTotalAdjustCharge(currentAmountMap, acctResId, charge);
          BalHelperExt.validateMaxChgValue(totalAdjustCharge, acctResDto.getMaxAdjustValue());
        }
        BalDto toBeCheckedBal = BalHelper.getBalFromListByBalId(this.newBalList, updateBal.getBalId());
        if (toBeCheckedBal == null || toBeCheckedBal.getAcctResId() == null || toBeCheckedBal.getRealBal() == null)
          continue;
        if (acctResDto.getMaxValue() != null
            && acctResDto.getMaxValue().longValue() + toBeCheckedBal.getRealBal().longValue() < 0L)
          if ("H".equals(this.dataBus.getAcctBookType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00143"));
            // ExceptionHandler.publish("BL-S-ACT-00143", 0);
          } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00297"));
            // ExceptionHandler.publish("BL-S-ACT-00297", 0);
          }
        BalHelper.validateMaxExpDate(acctResDto.getMaxExpDate(), toBeCheckedBal.getExpDate(), DateUtil.GetDBDateTime());
      }
      if (extMap == null)
        extMap = new HashMap<>();
    }
    log.debug("AcctResMaxValueValidate end");
    balChangeTriggerSubsEventService.invoke();
  }

  private Long getTotalAdjustCharge(Map<Long, Long> currentAmountMap, Long acctResId, Long charge) {
    Long totalCharge = charge;
    if (currentAmountMap == null)
      return null;
    if (currentAmountMap.containsKey(acctResId))
      totalCharge = Long.valueOf(totalCharge.longValue() + ((Long) currentAmountMap.get(acctResId)).longValue());
    currentAmountMap.put(acctResId, totalCharge);
    return totalCharge;
  }

  public Information getInformation() {
    return null;
  }

}
