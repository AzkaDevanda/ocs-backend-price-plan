package com.ocs.portal.entity;

import java.io.Serializable;

import com.ocs.portal.entity.embeddable.CcSubsAcmId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CC_SUBS_ACM", schema = "MDBTT")
public class CcSubsAcm implements Serializable {

    @EmbeddedId
    private CcSubsAcmId id;

    @Column(name = "VALUE")
    private Long value;

    @Column(name = "RESERVE_VALUE")
    private Long reserveValue;

}