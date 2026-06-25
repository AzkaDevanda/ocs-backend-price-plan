package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "BFM_APP", schema = "POT")
public class BfmApp {

    @Id
    @Column(name = "APP_ID")
    private Long appId;

    @Column(name = "APP_CODE", length = 60)
    private String appCode;

    @Column(name = "APP_NAME", length = 60)
    private String appName;

    @Column(name = "APP_URL", length = 255)
    private String appUrl;

    @Column(name = "ICON_PATH", length = 255)
    private String iconPath;

    @Column(name = "COMMENTS", length = 500)
    private String comments;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;
}