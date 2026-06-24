package com.sts.sinorita.balanceAdjustment;

import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.repository.SystemParamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalManager {

  private final SystemParamRepository systemParamRepository;

  public BalDto getBasicBal(BalDto[] balDtoArray) {
    BalDto balDto = null;
    if (balDtoArray != null) {
      String defaultAcctResIdStr = systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID");
      Long defaultAcctResId = defaultAcctResIdStr != null ? Long.valueOf(defaultAcctResIdStr) : null;
      for (int i = 0; i < balDtoArray.length; i++) {
        if (balDtoArray[i] != null && defaultAcctResId != null && defaultAcctResId.equals((balDtoArray[i]).getAcctId())) {
          balDto = balDtoArray[i];
          break;
        }
      }
    }
    return balDto;
  }

}
