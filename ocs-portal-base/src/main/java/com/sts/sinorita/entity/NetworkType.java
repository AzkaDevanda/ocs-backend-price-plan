package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NETWORK_TYPE")
public class NetworkType {
    @Id
    @Column(name = "NETWORK_TYPE", nullable = false)
    private Character networkType;

    @Column(name = "NETWORK_TYPE_NAME", nullable = false)
    private String networkTypeName;

    @Column(name = "COMMENTS")
    private String comments;
}
