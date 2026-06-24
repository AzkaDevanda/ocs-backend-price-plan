package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Embeddable
public class PaymentId {
    
    @Column(name = "PAYMENT_ID", nullable = false)
    private Long paymentId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

}
