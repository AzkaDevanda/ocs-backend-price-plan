package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BILL_FORMAT_FLOW", schema = "STS")
public class BillFormatFlow {

    @EmbeddedId
    private BillFormatFlowId id;

    @Column(name = "TEMP_FILE")
    private String tempFile;

    @Column(name = "PRINT_FORMAT")
    private String printFormat;

    @Column(name = "OUT_PATH")
    private String outPath;

    @Column(name = "OUT_FILE")
    private String outFile;

    @Column(name = "BILL_ONDEMAND_FLAG")
    private String billOndemandFlag;

    @Column(name = "BILL_FORMAT_FLOW_TYPE")
    private String billFormatFlowType;

    @Column(name = "SP_ID")
    private Long spId;
}