package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.RecordBalanceChangeParam;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Service
@RequiredArgsConstructor
public class AddAdjustBalDataPrepareService {
  private final Logger log = LoggerFactory.getLogger(AddAdjustBalDataPrepareService.class);

  private final RecordBalanceChangeService recordBalanceChangeService;

  private final DataBusStoreService dataBusStoreService;

  private final ValidateBeforeDataStoreService validateBeforeDataStoreService;

  // private AddAdjustBalApplicationRequestDto dto;

  private Long charge;

  private LocalDateTime effDate;

  private LocalDateTime expDate;

  private BalDto[] allBalList;

  private BalDto[] oldBalList;

  private BalDto[] newBalList;

  private Long balAdjustAmount;

  private LocalDateTime balAdjustEffDate;

  private LocalDateTime balAdjustExpDate;

  private Long balAdjustSeconds;

  private RecordBalanceChangeParam recordBalanceChangeParam;

  public static BalDto[] filterExpireBalExceptKeepUniqueAcctResBySubsId(BalDto[] balList, Long subsId) {
    if (balList == null)
      return null;
    List<BalDto> resultBalList = new ArrayList<>();
    for (BalDto bal : balList) {
      if (subsId == null || bal.getSubsId() == null || subsId.longValue() == bal.getSubsId().longValue())
        if ("Y".equals(bal.getAcctResDto().getRefillable()) || "M".equals(bal.getAcctResDto().getRefillable())) {
          resultBalList.add(bal);
        } else if (bal.getExpDate() == null) {
          resultBalList.add(bal);
        } else {
          LocalDateTime now = LocalDateTime.now();

          if (bal.getExpDate().isAfter(now)) {
            resultBalList.add(bal);
          }
        }
    }
    return resultBalList.toArray(new BalDto[0]);
  }

  public Information getInformation() {
    return null;
  }

  public void invoke(AddBalanceAccountRequestDto requestDto) {
    this.charge = requestDto.getBalance() != null ? requestDto.getBalance() : 0L;
    this.effDate = requestDto.getEffDate();
    this.expDate = requestDto.getExpDate();
    log.info("addAdjustBalDataPrepareService passed.");
    this.oldBalList = filterExpireBalExceptKeepUniqueAcctResBySubsId(this.allBalList, null);
    this.newBalList = this.oldBalList;
    makeRecordBalChangeRequest();
    log.info("recordBalanceChangeService invoked.");
    AcctDto acct = new AcctDto();
    acct.setAcctId(requestDto.getAcctId());
    AcctResDto acctResDto = new AcctResDto();
    acctResDto.setAcctResId(requestDto.getAcctResId());

    validateBeforeDataStoreService.setAcct(acct);
    recordBalanceChangeService.setAcct(acct);
    recordBalanceChangeService.setAcctResDto(acctResDto);
    recordBalanceChangeService.invoke(requestDto);
  }

  public void makeRecordBalChangeRequest() {
    if (this.recordBalanceChangeParam == null)
      this.recordBalanceChangeParam = new RecordBalanceChangeParam();
    this.balAdjustAmount = this.charge;
    this.balAdjustEffDate = this.effDate;
    if (this.expDate != null) {
      this.balAdjustExpDate = this.expDate;
      this.balAdjustSeconds = ChronoUnit.DAYS.between(this.effDate, this.expDate);
    }
    this.recordBalanceChangeParam.setOperateAmount(this.balAdjustAmount);
    this.recordBalanceChangeParam.setOperateEffDate(this.balAdjustEffDate);
    this.recordBalanceChangeParam.setOperateExpDate(this.balAdjustExpDate);
    this.recordBalanceChangeParam.setOperateSeconds(this.balAdjustSeconds);
  }

}
