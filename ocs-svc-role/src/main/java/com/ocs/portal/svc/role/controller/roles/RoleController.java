package com.ocs.portal.svc.role.controller.roles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import com.ocs.portal.svc.role.dto.request.roles.*;
import com.ocs.portal.svc.role.service.roles.*;
import com.ocs.portal.svc.role.dto.response.ProdRolesDto;
import com.ocs.portal.svc.role.dto.response.UserDto;
import com.ocs.portal.svc.role.utils.LocalDateAdapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/roles")
@Tag(name = "Role Controller", description = "Role APIs")
public class RoleController {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RolePrivService rolePrivService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    PortalService portalService;

    @Autowired
    RoleService roleService;
    @Autowired
    RoleMenuService roleMenuService;

    @PostMapping(value = "prod/roles")
    public ResponseEntity<CustomeResponse> addRole(@RequestBody @Validated ProdRolesDto dto) {
        log.info("Request addRole : {}", gson.toJson(dto));
        return roleService.addRoles(dto);
    }


    @PutMapping(value = "prod/roles")
    public ResponseEntity<CustomeResponse> modRole(@RequestBody @Validated ProdRolesDto dto) {
        log.info("Request modRole : {}", gson.toJson(dto));
        return roleService.modifyRole(dto);
    }

    @DeleteMapping(value = "prod/roles/{roleId}")
    public ResponseEntity<CustomeResponse> delRole(@PathVariable Long roleId) {
        log.info("Request delRole : {}", roleId);
        return roleService.delRole(roleId);
    }

    @PostMapping(value = {"{roleId}/privs/new"})
    public ResponseEntity<CustomeResponse> grantPrivToRoleNew(@PathVariable Long roleId, @RequestBody List<ProdPrivOperDto> list) {
        log.info("Request grantPrivToRoleNew begin ...");
        return rolePrivService.grantPrivToRoleNew(roleId.intValue(), list);
    }

    @DeleteMapping(value = {"{roleId}/privs"})
    public ResponseEntity<CustomeResponse> degrantPrivToRole(@PathVariable Long roleId, @RequestBody List<Long> list) {
        log.info("degrantPrivToRole begin ...");
        rolePrivService.delUserPrivsByRoleId(roleId, list);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }

    @PutMapping(value = {"{roleId}/users/new"})
    public ResponseEntity<CustomeResponse> grantUserToRoleNew(@PathVariable Long roleId, @RequestBody List<UserRoleExtDto> list) {
        log.info("Request grantUserToRoleNew begin ...");
        return roleUserService.addUserRoles(roleId, list);
    }

    @DeleteMapping(value = {"{roleId}/users/new"})
    public ResponseEntity<CustomeResponse> ungrantUserFromRoleNew(@PathVariable Long roleId, @RequestBody List<UserRoleDto> list) {
        log.info("Request ungrantUserFromRoleNew begin ...");
        return roleUserService.ungrantUserToRoleNew(roleId, list);
    }

    @PutMapping(value = {"{roleId}/portals/new"})
    public ResponseEntity<CustomeResponse> grantPortalToRoleNew(@PathVariable Long roleId, @RequestBody List<PortalDto> list) {
        log.info("Request grantPortalToRoleNew begin ...");
        return portalService.grantPortalToRoleNews(roleId, list);
    }

    @DeleteMapping(value = {"{roleId}/portals"})
    public ResponseEntity<CustomeResponse> ungrantPortalFromRole(@PathVariable Long roleId, @RequestBody List<PortalDto> list) {
        log.info("Request ungrantPortalToRole begin ...");
        return portalService.ungrantPortalToRole(roleId, list);
    }

    @GetMapping(value = {"dirs/{dirId}/menus"})
    public ResponseEntity<CustomeResponse> queryMenuListByDirId(@PathVariable Long dirId) {
        log.info("Request queryMenuListByDirId");
        return roleMenuService.meQueryMenuListByDirId(dirId);
    }

    @GetMapping(value = {"{roleId}/users/grant/filter"})
    public ResponseEntity<CustomeResponse> qryUserListByRoleIdAndFilter(@ModelAttribute PagingRequestDto pagingRequestDto, @PathVariable Long roleId, UserDto userDto) {
        log.info("Request qryUserListByRoleIdAndFilter");
        return roleUserService.qryUserListByRoleIdAndFilter(pagingRequestDto, userDto, roleId);
    }

    @GetMapping(value = {"{roleId}/users/ungrant/filter"})
    public ResponseEntity<CustomeResponse> queryUnGrantUserListByRoleIdAndFilter(@PathVariable Long roleId, UserDto userDto, @ModelAttribute PagingRequestDto page) {
        log.info("Request queryUnGrantUserListByRoleIdAndFilter");
        return roleUserService.queryUnGrantUserListByRoleIdAndFilter(page, userDto, roleId);
    }

    @GetMapping(value = {"{roleId}/portlets"})
    public ResponseEntity<CustomeResponse> queryPortletListByRoleId(@PathVariable Long roleId) {
        log.info("Request queryPortletListByRoleId");
        return roleUserService.queryRoleOwnedPortletList(roleId);
    }

    @PutMapping(value = {"privlevel"})
    public ResponseEntity<CustomeResponse> modRolePrivLevel(@RequestBody ProdRolePrivDto rolePrivDto) {
       return rolePrivService.modRolePrivLevel(rolePrivDto);
    }


}
