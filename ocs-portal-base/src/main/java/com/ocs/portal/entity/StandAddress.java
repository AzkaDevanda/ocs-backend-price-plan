package com.ocs.portal.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(
        name = "STAND_ADDRESS",
        schema = "STS"
)
public class StandAddress {

    @Id
    @Column(name = "STD_ADDR_ID", nullable = false, precision = 15)
    private Long stdAddrId;

    @Column(name = "STD_ADDR", nullable = false, length = 4000)
    private String stdAddr;

    @Column(name = "STD_ADDR_LEVEL", nullable = false, precision = 3)
    private Integer stdAddrLevel;

    @Column(name = "AREA_ID", precision = 6)
    private Long areaId;

    @Column(name = "UP_STD_ADDR_ID", precision = 15)
    private Long upStdAddrId;

    @Column(name = "SP_ID", precision = 6)
    private Long spId;
}
