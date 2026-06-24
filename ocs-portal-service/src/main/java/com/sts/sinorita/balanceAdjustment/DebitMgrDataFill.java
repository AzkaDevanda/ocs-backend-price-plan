package com.sts.sinorita.balanceAdjustment;

import java.util.Map;

import com.sts.sinorita.balanceAdjustment.invoke.adjust.DebitManagerEngine;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.DealDebitDto;
import com.sts.sinorita.dto.DebitInputDto;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.repository.AcctItemRepository;
import org.aspectj.bridge.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.DebitInputDict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class DebitMgrDataFill {

  // @DataMapping(mapping = "acctBookData")
  private AcctBookData acctBookData;

  // @DataMapping(mapping = "acct")
  private AcctDto acct;

  // @DataMapping(mapping = "request.spId")
  private Long spId;

  // @DataMapping(mapping = "request.partyType")
  private String partyType;

  // @DataMapping(mapping = "request.partyCode")
  private String partyCode;

  // @DataMapping(mapping = "request.extMap")
  private Map<String, Object> extMap;

  private final BillingHelper billingHelper;

  private final AcctItemRepository acctItemRepository;

  private final DebitManagerEngine debitManagerEngine;

   public void invoke() {
       try {
           if (this.acct == null || this.acctBookData == null)
               return;
           if ("J".equals(this.acctBookData.getAcctBookType()) && (this.extMap == null ||
                   !"Y".equals(this.extMap.get("IS_START_DEBIT_ENGINE"))))
               return;
           DebitInputDto debitInputDto = new DebitInputDto();

           debitInputDto.setAcctDto(this.acct);
           debitInputDto.setAcctId(this.acct.getAcctId());
           debitInputDto.setAcctBookId(this.acctBookData.getAcctBookId());
           debitInputDto.setPartyType(this.partyType);
           debitInputDto.setPartyCode(this.partyCode);
           debitInputDto.setGroupType("A");
           if (this.extMap != null && "Y".equals(this.extMap.get("EXCLUDE_OVERDUE")) && "Y".equals(this.acct.getPostpaid())) {

               Long overdueItemTypeId = billingHelper.getOverDueItemId();

               Long overdueAmount = acctItemRepository.selectOverdueAmount(this.acct.getAcctId(), overdueItemTypeId);

               if (overdueAmount != null && overdueAmount > 0) {
                   debitInputDto.setOverdueAmount(overdueAmount);
               }
           }
           DealDebitDto dealDebitDto = new DealDebitDto();
           dealDebitDto.setDebitInput(debitInputDto);
           dealDebitDto.setSpId(this.spId);

           if ("Y".equals(this.acct.getPostpaid())) {
               debitManagerEngine.dealDebit(dealDebitDto);
           }
       } catch (Exception e) {

           log.warn("Fail to deal Debit.", e);

           throw new ResponseStatusException(HttpStatus.CONFLICT, MessageService.getMessage("BL-S-ACT-00111"));
       }
   }

}
