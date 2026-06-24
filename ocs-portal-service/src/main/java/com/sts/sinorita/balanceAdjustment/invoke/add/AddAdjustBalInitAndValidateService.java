package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
@RequiredArgsConstructor
public class AddAdjustBalInitAndValidateService {
  private final Logger log = LoggerFactory.getLogger(AddAdjustBalInitAndValidateService.class);

  // private AddAdjustBalApplicationRequestDto dto;

  private final ValidateForBasicInfoInquiryService validateForBasicInfoInquiryService;

  private Long acctId;

  private String acctNbr;
  
  private String acctBookType;

  private Boolean acctLockFlag;

  public Information getInformation() {
    return null;
  }

  public void invoke(AddBalanceAccountRequestDto requestDto) {
    this.acctId = requestDto.getAcctId();
    this.acctNbr = requestDto.getAcctNbr();
    log.info("addAdjustBalInitAndValidateService passed.");
    validateAccountParams();
    this.acctLockFlag = true;
    this.acctBookType = "H";
    // validateForBasicInfoInquiryService.setAcctBookType(acctBookType);
    // validateForBasicInfoInquiryService.setAcctLockFlag(acctLockFlag);
    log.info("validateForBasicInfoInquiryService invoked.");
    validateForBasicInfoInquiryService.invoke(requestDto);
  }

  private void validateAccountParams() {
    if (acctId == null && acctNbr.isEmpty()) {
      throw new IllegalArgumentException("acctId and acctNbr cannot be null or empty at the same time.");
    }
  }

}
