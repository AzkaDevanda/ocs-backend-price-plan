package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class BankCardTypeInstId implements Serializable {

    @Column(name = "BANK_CARD_TYPE", nullable = false)
    private Integer bankCardType;

    @Column(name = "BANK_ID", nullable = false)
    private Integer bankId;
}
