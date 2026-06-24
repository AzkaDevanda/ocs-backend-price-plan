package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Table(name = "MAPPING")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mapping implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mapping_seq_generator")
    @SequenceGenerator(name = "mapping_seq_generator", sequenceName = "MAPPING_ID_SEQ", allocationSize = 1)
    @Column(name = "MAPPING_ID", nullable = false)
    private Integer id;

    @Column(name = "RATE_PLAN_ID", nullable = false)
    private Integer ratePlanId;

    @Column(name = "MAPPING_NAME", nullable = false, length = 60)
    private String mappingName;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "MAPPING_DEFAULT")
    private Character mappingDefault;

    @ColumnDefault("'N'")
    @Column(name = "MAPPING_EXIT")
    private Character mappingExit;
}