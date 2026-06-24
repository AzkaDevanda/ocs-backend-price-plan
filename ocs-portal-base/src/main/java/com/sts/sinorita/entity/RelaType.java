package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RELA_TYPE")
public class RelaType {
    @Id
    @Column(name = "RELA_TYPE", nullable = false)
    private Integer id;

    @Column(name = "RELA_TYPE_NAME", nullable = false)
    private String relaTypeName;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "ORI_OFFER_FLAG")
    private Character oriOfferFlag;

    @Column(name = "ORI_GROUP_FLAG")
    private Character oriGroupFlag;

    @Column(name = "DEST_OFFER_FLAG")
    private Character destOfferFlag;

    @Column(name = "DEST_GROUP_FLAG")
    private Character destGroupFlag;


}
