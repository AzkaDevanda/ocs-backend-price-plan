package com.sts.sinorita.auth.service;



import com.sts.sinorita.auth.entity.UserLogin;
import com.sts.sinorita.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
    UserRepository userRepository;

	public Optional<UserLogin> findByUsername(String username){
    return userRepository.findByUserName(username);
  }
}
