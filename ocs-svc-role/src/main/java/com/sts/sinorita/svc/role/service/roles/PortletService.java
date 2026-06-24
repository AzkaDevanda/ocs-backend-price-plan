package com.sts.sinorita.svc.role.service.roles;

import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.svc.role.dto.response.PortletTypeDto;
import com.sts.sinorita.svc.role.dto.response.PortletsDto;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.projection.PortletProjection;
import com.sts.sinorita.svc.role.projection.PortletTypeProjection;
import com.sts.sinorita.svc.role.repository.BfmPortletsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortletService {

    @Autowired
    BfmPortletsRepository bfmPortletsRepository;

    Logger logger = LoggerFactory.getLogger(PortletService.class);
    @Autowired
    RoleMapper roleMapper;

    public ResponseEntity<CustomeResponse> queryPortletTypeList() {
        logger.info("queryPortletTypeList()");
        List<PortletTypeProjection>list = bfmPortletsRepository.queryPortletTypeList();
        List<PortletTypeDto> response = roleMapper.toListPortletTypeDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", response));
    }

    public ResponseEntity<CustomeResponse> queryPortletTypeById(Long roleId, Long typeId) {
        logger.info("queryPortletTypeById()");
        List<PortletProjection>  listProjection = bfmPortletsRepository.findPortletsByRoleIdAndTypeId(roleId,typeId);
        List<PortletsDto> list = roleMapper.toListPortletsDto(listProjection);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
    }

    public ResponseEntity<CustomeResponse> findPortletsByRoleAndPortal(Long roleId, Long portalId) {
        logger.info("findPortletsByRoleAndPortal()");
        List<PortletProjection>  listProjection = bfmPortletsRepository.findPortletsByRoleAndPortal(roleId,portalId);
        List<PortletsDto> list = roleMapper.toListPortletsDto(listProjection);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
    }


}
