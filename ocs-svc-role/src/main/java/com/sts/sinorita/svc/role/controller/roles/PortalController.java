package com.sts.sinorita.svc.role.controller.roles;

import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.svc.role.dto.request.AddDirMenuToPortalRequest;
import com.sts.sinorita.svc.role.dto.request.BindMenuToPortalsRequestDto;
import com.sts.sinorita.svc.role.dto.request.PortalRequestDto;
import com.sts.sinorita.svc.role.dto.request.PortalSeqRequestDto;
import com.sts.sinorita.svc.role.dto.request.common.PagingRequestDto;
import com.sts.sinorita.svc.role.service.roles.PortalService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/portals/")
public class PortalController {
  Logger logger = LoggerFactory.getLogger(PortalController.class);

  @Autowired
  private PortalService portalService;

  @GetMapping(value = { "{portalId}/party/{partyId}/dirs" })
  public ResponseEntity<CustomeResponse> querySubDirListByParentInPortal(@PathVariable(required = false) Long partyId, @PathVariable(required = false) Long portalId) {
    logger.info("querySubDirListByParentInPortal");
    if (partyId == 0) {
      partyId = null;
    }
    return portalService.querySubDirListByParentInPortal(partyId, portalId);
  }

  @GetMapping(value = { "portals/{portalId}/party/{partyId}/menus" })
  public ResponseEntity<CustomeResponse> qryMenuListByPartyId(@PathVariable(required = false) Long partyId, @PathVariable(required = false) Long portalId) {
    logger.info("qryMenuListByPartyId");
    return portalService.queryRoleMenuListByPartyId(partyId, portalId);
  }

  @GetMapping(value = "portals")
  public ResponseEntity<CustomeResponse> queryPortalList() {
    logger.info("queryPortalList");
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", portalService.queryPortalList()));
  }
  
  @GetMapping("qry-portal-list-by-menu-id")
  public ResponseEntity<CustomeResponse> qryPortalListByMenuId(@ModelAttribute PagingRequestDto pagingRequestDto, @RequestParam(required = true) Long partyId, @RequestParam(required = false) String type, @RequestParam(required = false) Long spId, @RequestParam(required = false) Integer isTypeMenu) {
    return portalService.qryPortalListByMenuId(pagingRequestDto, partyId, type, spId, isTypeMenu);
  }

  @PostMapping("add-portal")
  public ResponseEntity<CustomeResponse> addPortal(@RequestBody PortalRequestDto portalRequestDto) {
    return portalService.addPortal(portalRequestDto);
  }

  @PutMapping("mod-portal")
  public ResponseEntity<CustomeResponse> modPortal(@RequestBody PortalRequestDto portalRequestDto) {
    return portalService.modPortal(portalRequestDto);
  }

  @PutMapping("mod-seq/{portalId}")
  public ResponseEntity<CustomeResponse> modSeq(@PathVariable Long portalId, @RequestBody List<PortalSeqRequestDto> portalSeqRequestDtos) {
    return portalService.modSeq(portalId, portalSeqRequestDtos);
  }

  @DeleteMapping("del-portal/{portalId}")
  public ResponseEntity<CustomeResponse> delPortal(@PathVariable Long portalId) {
    return portalService.delPortal(portalId);
  }

  @PostMapping("add-dir-menu-to-portal")
  public ResponseEntity<CustomeResponse> addDirMenuToPortal(@RequestBody AddDirMenuToPortalRequest dirMenuToPortalRequestDto) {
    return portalService.addDirMenuToPortal(dirMenuToPortalRequestDto);
  }

  @DeleteMapping("del-dir-menu-from-portal/{partyId}/{seq}/{portalId}")
  public ResponseEntity<CustomeResponse> delDirMenuFromPortal(@PathVariable Long partyId, @PathVariable Long seq, @PathVariable Long portalId) {
    return portalService.delDirMenuFromPortal(partyId, seq, portalId);
  }

  @PostMapping("bind-menu-to-portals")
  public ResponseEntity<CustomeResponse> bindMenuToPortals(@RequestBody BindMenuToPortalsRequestDto bindMenuToPortalsRequestDto) {
    return portalService.bindMenuToPortals(bindMenuToPortalsRequestDto);
  }

  @GetMapping("{portalId}/dirMenus")
  public ResponseEntity<BaseResponseDto> qryListDirMenus(@PathVariable Long portalId, @RequestParam Long spId) {
    return portalService.qryListDirMenusPortal(portalId, spId);
  }
}
