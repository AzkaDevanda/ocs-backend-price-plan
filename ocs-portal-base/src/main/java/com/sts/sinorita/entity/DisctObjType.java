package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DISCT_OBJ_TYPE")
public class DisctObjType {
    @Id
    @Column(name = "DISCT_OBJ_TYPE", nullable = false)
    private Character id;

    @Size(max = 60)
    @NotNull
    @Column(name = "DISCT_OBJ_TYPE_NAME", nullable = false, length = 60)
    private String disctObjTypeName;

    @Size(max = 120)
    @Column(name = "COMMENTS", length = 120)
    private String comments;

}