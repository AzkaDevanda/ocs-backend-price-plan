package com.sts.sinorita.balanceAdjustment;

import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.AcctAttrValueDto;
import com.sts.sinorita.dto.request.balanceAdjustment.QryAcctAttrValueByCodeRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcctInfoServBllService {

  private final AcctInfoManager acctInfoManager;

  public void qryAcctAttrValueByCode(QryAcctAttrValueByCodeRequestDto dict) {
    Long acctId = dict.getAcctId();
    String attrCode = dict.getAcctAttrCode();
    // AcctInfoMgr acctInfoMgr = new AcctInfoMgr();
    AcctAttrValueDto acctAttrValueDto = acctInfoManager.qryAcctAttrValueByCode(acctId, attrCode);
    if (acctAttrValueDto != null)
      dict.setAttrValue(acctAttrValueDto.getAttrValue());
  }

}
