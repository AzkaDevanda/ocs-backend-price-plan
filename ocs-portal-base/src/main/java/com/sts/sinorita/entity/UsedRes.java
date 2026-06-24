package com.sts.sinorita.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USED_RES", schema = "STS")
public class UsedRes {

    @Id
    @Column(name = "USED_RES_ID", nullable = false, precision = 15)
    private Long usedResId;

    @Column(name = "PROD_ID", nullable = false, precision = 12)
    private Long prodId;

    @Column(name = "RES_TYPE", nullable = false, length = 1)
    private String resType;

    @Column(name = "RES_ID", nullable = false, precision = 12)
    private Long resId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "SP_ID", precision = 6)
    private Long spId;

}
