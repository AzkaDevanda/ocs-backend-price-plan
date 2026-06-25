package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(
        name = "BFM_PROCESS_BO",
        schema = "STS"
)
public class BfmProcessBo implements Serializable {

    @Id
    @Column(name = "PROCESS_INST_ID", length = 60, nullable = false)
    private String processInstId;

    @Column(name = "BO_ID", length = 60, nullable = false)
    private String boId;

    @Column(name = "BO_ACCESS_NAME", length = 60)
    private String boAccessName;

    @Column(name = "BO_KEY_VALUE", length = 255, nullable = false)
    private String boKeyValue;

    @Column(name = "COMMON_DATA_ID", length = 60)
    private String commonDataId;

    @Column(name = "DISPATCH_ORDER_ID", precision = 14, scale = 0)
    private Long dispatchOrderId;

    @Column(name = "IS_TRACK", length = 1)
    private String isTrack;

    @Column(name = "ROUTING_ID", precision = 6, scale = 0)
    private Integer routingId;

    /**
     * DEFAULT: to_number(to_char(SYSDATE, 'mm'))
     * Jangan set manual kalau mau pakai default DB
     */
    @Column(
            name = "PART_ID",
            precision = 9,
            scale = 0,
            nullable = false,
            insertable = false
    )
    private Integer partId;

    @Lob
    @Column(name = "ACTIVITY_INSTANCE_TRACE")
    private byte[] activityInstanceTrace;
}