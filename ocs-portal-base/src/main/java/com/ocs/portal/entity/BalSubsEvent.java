package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BalSubsEventId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "BAL_SUBS_EVENT")
public class BalSubsEvent implements Serializable {

    @EmbeddedId
    BalSubsEventId id;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "REF_ATTR")
    private String refAttr;

    @Column(name = "TRIGGER_MODE")
    private Character triggerMode;

    @Column(name = "ANTIBILL_SHOCK")
    private Character antibillShock;

    @Column(name = "EXT_ATTR", length = 1000)
    private String extAttr;

    @Column(name = "NOTIFY_PARAMS_ID")
    private Integer notifyParamsId;


}