package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FijiSubsNotesId implements Serializable {

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "SEQ")
    private Integer seq;
}