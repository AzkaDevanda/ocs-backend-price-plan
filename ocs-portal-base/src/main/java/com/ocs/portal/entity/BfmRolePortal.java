package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BfmRolePortalId;
import jakarta.persistence.*;
import lombok.Data;

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
