package com.ocs.portal.svc.role.service.roles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.BfmUserRole;
import com.ocs.portal.entity.JobRole;
import com.ocs.portal.entity.JobRoleHis;
import com.ocs.portal.entity.embeddable.BfmUserRoleId;
import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import com.ocs.portal.svc.role.dto.request.job.JobDto;
import com.ocs.portal.svc.role.dto.request.job.JobRoleDto;
import com.ocs.portal.svc.role.dto.request.job.JobRoleHisDto;
import com.ocs.portal.svc.role.dto.request.job.OrgAreaStaffUserDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleExtDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.JobProjection;
import com.ocs.portal.svc.role.projection.JobRoleProjection;
import com.ocs.portal.svc.role.projection.StaffJobProjection;
import com.ocs.portal.svc.role.projection.UserRoleProjection;
import com.ocs.portal.svc.role.repository.JobRoleHisRepository;
import com.ocs.portal.svc.role.repository.JobRoleRepository;
import com.ocs.portal.svc.role.repository.JobsRepository;
import com.ocs.portal.svc.role.repository.UserRoleRepository;
import com.ocs.portal.svc.role.utils.ListUtils;
import com.ocs.portal.svc.role.utils.LocalDateAdapter;
import com.ocs.portal.svc.role.utils.PageUtils;
import com.ocs.portal.svc.role.utils.ThreadLocalMap;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Transactional(rollbackOn = {Exception.class, ResponseStatusException.class, RuntimeException.class, JsonIOException.class})
@Service
public class JobsService {
    Logger logger = LoggerFactory.getLogger(JobsService.class);

    @Autowired
    PageUtils pageUtils;

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    JobRoleRepository jobRoleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    JobRoleHisRepository roleHisRepository;

    @Autowired
    UserRoleHisService userRoleHisService;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    public ResponseEntity<CustomeResponse> queryUnGrantJobsByRoleId(Long roleId, JobDto jobDto, PagingRequestDto pagingRequestDto) {
        Pageable pageable = pageUtils.getPageAble(pagingRequestDto);
        Page<JobDto> list = queryUnGrantJobsByRoleId(roleId, jobDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list, list.getTotalElements(), list.getTotalPages()));
    }

    public Page<JobDto> queryUnGrantJobsByRoleId(Long roleId, JobDto jobDto, Pageable page) {
        logger.info("queryUnGrantJobsByRoleId");
        Page<JobProjection> list = jobsRepository.queryUnGrantJobByRoleId(roleId, jobDto.getSpId(), jobDto.getJobName(), jobDto.getJobCode(), page);
        List<JobDto> listUser = roleMapper.toListJobDto(list.getContent());
        return new PageImpl<>(listUser, page, list.getTotalElements());
    }

    public ResponseEntity<CustomeResponse> queryGrantJobByRoleId(Long roleId, JobDto jobDto, PagingRequestDto pagingRequestDto) {
        Pageable pageable = pageUtils.getPageAble(pagingRequestDto);
        Page<JobDto> list = queryGrantJobByRoleId(roleId, jobDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list, list.getTotalElements(), list.getTotalPages()));
    }

    public Page<JobDto> queryGrantJobByRoleId(Long roleId, JobDto jobDto, Pageable page) {
        logger.info("queryGrantJobByRoleId");
        Page<JobProjection> list = jobsRepository.queryGrantJobByRoleId(roleId, jobDto.getSpId(), jobDto.getJobName(), jobDto.getJobCode(), page);
        List<JobDto> listUser = roleMapper.toListJobDto(list.getContent());
        return new PageImpl<>(listUser, page, list.getTotalElements());
    }

    public ResponseEntity<CustomeResponse> addJobAndUsersToRoles(Long roleId, List<JobDto> jobDtos) {
        addJobAndUsersToRole(roleId, jobDtos);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }

    private void addJobAndUsersToRole(Long roleId, List<JobDto> jobDtos) {
        List<Long> jobIds = (List<Long>) jobDtos.stream().map(JobDto::getJobId).collect(Collectors.toList());
        List<Long> filterJobIds = addJobsToRole(roleId, jobIds);
        Map<Long, UserRoleExtDto> updateUserRoleMap = new HashMap<>(16);
        Map<Long, UserRoleExtDto> insertUserRoleMap = new HashMap<>(16);
        LocalDate now = LocalDate.now();

        for (Long jobId : filterJobIds) {
            List<StaffJobProjection> user = jobRoleRepository.queryOrgAreaStaffUserInfoByJobId(jobId, ThreadLocalMap.getSpId());
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "users not found ..");
            }
            List<OrgAreaStaffUserDto> users = roleMapper.toListOrgStaf(user);
            List<Long> userIds = (List<Long>) users.stream().map(OrgAreaStaffUserDto::getUserId).collect(Collectors.toList());
            List<UserRoleProjection> list = jobRoleRepository.findByRoleIdAndUserIds(roleId, userIds, ThreadLocalMap.getSpId());
            List<UserRoleExtDto> userRoleExtDtos = roleMapper.toUserRoleExtDto(list);

            Map<Long, UserRoleExtDto> existUserRoleMap = new HashMap<>(16);
            for (UserRoleExtDto userRole : userRoleExtDtos)
                existUserRoleMap.put(userRole.getUserId(), userRole);
            for (Long userId : userIds) {
                UserRoleExtDto userRole;
                if (existUserRoleMap.containsKey(userId)) {
                    userRole = updateUserRoleMap.containsKey(userId) ? updateUserRoleMap.get(userId) : existUserRoleMap.get(userId);
                    userRole.setStaffRoleTimes((userRole.getStaffRoleTimes() == null) ? 1L : (userRole.getStaffRoleTimes() + 1L));
                    updateUserRoleMap.put(userId, userRole);
                    continue;
                }
                if (insertUserRoleMap.containsKey(userId)) {
                    userRole = insertUserRoleMap.get(userId);
                    userRole.setStaffRoleTimes(userRole.getStaffRoleTimes() + 1L);
                } else {
                    userRole = new UserRoleExtDto();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    userRole.setUserRoleTimes(1L);
                    userRole.setStaffRoleTimes(1L);
                    userRole.setUpdateDate(now);
                    userRole.setCreatedDate(now);
                    userRole.setSpId(ThreadLocalMap.getSpId());
                }
                insertUserRoleMap.put(userId, userRole);
            }
        }
        List<UserRoleExtDto> updateUserRoles = new ArrayList<>(updateUserRoleMap.values());
        List<UserRoleExtDto> insertUserRoles = new ArrayList<>(insertUserRoleMap.values());
        for (UserRoleExtDto userRole : updateUserRoles) {
            userRoleRepository.updateRoleTimes(userRole.getUserRoleTimes(), userRole.getStaffRoleTimes(), userRole.getUserId(), userRole.getRoleId());
        }
        if (!insertUserRoles.isEmpty()) {
            List<BfmUserRole> list = new ArrayList<>();
//            List<BfmUserRole> list = roleMapper.toUserRole(insertUserRoles);
            for (UserRoleExtDto userRoleExtDto : insertUserRoles) {
                logger.info("userRoleExtDto={}", userRoleExtDto.getUserId());
                BfmUserRole bfmUserRole = new BfmUserRole();
                BfmUserRoleId bfmUserRoleId = new BfmUserRoleId();
                bfmUserRoleId.setRoleId(userRoleExtDto.getRoleId());
                bfmUserRoleId.setUserId(userRoleExtDto.getUserId());

                bfmUserRole.setId(bfmUserRoleId);
                bfmUserRole.setUserRoleTimes(userRoleExtDto.getStaffRoleTimes());
                bfmUserRole.setUpdateDate(userRoleExtDto.getUpdateDate());
                bfmUserRole.setCreatedDate(userRoleExtDto.getCreatedDate());
                bfmUserRole.setSpId(userRoleExtDto.getSpId());
                list.add(bfmUserRole);
            }
            if (!list.isEmpty()) {
                userRoleRepository.saveAll(list);
            }
            logger.info("addJobAndUsersToRole success");
        }
    }

    public List<Long> addJobsToRole(Long roleId, List<Long> jobIdList) {
        LocalDate nowDate = LocalDate.now();
        JobRoleDto jobRoleDto = new JobRoleDto();
        Long spId = ThreadLocalMap.getSpId();
        List<Long> jobIds = jobsRepository.queryJobIdsByRoleId(roleId, ThreadLocalMap.getSpId());
        List<Long> filterJobIdList = ListUtils.subtract(jobIdList, jobIds);
        for (Long jobId : filterJobIdList) {
//            jobRoleDto.setJobRoleId(this.sequenceService.getSequenceId("T_BFM_JOB_ROLE_ID_SEQ"));
            jobRoleDto.setState("A");
            jobRoleDto.setStateDate(nowDate);
            jobRoleDto.setCreatedDate(nowDate);
            jobRoleDto.setUpdateDate(nowDate);
            jobRoleDto.setJobId(jobId);
            jobRoleDto.setRoleId(roleId);
            jobRoleDto.setSpId(spId);
            JobRole jobRole = roleMapper.toJobDto(jobRoleDto);
            jobRoleRepository.save(jobRole);
        }
        return filterJobIdList;
    }

    public ResponseEntity<CustomeResponse> removeJobsFromRole(Long roleId, List<JobDto> jobDtos) {
        List<Long> jobIds = (List<Long>) jobDtos.stream().map(JobDto::getJobId).collect(Collectors.toList());
        logger.info("removeJobsFromRole");
        delJobsFromRole(roleId, jobIds);

        for (Long jobId : jobIds) {
            List<StaffJobProjection> user = jobRoleRepository.queryOrgAreaStaffUserInfoByJobId(jobId, ThreadLocalMap.getSpId());
            List<OrgAreaStaffUserDto> users = roleMapper.toListOrgStaf(user);

            List<Long> userIds = (List<Long>) users.stream().map(OrgAreaStaffUserDto::getUserId).collect(Collectors.toList());
            for (Long userId : userIds)
                removeRoleFromUser(userId, roleId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }


    private void delJobsFromRole(Long roleId, List<Long> jobIds) {
        List<JobRoleProjection> listRole = jobRoleRepository.selectJobRolesByJobIdAndRoleList(jobIds, Collections.singletonList(roleId));
        List<JobRoleDto> deleteJobRoles = roleMapper.toListJobRoleDto(listRole);
        logger.info("delJobsFromRole {}", deleteJobRoles);
        recordJobRoleHis(deleteJobRoles);
        jobRoleRepository.deleteByRoleIdAndJobIds(roleId, jobIds);
    }

    public void recordJobRoleHis(List<JobRoleDto> jobRoles) {
        LocalDate date = LocalDate.now();
        List<JobRoleHisDto> jobRoleHisDtos = new ArrayList<>();
        for (JobRoleDto jobRoleDto : jobRoles) {
            JobRoleHisDto jobRoleHisDto = new JobRoleHisDto();
            BeanUtils.copyProperties(jobRoleDto, jobRoleHisDto);
            jobRoleHisDto.setCreatedDate(date);
            jobRoleHisDto.setUpdateDate(date);

            jobRoleHisDto.setOperatorType("D");
            jobRoleHisDtos.add(jobRoleHisDto);
        }
        List<JobRoleHis> listRoleJob = roleMapper.toJobRoleHis(jobRoleHisDtos);
        roleHisRepository.saveAll(listRoleJob);
        logger.info("insert job role success");
    }


    private void removeRoleFromUser(Long userId, Long roleId) {
//        UserRoleExtDto userRoleExtDto = this.jobMapper.selectUserRole(userId, roleId);
        Optional<UserRoleProjection> userRoleProjection = userRoleRepository.selectUserRoleByUserIdAndRoleId(userId, roleId, ThreadLocalMap.getSpId());
        UserRoleExtDto userRoleExtDto = new UserRoleExtDto();
        if (userRoleProjection.isPresent()) {
            userRoleExtDto = roleMapper.toRoleDto(userRoleProjection.get());
        }
        if (userRoleExtDto != null) {
            Long staffRoleTimes = null;
            if(userRoleExtDto.getStaffRoleTimes()!=null){
                staffRoleTimes = userRoleExtDto.getStaffRoleTimes();
            }

            if (userRoleExtDto.getUserRoleTimes() == null) {
                if (staffRoleTimes != null && staffRoleTimes.longValue() > 0L) {
                    userRoleExtDto.setStaffRoleTimes(staffRoleTimes - 1L);
                    logger.info("updateRoleTimes");
                    userRoleRepository.updateRoleTimes(userRoleExtDto.getUserRoleTimes(), userRoleExtDto.getStaffRoleTimes(), userRoleExtDto.getUserId(), userRoleExtDto.getRoleId());
                }
            } else if (staffRoleTimes != null && staffRoleTimes == 1L) {
                logger.debug("userRoleExtDto.getStaffRoleTimes().longValue() == 1L");
                logger.info("recordUserRoleHis :: deleteUserRoles ");
                userRoleHisService.recordUserRoleHis(Collections.singletonList(userRoleExtDto));
                userRoleRepository.deleteUserRoles(userRoleExtDto.getRoleId(), userRoleExtDto.getUserId(), userRoleExtDto.getSpId());
            } else if (staffRoleTimes != null){
                logger.info("setStaffRoleTimes :: updateRoleTimes ");
                userRoleExtDto.setStaffRoleTimes(staffRoleTimes - 1L);
                userRoleRepository.updateRoleTimes(userRoleExtDto.getUserRoleTimes(), userRoleExtDto.getStaffRoleTimes(), userRoleExtDto.getUserId(), userRoleExtDto.getRoleId());
            }
        }
    }
}
