package com.ocs.portal.svc.role.controller.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.service.users.SecurityRulesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
@Tag(name = "Security Rules Controller", description = "Security APIs")
public class SequrityRulesController {

    @Autowired
    SecurityRulesService securityRulesService;

    @GetMapping(value = {"sysparams/securityrules/current"})
    ResponseEntity<CustomeResponse> queryCurrentSecurityRule() {
        return securityRulesService.queryCurrentSecurityRules();
    }


}
