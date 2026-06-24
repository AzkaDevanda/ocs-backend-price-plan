package com.sts.sinorita.dto.response.accountconfig;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryBalTypeResponseDto {
    private String comments;
    private String balTypeName;
    private Integer balType;
}
