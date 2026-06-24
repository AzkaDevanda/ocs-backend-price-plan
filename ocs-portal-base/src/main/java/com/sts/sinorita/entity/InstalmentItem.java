package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "INSTALMENT_ITEM")
public class InstalmentItem {
    @EmbeddedId
    private InstalmentItemId id;

    @NotNull
    @Column(name = "ITEM_PERCENT", nullable = false)
    private Integer itemPercent;

    @NotNull
    @Column(name = "REPEAT_TIME", nullable = false)
    private Integer repeatTime;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "FEE_PERCENT")
    private Integer feePercent;

}