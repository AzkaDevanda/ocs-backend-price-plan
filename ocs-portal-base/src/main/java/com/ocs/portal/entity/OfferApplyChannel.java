package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_APPLY_CHANNEL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferApplyChannel {
    @Id
    @Column(name = "OFFER_ID")
    private Integer id;

    @Column(name = "CONTACT_CHANNEL_ID")
    private Integer contactChannelId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "EXCLUDE_FLAG")
    private Character excludeFlag;
}
