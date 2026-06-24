package com.sts.sinorita.svc.role.service.users;

import com.sts.sinorita.entity.BfmStaffHis;
import com.sts.sinorita.svc.role.dto.request.StaffHistoryDto;
import com.sts.sinorita.svc.role.dto.response.StaffDto;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.repository.BfmStaffHisRepository;
import com.sts.sinorita.svc.role.repository.BfmStaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StaffHisService {

    @Autowired
    BfmStaffHisRepository bfmStaffHisRepository;

    @Autowired
    RoleMapper roleMapper;

    Logger logger = LoggerFactory.getLogger(StaffHisService.class);
    public int addStaffHis(StaffDto staffDto, Long modifierUserId, String modifierIpAddress, String modifierComments) {
        StaffHistoryDto staffHistoryDto = new StaffHistoryDto();
        staffHistoryDto.setStaffCode(staffDto.getStaffCode());
        staffHistoryDto.setStaffName(staffDto.getStaffName());
        staffHistoryDto.setStaffId(staffDto.getStaffId());
        staffHistoryDto.setCreatedDate(staffDto.getCreatedDate());
        staffHistoryDto.setState(staffDto.getState());
        staffHistoryDto.setStateDate(staffDto.getStateDate());
        staffHistoryDto.setUserId(staffDto.getUserId());
        staffHistoryDto.setRecUserId(modifierUserId);
        staffHistoryDto.setRecCreateDate(LocalDateTime.now().toLocalDate());
        staffHistoryDto.setIpAddress(modifierIpAddress);
        staffHistoryDto.setComments(modifierComments);
        staffHistoryDto.setEmail(staffDto.getEmail());
        staffHistoryDto.setPhone(staffDto.getPhone());
        staffHistoryDto.setAddress(staffDto.getAddress());
        staffHistoryDto.setRemark(staffDto.getRemark());
        staffHistoryDto.setExtStr01(staffDto.getExtStr01());
        staffHistoryDto.setExtStr02(staffDto.getExtStr02());
        staffHistoryDto.setExtStr03(staffDto.getExtStr03());
        staffHistoryDto.setExtStr04(staffDto.getExtStr04());
        staffHistoryDto.setExtStr05(staffDto.getExtStr05());
        staffHistoryDto.setExtNum01(staffDto.getExtNum01());
        staffHistoryDto.setExtNum02(staffDto.getExtNum02());
        staffHistoryDto.setExtDat01(staffDto.getExtDat01());
        staffHistoryDto.setExtDat02(staffDto.getExtDat02());

        if ("Add new staff and user".equals(modifierComments)) {
            logger.info("Add new staff and user");
            staffHistoryDto.setOperatorType("A");
            BfmStaffHis staffHis = roleMapper.toStaffHis(staffHistoryDto);
            bfmStaffHisRepository.save(staffHis);
            logger.info("addStaffHis success : " + staffHis.getStaffCode());
        }
        return 0;
    }

}
