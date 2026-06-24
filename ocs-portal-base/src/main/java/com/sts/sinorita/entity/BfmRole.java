package com.sts.sinorita.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BFM_ROLE", schema = "POT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BfmRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_gen")
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "T_BFM_ROLE_ID_SEQ", allocationSize = 1)
    @Column(name = "ROLE_ID", nullable = false)
    private Integer roleId;

    @Column(name = "ROLE_NAME", length = 60, nullable = false)
    private String roleName;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "ROLE_CODE", length = 60, nullable = false)
    private String roleCode;

    @Column(name = "IS_LOCKED", length = 1)
    private Character isLocked;

    @Builder.Default
    @Column(name = "APP_ID")
    private Integer appId = 1;

    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", length = 60)
    private String name;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}
