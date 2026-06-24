package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sts.sinorita.balanceAdjustment.invoke.add.RecordBalanceChangeService;
import com.sts.sinorita.balanceAdjustment.invoke.add.ValidateForBasicInfoInquiryService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.AdjustBalanceRequest;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class SingleBalAdjustPrepareService {

  private final ValidateForProdSpecAndPricePlanService validateForProdSpecAndPricePlanService;

  private final BasicInfoInquiryService basicInfoInquiryService;

  private final ValidateForBasicInfoInquiryService validateForBasicInfoInquiryService;

  private final RecordBalanceChangeService recordBalanceChangeService;

  private final ValidateForMaxExpireDateService validateForMaxExpireDateService;

  private final ValidateForAcctResMaxValueService validateForAcctResMaxValueService;

  // TODO : jait invoke line singe bal adjust preapre kalau udah semua service ke buat
  public void invoke(AdjustBalanceRequest request) {
    // ApplicationContext.invokeBusinessComponent("bd.bl.balance.SingleBalAdjustBC", this.balAdjustDataBus);
      log.info("SingleBalAdjustPrepareService invoked");
    AddBalanceAccountRequestDto addBalanceAccountRequestDto = new AddBalanceAccountRequestDto();
    addBalanceAccountRequestDto.setAcctId(request.getAcctId());
    addBalanceAccountRequestDto.setAcctNbr(request.getAcctNbr());
    addBalanceAccountRequestDto.setAcctResId(request.getAcctResId());
    // addBalanceAccountRequestDto.setBalance(req);
    addBalanceAccountRequestDto.setComment(request.getComments());
    addBalanceAccountRequestDto.setContactChannelId(request.getContactChannelId());
    addBalanceAccountRequestDto.setEffDate(request.getEffDate());
    addBalanceAccountRequestDto.setExpDate(request.getExpDate());
    addBalanceAccountRequestDto.setPartyCode(request.getPartyCode());
    addBalanceAccountRequestDto.setPartyType(request.getPartyType());
    validateForBasicInfoInquiryService.setFlag('A');
    
    basicInfoInquiryService.invoke();
    validateForBasicInfoInquiryService.invoke(addBalanceAccountRequestDto);
    recordBalanceChangeService.setFlag('A');
    recordBalanceChangeService.invoke(addBalanceAccountRequestDto);
    validateForProdSpecAndPricePlanService.invoke();
    validateForMaxExpireDateService.invoke();
    validateForAcctResMaxValueService.invoke();
  }

  public Information getInformation() {
    return null;
  }
}
