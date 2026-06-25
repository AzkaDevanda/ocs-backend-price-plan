package com.ocs.portal.svc.role.controller.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.service.roles.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/apps")
public class AppController {

    @Autowired
    AppService appService;

    Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping("apps")
    public ResponseEntity<CustomeResponse> qryAppList(){
        logger.info("Request qryAppList");
        return appService.queryAppList();
    }
}
