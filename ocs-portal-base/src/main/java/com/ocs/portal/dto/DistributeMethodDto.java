package com.ocs.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributeMethodDto {
    private Character distributeMethod;
    private String distributeMethodName;
}
