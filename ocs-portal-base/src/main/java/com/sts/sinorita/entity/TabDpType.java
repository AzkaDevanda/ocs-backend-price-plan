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
@Table(name = "TAB_DP_TYPE")
public class TabDpType {
    @Id
    @Column(name = "TAB_DP_TYPE", nullable = false, length = 1)
    private Character tabDpType;

    @Column(name = "TAB_DP_TYPE_NAME", nullable = false, length = 60)
    private String tabDpTypeName;

    @Column(name = "COMMENTS", length = 150)
    private String comments;
}
