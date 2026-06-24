package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ATTR_CATG")
public class AttrCatg implements Serializable {
    @Id
    @Column(name = "ATTR_CATG", nullable = false)
    private Integer attrCatg;

    @Column(name = "ATTR_CATG_NAME", nullable = false)
    private String attrCatgName;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;
}
