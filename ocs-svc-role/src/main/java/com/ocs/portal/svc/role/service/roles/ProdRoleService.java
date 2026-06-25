package com.ocs.portal.svc.role.service.roles;

import com.ocs.portal.entity.BfmRolePrivHis;
import com.ocs.portal.svc.role.dto.request.roles.RolePrivDto;
import com.ocs.portal.svc.role.dto.response.ProdRolesDto;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.ocs.portal.svc.role.projection.RoleProjection;
import com.ocs.portal.svc.role.repository.BfmRolePortalRepository;
import com.ocs.portal.svc.role.repository.RolePrivHisRepository;
import com.ocs.portal.svc.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
@Service
public class ProdRoleService {
    Logger logger = LoggerFactory.getLogger(ProdRoleService.class);

    @Autowired
    BfmRolePortalRepository bfmRolePortalRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RolePrivHisRepository rolePrivHisRepository;


    public String concatComments(StringBuffer sb, String roleCode, String privName) {
        String comments = String.format("granted %s %s to role %s", new Object[]{privName, sb.substring(0, sb.length() - 1), roleCode});
        return comments;
    }

    public ProdRolesDto queryRoleById(Integer roleId) {
        RoleProjection roleProjection = roleRepository.selectByRoleId(roleId);
        if (roleProjection != null) {
            ProdRolesDto prodRolesDto = roleMapper.toProdRolesDto(roleProjection);
            return prodRolesDto;
        }
        return null;
    }

    public void clearComponentCacheByMenuId(Long menuId) {
        if (null == menuId)
            return;
    }

    public void recordRolePrivHis(List<RolePrivDto> rolePrivs) {
        try {
            LocalDate date = LocalDate.now();
            List<BfmRolePrivHis> rolePrivHisDtos = new ArrayList<>();
            for (RolePrivDto rolePrivDto : rolePrivs) {
                BfmRolePrivHis rolePrivHisDto = new BfmRolePrivHis();
                BeanUtils.copyProperties(rolePrivDto, rolePrivHisDto);
                rolePrivHisDto.setCreatedDate(date);
                rolePrivHisDto.setUpdateDate(date);
                rolePrivHisDto.setOperatorType("D");
                rolePrivHisDtos.add(rolePrivHisDto);
            }
            rolePrivHisRepository.saveAll(rolePrivHisDtos);
        } catch (Exception e) {
            logger.info("recordRolePrivHis error");
            logger.error("error msg : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed recordRolePrivHis ..");
        }
    }
}
