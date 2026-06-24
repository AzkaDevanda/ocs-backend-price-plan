package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AdviceTypeTimeSpanId implements Serializable {
    @Column(name = "ADVICE_TYPE", nullable = false, precision = 6)
    private Long adviceType;

    @Column(name = "SEQ", nullable = false, precision = 3)
    private Integer seq;
}