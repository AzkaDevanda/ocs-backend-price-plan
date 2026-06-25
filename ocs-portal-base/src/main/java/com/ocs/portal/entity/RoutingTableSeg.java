package com.ocs.portal.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ROUTING_TABLE_SEG", schema = "STS")
@IdClass(RoutingTableSeg.RoutingTableSegId.class)
public class RoutingTableSeg implements Serializable {

    @Id
    @Column(name = "SUBS_ID_TYPE", nullable = false)
    private Long subsIdType;

    @Id
    @Column(name = "NBR_PREFIX", nullable = false, length = 60)
    private String nbrPrefix;

    @Id
    @Column(name = "ROUTING_ID", nullable = false)
    private Long routingId;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "LENGTH")
    private Long length;

    @Column(name = "IS_AUTO_FILL", length = 1)
    private String isAutoFill = "N";

    @Data
    public static class RoutingTableSegId implements Serializable {
        private Long subsIdType;
        private String nbrPrefix;
        private Long routingId;

        public RoutingTableSegId() {}

        public RoutingTableSegId(Long subsIdType, String nbrPrefix, Long routingId) {
            this.subsIdType = subsIdType;
            this.nbrPrefix = nbrPrefix;
            this.routingId = routingId;
        }
    }
}
