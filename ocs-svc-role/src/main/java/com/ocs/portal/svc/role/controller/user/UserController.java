package com.ocs.portal.svc.role.controller.user;

import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.svc.role.dto.request.roles.ProdPrivOperDto;
import com.ocs.portal.svc.role.service.users.FileHelperUsers;
import com.ocs.portal.svc.role.service.users.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UsersService usersService;

    @Autowired
    FileHelperUsers fileHelper;

    @GetMapping(value = {"{userId}/user/portals"})
    public ResponseEntity<BaseResponseDto> getUserPortalListByUserId(@PathVariable Long userId) {
        logger.info("getUserPortalListByUserId start");
        return usersService.queryUserPortalListByUserId(userId);
    }

    @PostMapping(value = {"{userId}/user/portals"})
    public ResponseEntity<BaseResponseDto> grantPortalToUser(@PathVariable Long userId, @RequestParam(value = "defaultPortalId", required = false) Long defaultPortalId, @RequestBody List<Long> portalList) {
        return usersService.grantPortalToUsers(userId, defaultPortalId, portalList);
    }

    // @GetMapping(value = {"{userId}/role/portals"})
    // public ResponseEntity<BaseResponseDto> queryRolePortalListByUserId(@PathVariable Long userId) {
    //     return usersService.queryRolePortalListByUserId(userId);
    // }

    // @GetMapping(value = {"{userId}/portals"})
    // public ResponseEntity<BaseResponseDto> getPortalListByUserId(@PathVariable Long userId) {
    //     return usersService.getPortalListByUserId(userId);
    // }

    // @GetMapping(value = {"{userId}/portlets"})
    // public ResponseEntity<BaseResponseDto> qryUserOwnedPortletLists(@PathVariable Long userId) {
    //     return usersService.qryUserOwnedPortletList(userId);
    // }

    // @GetMapping(value = {"{userId}/menus"})
    // public ResponseEntity<BaseResponseDto> queryPrivListByUserId(@PathVariable Long userId) {
    //     return usersService.queryPrivListByUserIds(userId);
    // }

    // @PostMapping(value = {"{userId}/privs/new"})
    // public ResponseEntity<BaseResponseDto> addPrivsToUserPrivNew(@PathVariable Long userId, @RequestBody List<ProdPrivOperDto> list) {
    //     return usersService.addPrivsToUserPrivNew(userId, list);
    // }

    // @DeleteMapping(value = {"{userId}/privs/new"})
    // public ResponseEntity<BaseResponseDto> delPrivsFromUserPrivNew(@PathVariable Long userId, @RequestBody List<ProdPrivOperDto> list) {
    //     return usersService.delPrivsFromUserPrivNew(userId, list);
    // }

    // @GetMapping("export-users")
    // public ResponseEntity<byte[]> exportUsers(@RequestParam Integer userId) throws IOException {
    //     byte[] excelBytes = fileHelper.exportUsers(userId);

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=UsersData.xlsx");
    //     headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    //     return ResponseEntity.ok().headers(headers).body(excelBytes);
    // }

}
