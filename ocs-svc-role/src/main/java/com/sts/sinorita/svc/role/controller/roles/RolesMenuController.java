package com.sts.sinorita.svc.role.controller.roles;

import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.svc.role.dto.request.roles.ProdRolePrivDto;
import com.sts.sinorita.svc.role.service.roles.PortalService;
import com.sts.sinorita.svc.role.service.roles.RoleMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/roles/menus")
public class RolesMenuController {

    Logger logger = LoggerFactory.getLogger(RolesMenuController.class);

    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    PortalService portalService;


    @GetMapping(value = {"{roleId}/menus"})
    public ResponseEntity<CustomeResponse> queryPrivListByRoleId(@PathVariable Long roleId) {
        logger.info("Request queryPrivListByRoleId");
        return roleMenuService.queryPrivListByRoleId(roleId);
    }

    @RequestMapping(value = {"{roleId}/menus/{menuId}/components"}, method = {RequestMethod.GET})
    public ResponseEntity<CustomeResponse> queryCompListByMenuId(@PathVariable Long menuId, @PathVariable Long roleId){
        logger.info("Request queryCompListByMenuId");
        if (menuId == 0){
            menuId = null;
        }
        return roleMenuService.queryRoleCompListByMenuId(menuId, roleId);
    }

    @PutMapping(value = {"autoOpen"})
    public ResponseEntity<CustomeResponse> modRoleAutoOpen(@RequestBody ProdRolePrivDto rolePrivDto) {
        logger.info("Request modRoleAutoOpen");
        return roleMenuService.modRoleAutoOpen(rolePrivDto);
    }

    @PutMapping(value = {"access/update"})
    public ResponseEntity<CustomeResponse> modRoleUpdate(@RequestBody ProdRolePrivDto rolePrivDto) {
        logger.info("Request access/update");
        return roleMenuService.update(rolePrivDto);
    }

    @GetMapping(value = {"{roleId}/components"})
    public ResponseEntity<CustomeResponse>queryRoleComponentPrivList(@PathVariable Long roleId){
        logger.info("Request queryRoleComponentPrivList");
        return roleMenuService.queryRoleComponentPrivLis(roleId);
    }

    @GetMapping(value = {"{roleId}/nomenu/components"})
    public ResponseEntity<CustomeResponse>queryCompListNoMenuId(@PathVariable Long roleId){
        logger.info("Request queryCompListByMenuId");
        return roleMenuService.queryRoleCompListByMenuId(null, roleId);
    }

}
