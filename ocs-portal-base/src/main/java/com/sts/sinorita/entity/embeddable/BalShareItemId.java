package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BalShareItemId implements Serializable {

    @Column(name = "BAL_SHARE_ID")
    private Long balShareId;

    @Column(name = "ACCT_ITEM_TYPE_ID")
    private Long acctItemTypeId;
}
