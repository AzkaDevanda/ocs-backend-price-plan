package com.ocs.portal.entity.embeddable;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class EventSettPK  implements Serializable {
    private Long eventPaymentId;
    private Long acctBookId;

    public EventSettPK() {}

    public EventSettPK(Long eventPaymentId, Long acctBookId) {
        this.eventPaymentId = eventPaymentId;
        this.acctBookId = acctBookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventSettPK)) return false;
        EventSettPK that = (EventSettPK) o;
        return Objects.equals(eventPaymentId, that.eventPaymentId)
                && Objects.equals(acctBookId, that.acctBookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventPaymentId, acctBookId);
    }
}