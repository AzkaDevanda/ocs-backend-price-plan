package com.ocs.portal.dto.response.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappingResponseDto {
    private int mappingId;
    private String mappingName;
    private List<PriceVerMappingResponseDto> priceVer;
}
