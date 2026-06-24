package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "IAD_PORT", schema = "STS")
public class IadPort {

    @Id
    @Column(name = "IAD_PORT_ID")
    private Long iadPortId;

    @Column(name = "IP_ID")
    private Long ipId;

    @Column(name = "PORT_NBR")
    private Long portNbr;

    @Column(name = "STATE")
    private String state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATE_DATE")
    private Date stateDate;

    @Column(name = "SP_ID")
    private Long spId;
}
