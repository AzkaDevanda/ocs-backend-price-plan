package com.sts.sinorita.svc.role.service.roles;

import com.sts.sinorita.entity.BfmServLog;
import com.sts.sinorita.svc.role.dto.request.log.LogDto;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.repository.BfmServLogRepository;
import com.sts.sinorita.svc.role.utils.IPUtil;
import com.sts.sinorita.svc.role.utils.LogEvent;
import com.sts.sinorita.svc.role.utils.ThreadLocalMap;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
@Service
public class LogService {

    public String userId = "";

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    BfmServLogRepository bfmServLogRepository;

    Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    IPUtil ipUtil;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void addAuthLog(LogEvent logEvent, String comments, int isSuccess) {
        LogDto logTemp = new LogDto();
        logTemp.setEventType(logEvent.getEventType());
        logTemp.setEventCode(logEvent.getEventCode());
        logTemp.setComments(comments);
        logTemp.setIsSuccess(isSuccess);

        BfmServLog logServ = new BfmServLog();
        try {
        if (StringUtils.isEmpty(logTemp.getPartyCode()) && ThreadLocalMap.getUserId() != null) {
            logTemp.setPartyCode((ThreadLocalMap.getUserCode() == null) ? null : ThreadLocalMap.getUserCode().toString());
            logTemp.setPartyId(ThreadLocalMap.getUserId());
            logTemp.setPartyName(ThreadLocalMap.getUserName());
        }
        if (StringUtils.isEmpty(logTemp.getSrcIp()))
            logTemp.setSrcIp(ThreadLocalMap.getLoginIp());
            logTemp.setPartyType("Z");
            logTemp.setEventSrc("ff");
            logTemp.setServerIp(ipUtil.getLocalIP());
            logTemp.setSpId(ThreadLocalMap.getSpId());

            logServ = roleMapper.toBfmServLog(logTemp);
            logServ.setPartId(1);
            logServ.setLogDate(LocalDateTime.now());
            logServ.setCreatedBy(getUserId());
            logger.info("add auth log");
            bfmServLogRepository.save(logServ);
            logger.info("addAuthLog success");
        } catch (Exception e) {
            logger.error("addAuthLog error"+ e.getMessage());
            logger.error("addAuthLog error"+ e.getStackTrace());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed addAuthLog .."+e.getStackTrace());
        }
    }
}
