package com.sts.sinorita.balanceAdjustment;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.AcctAttrValueDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AttrDto;
import com.sts.sinorita.mapper.attr.AttrMapper;
import com.sts.sinorita.repository.AcctAttrValueRepository;
import com.sts.sinorita.repository.AttrRepository;
import com.sts.sinorita.util.ValidateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcctInfoManager {

  private final AcctAttrValueRepository acctAttrValueRepository;

  private final AttrRepository attrRepository;

  private final AttrMapper attrMapper;

  public AcctAttrValueDto qryAcctAttrValueByCode(Long acctId, String attrCode) {
    ValidateUtil.isNull(attrCode, "attrCod is missing.");
    ValidateUtil.isNull(acctId, "acctId is missing.");
    // IAcctAttrValueDAO acctAttrValueDAO = (IAcctAttrValueDAO)
    // DAOFactory.create("AcctAttrValue",
    // JdbcUtil4BL.getDbBackService());
    AttrDto attrDto = attrRepository.selectAttrByCode(attrCode)
        .map(attrMapper::selectAttrByCodeToAttrDto)
        .orElse(null);
    if (attrDto == null) {
      log.debug("attrCode=[{}]", new Object[] { attrCode });
      // throw ExceptionHandler.publish("S-ACT-00076", 0);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00076"));
    }
    AcctAttrValueDto acctAttrValueOldDto = acctAttrValueRepository.selectAcctAttrValue(acctId, attrDto.getAttrId())
        .map(attrMapper::toAcctAttrValueDto).orElse(null);
    return acctAttrValueOldDto;
  }

}
