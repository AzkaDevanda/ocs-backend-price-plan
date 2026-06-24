package com.sts.sinorita.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BAL_PCRF", schema = "STS")
@Data
public class BalPcrf {

    @Id
    @Column(name = "BAL_THRESHOLD_ID", nullable = false)
    private Long balThresholdId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "TRIGGER_MODE", length = 1)
    private String triggerMode;
}
