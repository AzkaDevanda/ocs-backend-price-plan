package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "SERV_TYPE_EVENT_RELA")
public class ServTypeEventRela {

    @Id
    @Column(name = "RELA_ID", nullable = false)
    private Long relaId;

    @Column(name = "EVENT_TYPE_ID")
    private Long eventTypeId;

    @Column(name = "SERV_TYPE", nullable = false)
    private Long servType;

    @Column(name = "SUBS_EVENT_ID", length = 60, nullable = false)
    private String subsEventId;

    @Column(name = "CONTACT_CHANNEL_ID", nullable = false)
    private Long contactChannelId;

    @Column(name = "CTRL_SCRIPT", length = 4000)
    private String ctrlScript;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "BOOKING_FLAG", length = 1)
    private String bookingFlag;

    @Column(name = "DISP_ORDER")
    private Long dispOrder;

    @Column(name = "SHORT_KEY", length = 60)
    private String shortKey;

    @Column(name = "DISPLAY_NAME", length = 60)
    private String displayName;

    @Column(name = "PORTAL_IDS", length = 255)
    private String portalIds;

}
