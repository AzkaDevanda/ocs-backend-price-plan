package com.sts.sinorita.svc.role.controller.user;

import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.svc.role.service.users.SecurityRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class PermissionController {

    @Autowired
    SecurityRulesService securityRulesService;

    @GetMapping({"email/enabled"})
    public ResponseEntity<CustomeResponse> queryUserHasEmail() {
        return securityRulesService.querySysParamValueByMaskRc("USER_HAS_EMAIL");
    }

}
