package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "FIJI_BESPEAK_INFO")
public class FijiBespeakInfo {

    @Id
    @Column(name = "FIJI_BESPEAK_INFO_ID", nullable = false)
    private Long id;

    @Column(name = "PREFIX", length = 32)
    private String prefix;

    @Column(name = "ACC_NBR", length = 32)
    private String accNbr;

    @Column(name = "INFO", length = 4000)
    private String info;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "ERR_REASON", length = 4000)
    private String errReason;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
