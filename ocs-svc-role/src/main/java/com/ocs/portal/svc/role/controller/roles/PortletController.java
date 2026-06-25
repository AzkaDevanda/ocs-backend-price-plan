package com.ocs.portal.svc.role.controller.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.service.roles.PortletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/portlets")
public class PortletController {

    @Autowired
    PortletService portletService;

    Logger logger = LoggerFactory.getLogger(PortletController.class);

    @GetMapping(value = {"portlets/types"})
    public ResponseEntity<CustomeResponse> queryPortletTypes() {
        logger.info("Request queryPortletTypes");
       return portletService.queryPortletTypeList();
    }


    @GetMapping(value = {"{roleId}/types/{typeId}/portlets"})
    public ResponseEntity<CustomeResponse> queryPortletListByTypeIdLeftJoinRoleOwnedInfo(@PathVariable Long roleId, @PathVariable Long typeId) {
        logger.info("Request queryPortletListByTypeIdLeftJoinRoleOwnedInfo");
        return portletService.queryPortletTypeById(roleId, typeId);
    }

    @GetMapping(value = {"{roleId}/portals/{portalId}/portlets"})
    public ResponseEntity<CustomeResponse> queryPortletListByPortalIdLeftJoinRoleOwnedInfo(@PathVariable Long roleId, @PathVariable Long portalId) {
        logger.info("Request queryPortletListByPortalIdLeftJoinRoleOwnedInfo");
        return portletService.findPortletsByRoleAndPortal(roleId, portalId);
    }
}
