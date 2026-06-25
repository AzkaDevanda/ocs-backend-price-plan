package com.ocs.portal.svc.role.service.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.BfmRole;
import com.ocs.portal.svc.role.dto.request.roles.PortalDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.PortalChannelProjection;
import com.ocs.portal.svc.role.projection.PortalProjection;
import com.ocs.portal.svc.role.repository.PortalRepository;
import com.ocs.portal.svc.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
@Service
public class RoleCommonService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PortalRepository portalRepository;
    @Autowired
    private RoleMapper roleMapper;

    public ResponseEntity<CustomeResponse> queryRoleList() {
        List<BfmRole> list = roleRepository.selectAll();
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "Succes", list));
    }

    public ResponseEntity<CustomeResponse> queryPortalList(Integer spId) {
        List<PortalChannelProjection> list = portalRepository.selectPortalList(spId);
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "Succes", list));
    }

    public ResponseEntity<CustomeResponse> queryPortalListRoleId(Long roleId) {
        List<PortalDto> list = queryPortalListByRoleId(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "Succes", list));
    }

    public List<PortalDto> queryPortalListByRoleId(Long roleId) {
        List<PortalProjection> list = portalRepository.selectPortalListByRoleId(roleId);
        List<PortalDto> listResponse = new ArrayList<>();
        if (!list.isEmpty()) {
            listResponse = roleMapper.toListPortalDto(list);
        }
        return listResponse;
    }
}
