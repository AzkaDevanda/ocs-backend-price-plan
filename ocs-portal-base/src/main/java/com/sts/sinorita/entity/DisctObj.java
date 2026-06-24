package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DISCT_OBJ")
public class DisctObj implements Serializable {
    @Id
    @Column(name = "DISCT_OBJ_ID", nullable = false)
    private Integer id;

    @ColumnDefault("-1")
    @Column(name = "OBJ_IDENTITY_ID")
    private Integer objIdentityId;

    @Column(name = "DISCT_OBJ_NAME", nullable = false, length = 60)
    private String disctObjName;

    @Column(name = "DISCT_OBJ_TYPE")
    private Character disctObjType;

    @Column(name = "TAB_DP_COND_GRP_ID", nullable = false)
    private Integer tabDpCondGrpId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "MEMBER_ALIAS")
    private String memberAlias;

    @ColumnDefault("-1")
    @Column(name = "GST_SEQ")
    private Short gstSeq;

}