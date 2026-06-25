package com.ocs.portal.entity.mdbtt;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.ocs.portal.entity.embeddable.ReserveBalId;

@Entity
@Table(name = "RESERVE_BAL", schema = "MDBTT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveBal implements Serializable {

  @EmbeddedId
  private ReserveBalId embeddedReserveBalId;

  public Long getBalId() {
    return embeddedReserveBalId != null ? embeddedReserveBalId.getBalId() : null;
  }

  public void setBalId(Long id) {
    if (embeddedReserveBalId == null) {
      embeddedReserveBalId = new ReserveBalId();
    }
    embeddedReserveBalId.setBalId(id);
  }

  public String getSessionId(){
    return embeddedReserveBalId !=null ? embeddedReserveBalId.getSessionId() : null;
  }

  public void setSessionId(String sessionId){
    if (embeddedReserveBalId == null) {
      embeddedReserveBalId = new ReserveBalId();
    }
    embeddedReserveBalId.setSessionId(sessionId);
  }

  @Column(name = "ACCT_ID")
  private Long acctId;

  @Column(name = "USED_BAL")
  private Long usedBal;

  @Column(name = "RESERVE_BAL")
  private Long reserveBal;

  @Column(name = "UPDATE_DATE")
  private LocalDateTime updateDate;

  @Column(name = "BAL_SHARE_ID")
  private Long balShareId;
}