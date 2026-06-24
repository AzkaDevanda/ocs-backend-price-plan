package com.sts.sinorita.svc.role.controller.roles;

import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.svc.role.service.roles.RoleCommonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/common/roles")
@Tag(name = "Common Role", description = "Common Role APIs")
public class CommonRoleController {

    @Autowired
    RoleCommonService commonRoleService;

    Logger logger = LoggerFactory.getLogger(CommonRoleController.class);

    @GetMapping(value = "role-list")
    public ResponseEntity<CustomeResponse> queryRoleList() {
        logger.info("Request queryRoleList");
        return commonRoleService.queryRoleList();
    }

    @GetMapping(value = "portal-list-by-spid/{spId}")
    public ResponseEntity<CustomeResponse> queryPortalListBySpid(@PathVariable Integer spId) {
        logger.info("Request queryPortalListBySpid");
        return commonRoleService.queryPortalList(spId);
    }

    @GetMapping(value = {"{roleId}/portals"})
    public ResponseEntity<CustomeResponse> queryPortalListByRoleId(@PathVariable Long roleId) {
        logger.info("Request queryPortalListByRoleId");
        return commonRoleService.queryPortalListRoleId(roleId);
    }

}
