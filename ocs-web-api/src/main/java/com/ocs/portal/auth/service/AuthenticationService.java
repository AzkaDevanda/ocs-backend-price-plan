package com.ocs.portal.auth.service;

import com.ocs.portal.auth.config.jwt.JwtService;
import com.ocs.portal.auth.entity.UserLogin;
import com.ocs.portal.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtUtil;

  // Autentikasi pengguna dan menghasilkan JWT token
  public UserLogin authenticateUser(String username, String password) {
    // Cari pengguna di database berdasarkan username
    UserLogin user = userRepository.findByUserName(username).orElseThrow(()-> new RuntimeException("User not found"));
    // Jika pengguna tidak ditemukan
     String pass = user.getPassword();
     String resultPass = pass.substring("{bcrypt}".length()).trim();

    // Verifikasi password dengan bcrypt
    if (!passwordEncoder.matches(password, resultPass)) {
      // Jika password tidak cocok,
      //      return jwtUtil.generateToken(user);
      throw new RuntimeException("Invalid credentials");
    }

    return user;
  }
}
