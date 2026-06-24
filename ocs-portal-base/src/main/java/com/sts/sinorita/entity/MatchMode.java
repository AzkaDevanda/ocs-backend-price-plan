package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "MATCH_MODE")
public class MatchMode implements Serializable {
    @Id
    @Column(name = "MATCH_MODE", length = 1, nullable = false)
    private Character matchMode;

    @Column(name = "MATCH_MODE_NAME", length = 60, nullable = false)
    private String matchModeName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;
}
