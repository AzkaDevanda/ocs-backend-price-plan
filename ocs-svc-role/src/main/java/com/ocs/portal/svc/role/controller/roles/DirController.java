package com.ocs.portal.svc.role.controller.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import com.ocs.portal.svc.role.dto.request.dirMenu.DirMenuRequestDto;
import com.ocs.portal.svc.role.dto.request.dirMenu.DirRequestDto;
import com.ocs.portal.svc.role.dto.request.dirMenu.MenuRequestDto;
import com.ocs.portal.svc.role.service.roles.DirService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "api/dirs")
public class DirController {
  @Autowired
  DirService dirService;

  Logger logger = Logger.getLogger(DirController.class.getName());

  @GetMapping(value = { "{parentId}/dirs" })
  public ResponseEntity<CustomeResponse> queryDirListByParentId(@PathVariable Long parentId) {
    logger.info("Request queryDirListByParentId");
    return dirService.queryDirListByParentId(parentId);
  }

  @GetMapping(value = { "dirs/{dirId}/menus" })
  public ResponseEntity<CustomeResponse> qryMenuListByDirId(@PathVariable Long dirId) {
    logger.info("Request qryMenuListByDirId");
    return dirService.qryMenuListByDirId(dirId);
  }

  @GetMapping("all-dirs-or-menu")
  public ResponseEntity<CustomeResponse> getAllDirsOrMenu(@RequestParam(required = false) Long parentId) {
    return dirService.getAllDirsOrMenu(parentId);
  }
  
  @PostMapping("add-dir")
  public ResponseEntity<CustomeResponse> addDir(@RequestBody DirRequestDto dirRequestDto) {
    return dirService.addDir(dirRequestDto);
  }

  @PutMapping("mod-dir")
  public ResponseEntity<CustomeResponse> modDir(@RequestBody DirRequestDto dirRequestDto) {
    return dirService.modDir(dirRequestDto);
  }

  @DeleteMapping("del-dir/{dirId}")
  public ResponseEntity<CustomeResponse> delDir(@PathVariable Long dirId, @RequestParam Long spId) {
    return dirService.delDir(dirId, spId);
  }

  // ====> Menu Management
  @GetMapping("qry-menu-list")
  public ResponseEntity<CustomeResponse> qryMenuList(@ModelAttribute PagingRequestDto pagingRequestDto, @RequestParam(required = false) String url, @RequestParam(required = false) String privName) {
    return dirService.qryMenuList(pagingRequestDto, url, privName);
  }

  @GetMapping("qry-all-menu-list")
  public ResponseEntity<CustomeResponse> qryAllMenuList(@ModelAttribute PagingRequestDto pagingRequestDto, @RequestParam(required = false) String state, @RequestParam(required = false) Long appId, @RequestParam(required = false) String privName, @RequestParam(required = false) String url) {
    return dirService.qryAllMenuList(pagingRequestDto, state, appId, privName, url);
  }

  @GetMapping("qry-no-dir-menu-list")
  public ResponseEntity<CustomeResponse> qryNoDirMenuList(@ModelAttribute PagingRequestDto pagingRequestDto, @RequestParam(required = false) String state, @RequestParam(required = false) Long appId, @RequestParam(required = false) String privName, @RequestParam(required = false) String url) {
    return dirService.qryNoDirMenuList(pagingRequestDto, state, appId, privName, url);
  }

  @PostMapping("add-menu")
  public ResponseEntity<CustomeResponse> addMenu(@RequestBody MenuRequestDto menuRequestDto) {
    return dirService.addMenu(menuRequestDto);
  }

  @PutMapping("mod-menu")
  public ResponseEntity<CustomeResponse> modMenu(@RequestBody MenuRequestDto menuRequestDto) {
    return dirService.modMenu(menuRequestDto);
  }

  @DeleteMapping("del-menu/{menuId}")
  public ResponseEntity<CustomeResponse> delMenu(@PathVariable Long menuId) {
    return dirService.delMenu(menuId);
  }

  // ====> Dir Menu Management
  @PostMapping("add-dir-menu")
  public ResponseEntity<CustomeResponse> addDirMenu(@RequestBody DirMenuRequestDto dirMenuRequestDto) {
    return dirService.addDirMenu(dirMenuRequestDto);
  }
  
  @DeleteMapping("del-dir-menu-from-dir")
  public ResponseEntity<CustomeResponse> delDirMenuFromDir(@RequestParam Long dirId, @RequestParam List<Long> menuIds, @RequestParam Long spId) {
    return dirService.delDirMenuFromDir(dirId, menuIds, spId);
  }
}
