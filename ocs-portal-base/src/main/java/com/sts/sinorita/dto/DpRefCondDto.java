package com.sts.sinorita.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DpRefCondDto {
    private Integer dpRefCondId;
    private String dpRefCondType;
    private String dpRefCondName;
    private Integer dpRefCondParamNum;
    private String dataType;
    private String comments;
}
