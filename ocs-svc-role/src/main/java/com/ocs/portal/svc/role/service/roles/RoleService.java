package com.ocs.portal.svc.role.service.roles;

import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.BfmRole;
import com.ocs.portal.entity.BfmRolePriv;
import com.ocs.portal.entity.BfmUser;
import com.ocs.portal.entity.embeddable.BfmRolePrivId;
import com.ocs.portal.svc.role.auth.service.UserService;
import com.ocs.portal.svc.role.dto.request.roles.ProdRolePrivDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleExtDto;
import com.ocs.portal.svc.role.dto.response.ProdComponentPrivDto;
import com.ocs.portal.svc.role.dto.response.ProdRolesDto;
import com.ocs.portal.svc.role.dto.response.UserDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.UserRoleExtProjection;
import com.ocs.portal.svc.role.repository.*;
import com.ocs.portal.svc.role.repository.custom.RolePortalCustomRepository;
import com.ocs.portal.svc.role.utils.LogEvent;
import com.ocs.portal.svc.role.utils.MessageUtil;
import com.ocs.portal.svc.role.utils.ThreadLocalMap;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
@Service
public class RoleService {

    Logger log = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    RolePrivsRepository rolePrivsRepository;
    @Autowired
    private LogService logService;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    ProdRoleService prodRoleService;

    @Autowired
    RolePortalCustomRepository rolePortalCustomRepository;

    @Autowired
    MessageUtil messageUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private BfmUserRepository bfmUserRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    private BfmUserPortalRepository bfmUserPortalRepository;


    public void addRolePriv(Long roleId, List<ProdRolePrivDto> rolePrivList) {
        log.info("addRolePriv");
        if (!rolePrivList.isEmpty()) {
            List<BfmRolePriv> list = new ArrayList<>();

            for (ProdRolePrivDto rolePriv : rolePrivList) {
                BfmRolePriv bfmRolePriv = new BfmRolePriv();
                BfmRolePrivId bfmRolePrivId = new BfmRolePrivId();
                bfmRolePrivId.setRoleId(roleId);
                bfmRolePrivId.setPrivId(rolePriv.getPrivId());

                bfmRolePriv.setId(bfmRolePrivId);
                bfmRolePriv.setPrivLevel(rolePriv.getPrivEl());
                list.add(bfmRolePriv);
            }
            rolePrivsRepository.saveAll(list);
            log.info("Role Privs saved successfully");
        }
    }

    public ResponseEntity<CustomeResponse> addRoles(ProdRolesDto dto) {
        ProdRolesDto roleDto;
        try {
            Long spId = ThreadLocalMap.getSpId();
            if (spId == null) {
                spId = Long.valueOf(0L);
            }
            dto.setSpId(spId);
            roleDto = addRole(dto);
            logService.addAuthLog(LogEvent.ADD_ROLE, "add role " + dto.getRoleCode(), 0);
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.ADD_ROLE, "add role " + dto.getRoleCode(), 1);
            throw e;
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomeResponse(201, "success", roleDto));
    }

    public ProdRolesDto addRole(ProdRolesDto rolerDto) {
        BfmRole roler = roleMapper.toBfmRole(rolerDto);
        log.info("addRole");
        if (roleRepository.isSameName(roler.getRoleName(), roler.getRoleId()) != 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("46430079"));
        }
        if (roleRepository.isSameCode(roler.getRoleCode(), roler.getRoleId()) != 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("46430077"));
        }
        log.info("start save BfmRole");
        LocalDateTime now = LocalDateTime.now();
        roler.setIsLocked('N');
        roler.setCreatedDate(now);
        roler.setUpdateDate(now);
        roler = roleRepository.save(roler);
        rolerDto.setRoleId(roler.getRoleId().longValue());
        return rolerDto;
    }

    public ResponseEntity<CustomeResponse> modifyRole(ProdRolesDto dto) {
        log.info("modify role " + dto.getRoleId());
        modRole(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, "success", null));
    }

    public ResponseEntity<CustomeResponse> delRole(Long roleId) {
        ProdRolesDto dto = prodRoleService.queryRoleById(roleId.intValue());
        String isLocked = dto.getIsLocked();
        if (isLocked != null && "Y".equalsIgnoreCase(isLocked)) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, MessageUtil.getMessage("46430078"), null));
        }
        if (rolePortalCustomRepository.fieldIsReferenced("POT.BFM_USER_ROLE", roleId) >= 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, MessageUtil.getMessage("46430064"), null));
        }
        if (rolePortalCustomRepository.fieldIsReferenced("POT.BFM_ROLE_PRIV", roleId) >= 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, MessageUtil.getMessage("46430073"), null));
        }
        if (rolePortalCustomRepository.fieldIsReferenced("POT.BFM_ROLE_DATA_PRIV", roleId) >= 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, MessageUtil.getMessage("46430056"), null));
        }
        if (rolePortalCustomRepository.fieldIsReferenced("POT.BFM_ROLE_PORTAL", roleId) >= 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, MessageUtil.getMessage("46430087"), null));
        }
        if (rolePortalCustomRepository.fieldIsReferenced("POT.BFM_JOB_ROLE", roleId) >= 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, MessageUtil.getMessage("46420027"), null));
        }
        roleRepository.deleteRole(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, "success", null));
    }


    public void modRole(ProdRolesDto roleDto) {
        Long roleId = roleDto.getRoleId();
        LocalDateTime now = LocalDateTime.now();
        ProdRolesDto dto = prodRoleService.queryRoleById(roleId.intValue());
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, MessageUtil.getMessage("46430054"));
        }
        String isLocked = dto.getIsLocked();
        if (isLocked != null && "Y".equalsIgnoreCase(isLocked)) {
            throw new ResponseStatusException(HttpStatus.OK, MessageUtil.getMessage("46430078"));
        }
//        if (roleRepository.isSameName(roleDto.getRoleName(), roleDto.getRoleId().intValue()) != 0) {
//            throw new ResponseStatusException(HttpStatus.OK, MessageUtil.getMessage("46430079"));
//        }
//        if (roleRepository.isSameCode(roleDto.getRoleCode(), roleDto.getRoleId().intValue()) != 0) {
//            throw new ResponseStatusException(HttpStatus.OK, MessageUtil.getMessage("46430077"));
//        }
        roleRepository.updateRole(roleDto.getRoleName(), roleDto.getComments(), roleDto.getAppId(), roleDto.getRoleCode(), roleDto.getRoleId().intValue(), now);
        log.debug("success updateRole");
    }

    public ResponseEntity<CustomeResponse> grantRoleToUserNews(Long userId, List<UserRoleDto> roleList) {
        grantRoleToUserNew(userId, roleList);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, "success", null));
    }

    private void grantRoleToUserNew(Long userId, List<UserRoleDto> roleList) {
        log.info("grantRoleToUserNew");
        String comments = "";
        try {
            List<Long> roleIdList = new ArrayList<>();
            StringBuffer sb = new StringBuffer("");
            for (UserRoleDto userRoleDto : roleList) {
                sb.append(userRoleDto.getRoleCode()).append(",");
                roleIdList.add(userRoleDto.getRoleId());
            }
            grantRoleToUser(userId, roleIdList, ThreadLocalMap.getSpId());
            log.info("grantRoleToUser : " + LocalDateTime.now());
            UserDto userDto = qryUserByUserId(userId);
            String msg = messageUtil.getMessage("4641001101");
            comments = MessageFormat.format(msg, sb.substring(0, sb.length() - 1), userDto.getUserCode());
            logService.addAuthLog(LogEvent.GRANT_ROLE_TO_USER, comments, 0);
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.GRANT_ROLE_TO_USER, comments, 1);
            throw new ResponseStatusException(HttpStatus.OK, "failed to grant to User");
        }

    }

    public int grantRoleToUser(Long userId, List<Long> roleList, Long spId) {
        log.info("grantRoleToUser");
        List<UserRoleExtDto> existUserRoles = selectRoleListByUserId(userId, spId);
        Map<Long, UserRoleExtDto> existUserRoleMap = new HashMap<>();
        for (UserRoleExtDto userRole : existUserRoles) {
            existUserRoleMap.put(userRole.getRoleId(), userRole);
        }
        List<UserRoleExtDto> needUpdateUserRoles = new ArrayList<>(16);
        List<UserRoleExtDto> needInsertUserRoles = new ArrayList<>(16);
        LocalDateTime now = LocalDateTime.now();
        for (Long roleId : roleList) {
            if (existUserRoleMap.containsKey(roleId)) {
                UserRoleExtDto userRoleExtDto = existUserRoleMap.get(roleId);
                if (userRoleExtDto.getUserRoleTimes() != null) {
                    userRoleExtDto.setUserRoleTimes(null);
                    needUpdateUserRoles.add(userRoleExtDto);
                }
                continue;
            }
            UserRoleExtDto userRole = new UserRoleExtDto();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRole.setSpId(spId);
//            userRole.setUpdateDate(now.toLocalDate());
//            userRole.setCreatedDate(now.toLocalDate());
            needInsertUserRoles.add(userRole);
        }
        int i = 0;
        for (UserRoleExtDto userRoleExtDto : needUpdateUserRoles) {
            userRoleRepository.updateRoleTimes(userRoleExtDto.getUserRoleTimes(), userRoleExtDto.getStaffRoleTimes(), userRoleExtDto.getUserId(), userRoleExtDto.getRoleId());
        }
        if (!needInsertUserRoles.isEmpty()) {
            roleUserService.insertUserRoles(needInsertUserRoles);
        }
        return i;
    }

    public ResponseEntity<BaseResponseDto> degrantRoleFromUserNew(Long userId, List<UserRoleDto> roleList) {
       log.info("degrantRoleFromUserNew"+ LocalDateTime.now());
        String comments = "";
        try {
            List<Long> roleIdList = new ArrayList<>();
            StringBuffer sb = new StringBuffer("");
            for (UserRoleDto userRoleDto : roleList) {
                sb.append(userRoleDto.getRoleCode()).append(",");
                roleIdList.add(userRoleDto.getRoleId());
            }
            UserDto userDto = qryUserByUserId(userId);
            degrantRoleFromUser(userId, roleIdList, ThreadLocalMap.getSpId());
            comments = String.format("ungranted role %s from user %s", new Object[]{sb.substring(0, sb.length() - 1), userDto
                    .getUserCode()});
            logService.addAuthLog(LogEvent.UNGRANT_ROLE_TO_USER, comments, 0);
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.UNGRANT_ROLE_TO_USER, comments, 1);
            throw e;
        }
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success",200,null));
    }

    private UserDto qryUserByUserId(Long userId) {
        BfmUser bfmUser = bfmUserRepository.findById(userId.intValue()).orElse(null);
        assert bfmUser != null;
        return roleMapper.userDto(bfmUser);
    }

    private List<UserRoleExtDto> selectRoleListByUserId(Long userId, Long spId) {
        log.info("selectRoleListByUserId");
        List<UserRoleExtProjection> list = userRoleRepository.selectRoleListByUserId(userId, spId);
        return roleMapper.toListUserRoleExtDtoFromUserRoleExt(list);
    }

    public int degrantRoleFromUser(Long userId, List<Long> roleList, Long spId) {
        log.info("degrantRoleFromUser");
        List<UserRoleExtDto> existUserRoles = selectRoleListByUserId(userId, spId);
        List<UserRoleExtDto> needUpdateUserRoles = new ArrayList<>(16);
        List<UserRoleDto> needDeleteUserRoles = new ArrayList<>(16);
        Map<Long, UserRoleExtDto> existUserRoleMap = new HashMap<>();
        for (UserRoleExtDto userRole : existUserRoles)
            existUserRoleMap.put(userRole.getRoleId(), userRole);
        for (Long roleId : roleList) {
            if (existUserRoleMap.containsKey(roleId)) {
                UserRoleExtDto userRole = existUserRoleMap.get(roleId);
                if (userRole.getStaffRoleTimes() != null && userRole.getStaffRoleTimes().longValue() != 0L) {
                    userRole.setUserRoleTimes(Long.valueOf(1L));
                    needUpdateUserRoles.add(userRole);
                    continue;
                }
                needDeleteUserRoles.add(userRole);
            }
        }
        for (UserRoleExtDto userRoleExtDto : needUpdateUserRoles) {
            userRoleRepository.updateRoleTimes(userRoleExtDto.getUserRoleTimes(), userRoleExtDto.getStaffRoleTimes(), userRoleExtDto.getUserId(), userRoleExtDto.getRoleId());
            log.info("updateRoleTimes");
        }

        int i = 0;
        if (!needDeleteUserRoles.isEmpty()) {
            userRoleHisService.recordUserRoleHis(needDeleteUserRoles);
            for (int index = 0; index < needDeleteUserRoles.size(); index++) {
                i += userRoleRepository.deleteUserRoles(needDeleteUserRoles.get(i).getRoleId(), needDeleteUserRoles.get(i).getUserId(), needDeleteUserRoles.get(i).getSpId());
                log.info("needDeleteUserRoles success");
            }
        }

        return 0;
    }

    @Autowired
    UserRoleHisService userRoleHisService;

    @Autowired
    RoleUserService roleUserService;


    public List<ProdComponentPrivDto> queryUserComponentPrivList(@PathVariable Long userId) {
        return roleMapper.toListProdComponentPrivDto(bfmUserPortalRepository.selectUserComponentPrivList(userId));
    }
}
