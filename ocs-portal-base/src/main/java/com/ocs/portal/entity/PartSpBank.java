package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "PART_SP_BANK")
public class PartSpBank implements Serializable {
    @EmbeddedId
    private PartSpBankId id;

    @Column(name = "NNE", nullable = false, length = 30)
    private String nne;

    @Column(name = "BANK_ID")
    private Integer bankId;

    @Column(name = "ACCOUNT_NBR", nullable = false, length = 255)
    private String accountNbr;

    @Column(name = "BRANCH_CODE", length = 30)
    private String branchCode;
}
