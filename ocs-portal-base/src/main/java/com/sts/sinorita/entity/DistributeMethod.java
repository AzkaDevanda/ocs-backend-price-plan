package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISTRIBUTE_METHOD")
public class DistributeMethod {
    @Id
    @Column(name = "DISTRIBUTE_METHOD", nullable = false, length = 1)
    private Character distributeMethod;

    @Column(name = "DISTRIBUTE_METHOD_NAME", nullable = false, length = 60)
    private String distributeMethodName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;
}
