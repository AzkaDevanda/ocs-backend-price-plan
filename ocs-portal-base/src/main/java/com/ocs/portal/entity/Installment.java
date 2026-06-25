package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "INSTALLMENT", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Installment {
  @Id
  @Column(name = "INSTALLMENT_ID", precision = 12, scale = 0, nullable = false)
  private Long installmentId;

  @Column(name = "INSTALLMENT_TYPE_ID", precision = 3, scale = 0)
  private Integer installmentTypeId;

  @Column(name = "TOTAL_AMOUNT", precision = 15, scale = 0, nullable = false)
  private Long totalAmount;

  @Column(name = "NUM_OF_INSTALLT", precision = 3, scale = 0, nullable = false)
  private Integer numOfInstallt;

  @Column(name = "PARTY_TYPE", length = 1, nullable = false)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 30)
  private String partyCode;

  @Column(name = "PAIED_AMOUNT", precision = 15, scale = 0)
  private Long paiedAmount;

  @Column(name = "FIRST_DUEDATE", nullable = false)
  private LocalDate firstDuedate;

  @Column(name = "LAST_DUEDATE", nullable = false)
  private LocalDate lastDuedate;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "INSTALL_STATUS", length = 1, nullable = false)
  private String installStatus; // A = Active, C = Close

  @Column(name = "INSTALL_STATUS_DATE", nullable = false)
  private LocalDate installStatusDate;

  @Column(name = "ORI_INSTALLMENT_ID", precision = 12, scale = 0)
  private Long oriInstallmentId;

  @Column(name = "ACCT_ID", precision = 12, scale = 0, nullable = false)
  private Long acctId;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;
}