package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BalAdviceId;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Table(name = "BAL_ADVICE")
public class BalAdvice {

    @EmbeddedId
    private BalAdviceId id;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ADVICE_NBR", length = 60)
    private String adviceNbr;

    @Column(name = "TRIGGER_MODE")
    private Character triggerMode;

    @ColumnDefault("-1")
    @Column(name = "ADVICE_EVENT_ID")
    private Integer adviceEventId;

    @Column(name = "NOTIFY_PARAMS_ID")
    private Integer notifyParamsId;


}