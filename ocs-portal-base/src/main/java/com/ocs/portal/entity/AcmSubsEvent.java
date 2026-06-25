package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.AcmSubEventId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACM_SUBS_EVENT")
public class AcmSubsEvent implements Serializable {


    @EmbeddedId
    AcmSubEventId id;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "EXT_ATTR", length = 1000)
    private String extAttr;

    @Column(name = "TRIGGER_MODE")
    private Character triggerMode;

    @Column(name = "ANTIBILL_SHOCK")
    private Character antibillShock;

    @Column(name = "NOTIFY_PARAMS_ID")
    private Integer notifyParamsId;

}