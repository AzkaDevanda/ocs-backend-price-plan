package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCT_RES_FREE", schema = "STS")
public class AcctResFree {
    @Id
    @Column(name = "ACCT_RES_ID")
    private Long id;

    @Column(name = "VALUE")
    private Long value;

    @Column(name = "RUM")
    private Integer rum;

    @Column(name = "SP_ID")
    private Integer spId;
}
