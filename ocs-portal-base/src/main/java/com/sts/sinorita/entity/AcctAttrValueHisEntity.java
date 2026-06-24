package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entity untuk tabel STS.ACCT_ATTR_VALUE_HIS
 */
@Data
@Entity
@Table(name = "ACCT_ATTR_VALUE_HIS", schema = "STS")
@IdClass(AcctAttrValueHisEntity.Pk.class) // karena PK terdiri dari 3 kolom
public class AcctAttrValueHisEntity implements Serializable {

  @Id
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @Id
  @Column(name = "ATTR_ID", nullable = false)
  private Long attrId;

  @Id
  // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acctAttrValueHisSeq")
  // @SequenceGenerator(name = "acctAttrValueHisSeq", sequenceName = "ACCT_ATTR_VALUE_HIS", allocationSize = 1)
  @Column(name = "SEQ", nullable = false)
  private Long seq;

  @Column(name = "ATTR_VALUE", length = 120)
  private String attrValue;

  @Column(name = "EFF_DATE")
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "REC_CREATED_DATE", nullable = false)
  private LocalDateTime recCreatedDate;

  @Column(name = "REC_EFF_DATE")
  private LocalDateTime recEffDate;

  @Column(name = "REC_EXP_DATE")
  private LocalDateTime recExpDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ROUTING_ID")
  private Long routingId;

  /**
   * Class untuk composite primary key (acctId + attrId + seq)
   */
  @Data
  public static class Pk implements Serializable {
    private Long acctId;
    private Long attrId;
    private Long seq;

    public Pk() {
    }

    public Pk(Long acctId, Long attrId, Long seq) {
      this.acctId = acctId;
      this.attrId = attrId;
      this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (!(o instanceof Pk pk))
        return false;
      return Objects.equals(acctId, pk.acctId) &&
          Objects.equals(attrId, pk.attrId) &&
          Objects.equals(seq, pk.seq);
    }

    @Override
    public int hashCode() {
      return Objects.hash(acctId, attrId, seq);
    }
  }
}
