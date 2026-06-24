package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "CUST_PRICE_BOOK_VER", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustPriceBookVer {

  @Id
  @Column(name = "PRICE_BOOK_VER_ID", nullable = false)
  private Long priceBookVerId;

  @Column(name = "CONTRACT_ITEM_ID")
  private Long contractItemId;

  @Column(name = "SEQ")
  private Integer seq;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @Column(name = "QUOTATION_ITEM_ID")
  private Long quotationItemId;

  @Column(name = "SP_ID")
  private Integer spId;
}
