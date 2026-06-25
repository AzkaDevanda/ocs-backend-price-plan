package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "GOODS_ORDER", schema = "STS")
public class GoodsOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_ORDER_SEQ")
    @SequenceGenerator(
            name = "GOODS_ORDER_SEQ",
            sequenceName = "GOODS_ORDER_ID_SEQ",
            allocationSize = 1
    )
    @Column(name = "GOODS_ORDER_ID", nullable = false)
    private Long goodsOrderId;

    @Column(name = "GOODS_PROD_SPEC_ID")
    private Long goodsProdSpecId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "GOODS_PKG_ID")
    private Long goodsPkgId;

    @Column(name = "GOODS_ID")
    private Long goodsId;

    @Column(name = "GOODS_SN", length = 255)
    private String goodsSn;

    @Column(name = "SUBS_GOODS_INST_ID")
    private Long subsGoodsInstId;

    @Column(name = "MODEL_UNIT_ID")
    private Integer modelUnitId;

    @Column(name = "CNT")
    private Integer cnt;

    @Column(name = "OLD_GOODS_PKG_ID")
    private Long oldGoodsPkgId;

    @Column(name = "OLD_GOODS_PROD_SPEC_ID")
    private Long oldGoodsProdSpecId;

    @Column(name = "OLD_GOODS_ID")
    private Long oldGoodsId;

    @Column(name = "OLD_GOODS_SN", length = 60)
    private String oldGoodsSn;

    @Column(name = "OLD_CNT")
    private Integer oldCnt;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "PORT_FLAG", length = 1)
    private String portFlag;

    @Column(name = "REASON_ID")
    private Integer reasonId;

    @Column(name = "RETURN_REASON", length = 255)
    private String returnReason;

    @Column(name = "MODEL_ID")
    private Long modelId;

    @Column(name = "PROMOTION_CODE", length = 30)
    private String promotionCode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "IS_GIFT", length = 1)
    private String isGift;

    @Column(name = "GOODS_PRICE_ID")
    private Long goodsPriceId;
}
