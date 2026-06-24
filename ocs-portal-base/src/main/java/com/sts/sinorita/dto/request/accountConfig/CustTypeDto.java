package com.sts.sinorita.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustTypeDto {
    private Character CustType;
    private String CustTypeName;
    private String Comments;
}
