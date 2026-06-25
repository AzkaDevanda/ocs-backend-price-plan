package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.ProdEventProcessId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROD_EVENT_PROCESS")
@Data
@NoArgsConstructor
public class ProdEventProcess {

    @EmbeddedId
    private ProdEventProcessId id;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "AVP")
    private String avp;

    @Column(name = "SP_ID")
    private Integer spId;

    public Long getNodeId() {
        return id != null ? id.getNodeId() : null;
    }

    public Long getBcId() {
        return id != null ? id.getBcId() : null;
    }
}