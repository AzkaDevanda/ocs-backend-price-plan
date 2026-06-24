package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "VVU_APN_IP")
public class ApnIp implements Serializable {
    @Id
    @Column(name = "APN_IP_ID")
    private Long apnIpId;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "IP_STATE")
    private String ipState;

    @Column(name = "BOUND_SERVICE_NBR")
    private String boundServiceNbr;

    @Column(name = "APN_NAME")
    private String apnName;

    @Column(name = "CREATED_DATE")
    private Date createdDate;
}
