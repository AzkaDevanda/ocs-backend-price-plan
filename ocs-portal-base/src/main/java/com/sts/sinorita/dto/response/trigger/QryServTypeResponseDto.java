package com.sts.sinorita.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryServTypeResponseDto {
    private Integer servType;
    private String servTypeName;
    private Character networkType;
    private Character catgType;
    private Character paidFlag;
    private String stdCode;
    private String networkTypeName;
}
