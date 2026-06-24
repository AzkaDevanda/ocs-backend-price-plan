package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.SubsGoodsInstAttrId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SUBS_GOODS_INST_ATTR", schema = "STS")
public class SubsGoodsInstAttr implements Serializable {

    @EmbeddedId
    private SubsGoodsInstAttrId id;

    @Column(name = "VALUE", length = 3000)
    private String value;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}
