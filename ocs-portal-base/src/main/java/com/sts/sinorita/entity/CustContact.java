package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CUST_CONTACT", schema = "STS")
public class CustContact implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_CONTACT_ID_GEN")
//    @SequenceGenerator(name = "CUST_CONTACT_ID_GEN", sequenceName = "CUST_CONTACT_ID_SEQ", allocationSize = 1)
    @Column(name = "CUST_CONTACT_ID", nullable = false)
    private Long custContactId;

    @Column(name = "CUST_ID", nullable = false)
    private Long custId;

    @Column(name = "CONTACT_EVENT_ID", length = 1)
    private String contactEventId;

    @Column(name = "PARENT_CUST_CONTACT_ID")
    private Long parentCustContactId;

    @Column(name = "CONTACT_CHANNEL_ID", nullable = false)
    private Integer contactChannelId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "CONTACT_TYPE", length = 1, nullable = false)
    private String contactType;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "CONTACT_OBJ", length = 255)
    private String contactObj;

    @Column(name = "IP_PORT", length = 30)
    private String ipPort;

    @Column(name = "CONTACT_CONTENT", length = 4000)
    private String contactContent;

    @Column(name = "RELA_ID")
    private Long relaId;

    @Column(name = "APP_ID", length = 30)
    private String appId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "TRANSACTION_ID", length = 60)
    private String transactionId;

    @Column(name = "PART_ID", nullable = false, updatable = false)
    private Integer partId;

    @Column(name = "QT_VER_ID")
    private Long qtVerId;


    @PrePersist
    protected void onCreate() {
        // 1. Set Created Date jika null
        if (this.createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }

        // 2. Logic PART_ID (Sangat Penting untuk Tabel Partitioned)
        // Kita mengikuti logic DB: ambil angka bulan (1-12)
        if (this.partId == null) {
            this.partId = this.createdDate.getMonthValue();
        }

        // 3. Default Mandatory Values (jika belum diisi)
        if (this.contactType == null) {
            this.contactType = "A"; // Default Inbound
        }

        if (this.contactChannelId == null) {
            this.contactChannelId = 1; // Sesuaikan dengan master channel Anda
        }
    }
}
