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
@Table(name = "RECURRING_RE_TYPE")
public class RecurringReType {
    @Id
    @Column(name = "RECURRING_RE_TYPE", nullable = false, length = 1)
    private Character recurringReType;

    @Column(name = "RECURRING_RE_TYPE_NAME", nullable = false, length = 60)
    private String recurringReTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;
}
