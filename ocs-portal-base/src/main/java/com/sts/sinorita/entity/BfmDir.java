package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "BFM_DIR", schema = "POT")
@Data
public class BfmDir {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_bfm_dir_id_seq")
    @SequenceGenerator(name = "t_bfm_dir_id_seq", sequenceName = "T_BFM_DIR_ID_SEQ", allocationSize = 1)
    @Column(name = "DIR_ID", nullable = false)
    private Long dirId;

    @Column(name = "DIR_NAME", length = 255)
    private String dirName;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "ICON_URL", length = 255)
    private String iconUrl;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "DIR_CODE", length = 255)
    private String dirCode;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "CLIENT_TYPE", length = 8)
    private String clientType;
}
