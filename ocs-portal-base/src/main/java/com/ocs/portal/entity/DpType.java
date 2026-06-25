package com.ocs.portal.entity;

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
@Table(name = "DP_TYPE")
public class DpType {
    @Id
    @Column(name = "DP_TYPE", nullable = false)
    private Character dpType;

    @Column(name = "DP_TYPE_NAME", nullable = false)
    private String dpTypeName;

    @Column(name = "COMMENTS")
    private String comments;
}
