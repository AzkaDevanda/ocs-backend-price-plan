package com.ocs.portal.svc.role.service.roles;

import com.ocs.portal.svc.role.dto.response.PortalMenuDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.PortalMenuDirProjection;
import com.ocs.portal.svc.role.projection.PortalPrivProjection;
import com.ocs.portal.svc.role.repository.RoleMenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PortalMenuService {

    Logger logger = LoggerFactory.getLogger(PortalMenuService.class);

    @Autowired
    RoleMenuRepository roleMenuRepository;

    @Autowired
    RoleMapper roleMapper;


    public List<PortalMenuDto> selectSubDirListByParentInPortal(Long partyId, Long portalId) {
        List<PortalMenuDirProjection> list = roleMenuRepository.findByPortalIdAndParentId(portalId, partyId);
        logger.info("selectSubDirListByParentInPortal : list size: " + list.size());
        return roleMapper.doListPortalMenuDto(list);
    }


    public List<PortalMenuDto> selectMenuListByPartyIdRoleId(Long partyId, Long portalId) {
        List<PortalPrivProjection> list = roleMenuRepository.selectMenuListByPartyIdRoleId(portalId, partyId);
        logger.info("selectMenuListByPartyIdRoleId : list size: " + list.size());
        return roleMapper.doListPortalMenuDtoFromPriv(list);
    }

}
