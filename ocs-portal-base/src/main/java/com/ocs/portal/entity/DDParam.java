package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "DD_PARAM")
public class DDParam {
    @Id
    @Column(name = "PAYMENT_METHOD_ID", nullable = false)
    private Integer paymentMethodId;

    @Column(name = "DAYS_BEF_EXTRA")
    private Integer daysBefExtra;

    @Column(name = "SP_IBAN", nullable = false, length = 60)
    private String spIban;

    @Column(name = "REISSUE_DELAY", nullable = false)
    private Integer reissueDelay = 15;

    @Column(name = "CLOSE_MANDATE_LIMIT", nullable = false)
    private Integer closeMandateLimit = 3;

    @Column(name = "RESERVE1", length = 255)
    private String reserve1;

    @Column(name = "RESERVE2", length = 255)
    private String reserve2;

    @Column(name = "RESERVE3", length = 255)
    private String reserve3;

    @Column(name = "RESERVE4", length = 255)
    private String reserve4;

    @Column(name = "RESERVE5", length = 255)
    private String reserve5;

    @Column(name = "RESERVE6", length = 255)
    private String reserve6;

    @Column(name = "RESERVE7", length = 255)
    private String reserve7;

    @Column(name = "RESERVE8", length = 255)
    private String reserve8;

    @Column(name = "RESERVE9", length = 255)
    private String reserve9;

    @Column(name = "RESERVE10", length = 255)
    private String reserve10;
}
