package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "SORT_OPERATOR", schema = "STS")
public class SortOperator implements Serializable {

    @Id
    @Column(name = "SORT_OPERATOR", nullable = false, length = 1)
    private String sortOperator;

    @Column(name = "SORT_OPERATOR_NAME", nullable = false, length = 60)
    private String sortOperatorName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;
}
