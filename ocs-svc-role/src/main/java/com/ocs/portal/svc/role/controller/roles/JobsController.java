package com.ocs.portal.svc.role.controller.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import com.ocs.portal.svc.role.dto.request.job.JobDto;
import com.ocs.portal.svc.role.service.roles.JobsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/stafforg/jobs")
public class JobsController {
    @Autowired
    JobsService jobsService;

    Logger logger = LoggerFactory.getLogger(JobsController.class);

    @GetMapping({"{roleId}/ungrant/filter"})
    public ResponseEntity<CustomeResponse> queryUnGrantJobsByRoleId(@PathVariable Long roleId, @ModelAttribute PagingRequestDto page, JobDto jobDto) {
        logger.info("Request queryUnGrantJobsByRoleId");
        return jobsService.queryUnGrantJobsByRoleId(roleId, jobDto, page);
    }
    @GetMapping({"{roleId}/grant/filter"})
    public ResponseEntity<CustomeResponse> queryGrantJobsByRoleId(@PathVariable Long roleId, @ModelAttribute PagingRequestDto page, JobDto jobDto) {
        logger.info("Request queryGrantJobsByRoleId");
        return jobsService.queryGrantJobByRoleId(roleId, jobDto, page);
    }

    @PostMapping({"{roleId}/new"})
    public ResponseEntity<CustomeResponse> addJobsUserToRole(@PathVariable Long roleId, @RequestBody List<JobDto> jobDtos) {
        logger.info("Request addJobsUserToRole");
        return jobsService.addJobAndUsersToRoles(roleId, jobDtos);
    }

    @DeleteMapping({"{roleId}/new"})
    public ResponseEntity<CustomeResponse> removeJobsFromRole(@PathVariable Long roleId, @RequestBody List<JobDto> jobDtos) {
        logger.info("Request removeJobsFromRole");
        return jobsService.removeJobsFromRole(roleId, jobDtos);
    }

}
