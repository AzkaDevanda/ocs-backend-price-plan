package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctItemDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingBaseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.InstalmentDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.PostInvoicingAdjustDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class DealCreditControlLimit {

  // @DataMapping(mapping = "acct", required = true)
  private AcctDto acct;

  // @DataMapping(mapping = "subs")
  private SubsDto subs;

  // @DataMapping(mapping = "this", required = true)
  private BillingBaseDataBus dataBus;

  private final SendSmsService sendSmsService;
  private final BalanceChangeTrigger balanceChangeTrigger;

  public void invoke() {
    try {
      if (!"Y".equals(this.acct.getPostpaid()))
        return;
      BalDto oldBasicBal = BalHelper.getDefaultBal(this.acct.getOldBalList());
      BalDto newBasicBal = BalHelper.getDefaultBal(this.acct.getNewBalList());
      if (oldBasicBal != null && newBasicBal != null && oldBasicBal
          .getGrossBal().longValue() != newBasicBal.getGrossBal().longValue()) {
        String input = "";
        String acctBookType = this.dataBus.getAcctBookType();
        if ("A".equals(acctBookType)) {
          input = getPostAjustCreditControlStr();
        } else if ("W".equals(acctBookType)) {
          input = getInstalmentCreditControlStr();
        } else {
          input = StringUtil.stringFormat("14={0},54={1},253=0,201={2},211={3},1000={4},1112=1",
              (Object[]) new String[] { "0", "0",

                  StringUtil.objToString((this.subs == null) ? "" : this.subs.getSubsId()),
                  StringUtil.objToString(this.acct.getAcctId()),
                  StringUtil.objToString(this.acct.getRoutingId()) });
        }
        if (StringUtil.isNotEmpty(input)){
          // ZxosHelper.creditControlLimit(input, true, false);
        }
      }
    } catch (Exception e) {
      // ExceptionHandler.publish("BL-S-ACT-00206", 0);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00206"));
    }
//    sendSmsService.invoke()
      balanceChangeTrigger.invoke();

  }

  private String getPostAjustCreditControlStr() {
    PostInvoicingAdjustDataBus postAdjustDataBus = (PostInvoicingAdjustDataBus) this.dataBus;
    AcctItemDto[] acctItemDtoList = postAdjustDataBus.getNewAcctItemList();
    if (AssertUtil.isEmpty((Object[]) acctItemDtoList))
      return null;
    StringBuffer input = new StringBuffer();
    for (AcctItemDto acctItemDto : acctItemDtoList) {
      input.append('|');
      input.append(StringUtil.stringFormat("14={0},54={1},253=0,201={2},211={3},1000={4},1112=1",
          (Object[]) new String[] { "0",

              (acctItemDto.getAcctItemTypeId() == null) ? "0" : acctItemDto.getAcctItemTypeId().toString(),
              StringUtil.objToString(acctItemDto.getSubsId()),
              StringUtil.objToString(acctItemDto.getAcctId()),
              StringUtil.objToString(this.acct.getRoutingId()) }));
    }
    return input.substring(1);
  }

  private String getInstalmentCreditControlStr() {
    InstalmentDataBus instalmentDataBus = (InstalmentDataBus) this.dataBus;
    AcctItemDto[] acctItemDtoList = instalmentDataBus.getNewAcctItemList();
    if (AssertUtil.isEmpty((Object[]) acctItemDtoList))
      return null;
    StringBuffer input = new StringBuffer();
    for (AcctItemDto acctItemDto : acctItemDtoList) {
      if (acctItemDto != null) {
        input.append('|');
        input.append(StringUtil.stringFormat("14={0},54={1},253=0,201={2},211={3},1000={4},1112=1",
            (Object[]) new String[] { "0",

                (acctItemDto.getAcctItemTypeId() == null) ? "0" : acctItemDto.getAcctItemTypeId().toString(),
                StringUtil.objToString(acctItemDto.getSubsId()),
                StringUtil.objToString(acctItemDto.getAcctId()),
                StringUtil.objToString(this.acct.getRoutingId()) }));
      }
    }
    if (input.length() > 0)
      return input.substring(1);
    return null;
  }

}
