package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RE_PP_RECURRING")
public class RePpRecurring implements Serializable {
    @EmbeddedId
    private RePpRecurringId id;

    @Column(name = "RECURRING_RE_TYPE", nullable = false)
    private Character recurringReType;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "STATE")
    private Character state;

}