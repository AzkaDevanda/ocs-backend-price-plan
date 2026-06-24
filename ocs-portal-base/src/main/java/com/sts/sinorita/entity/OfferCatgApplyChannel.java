package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OFFER_CATG_APPLY_CHANNEL")
public class OfferCatgApplyChannel {

    @Id
    @Column(name = "OFFER_CATG_ID", nullable = false)
    private Long id;

    @Column(name = "CONTACT_CHANNEL_ID", nullable = false)
    private Integer contactChannelId;

    @Column(name = "SP_ID")
    private Integer spId;
}
