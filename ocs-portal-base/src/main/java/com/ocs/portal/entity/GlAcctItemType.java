package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "GL_ACCT_ITEM_TYPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlAcctItemType implements Serializable {
    @EmbeddedId
    private GlAcctItemTypeId id;

    @Column(name = "SP_ID")
    private Integer spId;

}
