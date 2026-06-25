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
@Table(name = "GST_TYPE")
public class GstType implements Serializable {
    @Id
    @Column(name = "GST_TYPE", nullable = false, length = 1)
    private Character gstType;

    @Column(name = "GST_TYPE_NAME", nullable = false, length = 60)
    private String gstTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;
}
