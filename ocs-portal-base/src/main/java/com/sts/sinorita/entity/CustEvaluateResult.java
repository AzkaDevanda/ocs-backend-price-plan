package com.sts.sinorita.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUST_EVALUATE_RESULT", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustEvaluateResult {

  @Id
  @Column(name = "CUST_EVALUATE_RESULT_ID")
  private Long custEvaluateResultId;

  @Column(name = "CUST_ID")
  private Long custId;

  @Column(name = "EVALUATE_ITEM_ID")
  private Long evaluateItemId;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "EVALUATE_VATE")
  private String evaluateVate;

  @Column(name = "SP_ID")
  private Long spId;
}
