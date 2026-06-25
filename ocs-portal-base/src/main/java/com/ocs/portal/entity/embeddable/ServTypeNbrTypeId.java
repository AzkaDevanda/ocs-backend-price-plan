package com.ocs.portal.entity.embeddable;

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
public class ServTypeNbrTypeId {
    @Column(name = "ACC_NBR_TYPE_ID", nullable = false)
    private Long accNbrTypeId;


    @Column(name = "SERV_TYPE", nullable = false)
    private Long servType;
}
