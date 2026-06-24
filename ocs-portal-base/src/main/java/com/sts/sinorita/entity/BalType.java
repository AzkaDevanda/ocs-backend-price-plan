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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BAL_TYPE")
public class BalType implements Serializable {

    @Id
    @Column(name = "BAL_TYPE", nullable = false)
    private Integer balTypeId;

    @Column(name = "BAL_TYPE_NAME", nullable = false, length = 60)
    private String balTypeName;

    @Column(name = "COMMENTS", length = 300)
    private String comments;
}
