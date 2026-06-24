package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ALL_CUST", schema = "STS", indexes = {
    @Index(name = "IDX_ALL_CUST_CUST_CODE", columnList = "CUST_CODE"),
    @Index(name = "IDX_ALL_CUST_PARENT_ID", columnList = "PARENT_ID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllCust {

  @Id
  @Column(name = "CUST_ID", nullable = false, precision = 12)
  private Long custId;

  @Column(name = "CUST_CODE", nullable = false, length = 60)
  private String custCode;

  @Column(name = "CUST_NAME", nullable = false, length = 255)
  private String custName;

  @Column(name = "CUST_TYPE", nullable = false, length = 1)
  private String custType;

  @Column(name = "PARENT_ID", precision = 12)
  private Long parentId;

  @Column(name = "ROUTING_ID", precision = 6)
  private Long routingId;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;
}
