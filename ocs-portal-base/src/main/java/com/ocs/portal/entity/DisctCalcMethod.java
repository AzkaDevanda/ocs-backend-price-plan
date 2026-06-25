package com.ocs.portal.entity;

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
@Table(name = "DISCT_CALC_METHOD")
public class DisctCalcMethod {
    @Id
    @Column(name = "DISCT_CALC_METHOD", nullable = false)
    private Character id;

    @Size(max = 60)
    @NotNull
    @Column(name = "DISCT_CALC_METHOD_NAME", nullable = false, length = 60)
    private String disctCalcMethodName;

    @Size(max = 3000)
    @Column(name = "COMMENTS", length = 3000)
    private String comments;

}