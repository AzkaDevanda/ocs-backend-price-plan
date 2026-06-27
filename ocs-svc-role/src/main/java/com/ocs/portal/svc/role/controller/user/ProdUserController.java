package com.ocs.portal.svc.role.controller.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.dto.request.ResetPwdRequest;
import com.ocs.portal.svc.role.dto.request.UserHisFilterDto;
import com.ocs.portal.svc.role.dto.request.UserReqParam;
import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleDto;
import com.ocs.portal.svc.role.dto.response.UserDto;
import com.ocs.portal.svc.role.service.roles.RoleService;
import com.ocs.portal.svc.role.service.users.UsersService;
import com.ocs.portal.svc.role.utils.LocalDateAdapter;
import com.ocs.portal.svc.role.utils.MessageUtil;
import com.ocs.portal.svc.role.utils.StringSanitizer;
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

}
