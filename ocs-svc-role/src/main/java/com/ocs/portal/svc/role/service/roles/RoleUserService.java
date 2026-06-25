package com.ocs.portal.svc.role.service.roles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.BfmUserRole;
import com.ocs.portal.entity.embeddable.BfmUserRoleId;
import com.ocs.portal.svc.role.constant.HttpStatusConstant;
import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleExtDto;
import com.ocs.portal.svc.role.dto.response.PortletsDto;
import com.ocs.portal.svc.role.dto.response.ProdRolesDto;
import com.ocs.portal.svc.role.dto.response.UserDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.*;
import com.ocs.portal.svc.role.repository.JobRoleRepository;
import com.ocs.portal.svc.role.repository.UserRoleRepository;
import com.ocs.portal.svc.role.utils.LocalDateAdapter;
import com.ocs.portal.svc.role.utils.LogEvent;
import com.ocs.portal.svc.role.utils.PageUtils;
import com.ocs.portal.svc.role.utils.ThreadLocalMap;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
public class RoleUserService {

    Logger log = LoggerFactory.getLogger(RoleUserService.class);

    @Autowired
    JobRoleRepository jobsRepository;

    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    ProdRoleService prodRoleService;

    @Autowired
    LogService logService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PageUtils pageUtils;

    Gson gson = createGson();

    public ResponseEntity<CustomeResponse> addUserRoles(Long roleId, List<UserRoleExtDto> list) {
        int iRet = 0;
        String comments = "";
        try {
            iRet = addUserToRoleNew(roleId, list);
            ProdRolesDto roleDto = prodRoleService.queryRoleById(roleId.intValue());
            StringBuffer sb = new StringBuffer("");
            for (UserRoleDto userRoleDto : list)
                sb.append(userRoleDto.getUserCode()).append(",");
            comments = prodRoleService.concatComments(sb, roleDto.getRoleCode(), "user");
            logService.addAuthLog(LogEvent.GRANT_ROLE_TO_USER, comments, 0);
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.GRANT_ROLE_TO_USER, comments, 1);
            throw e;
        }
        log.debug("grantUserToRoleNew success ...");
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "Succes", null));
    }


    private int addUserToRoleNew(Long roleId, List<UserRoleExtDto> userRoleList) {
        log.info("userRoleList :"+ gson.toJson(userRoleList));
        log.debug("userRoleList :"+ gson.toJson(userRoleList));
        if (userRoleList.isEmpty()) {
            return -1;
        }
        List<UserRoleExtDto> needUpdateUserRoles = new ArrayList<>();
        List<UserRoleExtDto> needInsertUserRoles = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (UserRoleExtDto temp : userRoleList) {
            log.debug("selectUserRoleByUserIdAndRoleId");
            log.info("selectUserRoleByUserIdAndRoleId");
            Optional<UserRoleProjection> userRoleProjection = userRoleRepository.selectUserRoleByUserIdAndRoleId(temp.getUserId(), roleId, ThreadLocalMap.getSpId());
            UserRoleExtDto userRoleExtDto = null;
            if (userRoleProjection.isPresent()) {
                log.debug("userRoleProjection.isPresent()");
                userRoleExtDto = roleMapper.toRoleDto(userRoleProjection.get());
                log.debug("userRoleExtDto :"+ gson.toJson(userRoleExtDto));
            }
            if (userRoleExtDto != null && userRoleExtDto.getUserRoleTimes() != null) {
                userRoleExtDto.setUserRoleTimes(null);
                needUpdateUserRoles.add(userRoleExtDto);
                log.debug("needUpdateUserRoles" + gson.toJson(userRoleExtDto));
                log.info("needUpdateUserRoles" + gson.toJson(userRoleExtDto));
                continue;
            }
            log.debug("needInsertUserRoles ok" + gson.toJson(userRoleExtDto));
            if (userRoleExtDto == null) {
                temp.setRoleId(roleId);
                temp.setSpId(ThreadLocalMap.getSpId());
                temp.setCreatedDate(now);
                temp.setUpdateDate(now);
                needInsertUserRoles.add(temp);
                log.debug("new bfm user roles");
                log.info("new bfm user roles");
            }
        }
        int i = 0;
        for (UserRoleExtDto userRole : needUpdateUserRoles) {
            log.info("needUpdateUserRoles");
            i += userRoleRepository.updateRoleTimes(userRole.getUserRoleTimes(), userRole.getStaffRoleTimes(), userRole.getUserId(), userRole.getRoleId());
            log.info("updateRoleTimes " + gson.toJson(userRole));
        }
        if (!needInsertUserRoles.isEmpty()) {
            log.info("needInsertUserRoles");
            i += insertUserRoles(needInsertUserRoles);
            log.info("insertUserRoles " + gson.toJson(needInsertUserRoles));
        }
        return i;
    }


    public int insertUserRoles(List<UserRoleExtDto> dtoList) {
        log.info("insertUserRoles begin ..."+ gson.toJson(dtoList));
        LocalDateTime now = LocalDateTime.now();
        List<BfmUserRole> entities = dtoList.stream().map(dto -> {
            BfmUserRole entity = new BfmUserRole();
            BfmUserRoleId id = new BfmUserRoleId();
            id.setRoleId(dto.getRoleId());
            id.setUserId(dto.getUserId());
            entity.setId(id);
            entity.setSpId(dto.getSpId());
            entity.setCreatedDate(now.toLocalDate());
            entity.setUpdateDate(now.toLocalDate());

            return entity;
        }).collect(Collectors.toList());
        log.info("BfmUserRole begin ..."+ gson.toJson(entities));
        userRoleRepository.saveAll(entities); // JPA batch insert
        log.info("userRoleRepository end ..."+ gson.toJson(entities));
        return 1;
    }


    public ResponseEntity<CustomeResponse> ungrantUserToRoleNew(Long roleId, List<UserRoleDto> list) {
        log.debug("ungrantUserToRoleNew begin ...");
        int iRet = 0;
        String comments = "";
        try {
            iRet = removeUserFromRole(roleId, list);
            ProdRolesDto roleDto = prodRoleService.queryRoleById(roleId.intValue());
            StringBuffer sb = new StringBuffer("");
            for (UserRoleDto userRoleDto : list)
                sb.append(userRoleDto.getUserCode()).append(",");
            comments = String.format("ungranted user %s from role %s", new Object[]{sb.substring(0, sb.length() - 1), roleDto.getRoleCode()});
            logService.addAuthLog(LogEvent.UNGRANT_ROLE_TO_USER, comments, 0);
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.UNGRANT_ROLE_TO_USER, comments, 1);
            throw e;
        }
        log.debug("ungrantUserToRoleNew end ...");
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "Succes", null));

    }

    public int removeUserFromRole(Long roleId, List<UserRoleDto> list) {
        log.debug("removeUserFromRole");
        log.info("removeUserFromRole");

        return delRoleUserByRoleId(roleId, list);
    }

    @Autowired
    UserRoleHisService userRoleHisService;

    public int delRoleUserByRoleId(Long roleId, List<UserRoleDto> userRoleDtoList) {
       log.debug("delRoleUserByRoleId");
       log.info("delRoleUserByRoleId");
        Long spId = ThreadLocalMap.getSpId();
        Iterator<UserRoleDto> iterator = userRoleDtoList.iterator();
        List<UserRoleExtDto> needUpdateUserRoles = new ArrayList<>(8);
        List<UserRoleDto> needDeleteUserRoles = new ArrayList<>(8);
        while (iterator.hasNext()) {
            UserRoleDto temp = iterator.next();

            Optional<UserRoleProjection> userRoleProjection = userRoleRepository.selectUserRoleByUserIdAndRoleId(temp.getUserId(), roleId, ThreadLocalMap.getSpId());
            UserRoleExtDto userRoleExtDto = new UserRoleExtDto();
            if (userRoleProjection.isPresent()) {
                userRoleExtDto = roleMapper.toRoleDto(userRoleProjection.get());
            }
            if (userRoleExtDto != null && userRoleExtDto.getStaffRoleTimes() != null && userRoleExtDto.getStaffRoleTimes().longValue() != 0L) {
                userRoleExtDto.setUserRoleTimes(Long.valueOf(1L));
                needUpdateUserRoles.add(userRoleExtDto);
                continue;
            }
            if (userRoleExtDto != null) {
                temp.setRoleId(roleId);
                temp.setSpId(spId);
                needDeleteUserRoles.add(temp);
            }
        }
        int i = 0;
        for (UserRoleExtDto userRole : needUpdateUserRoles)
            i += userRoleRepository.updateRoleTimes(userRole.getUserRoleTimes(), userRole.getStaffRoleTimes(), userRole.getUserId(), userRole.getRoleId());
        if (!needDeleteUserRoles.isEmpty()) {
            this.userRoleHisService.recordUserRoleHis(needDeleteUserRoles);
            for (int index = 0; index < needDeleteUserRoles.size(); index++) {
                i += this.userRoleRepository.deleteUserRoles(needDeleteUserRoles.get(i).getRoleId(), needDeleteUserRoles.get(i).getUserId(), needDeleteUserRoles.get(i).getSpId());
            }

        }
        return i;
    }

    public ResponseEntity<CustomeResponse> qryUserListByRoleIdAndFilter(PagingRequestDto pagingRequest, UserDto userDto, Long roleId) {
        Pageable pageable = pageUtils.getPageAble(pagingRequest);
        Page<UserDto> list = queryUserListByRoleIdAndFilter(roleId, userDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, list, list.getTotalElements(), list.getTotalPages()));
    }

    private Page<UserDto> queryUserListByRoleIdAndFilter(Long roleId, UserDto userDto, Pageable page) {
        log.info("queryUserListByRoleIdAndFilter");
        Page<UsersRoleProjection> list = userRoleRepository.selectUserListByRoleIdAndFilter(roleId, null, userDto.getUserName(), userDto.getUserCode(), page);
        List<UserDto> listUser = roleMapper.toPageUserDto(list.getContent());
        return new PageImpl<>(listUser, page, list.getTotalElements());
    }

    public List<UserDto> queryUserListByUserId(Long userId) {
        List<UsersRoleProjection> usersRoleProjections = userRoleRepository.selectRoleByUserId(userId, ThreadLocalMap.getSpId());
        return roleMapper.toPageUserDto(usersRoleProjections);
    }

    public List<UserDto> selectRoleByRoleID(Long roleID) {
        List<UsersRoleProjection> usersRoleProjections = userRoleRepository.selectAllByRoleID(roleID, ThreadLocalMap.getSpId());
        return roleMapper.toPageUserDto(usersRoleProjections);
    }

    public List<UserDto> selectRoleByAll() {
        List<UsersRoleProjection> usersRoleProjections = userRoleRepository.selectAllBySPID(ThreadLocalMap.getSpId());
        return roleMapper.toPageUserDto(usersRoleProjections);
    }

    public ResponseEntity<CustomeResponse> queryUnGrantUserListByRoleIdAndFilter(PagingRequestDto pagingRequest, UserDto userDto, Long roleId) {
        Pageable pageable = pageUtils.getPageAble(pagingRequest);
        Page<UserDto> list = queryUnGrantUserListByRoleIdAndFilter(roleId, userDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, list, list.getTotalElements(), list.getTotalPages()));
    }

    private Page<UserDto> queryUnGrantUserListByRoleIdAndFilter(Long roleId, UserDto userDto, Pageable page) {
        log.info("queryUnGrantUserListByRoleIdAndFilter");
        Page<UserInfoProjection> list = userRoleRepository.selectUnGrantUserListByRoleIdAndFilter(roleId, userDto.getUserName(), userDto.getUserCode(), page);
        List<UserDto> listUser = roleMapper.toPageUserDtoFromUserInfo(list.getContent());
        return new PageImpl<>(listUser, page, list.getTotalElements());
    }

    public ResponseEntity<CustomeResponse> queryRoleOwnedPortletList(Long roleId) {
        List<PortletsDto> list = selectRoleOwnedPortletList(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
    }

    private List<PortletsDto> selectRoleOwnedPortletList(Long roleId) {
        log.info("selectRoleOwnedPortletList");
        List<PortletProjection> list = userRoleRepository.selectRoleOwnedPortletList(roleId);
        return roleMapper.toListPortletsDto(list);
    }

    public List<StaffJobProjection> getListJob(String username){
        return jobsRepository.getJobsByUser(username, null);
    }
}
