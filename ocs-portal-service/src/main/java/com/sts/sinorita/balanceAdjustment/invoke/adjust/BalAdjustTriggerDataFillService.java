package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.BalChangeTriggerParam;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class BalAdjustTriggerDataFillService {

  private BalChangeTriggerParam balChangeTriggerParam;

  public Information getInformation() {
    return null;
  }

  public void invoke() {
    makeBalChangeTrig();
  }

  public void makeBalChangeTrig() {
    if (this.balChangeTriggerParam == null)
      this.balChangeTriggerParam = new BalChangeTriggerParam();
    this.balChangeTriggerParam.setComments(" By balance adjust");
  }

}
