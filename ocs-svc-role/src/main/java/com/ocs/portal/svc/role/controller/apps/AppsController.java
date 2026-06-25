package com.ocs.portal.svc.role.controller.apps;

import com.ocs.portal.svc.role.dto.request.log.LogDto;
import com.ocs.portal.svc.role.service.roles.LogService;
import com.ocs.portal.svc.role.utils.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/logger")
public class AppsController {

    @Autowired
    LogService logService;

    @PostMapping(value = "save")
    public ResponseEntity<String> doSaveLogger(@RequestBody LogDto logDto){
        LogEvent log = LogEvent.getLogEvent(logDto.getEventCode());
        logService.addAuthLog(log,logDto.getComments(), logDto.getIsSuccess());
        return ResponseEntity.status(HttpStatus.OK).body("succes");
    }

}
