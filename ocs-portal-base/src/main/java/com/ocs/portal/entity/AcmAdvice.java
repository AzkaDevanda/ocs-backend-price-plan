package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ACM_ADVICE")
public class AcmAdvice implements Serializable {

    @Id
    @Column(name = "ACM_THRESHOLD_ID", nullable = false)
    private Integer acmThresholdId;

    @ColumnDefault("-1")
    @Column(name = "ADVICE_TYPE")
    private Integer adviceType;

    @Column(name = "TRIGGER_MODE")
    private Character triggerMode;

    @Column(name = "SP_ID")
    private Integer spId;

    @ColumnDefault("-1")
    @Column(name = "ADVICE_EVENT_ID")
    private Integer adviceEventId;

    @Column(name = "NOTIFY_PARAMS_ID")
    private Integer notifyParamsId;


}