package com.sts.sinorita.svc.role.service.roles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.entity.pot.BfmPriv;
import com.sts.sinorita.svc.role.dto.request.FilterCondition;
import com.sts.sinorita.svc.role.dto.request.dirMenu.PrivRequestDto;
import com.sts.sinorita.svc.role.mapper.BfmPrivMapper;
import com.sts.sinorita.svc.role.repository.BfmPrivRepository;
import com.sts.sinorita.svc.role.service.ValidationService;
import com.sts.sinorita.svc.role.utils.MessageUtil;

@Service
public class PrivService {
  // =====> Repository
  @Autowired
  private BfmPrivRepository bfmPrivRepository;

  // ====> Service
  @Autowired
  private ValidationService validationService;
  
  // =====> Mapper
  @Autowired
  private BfmPrivMapper bfmPrivMapper;

  public PrivRequestDto addPriv(PrivRequestDto privRequest) {
    List<FilterCondition> filters = new ArrayList<>();
    filters.add(new FilterCondition("PRIV_NAME", "UPPER", "=", privRequest.getPrivName()));
    filters.add(new FilterCondition("PRIV_TYPE", "UPPER", "=", privRequest.getPrivType()));
    filters.add(new FilterCondition("STATE", "NONE", "=", "A"));
    filters.add(new FilterCondition("APP_ID", "NONE", "=", privRequest.getAppId()));
    filters.add(new FilterCondition("SP_ID", "NONE", "=", privRequest.getSpId()));
    
    if(validationService.isSameValueWithCustomFilters("BFM_PRIV", filters, null, null))
      if (privRequest.getPrivType() != null && privRequest.getPrivType().equals("M")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00040"));
      } else if (privRequest.getPrivType() != null && privRequest.getPrivType().equals("C")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00050"));
      } else {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00019"));
      }

    if (StringUtils.isBlank(privRequest.getIsHold()))
      privRequest.setIsHold("Y");
    if (StringUtils.isBlank(privRequest.getIsAuthorized()))
      privRequest.setIsAuthorized("N");
    
    BfmPriv bfmPriv = bfmPrivMapper.toEntityBfmPriv(privRequest);
    bfmPriv.setState("A");
    bfmPriv.setStateDate(LocalDateTime.now());
    bfmPrivRepository.save(bfmPriv);
    
    return bfmPrivMapper.toPrivRequestDto(bfmPriv);
  }

  public void modPriv(PrivRequestDto privRequest) {
    List<FilterCondition> filters = new ArrayList<>();
    filters.add(new FilterCondition("PRIV_NAME", "UPPER", "=", privRequest.getPrivName()));
    filters.add(new FilterCondition("PRIV_TYPE", "UPPER", "=", privRequest.getPrivType()));
    filters.add(new FilterCondition("STATE", "NONE", "=", "A"));
    filters.add(new FilterCondition("APP_ID", "NONE", "=", privRequest.getAppId()));
    filters.add(new FilterCondition("SP_ID", "NONE", "=", privRequest.getSpId()));
    
    if(validationService.isSameValueWithCustomFilters("BFM_PRIV", filters, "PRIV_ID", privRequest.getPrivId()))
      if (privRequest.getPrivType() != null && privRequest.getPrivType().equals("M")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00040"));
      } else if (privRequest.getPrivType() != null && privRequest.getPrivType().equals("C")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00050"));
      } else {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00019"));
      }

    if (StringUtils.isBlank(privRequest.getIsHold()))
      privRequest.setIsHold("Y");
    if (StringUtils.isBlank(privRequest.getIsAuthorized()))
      privRequest.setIsAuthorized("N");
    
    BfmPriv exPriv = bfmPrivRepository.findById(privRequest.getPrivId()).orElse(null);
    if (exPriv != null) {
      exPriv.setPrivCode(privRequest.getPrivCode());
      exPriv.setPrivName(privRequest.getPrivName());
      exPriv.setUrl(privRequest.getUrl());
      exPriv.setIsHold(privRequest.getIsHold());
      exPriv.setComments(privRequest.getComments());
      bfmPrivRepository.save(exPriv);
    }

    return;
  }

  public void delPriv(Long privId, String privType) {
    Map<String, Object> checkValidation = new HashMap<>();
    checkValidation.put("PRIV_ID", privId);

    if(validationService.checkReferenceExists("BFM_USER_PRIV", checkValidation, null, null))
      if (privType != null && privType.equals("M")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00041"));
      } else if (privType != null && privType
        .equals("C")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00048"));
      } else if (privType != null && privType
        .equals("P")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00052"));
      } else {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00013"));
      }

    if(validationService.checkReferenceExists("BFM_ROLE_PRIV", checkValidation, null, null))
      if (privType != null && privType.equals("M")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00042"));
      } else if (privType != null && privType
        .equals("C")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00049"));
      } else if (privType != null && privType
        .equals("P")) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00053"));
      } else {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00014"));
      }
    
    BfmPriv exPriv = bfmPrivRepository.findById(privId).orElse(null);
    if (exPriv != null) {
      exPriv.setState("X");
      exPriv.setStateDate(LocalDateTime.now());
      bfmPrivRepository.save(exPriv);
    }

    return;
  }
}
