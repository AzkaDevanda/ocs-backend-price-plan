package com.sts.sinorita.svc.role.controller.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.svc.role.dto.request.ResetPwdRequest;
import com.sts.sinorita.svc.role.dto.request.UserHisFilterDto;
import com.sts.sinorita.svc.role.dto.request.UserReqParam;
import com.sts.sinorita.svc.role.dto.request.common.PagingRequestDto;
import com.sts.sinorita.svc.role.dto.request.roles.UserRoleDto;
import com.sts.sinorita.svc.role.dto.response.UserDto;
import com.sts.sinorita.svc.role.service.roles.RoleService;
import com.sts.sinorita.svc.role.service.users.UsersService;
import com.sts.sinorita.svc.role.utils.LocalDateAdapter;
import com.sts.sinorita.svc.role.utils.MessageUtil;
import com.sts.sinorita.svc.role.utils.StringSanitizer;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "api/prod/users")
@Tag(name = "Prod User Controller", description = "Users APIs")
public class ProdUserController {

    Logger logger = LoggerFactory.getLogger(ProdUserController.class);

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .create();

    @Autowired
    UsersService usersService;

    @Autowired
    RoleService roleService;

    @GetMapping(value = {"{userId}/roles"})
    public ResponseEntity<CustomeResponse> getRoleListByUserId(@PathVariable Long userId) {
        logger.info("getRoleListByUserId = " + userId);
        return usersService.getRoleListByUserIdWithoutJob(userId);
    }

    @GetMapping(value = "getMessage/{code}")
    public String getMessage(@PathVariable("code") String code) {
        return MessageUtil.getMessage(code);
    }

    @GetMapping(value = "list")
    public ResponseEntity<CustomeResponse> queryUserList(@ModelAttribute PagingRequestDto pagingRequestDto, @ModelAttribute UserReqParam user) {
        if (pagingRequestDto.getSortBy() == null){
            pagingRequestDto.setSortBy("userId");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", usersService.queryUserListRc(user, pagingRequestDto)));
    }

    @PostMapping(value = "add")
    public ResponseEntity<CustomeResponse> addUser(@RequestBody @Valid UserDto user) throws MessagingException {

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", usersService.addUser(user)));
    }

    @PutMapping(value = "edit")
    public ResponseEntity<CustomeResponse> editUser(@RequestBody @Valid UserDto user) throws MessagingException {

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", usersService.editUser(user)));
    }

    @PatchMapping(value = "{userId}/pwd")
    public ResponseEntity<CustomeResponse> resetPwd(@PathVariable Long userId, @RequestBody ResetPwdRequest request) throws MessagingException {
        StringSanitizer.sanitize(request);
        usersService.resetPassword(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }

    @PutMapping(value = "{userId}/pwd")
    public ResponseEntity<CustomeResponse> editPwd(@PathVariable Long userId, @RequestBody ResetPwdRequest request) throws MessagingException {
        usersService.editPasswordByUser(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }

    @PatchMapping(value = {"{userId}/lock"})
    public ResponseEntity<CustomeResponse> lockUser(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Integer userId, @RequestParam(required = false) String opReason) {
//        String token = authorizationHeader.startsWith("Bearer ")
//                ? authorizationHeader.substring(7)
//                : authorizationHeader;

        return usersService.lockUser("", userId, opReason);
    }

    @PatchMapping(value = {"{userId}/unLock"})
    public ResponseEntity<CustomeResponse> unLockUser(@PathVariable Integer userId, @RequestParam(required = false) String opReason) {
        return usersService.unLock(userId, opReason);
    }


    @PatchMapping(value = {"{userId}/disable"})
    public ResponseEntity<CustomeResponse> disableUser(@PathVariable Integer userId, @RequestParam(required = false) String opReason) {
        return usersService.disableUser(userId, opReason);
    }

    @PatchMapping(value = {"{userId}/enable"})
    public ResponseEntity<CustomeResponse> enableUser(@PathVariable Integer userId, @RequestParam(required = false) String opReason) {
        return usersService.enableUser(userId, opReason);
    }

    @DeleteMapping(value = {"/{userId}/remove"})
    public Object removeUser(@PathVariable Long userId) {
        return usersService.removeUser(userId, "");
    }

    @PostMapping(value = "{userId}/roles/new")
    public ResponseEntity<CustomeResponse> grantRoleToUserNew(@PathVariable Long userId, @RequestBody @Validated List<UserRoleDto> dto) {
        logger.info("Request grantRoleToUserNew : {}", gson.toJson(dto));
        return roleService.grantRoleToUserNews(userId, dto);
    }

    @DeleteMapping(value = {"{userId}/roles/new"})
    public ResponseEntity<BaseResponseDto> degrantRoleFromUserNew(@PathVariable Long userId, @RequestBody List<UserRoleDto> roleList) {
        logger.info("Request degrantRoleFromUserNew {}", gson.toJson(roleList));
        return roleService.degrantRoleFromUserNew(userId, roleList);
    }

    @GetMapping(value = {"{userId}/components"})
    public ResponseEntity<BaseResponseDto> queryUserComponentPrivList(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, roleService.queryUserComponentPrivList(userId)));
    }

    @GetMapping(value = {"history"})
    public ResponseEntity<CustomeResponse> queryUserHistory(@ModelAttribute PagingRequestDto page, @ModelAttribute UserHisFilterDto dto) {
        return usersService.queryUserHistory(dto, page);
    }
}
