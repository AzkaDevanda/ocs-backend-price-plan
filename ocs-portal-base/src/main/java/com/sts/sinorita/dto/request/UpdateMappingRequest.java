package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMappingRequest {
    private String mappingName;
    List<UpdateMappingUnitRequest> mappingUnit;
}
