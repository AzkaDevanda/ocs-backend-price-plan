package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.AdjustDto;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class AddAdjustBalDataFillService {
  private final Logger log = LoggerFactory.getLogger(AddAdjustBalDataFillService.class);

  private final ValidateBeforeDataStoreService validateBeforeDataStoreService;

  private Long spId = 0L;

  private String comments = "test";

  private Long adjustReasonId;

  private Long subsId;

  private AcctBookData acctBookData;

  private AdjustDto adjustDto;

  public Information getInformation() {
    return null;
  }

  public void invoke(AddBalanceAccountRequestDto requestDto) {
    this.comments = requestDto.getComment();
    log.info("addAdjustBalDataFillService invoked.");
    recordAdjust();
    log.info("addAdjustBalDataFillService passed.");
    validateBeforeDataStoreService.invoke();
  }

  public void recordAdjust() {
    if (this.adjustDto == null)
      this.adjustDto = new AdjustDto();
    this.adjustDto.setAdjustId(this.acctBookData.getAcctBookId());
    this.adjustDto.setSpId(this.spId);
    if (!this.comments.isEmpty())
      this.adjustDto.setComments(this.comments);
    if (this.adjustReasonId != null)
      this.adjustDto.setAdjustReasonId(this.adjustReasonId);
    if (this.subsId != null)
      this.adjustDto.setSubsId(this.subsId);
  }

}
