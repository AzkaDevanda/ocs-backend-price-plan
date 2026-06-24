package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.AdjustBalanceRequest;
import com.sts.sinorita.mapper.balanceAdjustment.AcctBookMapper;
import com.sts.sinorita.projection.balanceAdjustment.FindAcctBookByPartnerSnProjection;
import com.sts.sinorita.repository.AcctBookRepository;
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
public class BalAdjustRepeatInfoInquiryService {

  private final SingleBalAdjustPrepareService singleBalAdjustPrepareService;

  private final AcctBookRepository acctBookRepository;

  private final AcctBookMapper acctBookMapper;

  private String partnerSn;

  private Long contactChannelId;

  private Boolean isRepeat;

  private AcctBookDto acctBookData;

  private BalDto balBeforeAdjust;

  private BalDto balAfterAdjust;

  public Information getInformation() {
    return null;
  }

  public void invoke(AdjustBalanceRequest request) {
    log.info("BalAdjustRepeatInfoInquiryService invoked");
    log.info(">>>>>>>> ENTER BalAdjustRepeatInfoInquiryService");

    log.info("isRepeat = {}", isRepeat);
    log.info("partnerSn = {}", partnerSn);
    log.info("contactChannelId = {}", contactChannelId);

    this.setContactChannelId(request.getContactChannelId());
    if (!Boolean.TRUE.equals(this.isRepeat)){
        log.info("RETURNING EARLY BECAUSE isRepeat != TRUE");
        return;
    }
    // IBalAdjustRepeatInfoInquiryDAO balAdjustRepeatInfoInquiryDAO =
    // (IBalAdjustRepeatInfoInquiryDAO) DAOFactory
    // .createModuleDAO("BalAdjustRepeatInfoInquiry",
    // "billing.fc.balance.baladjustrptinfoqry",
    // JdbcUtil4BL.getDbBackService());
    List<FindAcctBookByPartnerSnProjection> projections = acctBookRepository.findAcctBookByPartnerSn(this.partnerSn,
        this.contactChannelId);
    this.acctBookData = projections.stream()
        .findFirst()
        .map(acctBookMapper::toAcctBookDtoForFindAcctBookByPartnerSn)
        .orElse(null);
    if (this.acctBookData == null)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00042"));
    // ExceptionHandler.publish("BL-S-ACT-00042", 0);
    Long preSuttleBal = (this.acctBookData.getPreSuttleBal() == null) ? this.acctBookData.getPreBalance()
        : this.acctBookData.getPreSuttleBal();
    if (this.balBeforeAdjust == null)
      this.balBeforeAdjust = new BalDto();
    this.balBeforeAdjust.setGrossBal(this.acctBookData.getPreBalance());
    this.balBeforeAdjust.setRealBal(preSuttleBal);
    if (this.acctBookData.getPreExpDate() != null)
      this.balBeforeAdjust.setExpDate(this.acctBookData.getPreExpDate());
    if (this.balAfterAdjust == null)
      this.balAfterAdjust = new BalDto();
    this.balAfterAdjust.setGrossBal(Long.valueOf(this.acctBookData.getPreBalance().longValue() + this.acctBookData
        .getCharge().longValue()));
    this.balAfterAdjust.setRealBal(Long.valueOf(preSuttleBal.longValue() + this.acctBookData.getCharge().longValue()));
    if (this.acctBookData.getPreExpDate() != null)
      this.balBeforeAdjust.setExpDate(
          DateUtil.offsetSecond(this.acctBookData.getPreExpDate(), this.acctBookData.getSeconds().longValue()));
    singleBalAdjustPrepareService.invoke(request);
    log.info("<<<<<<<< EXIT BalAdjustRepeatInfoInquiryService");
  }

}
