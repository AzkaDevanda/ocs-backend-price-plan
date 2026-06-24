package com.sts.sinorita.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BFM_PORTAL",schema = "POT")
public class Portal {
    @Id
    @Column(name = "PORTAL_ID", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Optional, tergantung DB
    private Long portalId;

    @Column(name = "PORTAL_NAME", length = 255)
    @Size(max = 255)
    private String portalName;

    @Column(name = "URL", length = 255)
    @Size(max = 255)
    private String url;

    @Column(name = "EXTRA_URL", length = 255)
    @Size(max = 255)
    private String extraUrl;

    @Column(name = "ICON_URL", length = 255)
    @Size(max = 255)
    private String iconUrl;

    @Column(name = "STATE", length = 1)
    @Size(max = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "APP_ID")
    private Long appId;

    @Column(name = "INDEX_ID")
    private Long indexId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "ALLOW_EXTERNAL_ACCESS", length = 1)
    @Size(max = 1)
    private String allowExternalAccess;
}
