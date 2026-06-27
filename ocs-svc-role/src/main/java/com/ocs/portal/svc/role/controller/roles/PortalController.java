package com.ocs.portal.svc.role.controller.portal;

import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.svc.role.service.roles.PortalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Prod Portal Controller", description = "Portal Management APIs")
@RestController
@RequestMapping(value = "api/portals")
public class PortalController {

    Logger logger = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    PortalService portalService;

  @GetMapping("{portalId}/dirMenus")
  public ResponseEntity<BaseResponseDto> qryListDirMenus(@PathVariable Long portalId, @RequestParam Long spId) {
    return portalService.qryListDirMenusPortal(portalId, spId);
  }

}
