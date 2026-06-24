package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.balanceAdjustment.SubsQueryService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AcctResRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.SystemParamRepository;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class ValidateForMaxExpireDateService {

  private final SubsQueryService subsQueryService;

  private final ConfigItemRepository configItemRepository;

  private final AcctResRepository acctResRepository;

  private final SystemParamRepository systemParamRepository;

  private BalDto[] updateBalList;

  private SubsDto subs;

  public void invoke() {
    // DataContext.getEntity();
    if (this.updateBalList != null && this.updateBalList.length > 0)
      for (int i = 0; i < this.updateBalList.length; i++) {
        BalDto updateBal = this.updateBalList[i];
        if (updateBal != null) {
          LocalDateTime newExpDate = getBalNewExpDate(updateBal);
          if (updateBal.getAcctResId() != null && updateBal
              .getAcctResId().equals(systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID"))) {
            AcctResDto acctResDto = acctResRepository.selectAcctRes(updateBal.getAcctResId()).get();
            if (acctResDto == null)
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00074"));
            // ExceptionHandler.publish("BL-S-ACT-00074", 0);
            if (this.subs != null && this.subs.getSubsId() != null)
              checkExpireDate(this.subs.getSubsId(), newExpDate);
          }
        }
      }
  }

  private LocalDateTime getBalNewExpDate(BalDto updateBal) {
    LocalDateTime oldExpDate = updateBal.getPreExpDate();
    Long seconds = Long.valueOf(0L);
    if (updateBal.getSeconds() != null)
      seconds = updateBal.getSeconds();
    LocalDateTime newExpDate = null;
    if (oldExpDate != null)
      newExpDate = DateUtil.offsetSecond(oldExpDate, (int) seconds.longValue());
    return newExpDate;
  }

  private void checkExpireDate(Long subsId, LocalDateTime newBalExpire) {

    Optional<ConfigItemParamProjection> findIsMaxBalExpireCheck = configItemRepository
        .findConfigItem("ACCT", "COMMON", "IS_MAX_BAL_EXPIRE_CHECK");
    String isMaxBalExpireCheck = findIsMaxBalExpireCheck.map(ConfigItemParamProjection::getDefaultValue)
        .orElse("N");
    // String isMaxBalExpireCheck =
    // ConfigItemCache.instance().getString("ACCT.COMMON.IS_MAX_BAL_EXPIRE_CHECK",
    // "N");
    if ("Y".equals(isMaxBalExpireCheck)) {
      String dbMaxBalExpire = subsQueryService.qryProdAttrValueByAttrCode(subsId, "EXP_MAX_BAL_EXPIRE");
      log.debug("dbMaxBalExpire={}", new Object[] { dbMaxBalExpire });
      if (StringUtil.isNotEmpty(dbMaxBalExpire)) {
        long maxBalExpire = Long.parseLong(dbMaxBalExpire);
        LocalDateTime maxDate = DateUtil.offsetSecond(DateUtil.getFullDate(DateUtil.GetDBDateTime()),
            (maxBalExpire + 1L) * 86400L);
        if (newBalExpire != null && !newBalExpire.isBefore(maxDate)) {
          log.info(StringUtil.stringFormat("Reach the max expdate [{0}]", maxDate));
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00407"));
          // ExceptionHandler.publish("S-ACT-00407", msg, 0);
        }
      }
    }
  }

}
