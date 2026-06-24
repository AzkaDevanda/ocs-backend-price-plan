package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScriptTempletListDto {
    private Integer TemplateId;
    private String scriptTempletName;
    private String templetContent;
    private String templetTypeScript;
}
