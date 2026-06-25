package com.ocs.portal.entity;

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
@Table(name = "ACCT_ITEM_TYPE_BIND_TYPE")
public class AcctItemTypeBindType {
    @Id
    @Column(name = "ACCT_ITEM_TYPE_BIND_TYPE", nullable = false, length = 1)
    private Character acctItemTypeBindType;

    @Column(name = "BIND_TYPE_NAME", length = 60)
    private String bindTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

}
