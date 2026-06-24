package com.sts.sinorita.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BillFormatFlowId implements Serializable {

    @Column(name = "BILL_FORMAT_ID")
    private Long billFormatId;

    @Column(name = "BILL_FLOW_ID")
    private Long billFlowId;

}
