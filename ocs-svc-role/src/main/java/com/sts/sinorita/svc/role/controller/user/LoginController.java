package com.sts.sinorita.svc.role.controller.user;

import com.sts.sinorita.dto.request.LoginDto;
import com.sts.sinorita.dto.request.RefreshTokenRequestDto;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.login.ResponseDto;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.svc.role.auth.config.jwt.JwtService;
import com.sts.sinorita.svc.role.auth.entity.UserLogin;
import com.sts.sinorita.svc.role.auth.repository.UserRepository;
import com.sts.sinorita.svc.role.auth.service.AuthenticationService;
import com.sts.sinorita.svc.role.auth.service.JWTBlacklist;
import com.sts.sinorita.svc.role.dto.request.roles.ProdRolePrivDto;
import com.sts.sinorita.svc.role.dto.response.UserDto;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.projection.StaffJobProjection;
import com.sts.sinorita.svc.role.service.roles.RoleMenuService;
import com.sts.sinorita.svc.role.service.roles.RoleUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@Tag(name = "Authentication")
@RequestMapping(value = "auth")
public class LoginController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    JwtService jwtService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);


    @PostMapping(value = "login")
    public ResponseEntity<BaseResponseDto> doLogin(@RequestBody LoginDto loginDto) {
        log.info(":: login request ::");
        UserLogin authenticatedUser = authenticationService.authenticateUser(loginDto.getUserName(), loginDto.getPassword());

//        find user_role id
        List<UserDto> listUser = roleUserService.queryUserListByUserId(authenticatedUser.getId().longValue());
        List<Long> roleId = new ArrayList<>();
        List<String> roleName = new ArrayList<>();
        for (UserDto user : listUser) {
            roleId.add(user.getRoleId());
            roleName.add(user.getRoleName());
        }
        List<ProdRolePrivDto> listMenu = roleMenuService.queryMenuByRoleId(roleId);

        String jwtToken = jwtService.generateToken(authenticatedUser, roleName);
        String newRefreshToken = jwtService.generateRefreshToken(authenticatedUser.getUsername());

        List<StaffJobProjection> list = roleUserService.getListJob(authenticatedUser.getUsername());

        ResponseDto responseDto = new ResponseDto();
        responseDto.setUserId(authenticatedUser.getId());
        responseDto.setUsername(authenticatedUser.getUsername());
        responseDto.setUserCode(authenticatedUser.getUserCode());
        responseDto.setForceLogin(authenticatedUser.getForceLogin());
        responseDto.setJobs(list);
        responseDto.setToken(jwtToken);
        responseDto.setMenus(roleMapper.toListMenu(listMenu));
        responseDto.setExpired(jwtService.getExpirationTime());
        responseDto.setRefreshToken(newRefreshToken);

        BaseResponseDto baseResponseDto = new BaseResponseDto();
        baseResponseDto.setData(responseDto);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return ResponseEntity.ok(baseResponseDto);
    }


//    @PostMapping("/signup")
//    public ResponseEntity<UserLogin> register(@RequestBody LoginDto registerUserDto) throws Exception {

    /// /        UserLogin registeredUser = authenticationService.sig(registerUserDto);
//        return ResponseEntity.ok(new UserLogin());
//    }
    @PostMapping("logout")
    public ResponseEntity<BaseResponseDto> doLogout(@RequestHeader("Authorization") String authorizationHeader) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        String token = authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : authorizationHeader;

        // Add the token to the blacklist
        JWTBlacklist.addToBlacklist(token);

        // Optionally clear the authentication context (for stateless auth)
        SecurityContextHolder.clearContext();
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        return ResponseEntity.ok(baseResponseDto);
    }



    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();

        try {
            if (!jwtService.isRefreshTokenValid(refreshToken))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expired");

            String username = jwtService.extractUsername(refreshToken);

            UserLogin user = userRepository.findByUserName(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

            // ambil roles
            List<UserDto> listUser = roleUserService.queryUserListByUserId(user.getId().longValue());
            List<String> roleName = listUser.stream().map(UserDto::getRoleName).collect(Collectors.toList());

            String newAccessToken = jwtService.generateToken(user, roleName);
            String newRefreshToken = jwtService.generateRefreshToken(username);

            ResponseDto responseDto = new ResponseDto();
            responseDto.setToken(newAccessToken);
            responseDto.setRefreshToken(newRefreshToken);
            responseDto.setExpired(jwtService.getExpirationTime());

            BaseResponseDto baseResponseDto = new BaseResponseDto();
            baseResponseDto.setData(responseDto);
            baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
            baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
            return ResponseEntity.ok(baseResponseDto);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }
    }


}
