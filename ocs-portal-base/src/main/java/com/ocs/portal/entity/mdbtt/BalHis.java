package com.ocs.portal.entity.mdbtt;

import com.ocs.portal.entity.embeddable.BalHisId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BAL_HIS", schema = "MDBTT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalHis {
  @EmbeddedId
  private BalHisId id;

  @Column(name = "DAILY_BAL", precision = 20, scale = 0)
  private Long dailyBal;

  @Column(name = "BILL_BAL", precision = 20, scale = 0)
  private Long billBal;

  public void setBalId(Long balId) {
    this.id.setBalId(balId);
  }
  public Long getBalId() {
    return this.id.getBalId();
  }
  public void setDateStamp(Long timeStamp){
    this.id.setDateStamp(timeStamp);
  }
  public Long getDateStamp() {
    return this.id.getDateStamp();
  }
}