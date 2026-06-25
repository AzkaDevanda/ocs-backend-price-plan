package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.SimServTypeId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "SIM_SERV_TYPE")
public class SimServType implements Serializable {
    @EmbeddedId
    private SimServTypeId id;

    @Column(name = "SP_ID")
    private Long spId;
}
