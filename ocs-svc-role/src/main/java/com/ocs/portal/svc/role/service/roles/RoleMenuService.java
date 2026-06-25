package com.ocs.portal.svc.role.service.roles;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.BfmRolePriv;
import com.ocs.portal.svc.role.dto.request.roles.ProdRolePrivDto;
import com.ocs.portal.svc.role.dto.request.roles.RolePrivDto;
import com.ocs.portal.svc.role.dto.response.ComponentPrivDto;
import com.ocs.portal.svc.role.dto.response.ProdComponentPrivDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.ComponentPrivProjection;
import com.ocs.portal.svc.role.projection.MenuProjection;
import com.ocs.portal.svc.role.projection.RolePrivMenuProjection;
import com.ocs.portal.svc.role.repository.BfmMenuRepository;
import com.ocs.portal.svc.role.repository.RolePrivsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
@Service
public class RoleMenuService {

    @Autowired
    RolePrivsRepository rolePrivsRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    BfmMenuRepository bfmMenuRepository;

    Logger log = LoggerFactory.getLogger(RoleMenuService.class);

    public ResponseEntity<CustomeResponse> queryPrivListByRoleId(Long roleId) {
        List<ProdRolePrivDto> list = queryPrivListByRoleId(roleId, 'M');
        log.info("queryPrivListByRoleId {}", roleId);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
    }

    public List<ProdRolePrivDto> queryMenuByRoleId(List<Long> roleId) {
        log.info("queryPrivListMenuByRoleId {}", roleId);
        return roleMapper.doListProdRolePrivDto(rolePrivsRepository.selectMenuByRoleId(roleId,'M'));
    }

    public ResponseEntity<CustomeResponse> modRoleAutoOpen(ProdRolePrivDto rolePrivDto) {
        log.info("modRoleAutoOpen ");
        Long roleId = rolePrivDto.getRoleId();
        Long privId = rolePrivDto.getPrivId();
        String autoOpen = rolePrivDto.getAutoOpenMenu();
        rolePrivsRepository.modifyRoleAutoOpen(autoOpen, roleId, privId);
//            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "modRoleAutoOpen failed");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomeResponse(201, "success", null));
    }

    public ResponseEntity<CustomeResponse> update(ProdRolePrivDto rolePrivDto) {
        Long roleId = rolePrivDto.getRoleId();
        Long privId = rolePrivDto.getPrivId();
        Optional<BfmRolePriv> rolePriv = rolePrivsRepository.selectRolePriv(roleId,privId);
        if(rolePriv.isPresent()) {
            log.info("update role CRUD");
            if (rolePrivDto.getAddStatus()!=null){
                rolePriv.get().setAddStatus(rolePrivDto.getAddStatus().charAt(0));
            }
            if (rolePrivDto.getEditStatus()!=null){
                rolePriv.get().setEditStatus(rolePrivDto.getEditStatus().charAt(0));
            }
            if (rolePrivDto.getReadStatus()!=null){
                rolePriv.get().setReadStatus(rolePrivDto.getReadStatus().charAt(0));
            }
            if (rolePrivDto.getDeleteStatus()!=null){
                rolePriv.get().setDeleteStatus(rolePrivDto.getDeleteStatus().charAt(0));
            }
            rolePriv.get().setUpdateDate(LocalDate.now());
            rolePrivsRepository.save(rolePriv.get());
            log.info("role access CRUD updated");
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role id not found, update role failed");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomeResponse(201, "success", null));
    }


    private List<ProdRolePrivDto> queryPrivListByRoleId(Long roleId, Character type) {
        List<RolePrivMenuProjection> list = rolePrivsRepository.selectMenuPrivListByRoleId(roleId, type);
        List<ProdRolePrivDto> listProdRolePrivDto = new ArrayList<>();
        if (!list.isEmpty()) {
            listProdRolePrivDto = roleMapper.doListProdRolePrivDto(list);
        }
        return listProdRolePrivDto;
    }


    public ResponseEntity<CustomeResponse> meQueryMenuListByDirId(Long dirId){
        List<RolePrivDto> list = queryMenuListByDirId(dirId, null);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
    }

    public List<RolePrivDto> queryMenuListByDirId(Long dirId,Long roleId)  {
      List<MenuProjection> list = bfmMenuRepository.selectMenuListByDirId(dirId,roleId);
        List<RolePrivDto> listProdRolePrivDto = new ArrayList<>();
        if(!list.isEmpty()){
         listProdRolePrivDto = roleMapper.toListRolePrivDto(list);
        }
        return listProdRolePrivDto;
    }

    public ResponseEntity<CustomeResponse>queryRoleCompListByMenuId(Long menuId, Long roleId){
        List<ComponentPrivDto> list = queryCompListByMenuIdList(menuId,roleId);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
    }
    public List<ComponentPrivDto> queryCompListByMenuIdList(Long menuId, Long roleId) {
         List<ComponentPrivProjection> list = bfmMenuRepository.selectCompListByMenuIdRoleId(roleId,menuId);
        return roleMapper.toListComponentPriv(list);
    }


    public ResponseEntity<CustomeResponse> queryRoleComponentPrivLis(Long roleId) {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", queryRoleComponentPrivList(roleId)));
    }

    private List<ProdComponentPrivDto> queryRoleComponentPrivList(Long roleId) {
        return roleMapper.toListProdComponentPrivDto(rolePrivsRepository.selectRoleComponentPrivList(roleId));
    }

}
