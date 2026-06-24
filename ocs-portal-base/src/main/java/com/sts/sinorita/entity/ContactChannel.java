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
@Table(name = "CONTACT_CHANNEL")
public class ContactChannel {

    @Id
    @Column(name = "CONTACT_CHANNEL_ID")
    private Integer contactChannelId;

    @Column(name = "CHANNEL_TYPE")
    private Integer channelType;

    @Column(name = "CONTACT_CHANNEL_NAME")
    private String contactChannelName;

    @Column(name = "SYSTEM_RESERVE")
    private Character systemReserve;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "CONTACT_CHANNEL_CODE")
    private String contactChannelCode;

}
