package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SUBS_BIND_ORDER", schema = "STS")
public class SubsBindOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBS_BIND_ORDER_SEQ_GEN")
    @SequenceGenerator(
            name = "SUBS_BIND_ORDER_SEQ_GEN",
            sequenceName = "STS.SUBS_BIND_ORDER_ID_SEQ",
            allocationSize = 1
    )
    @Column(name = "SUBS_BIND_ORDER_ID", nullable = false)
    private Long subsBindOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "BIND_TYPE", length = 3, nullable = false)
    private String bindType;

    @Column(name = "OLD_SUBS_ID")
    private Long oldSubsId;

    @Column(name = "SRC_SUBS_ID", nullable = false)
    private Long srcSubsId;

    @Column(name = "OPERATION_TYPE", length = 1, nullable = false)
    private String operationType;

    @Column(name = "BIND_ORDER_ITEM_ID")
    private Long bindOrderItemId;

    @Column(name = "RELA_EFF_DATE")
    private LocalDateTime relaEffDate;

    @Column(name = "RELA_EXP_DATE")
    private LocalDateTime relaExpDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}
