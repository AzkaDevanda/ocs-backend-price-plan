package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BfmUserRoleId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BFM_USER_ROLE", schema = "POT")
public class BfmUserRole {

    @EmbeddedId
    private BfmUserRoleId id;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "USER_ROLE_TIMES")
    private Long userRoleTimes;

    @Column(name = "STAFF_ROLE_TIMES")
    private Long staffRoleTimes;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    // Getters and Setters

}
