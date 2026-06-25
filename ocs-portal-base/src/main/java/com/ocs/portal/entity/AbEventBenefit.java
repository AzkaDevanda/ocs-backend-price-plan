package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.AbEventBenefitId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "AB_EVENT_BENEFIT")
public class AbEventBenefit {

  @EmbeddedId
  public AbEventBenefitId id = new AbEventBenefitId(); ;

  public void setEventInstId(Long eventInstId){
    if (this.id == null) {
      this.id = new AbEventBenefitId();
    }
    this.id.setEventInstId(eventInstId);
  }

  public Long getEventInstId(){
    return id.getEventInstId();
  }
  public void setAcctBookId(Long acctBookId){
    this.id.setAcctBookId(acctBookId);
  }
  public Long getAcctBookId(){
    return id.getAcctBookId();
  }

  @NotNull
  @Column(name = "PRICE_ID", nullable = false)
  private Integer priceId;

  @NotNull
  @Column(name = "SUB_BAL_TYPE_ID", nullable = false)
  private Integer subBalTypeId;

  @NotNull
  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @NotNull
  @Column(name = "BENEFIT_BAL", nullable = false)
  private Long benefitBal;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @PrePersist
  public void prePersist() {
    if (partId == null) {
      partId = (short) java.time.LocalDate.now().getMonthValue();
    }
  }

}