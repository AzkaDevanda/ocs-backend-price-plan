package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdEventProcessId implements Serializable {

    @Column(name = "NODE_ID", nullable = false)
    private Long nodeId;

    @Column(name = "BC_ID", nullable = false)
    private Long bcId;
}
