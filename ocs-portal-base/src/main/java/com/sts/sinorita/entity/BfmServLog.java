package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BFM_SERV_LOG", schema = "POT")
public class BfmServLog {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BFM_SERV_LOG_SEQ_generator")
        @SequenceGenerator(name = "BFM_SERV_LOG_SEQ_generator", sequenceName = "T_BFM_LOG_ID_SEQ", allocationSize = 1)
        @Column(name = "LOG_ID", nullable = false)
        private Long logId;

        @Column(name = "LOG_TYPE", length = 12)
        private String logType;

        @Column(name = "SERVICE_NAME", length = 64)
        private String serviceName;

        @Column(name = "CONTACT_CHANNEL", length = 64)
        private String contactChannel;

        @Column(name = "LOG_DATE")
        private LocalDateTime logDate;

        @Column(name = "SUBS_ID", length = 12)
        private String subsId;

        @Column(name = "CUST_ID", length = 12)
        private String custId;

        @Column(name = "COMMENTS", length = 4000)
        private String comments;

        @Column(name = "EVENT_SRC", length = 60)
        private String eventSrc;

        @Column(name = "EVENT_TYPE", length = 255)
        private String eventType;

        @Column(name = "EVENT_CODE", length = 255)
        private String eventCode;

        @Column(name = "PARTY_TYPE", length = 30)
        private String partyType;

        @Column(name = "PARTY_CODE", length = 255)
        private String partyCode;

        @Column(name = "PARTY_NAME", length = 60)
        private String partyName;

        @Column(name = "PARTY_ID")
        private Long partyId;

        @Column(name = "PART_ID")
        private Integer partId;

        @Column(name = "MENU_ID")
        private Integer menuId;

        @Column(name = "LOG_TIMESTAMP")
        private Long logTimestamp;

        @Column(name = "SRC_IP", length = 64)
        private String srcIp;

        @Column(name = "SERVER_IP", length = 64)
        private String serverIp;

        @Column(name = "URL", length = 512)
        private String url;

        @Column(name = "MENU_TYPE", length = 1)
        private String menuType;

        @Column(name = "PRIV_NAME", length = 256)
        private String privName;

        @Column(name = "IS_SUCCESS")
        private Integer isSuccess;

        @Column(name = "DURING_TIME")
        private Integer duringTime;

        @Column(name = "ONLINE_TIME")
        private Integer onlineTime;

        @Column(name = "LAST_OPER_TIME")
        private LocalDate lastOperTime;

        @Column(name = "SP_ID")
        private Long spId;

        @Column(name = "SESSION_ID", length = 60)
        private String sessionId;

        @Column(name = "BIS_TRANS_ID", length = 64)
        private String bisTransId;

        @Column(name = "EMAIL", length = 255)
        private String email;

        @Column(name = "PORTAL_ID")
        private Long portalId;

        @Column(name = "APP_NAME", length = 255)
        private String appName;

        @Column(name = "created_by", length = 100)
        private String createdBy;
}

