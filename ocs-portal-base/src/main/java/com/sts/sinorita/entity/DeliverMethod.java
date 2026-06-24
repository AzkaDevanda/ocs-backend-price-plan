package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DELIVER_METHOD", schema = "STS")
@Getter
@Setter
public class DeliverMethod {

    @Id
    @Column(name = "DELIVER_METHOD", length = 1, nullable = false)
    private String deliverMethod;

    @Column(name = "DELIVER_METHOD_NAME", length = 60, nullable = false)
    private String deliverMethodName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;
}
