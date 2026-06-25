package com.ocs.portal.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BalTriggerParam {

  private Long threshold;

  private Long ratio;

  private Long balThresholdId;

  private String triggerType;

  private Long triggerId;

  private Long acctResId;

  private Long offerVerId;

  private LocalDateTime triggerEffDate;

  private LocalDateTime priceEffDate;

  private Long pricePlanId;

  private String acctResIdList;

  private String reAttr;

  private String destination;

  private List<SubsEvent> subsEventList;

  private List<Advice> adviceList;

  public class SubsEvent {
    private Long subsEventId;

    private String extAttr;

    public SubsEvent(Long subsEventId, String extAttr) {
      this.subsEventId = subsEventId;
      this.extAttr = extAttr;
    }

    public Long getSubsEventId() {
      return this.subsEventId;
    }

    public void setSubsEventId(Long subsEventId) {
      this.subsEventId = subsEventId;
    }

    public String getExtAttr() {
      return this.extAttr;
    }

    public void setExtAttr(String extAttr) {
      this.extAttr = extAttr;
    }
  }

  public class Advice {
    private Long adviceType;

    private String contactType;

    public Advice(Long adviceType, String contactType) {
      this.adviceType = adviceType;
      this.contactType = contactType;
    }

    public Long getAdviceType() {
      return this.adviceType;
    }

    public void setAdviceType(Long adviceType) {
      this.adviceType = adviceType;
    }

    public String getContactType() {
      return this.contactType;
    }

    public void setContactType(String contactType) {
      this.contactType = contactType;
    }
  }

}
