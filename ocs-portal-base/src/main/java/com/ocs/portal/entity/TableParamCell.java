package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TABLE_PARAM_CELL", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableParamCell {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_param_cell_seq")
    @SequenceGenerator(name = "table_param_cell_seq", sequenceName = "TABLE_PARAM_CELL_ID_SEQ", allocationSize = 1)
    @Column(name = "CELL_ID", nullable = false)
    private Long cellId;

    @Column(name = "COL_ID", nullable = false)
    private Long colId;

    @Column(name = "ZONE_ID", nullable = false)
    private Long zoneId;

    @Column(name = "TABLE_PARAM_ID", nullable = false)
    private Long tableParamId;

    @Column(name = "PARAM_VER")
    private Integer paramVer;

    @Column(name = "CELL_VALUE", length = 60)
    private String cellValue;

    @Column(name = "DISP_CONTENT", length = 120)
    private String dispContent;

    @Column(name = "DISP_PREFIX", length = 120)
    private String dispPrefix;

    @Column(name = "DISP_SUFFIX", length = 120)
    private String dispSuffix;

    @Column(name = "VALUE_TYPE", length = 3)
    private String valueType;

    @Column(name = "RATE_PRECISION")
    private Integer ratePrecision;

    @Column(name = "LOCK_FLAG", length = 1)
    private String lockFlag;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Builder.Default
    @Column(name = "TEMPLATE_FLAG", length = 1)
    private String templateFlag = "N";

    @Column(name = "SRC_CELL_ID")
    private Long srcCellId;

    @Column(name = "SP_ID")
    private Long spId;
}
