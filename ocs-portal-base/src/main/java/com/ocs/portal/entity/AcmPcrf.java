package com.ocs.portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACM_PCRF")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcmPcrf {

    @Id
    @Column(name = "ACM_THRESHOLD_ID")
    private Long acmThresholdId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "TRIGGER_MODE")
    private String triggerMode;
}

