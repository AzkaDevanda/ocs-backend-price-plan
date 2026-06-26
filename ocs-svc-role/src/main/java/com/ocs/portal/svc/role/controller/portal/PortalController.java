package com.ocs.portal.svc.role.controller;

import com.ocs.portal.svc.role.dto.request.PortalFilterDto;
import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import com.ocs.portal.svc.role.dto.request.roles.PortalRoleDto;
import com.ocs.portal.svc.role.service.PortalService;
import com.ocs.portal.dto.response.CustomeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Prod Portal Controller", description = "Portal Management APIs")
@RestController
@RequestMapping(value = "api/prod/portals")
public class PortalController {

    Logger logger = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    PortalService portalService;

   @GetMapping("{portalId}/dirMenus")
  public ResponseEntity<BaseResponseDto> qryListDirMenus(@PathVariable Long portalId, @RequestParam Long spId) {
    return portalService.qryListDirMenusPortal(portalId, spId);
  }

}
