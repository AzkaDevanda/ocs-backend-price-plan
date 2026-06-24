package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.ChannelUnitTypeId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHANNEL_UNIT_TYPE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelUnitType {

    @EmbeddedId
    private ChannelUnitTypeId id;

    @Column(name = "CONTACT_UNIT_CODE", nullable = false, length = 60)
    private String contactUnitCode;

    @Column(name = "CONTACT_UNIT_NAME", nullable = false, length = 60)
    private String contactUnitName;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "TRA_RATE")
    private Long traRate;

    @Column(name = "\"PRECISION\"")
    private Long precision;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "ROUND_WAY", length = 1)
    private String roundWay;

    @Column(name = "UNIT_CLASS", length = 60)
    private String unitClass;
}