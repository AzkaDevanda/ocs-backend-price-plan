package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.BfmRolePortalId;
import com.sts.sinorita.entity.embeddable.BfmRolePrivId;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BFM_ROLE_PORTAL", schema = "POT")
public class BfmRolePortal {


    @EmbeddedId
    private BfmRolePortalId id;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "IS_DEFAULT", length = 1)
    private String isDefault;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

}
