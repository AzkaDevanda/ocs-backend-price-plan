package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class NotePaymentId implements Serializable {

    @Column(name = "EVENT_PAYMENT_ID", nullable = false)
    private Long eventPaymentId;

    @Column(name = "NOTE_ID", nullable = false)
    private Long noteId;
}