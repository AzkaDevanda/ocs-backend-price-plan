package com.sts.sinorita.svc.role.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProdSecurityRuleDto extends SecurityRuleDto implements Serializable {
    private Long userExpireDays;
}
