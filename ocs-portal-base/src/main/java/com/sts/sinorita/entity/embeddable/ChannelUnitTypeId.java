package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ChannelUnitTypeId implements Serializable {

    @Column(name = "CONTACT_CHANNEL_ID")
    private Long contactChannelId;

    @Column(name = "UNIT_TYPE_ID")
    private Long unitTypeId;
}
