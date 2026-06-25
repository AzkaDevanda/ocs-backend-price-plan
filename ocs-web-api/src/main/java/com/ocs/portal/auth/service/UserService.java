package com.ocs.portal.auth.service;



import com.ocs.portal.auth.entity.UserLogin;
import com.ocs.portal.auth.repository.UserRepository;
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
