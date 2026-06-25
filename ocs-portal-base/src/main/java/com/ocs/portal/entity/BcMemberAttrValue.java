package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BcMemberAttrValueId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BC_MEMBER_ATTR_VALUE", schema = "STS")
public class BcMemberAttrValue {

    @EmbeddedId
    private BcMemberAttrValueId id;

    @Column(name = "VALUE", length = 4000)
    private String value;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "SP_ID")
    private Long spId;
}
