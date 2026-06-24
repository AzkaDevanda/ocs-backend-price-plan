package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.BfmUserPrivId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BFM_USER_PRIV", schema = "POT")
public class BfmUserPriv {

    @EmbeddedId
    BfmUserPrivId id;

    @Column(name = "PRIV_LEVEL", length = 1)
    private String privLevel;

    @Column(name = "SP_ID")
    private Long spId;
}
