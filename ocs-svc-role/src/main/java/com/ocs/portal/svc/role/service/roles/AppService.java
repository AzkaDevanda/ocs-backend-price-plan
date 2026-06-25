package com.ocs.portal.svc.role.service.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.dto.response.AppDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.AppDtoProjection;
import com.ocs.portal.svc.role.repository.AppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {
    Logger logger = LoggerFactory.getLogger(AppService.class);

    @Autowired
    AppRepository appRepository;

    @Autowired
    RoleMapper roleMapper;

    public ResponseEntity<CustomeResponse> queryAppList(){
        logger.debug("queryAppList()");
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200,"success",selectAppList()));
    }

    private List<AppDto> selectAppList(){
        List<AppDtoProjection> appDtos = appRepository.selectAppList();
        return roleMapper.toListAppDto(appDtos);
    }
}
