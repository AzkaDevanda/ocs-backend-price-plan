package com.sts.sinorita.svc.role.service.users;

import com.sts.sinorita.entity.BfmStaff;
import com.sts.sinorita.entity.BfmUser;
import com.sts.sinorita.svc.role.dto.request.AttrDefDto;
import com.sts.sinorita.svc.role.dto.request.AttrDto;
import com.sts.sinorita.svc.role.dto.response.StaffDto;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.projection.StafDtoProjection;
import com.sts.sinorita.svc.role.repository.BfmStaffRepository;
import com.sts.sinorita.svc.role.repository.BfmUserRepository;
import com.sts.sinorita.svc.role.repository.custom.BfmStaffCustomRepository;
import com.sts.sinorita.svc.role.utils.DateUtils;
import com.sts.sinorita.svc.role.utils.ThreadLocalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StafService {

    Logger logger = LoggerFactory.getLogger(StafService.class);

    @Autowired
    BfmStaffRepository bfmStaffRepository;

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    private BfmUserRepository bfmUserRepository;

//    @Autowired
//    UsersService usersService;

    @Autowired
    StaffHisService staffHisService;


    public int disableStaff(Long staffId, Boolean userFlag) {
        logger.info("disableStaff");
        StaffDto staffDto = queryStaffDtoByStaffId(staffId);
        LocalDateTime localDateTime = LocalDateTime.now();

//        List<AttrDto> attrDataList =  qrySpecificAttrDataList("BFM_STAFF", "STAFF_ID", staffId.toString(), null, ThreadLocalMap.getSpId());
//        RedisOpUtil.delete("StaffDtoMap:staffId" + staffId);
        staffDto.setState("X");
        staffDto.setStateDate(localDateTime.toLocalDate());
        staffDto.setUpdateDate(localDateTime);

        BfmStaff bfmStaff = roleMapper.toBfmStaff(staffDto);
        bfmStaffRepository.save(bfmStaff);

        Long userId = queryStaffDtoByStaffId(staffId).getUserId();
        if (userFlag.booleanValue() && userId != null){
             BfmUser bfmUser = bfmUserRepository.findById(userId.intValue()).orElse(null);
             if (bfmUser != null){
                 bfmUser.setState("X");
                 bfmUserRepository.save(bfmUser);
                 logger.info("update state bfmUser");
             }
        }

        String comments = "Disable staff";
        Long operateUserId = ThreadLocalMap.getUserId();
        String remoteIp = ThreadLocalMap.getLoginIp();
        staffHisService.addStaffHis(staffDto, operateUserId, remoteIp, comments);
        logger.info("disableStaff success");
        return 0;
    }


    private StaffDto queryStaffDtoByStaffId(Long staffId) {
     return roleMapper.toStaffDtoEntity(bfmStaffRepository.findById(staffId).orElse(null));
    }
    @Autowired
    BfmStaffCustomRepository bfmStaffCustomRepository;

    public List<AttrDto> qrySpecificAttrDataList(String tableName, String keyName, String keyValue, List<String> attrCodes, Long spId) {
        List<AttrDefDto> attrDefList = bfmStaffRepository.qryAttrDefList(tableName, spId, attrCodes);
        List<Map<String, Object>> externalAttrDtoList = null;
        Map<String, Object> basicAttrList = null;
        if ("BFM_ORG".equalsIgnoreCase(tableName)) {
            basicAttrList = bfmStaffRepository.selectOrgBasicAttrList(Long.valueOf(keyValue),null);
        } else if ("BFM_STAFF".equalsIgnoreCase(tableName)) {
            basicAttrList = bfmStaffRepository.selectStaffBasicAttrList(Long.valueOf(keyValue));
        }
        if (!tableName.contains("ATTR"))
            tableName = tableName + "_ATTR";
        externalAttrDtoList = bfmStaffCustomRepository.getCurrentValueInAttrTableList(tableName, keyName, keyValue, null);
        List<AttrDto> attrData = new ArrayList<>();
        for (AttrDefDto attrDefDto : attrDefList) {
            Object currentValue = "";
            if (attrDefDto.getTableName().contains("ATTR")) {
                for (Map<String, Object> ext : externalAttrDtoList) {
                    if (attrDefDto.getExtCode().equals(ext.get("ATTR_CODE")))
                        currentValue = (ext.get("ATTR_VALUE") == null) ? "" : ext.get("ATTR_VALUE");
                }
            } else if (basicAttrList != null) {
                currentValue = (basicAttrList.get(attrDefDto.getColumnName()) == null) ? "" : basicAttrList.get(attrDefDto.getColumnName());
                if ("d".equalsIgnoreCase(attrDefDto.getColumnType()) && !"".equals(currentValue))
                    currentValue = DateUtils.formatTimeString(DateUtils.formatDateWithAuto(currentValue.toString()));
            }
            AttrDto bo = new AttrDto();
            bo.setAttrId(attrDefDto.getExtCode());
            bo.setAttrCode(attrDefDto.getExtCode());
            String var = currentValue.toString();
            bo.setAttrValue(var);
            bo.setDisplayAble(attrDefDto.getDisplayAble());
            attrData.add(bo);
        }
        return attrData;
    }

    public StaffDto queryStaffByUserId(Long userId){
        Optional<StafDtoProjection> optStafDto = bfmStaffRepository.selectStaff(null, userId);
        return optStafDto.map(stafDtoProjection -> roleMapper.toStaffDto(stafDtoProjection)).orElse(null);
    }

    public void removeStaff(Long staffId)  {
        Optional<StafDtoProjection> optStafDto = bfmStaffRepository.selectStaff(staffId, null);
        StaffDto staffDto = optStafDto.map(stafDtoProjection -> roleMapper.toStaffDto(stafDtoProjection)).orElse(null);
        assert staffDto != null;
        bfmStaffRepository.removeLimitsByUserId(staffDto.getUserId());
    }
}
