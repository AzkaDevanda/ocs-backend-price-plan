package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstalmentTypeItem implements Serializable {
    @EmbeddedId
    private InstalmentTypeItemId id;

    @Column(name = "SP_ID")
    private Integer spId;
}
