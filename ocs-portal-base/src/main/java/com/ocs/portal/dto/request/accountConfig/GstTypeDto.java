package com.ocs.portal.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GstTypeDto {
    private String gstType;
    private String gstTypeName;
    private String comments;
}
