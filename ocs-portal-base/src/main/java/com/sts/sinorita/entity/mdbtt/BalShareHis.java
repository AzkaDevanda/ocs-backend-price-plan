package com.sts.sinorita.entity.mdbtt;

import com.sts.sinorita.entity.embeddable.BalShareHisId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BAL_SHARE_HIS", schema = "MDBTT")
public class BalShareHis {
  @EmbeddedId
  private BalShareHisId id;

  public void setBalShareId(Long id) {
    this.id.setBalShareId(id);
  }
  public Long getBalShareId(){
    return this.id.getBalShareId();
  }
  public void setDateStamp(Long dateStamp) {
    this.id.setDateStamp(dateStamp);
  }
  public Long getDateStamp(){
    return this.id.getDateStamp();
  }

  @Column(name = "DAILY_BAL")
  private Long dailyBal;

  @Column(name = "BILL_BAL")
  private Long billBal;

  @Column(name = "POSTPAID_BASE_BAL")
  private Long postpaidBaseBal;

}