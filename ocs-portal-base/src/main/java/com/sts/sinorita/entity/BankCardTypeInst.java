package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.BankCardTypeInstId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "BANK_CARD_TYPE_INST")
public class BankCardTypeInst implements Serializable {
    @EmbeddedId
    private BankCardTypeInstId id;

    @Column(name = "SP_ID")
    private Integer spId;
}
