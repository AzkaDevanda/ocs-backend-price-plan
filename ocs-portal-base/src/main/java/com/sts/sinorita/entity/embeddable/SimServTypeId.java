package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimServTypeId {
    @Column(name = "SERV_TYPE", nullable = false)
    private Long servType;

    @Column(name = "SIM_TYPE_ID", nullable = false)
    private Long simTypeId;
}
