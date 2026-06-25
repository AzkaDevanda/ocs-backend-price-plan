package com.ocs.portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "ORDER_REASON",
        schema = "STS",
        uniqueConstraints = {
                @UniqueConstraint(name = "PK_ORDER_REASON", columnNames = "ORDER_REASON_ID")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReason {

    @Id
    @Column(name = "ORDER_REASON_ID", nullable = false)
    private Long orderReasonId;

    @Column(name = "ORDER_REASON_NAME", nullable = false, length = 60)
    private String orderReasonName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "SUBS_EVENT_ID")
    private Long subsEventId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "DEFAULT_FLAG", length = 1)
    private String defaultFlag;
}

