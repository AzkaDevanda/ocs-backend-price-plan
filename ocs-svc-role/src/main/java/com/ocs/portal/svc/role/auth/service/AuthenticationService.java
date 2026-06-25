package com.ocs.portal.svc.role.auth.service;


import com.ocs.portal.svc.role.auth.config.jwt.JwtService;
import com.ocs.portal.svc.role.auth.entity.UserLogin;
import com.ocs.portal.svc.role.auth.repository.UserRepository;
import com.ocs.portal.svc.role.service.roles.LogService;
import com.ocs.portal.svc.role.utils.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtUtil;

  @Autowired
  LogService logService;


  // Autentikasi pengguna dan menghasilkan JWT token
  public UserLogin authenticateUser(String username, String password) {
    // Cari pengguna di database berdasarkan username
    UserLogin user = userRepository.findByUserName(username)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

    logService.setUserId(user.getId().toString());

    if(user.getIsLocked().equalsIgnoreCase("Y")){
      log.info("user is locked");
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is locked");
    }

    if (user.getState().equalsIgnoreCase("X")){
      log.info("user is disabled");
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User has been disabled");
    }

    // Jika pengguna tidak ditemukan
     String pass = user.getPassword();
     String resultPass = pass.substring("{bcrypt}".length()).trim();

    // Verifikasi password dengan bcrypt
    if (!passwordEncoder.matches(password, resultPass)) {
      // Jika password tidak cocok,

      //return jwtUtil.generateToken(user);
      LogEvent logEvent = LogEvent.getLogEvent("INVALID_CREDENTIAL");
      logService.addAuthLog(logEvent,"INVALID_CREDENTIAL", 0);


//        kalau user sudah melebihi max
      int fail = Optional.ofNullable(user.getLoginFail()).orElse(0) + 1;
      userRepository.updateStateUser(user.getId().longValue(), "N",LocalDateTime.now(),fail);
      int max = 3;
      int msgIn = max - fail;
      if (fail >= max){
        //update locked
        userRepository.updateStateUser(user.getId().longValue(), "Y",LocalDateTime.now(), fail);
        log.info("USER LOCK {}", user.getId()+ " "+ user.getUsername() +" "+ user.getIsLocked());
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user is locked");
      }
      String msg ="Incorrect username or password. You have tried to log in " + fail + " times. You have "+ msgIn +" attempt left before your account is locked.";

      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, msg);
    }

    userRepository.updateStateUser(user.getId().longValue(), "N",LocalDateTime.now(),0);
    userRepository.updateLastLogin(user.getId().longValue(), LocalDateTime.now());

    return user;
  }
}
