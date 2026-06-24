package com.sts.sinorita.svc.role.service.users;

import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.svc.role.dto.response.ParamsDto;
import com.sts.sinorita.svc.role.dto.response.SecurityRuleDto;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.projection.ParamsProjection;
import com.sts.sinorita.svc.role.projection.SecurityRuleProjection;
import com.sts.sinorita.svc.role.repository.BfmParamsRepository;
import com.sts.sinorita.svc.role.repository.BfmSecurityRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityRulesService {

    @Autowired
    BfmParamsRepository bfmParamsRepository;

    @Autowired
    BfmSecurityRuleRepository bfmSecurityRuleRepository;

    Logger logger = LoggerFactory.getLogger(SecurityRulesService.class);

    @Autowired
    private RoleMapper roleMapper;

    public ResponseEntity<CustomeResponse> queryCurrentSecurityRules() {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", queryCurrentSecurityRule()));
    }

    public SecurityRuleDto queryCurrentSecurityRule() {
        logger.info("queryCurrentSecurityRule");
        logger.debug("queryCurrentSecurityRule");
        Optional<ParamsProjection> pro = bfmParamsRepository.querySysParamByMask("SECURITY_LEVEL");
        ParamsDto dto = new ParamsDto();
        if (pro.isPresent()) {
            dto = roleMapper.toParamDto(pro.get());
        }
        Optional<SecurityRuleProjection> securityRuleProjection = bfmSecurityRuleRepository.querySecurityRule(dto.getCurrentValue());
        SecurityRuleDto securityRule = new SecurityRuleDto();
        if (securityRuleProjection.isPresent()) {
            securityRule = roleMapper.toSecurityRule(securityRuleProjection.get());
        }
        return securityRule;
    }


    public ResponseEntity<CustomeResponse>querySysParamValueByMaskRc(String mask){
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200,"success", querySysParamValueByMask(mask)));
    }
    public ParamsDto querySysParamValueByMask(String mask) {
        logger.info("querySysParamValueByMask");
        logger.debug("querySysParamValueByMask");
        Optional<ParamsProjection> projection = bfmParamsRepository.querySysParamByMask(mask);
        ParamsDto dto = new ParamsDto();
        if (projection.isPresent()) {
            dto = roleMapper.toParamDto(projection.get());
        }
        return dto;
    }

}
