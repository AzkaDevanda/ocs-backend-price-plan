package com.sts.sinorita.svc.role.service.users;

import com.sts.sinorita.svc.role.dto.request.roles.ProdRolePrivDto;
import com.sts.sinorita.svc.role.dto.response.ProdComponentPrivDto;
import com.sts.sinorita.svc.role.dto.response.ProdUserPrivDto;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.repository.RolePrivsRepository;
import com.sts.sinorita.svc.role.utils.ThreadLocalMap;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdMenuService {

    @Autowired
    RolePrivsRepository rolePrivsRepository;

    @Autowired
    RoleMapper roleMapper;

    Logger logger = LoggerFactory.getLogger(ProdMenuService.class);

    public List<ProdComponentPrivDto> queryUserComponentListByMenuId(Long menuId, List<String> keyPrivCompOutId) {
        logger.debug("queryUserComponentListByMenuId method");
        logger.info("queryUserComponentListByMenuId method");
        return queryUnavailableElementsByMenuIdAndUserId(menuId, ThreadLocalMap.getUserId(), keyPrivCompOutId);
    }

    public List<ProdComponentPrivDto> queryUnavailableElementsByMenuIdAndUserId(Long menuId, Long userId, List<String> keyPrivCompOutId) {//keyPrivCompOutId = privId
        List<ProdComponentPrivDto> list = queryComponentListByMenuId(menuId);
        List<ProdUserPrivDto> userPrivList = queryPrivListByUserId(userId, null);
        List<String> allUserReadOnlyList = new ArrayList<>();
        List<String> allUserVisableList = new ArrayList<>();
        for (ProdUserPrivDto userPrivDto : userPrivList) {
            if ("1".equals(userPrivDto.getPrivLevel())) {
                allUserReadOnlyList.add(userPrivDto.getPrivId().toString());
                continue;
            }
            allUserVisableList.add(userPrivDto.getPrivId().toString());
        }
        List<Long> roleIds = new ArrayList<>(queryUserRoleIdsByUserId(ThreadLocalMap.getUserId()));
        roleIds.addAll(queryRoleIdsByJobId(ThreadLocalMap.getJobId()));
        List<String> allRoleReadOnlyList = new ArrayList<>();
        List<String> allRoleVisableList = new ArrayList<>();
        getRoleComponent(roleIds, allRoleReadOnlyList, allRoleVisableList);
        List<ProdComponentPrivDto> compOutList = new ArrayList<>();
        for (ProdComponentPrivDto dto : list) {
            if (allRoleVisableList.contains(dto.getPrivId().toString()))
                continue;
            if (!allUserReadOnlyList.isEmpty() && allUserReadOnlyList.contains(dto.getPrivId().toString())) {
                dto.setPrivLevel("1");
                compOutList.add(dto);
                continue;
            }
            if (allRoleReadOnlyList.contains(dto.getPrivId().toString())) {
                if (!allUserVisableList.contains(dto.getPrivId().toString())) {
                    dto.setPrivLevel("1");
                    compOutList.add(dto);
                }
                continue;
            }
            if (!keyPrivCompOutId.isEmpty() && keyPrivCompOutId.contains(dto.getPrivId().toString())) {
                compOutList.add(dto);
            }
        }
        logger.info("queryUserComponentListByMenuId end");
        return compOutList;
    }


    private void getRoleComponent(List<Long> roleIds, List<String> allRoleReadOnlyList, List<String> allRoleVisableList) {
        List<ProdRolePrivDto> rolePrivDtos = new ArrayList<>();
        rolePrivDtos = queryRolePrivByRoleIds(roleIds);

        if (ObjectUtils.isNotEmpty(rolePrivDtos)) {
            for (ProdRolePrivDto rolePrivDto : rolePrivDtos) {
                if ("1".equals(rolePrivDto.getPrivLevel())) {
                    allRoleReadOnlyList.add(rolePrivDto.getPrivId().toString());
                    continue;
                }
                allRoleVisableList.add(rolePrivDto.getPrivId().toString());
            }
        }
    }

    public List<ProdRolePrivDto> queryRolePrivByRoleIds(List<Long> roleIds) {
        return roleMapper.toListProdRolePrivDto(rolePrivsRepository.queryRolePrivByRoleIds(roleIds));
    }

    public List<Long> queryUserRoleIdsByUserId(Long userId) {
        Long spId = ThreadLocalMap.getSpId();
        return rolePrivsRepository.queryRoleIdsByUserId(userId, spId);
    }

    public ArrayList<Long> queryRoleIdsByJobId(Long jobId) {
        Long spId = ThreadLocalMap.getSpId();
        return rolePrivsRepository.queryRoleIdsByJobId(jobId, spId);
    }


    public List<ProdUserPrivDto> queryPrivListByUserId(Long userId, String type) {
        return roleMapper.toListUserPrivDto(rolePrivsRepository.queryPrivListByUserId(userId, type));
    }


    public List<ProdComponentPrivDto> queryComponentListByMenuId(Long menuId) {
        return queryComponentListByCondition(menuId, null);
    }

    public List<ProdComponentPrivDto> queryComponentListByCondition(Long menuId, String privCode)   {
        return roleMapper.toListProdComponentPrivDto(rolePrivsRepository.selectComponentListByCondition(menuId, privCode));
    }

}

