package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ATTR_APPLY_CHANNEL")
public class AttrApplyChannel {
    @EmbeddedId
    private AttrApplyChannelId id;

    @Column(name = "SP_ID")
    private Integer spId;
}
