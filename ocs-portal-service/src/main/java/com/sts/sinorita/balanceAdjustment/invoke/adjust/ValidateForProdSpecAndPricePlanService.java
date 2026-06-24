package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctServPriceLimitDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctServProdLimitDto;
import com.sts.sinorita.mapper.balanceAdjustment.AcctServPriceLimitMapper;
import com.sts.sinorita.mapper.balanceAdjustment.AcctServProdLimitMapper;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctServPriceLimitListProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctServProdLimitListProjection;
import com.sts.sinorita.repository.AcctServPriceLimitRepository;
import com.sts.sinorita.repository.AcctServProdLimitRepository;
import com.sts.sinorita.util.CommonUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class ValidateForProdSpecAndPricePlanService {

  private final AcctServProdLimitRepository acctServProdLimitRepository;

  private final AcctServProdLimitMapper acctServProdLimitMapper;

  private final AcctServPriceLimitRepository acctServPriceLimitRepository;

  private final AcctServPriceLimitMapper acctServPriceLimitMapper;

  private String acctBookType;

  private Long subsDefaultPricePlanId;

  private Long prodSpecId;

  private Long spId;

  public void invoke() {
    log.info("ProdSpecAndPricePlanValidate called");
    if (this.subsDefaultPricePlanId != null)
      acctServPriceLimit();
    if (this.prodSpecId != null)
      acctServProdLimit();
    log.info("ProdSpecAndPricePlanValidate end");
  }

  private void acctServPriceLimit() {
    List<SelectAcctServPriceLimitListProjection> projectionsPricePlanLimitList = acctServPriceLimitRepository
        .selectAcctServPriceLimitList(this.acctBookType, this.spId);
    List<AcctServPriceLimitDto> pricePlanLimitList = projectionsPricePlanLimitList.stream()
        .map(acctServPriceLimitMapper::toAcctServPriceLimitDtoFromSelectAcctServPriceLimitList).toList();
    if (CommonUtil.isNotEmpty(pricePlanLimitList)) {
      AcctServPriceLimitDto firstPriceLimitDto = pricePlanLimitList.get(0);
      if ("X".equals(firstPriceLimitDto.getSuitableType())) {
        for (int i = 0; i < pricePlanLimitList.size(); i++) {
          AcctServPriceLimitDto priceLimitDto = pricePlanLimitList.get(i);
          if (this.subsDefaultPricePlanId.equals(priceLimitDto.getPricePlanId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00148"));
          // ExceptionHandler.publish("BL-S-ACT-00148", 0);
        }
      } else {
        boolean canOper = false;
        for (int i = 0; i < pricePlanLimitList.size(); i++) {
          AcctServPriceLimitDto priceLimitDto = pricePlanLimitList.get(i);
          if (this.subsDefaultPricePlanId.equals(priceLimitDto.getPricePlanId())) {
            canOper = true;
            break;
          }
        }
        if (!canOper)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00148"));
        // ExceptionHandler.publish("BL-S-ACT-00148", 0);
      }
    }
  }

  private void acctServProdLimit() {
    List<SelectAcctServProdLimitListProjection> projectionsProdSpecLimitList = acctServProdLimitRepository
        .selectAcctServProdLimitList(this.acctBookType, this.spId);
    List<AcctServProdLimitDto> prodSpecLimitList = projectionsProdSpecLimitList.stream()
        .map(acctServProdLimitMapper::toAcctServProdLimitDtoFromSeleAcctServProdLimitDto).toList();
    if (CommonUtil.isNotEmpty(prodSpecLimitList)) {
      AcctServProdLimitDto firstProdLimitDto = prodSpecLimitList.get(0);
      if ("X".equals(firstProdLimitDto.getSuitableType())) {
        for (int i = 0; i < prodSpecLimitList.size(); i++) {
          AcctServProdLimitDto prodLimitDto = prodSpecLimitList.get(i);
          if (this.prodSpecId.equals(prodLimitDto.getIndepProdSpecId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00149"));
          // ExceptionHandler.publish("BL-S-ACT-00149", 0);
        }
      } else {
        boolean canOper = false;
        for (int i = 0; i < prodSpecLimitList.size(); i++) {
          AcctServProdLimitDto prodLimitDto = prodSpecLimitList.get(i);
          if (this.prodSpecId.equals(prodLimitDto.getIndepProdSpecId())) {
            canOper = true;
            break;
          }
        }
        if (!canOper)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00149"));
        // ExceptionHandler.publish("BL-S-ACT-00149", 0);
      }
    }
  }

  public Information getInformation() {
    return null;
  }

}
