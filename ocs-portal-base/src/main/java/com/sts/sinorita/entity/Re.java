package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "RE")
@SequenceGenerator(name = "re_seq", sequenceName = "STS.RE_ID_SEQ", allocationSize = 1)
public class Re implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "re_seq")
    @Column(name = "RE_ID", nullable = false)
    private Integer id;

    @Column(name = "RE_TYPE", nullable = false)
    private Character reType;

    @Column(name = "RE_NAME", nullable = false, length = 254)
    private String reName;

    @Column(name = "COMMENTS", length = 254)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "RE_CODE", length = 30)
    private String reCode;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

}
