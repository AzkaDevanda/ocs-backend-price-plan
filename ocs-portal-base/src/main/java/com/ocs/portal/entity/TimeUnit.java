package com.ocs.portal.entity;

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
@Table(name = "TIME_UNIT", schema = "STS")
public class TimeUnit {

    @Id
    @Column(name = "TIME_UNIT", nullable = false)
    private Character id;

    @Column(name = "TIME_UNIT_NAME", nullable = false)
    private String timeUnitName;

    @Column(name = "COMMENTS")
    private String comments;
}
