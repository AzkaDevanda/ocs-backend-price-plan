package com.sts.sinorita.dto.response.rateplan;

import lombok.Data;

@Data
public class QryScriptTemplateDto {
    private Long scriptTempletId;
    private String scriptTempletName;
    private String comments;
    private String scriptTempletGroup;
    private String templetContent;
    private String templateFlag;
    private Long srcScriptTemplateId;
}
