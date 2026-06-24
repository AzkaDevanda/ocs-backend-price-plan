package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ADVICE_TYPE_SORT", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdviceTypeSort {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advice_type_sort_id_seq")
  @SequenceGenerator(name = "advice_type_sort_id_seq", sequenceName = "ADVICE_TYPE_SORT_ID_SEQ", allocationSize = 1)
  @Column(name = "ADVICE_TYPE_SORT_ID", nullable = false)
  private Long adviceTypeSortId;

  @Column(name = "PARENT_ADVICE_TYPE_SORT_ID")
  private Long parentAdviceTypeSortId;

  @Column(name = "ADVICE_TYPE_SORT_NAME", nullable = false, length = 255)
  private String adviceTypeSortName;

  @Column(name = "ADVICE_CATG", length = 60)
  private String adviceCatg;

  @Column(name = "SP_ID")
  private Long spId;
}
