package com.ocs.portal.svc.role.service.roles;

import com.ocs.portal.entity.BfmUserRoleHis;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleExtDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleHisDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.repository.UserRoleHisRepository;
import com.ocs.portal.svc.role.repository.UserRoleRepository;
import com.ocs.portal.svc.role.repository.custom.UserRoleHisCustomRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
public class UserRoleHisService {
    @Autowired
    UserRoleHisRepository userRoleHisRepository;
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserRoleHisCustomRepository userRoleHisCustomRepository;

    public void recordUserRoleHis(List<UserRoleDto> userRoles) {
        List<UserRoleExtDto> userRoleExtDtos = userRoleHisCustomRepository.selectUserRoles(userRoles);
        insertUserRoleHis(userRoleExtDtos);
    }

    private void insertUserRoleHis(List<UserRoleExtDto> userRoleExtDtos) {
        try {
            LocalDate date = LocalDate.now();
            List<BfmUserRoleHis> userRoleHisDtos = new ArrayList<>();
            for (UserRoleExtDto userRoleExtDto : userRoleExtDtos) {
                UserRoleHisDto userRoleHisDto = new UserRoleHisDto();
                BeanUtils.copyProperties(userRoleExtDto, userRoleHisDto);
                userRoleHisDto.setCreatedDate(date);
                userRoleHisDto.setUpdateDate(date);
                userRoleHisDto.setOperatorType("D");
                BfmUserRoleHis userHis = roleMapper.toBfmRoleHis(userRoleHisDto);
                userRoleHisDtos.add(userHis);
            }
            userRoleHisRepository.saveAll(userRoleHisDtos);
            log.info("insertUserRoleHis success");
        } catch (Exception e) {
            log.info("error {}", e.getMessage());

        }
    }

}
