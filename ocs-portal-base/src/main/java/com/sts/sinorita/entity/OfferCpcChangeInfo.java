package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "OFFER_CPC_CHANGE_INFO", schema = "STS")
public class OfferCpcChangeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_cpc_change_info_seq_generator")
    @SequenceGenerator(name = "offer_cpc_change_info_seq_generator", sequenceName = "OFFER_CPC_CHANGE_INFO_ID_SEQ", allocationSize = 1)
    @Column(name = "OFFER_CPC_CHANGE_INFO_ID", nullable = false)
    private Long offerCpcChangeInfoId;

    @Column(name = "OFFER_CPC_TASK_ID", nullable = false)
    private Long offerCpcTaskId;

    @Column(name = "OFFER_CPC_EXPORT_ID")
    private Long offerCpcExportId;

    @Column(name = "CREATE_STAFF_ID", nullable = false)
    private Long createStaffId;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "TFM_SERV_LOG_ID")
    private Long tfmServLogId;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "TABLE_NAME", nullable = false, length = 30)
    private String tableName;

    @Column(name = "ACTION", nullable = false, length = 1)
    private String action;

    // KEY & VALUE reserved keyword di Oracle
    @Column(name = "KEY", nullable = false, length = 400)
    private String key;

    @Column(name = "VALUE", nullable = false, length = 4000)
    private String value;

    @Column(name = "CONFLICT_DATE")
    private LocalDateTime conflictDate;

    @Column(name = "CONFLICT_STAFF_ID")
    private Long conflictStaffId;

    @Column(name = "SOLUTION_DATE")
    private LocalDateTime solutionDate;

    @Column(name = "SOLUTION_STAFF_ID")
    private Long solutionStaffId;

    @Column(name = "EXPORT_DATE")
    private LocalDateTime exportDate;

    @Column(name = "EXPORT_STAFF_ID")
    private Long exportStaffId;

    @Lob
    @Column(name = "ORI_DATA")
    private String oriData;

    @Lob
    @Column(name = "TAR_DATA")
    private String tarData;

    @Column(name = "UUID", length = 40)
    private String uuid;
}