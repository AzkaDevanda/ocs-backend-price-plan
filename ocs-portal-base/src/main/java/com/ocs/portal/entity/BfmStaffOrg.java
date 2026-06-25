package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BFM_STAFF_ORG")
public class BfmStaffOrg implements Serializable {
    @Id
    @Column(name = "STAFF_ORG_ID", nullable = false)
    private Long staffOrgId;

    @Column(name = "STAFF_ID")
    private Long staffId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "IS_DEFAULT", length = 1)
    private String isDefault;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}
