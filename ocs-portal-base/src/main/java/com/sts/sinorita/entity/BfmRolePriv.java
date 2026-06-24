package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.BfmRolePrivId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BFM_ROLE_PRIV", schema = "POT")
public class BfmRolePriv {

    @EmbeddedId
    private BfmRolePrivId id;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PRIV_LEVEL", length = 1)
    private String privLevel;

    @Column(name = "IS_AUTHORIZED", length = 1)
    private String isAuthorized;

    @Column(name = "AUTO_OPEN_MENU", length = 1)
    private String autoOpenMenu;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @Column(name = "STS_ADD")
    private Character addStatus;

    @Column(name = "STS_EDIT")
    private Character editStatus;

    @Column(name = "STS_READ")
    private Character readStatus;

    @Column(name = "STS_DELETE")
    private Character deleteStatus;
}
