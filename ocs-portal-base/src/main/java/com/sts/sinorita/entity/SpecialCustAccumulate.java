package com.sts.sinorita.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SPECIAL_CUST_ACCUMULATE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialCustAccumulate {
    @Id
    @Column(name = "SPECIAL_CUST_ACCUMULATE_ID", precision = 12, scale = 0, nullable = false)
    private Long specialCustAccumulateId;

    @Column(name = "SUBS_ID", precision = 12, scale = 0)
    private Long subsId;

    @Column(name = "ACCU_TYPE_ID", precision = 6, scale = 0)
    private Long accuTypeId;

    @Column(name = "CUST_ID", precision = 12, scale = 0)
    private Long custId;

    @Column(name = "COUNT", precision = 6, scale = 0)
    private Integer count;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private Long spId;
}