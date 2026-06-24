package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "BFM_PORTLETS", schema = "POT")
public class BfmPortlet {

    @Id
    @Column(name = "PORTLET_ID", nullable = false)
    private Long portletId;

    @Column(name = "TYPE_ID")
    private Long typeId;

    @Column(name = "PORTLET_NAME", length = 60)
    private String portletName;

    @Column(name = "DEFAULT_TITLE", length = 255)
    private String defaultTitle;

    @Lob
    @Column(name = "DEFAULT_PARAM")
    private String defaultParam;

    @Column(name = "SHOW_HEADER", length = 1)
    private Character showHeader;

    @Column(name = "IS_PUBLIC", length = 1)
    private Character isPublic;

    @Column(name = "MAXABLE", length = 1)
    private Character maxable;

    @Column(name = "COLLAPSABLE", length = 1)
    private Character collapsable;

    @Column(name = "SETTABLE", length = 1)
    private Character settable;

    @Column(name = "REFRESHABLE", length = 1)
    private Character refreshable;

    @Column(name = "CLOSABLE", length = 1)
    private Character closable;

    @Column(name = "STATE", length = 1)
    private Character state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "ICON", length = 255)
    private String icon;

    @Column(name = "PORTLET_CODE", length = 100)
    private String portletCode;

    @Column(name = "SP_ID")
    private Long spId;

}
