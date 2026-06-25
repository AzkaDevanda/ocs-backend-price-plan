package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BalShareItemId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "BAL_SHARE_ITEM", schema = "STS")
public class BalShareItem {

    @EmbeddedId
    private BalShareItemId id;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "COMMENTS")
    private String comments;

}
