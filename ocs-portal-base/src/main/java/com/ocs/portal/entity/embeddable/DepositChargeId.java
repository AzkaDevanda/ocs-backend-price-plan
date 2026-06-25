package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class DepositChargeId implements Serializable {

    @Column(name = "EVENT_INST_ID")
    private Long eventInstId;

    @Column(name = "PRICE_ID")
    private Long priceId;

    // Equals dan HashCode wajib untuk Composite Key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositChargeId that = (DepositChargeId) o;
        return Objects.equals(eventInstId, that.eventInstId) &&
                Objects.equals(priceId, that.priceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventInstId, priceId);
    }
}
