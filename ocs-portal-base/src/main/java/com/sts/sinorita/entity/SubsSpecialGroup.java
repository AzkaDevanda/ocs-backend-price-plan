package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SUBS_SPECIAL_GROUP", schema = "STS")
public class SubsSpecialGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBS_SPECIAL_GROUP_ID_GEN")
    @SequenceGenerator(name = "SUBS_SPECIAL_GROUP_ID_GEN", sequenceName = "SUBS_SPECIAL_GROUP_ID_SEQ", allocationSize = 1)
    @Column(name = "SUBS_SPECIAL_GROUP_ID", nullable = false)
    private Long subsSpecialGroupId;

    @Column(name = "SPECIAL_GROUP_TYPE_ID")
    private Long specialGroupTypeId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "ACC_NBR", length = 255)
    private String accNbr;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "PARTY_TYPE", length = 1, nullable = false)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "SP_ID")
    private Long spId;
}
