package com.ocs.portal.svc.role.service.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.dto.request.roles.ProdPrivOperDto;
import com.ocs.portal.svc.role.dto.request.roles.ProdRolePrivDto;
import com.ocs.portal.svc.role.dto.request.roles.RolePrivDto;
import com.ocs.portal.svc.role.dto.response.ProdComponentPrivDto;
import com.ocs.portal.svc.role.dto.response.ProdRolesDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.ProdComponentPrivProjection;
import com.ocs.portal.svc.role.projection.RolePrivProjection;
import com.ocs.portal.svc.role.repository.RolePrivsRepository;
import com.ocs.portal.svc.role.utils.LogEvent;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
@Service
public class RolePrivService {

    @Autowired
    LogService logService;

    @Autowired
    RoleService roleService;

    @Autowired
    ProdRoleService prodRoleService;

    @Autowired
    RolePrivsRepository rolePrivsRepository;

    @Autowired
    RoleMapper roleMapper;

    Logger logger = LoggerFactory.getLogger(ProdRoleService.class);

    public ResponseEntity<CustomeResponse> grantPrivToRoleNew(Integer roleId, List<ProdPrivOperDto> list) {
        String comments = "";
        try {
            if (CollectionUtils.isEmpty(list)) {
                throw new ResponseStatusException(HttpStatus.OK, "Success");
            }
            ProdRolesDto roleDto = prodRoleService.queryRoleById(roleId);
            List<ProdRolePrivDto> rolePrivDtoList = new ArrayList<>();
            StringBuffer sb = new StringBuffer("");
            String type = "privilege";
            for (ProdPrivOperDto privOperDto : list) {
                if (privOperDto.getPrivName() != null) {
                    sb.append(privOperDto.getPrivName()).append(",");
                } else {
                    sb.append(privOperDto.getPortletName()).append(",");
                    type = "portlets";
                }
                ProdRolePrivDto rolePrivDto = new ProdRolePrivDto();
                rolePrivDto.setPrivLevel(privOperDto.getPrivLevel());
                rolePrivDto.setPrivId(privOperDto.getPrivId());
                rolePrivDto.setRoleId(roleId.longValue());
                rolePrivDtoList.add(rolePrivDto);
            }
            comments = prodRoleService.concatComments(sb, roleDto.getRoleCode(), type);
            logService.addAuthLog(LogEvent.GRANT_PRIVILEGE_TO_ROLE, comments, 0);
            addPrivToRole(roleId.longValue(), rolePrivDtoList);
            Set<Long> menuIdSet = (Set<Long>) list.stream().map(ProdPrivOperDto::getMenuId).filter(Objects::nonNull).collect(Collectors.toSet());
            menuIdSet.forEach(menuId -> prodRoleService.clearComponentCacheByMenuId(menuId));
        } catch (Exception e) {
            logger.info("error : {}", e.getMessage());
            logService.addAuthLog(LogEvent.GRANT_PRIVILEGE_TO_ROLE, comments, 1);
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "failed grant to role");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomeResponse(201, "success", null));
    }

    private void addPrivToRole(Long roleId, List<ProdRolePrivDto> list) {
        roleService.addRolePriv(roleId, list);
    }

    public void delUserPrivsByRoleId(Long roleId, List<Long> privIdList) {
        try {
            logger.info("delUserPrivsByRoleId start..");
            Integer list = 0;
            List<RolePrivProjection> rolePrivDtos = new ArrayList<>();
            if (privIdList.isEmpty()){
                list = 1;
                logger.info("privIdList  isEmpty.");
                rolePrivDtos =  rolePrivsRepository.selectRolePrivsByRoleIdAndPrivList(roleId, null, list);

            }else
            {
                logger.info("list exist ");
                rolePrivDtos = rolePrivsRepository.selectRolePrivsByRoleIdAndPrivList(roleId, privIdList, list);
            }


            List<RolePrivDto> rolePrivDto = roleMapper.toRolePrivDto(rolePrivDtos);
            prodRoleService.recordRolePrivHis(rolePrivDto);
            if (list == 0) {
                logger.info("privIdList exist");
                rolePrivsRepository.delRolePrivs(roleId, privIdList, list);
            }else {
                logger.info("list null ");
                rolePrivsRepository.delRolePrivs(roleId, null, list);
            }

        } catch (Exception e) {
            logger.info("delUserPrivsByRoleId error : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "failed ungrant to role");
        }

    }

    public ResponseEntity<CustomeResponse>modRolePrivLevel(ProdRolePrivDto rolePrivDto){
        Long roleId = rolePrivDto.getRoleId();
        Long privId = rolePrivDto.getPrivId();
        String privLevel = rolePrivDto.getPrivLevel();
        if (rolePrivsRepository.modifyRolePrivLevel(roleId, privId, privLevel) == 1) {
            Optional<ProdComponentPrivProjection> opComp = rolePrivsRepository.queryComponentPrivByPrivId(privId);
            if (opComp.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "failed update PrivLevel");
            }
            ProdComponentPrivDto prodComponentPrivDto = roleMapper.toProdComponentPrivDto(opComp.get());

            //this.prodComponentService.clearComponentCacheByMenuId(prodComponentPrivDto.getMenuId());
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200,"success",prodComponentPrivDto));
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new CustomeResponse(HttpStatus.NOT_MODIFIED.value(),"failed",null));

    }

}
