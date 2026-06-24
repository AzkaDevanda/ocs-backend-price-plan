package com.sts.sinorita.svc.role.auth.service;

import com.sts.sinorita.svc.role.auth.entity.UserLogin;
import com.sts.sinorita.svc.role.auth.repository.UserRepository;
import com.sts.sinorita.svc.role.dto.response.UserDto;
import com.sts.sinorita.svc.role.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  UserMapper userMapper;

  public Optional<UserLogin> findByUsername(String username) {
    return userRepository.findByUserName(username);
  }

  public List<UserDto> selectUsersListAll(String state) {
    return userMapper.toListUserDto(userRepository.selectUsersListAll(state));
  }
}
