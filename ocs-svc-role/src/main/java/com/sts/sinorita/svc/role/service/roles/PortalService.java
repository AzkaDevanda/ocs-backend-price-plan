package com.sts.sinorita.svc.role.service.roles;

import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.BfmDir;
import com.sts.sinorita.entity.BfmPortalMenu;
import com.sts.sinorita.entity.BfmRolePortal;
import com.sts.sinorita.entity.embeddable.BfmPortalMenuId;
import com.sts.sinorita.entity.embeddable.BfmRolePortalId;
import com.sts.sinorita.entity.pot.BfmPortal;
import com.sts.sinorita.entity.pot.BfmPortalChannel;
import com.sts.sinorita.svc.role.constant.HttpStatusConstant;
import com.sts.sinorita.svc.role.dto.request.AddDirMenuToPortalRequest;
import com.sts.sinorita.svc.role.dto.request.BindMenuToPortalsRequestDto;
import com.sts.sinorita.svc.role.dto.request.DirNodeRequestDto;
import com.sts.sinorita.svc.role.dto.request.FilterCondition;
import com.sts.sinorita.svc.role.dto.request.PartyMenuRequestDto;
import com.sts.sinorita.svc.role.dto.request.PortalRequestDto;
import com.sts.sinorita.svc.role.dto.request.PortalSeqRequestDto;
import com.sts.sinorita.svc.role.dto.request.common.PagingRequestDto;
import com.sts.sinorita.svc.role.dto.request.roles.DirMenusDto;
import com.sts.sinorita.svc.role.dto.request.roles.PortalDto;
import com.sts.sinorita.svc.role.dto.request.roles.RolePortalDto;
import com.sts.sinorita.svc.role.dto.response.*;
import com.sts.sinorita.svc.role.mapper.BfmPortalMapper;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.projection.RolePortalProjection;
import com.sts.sinorita.svc.role.repository.BfmDirRepository;
import com.sts.sinorita.svc.role.repository.BfmPortalBizParamRepository;
import com.sts.sinorita.svc.role.repository.BfmPortalChannelRepository;
import com.sts.sinorita.svc.role.repository.BfmPortalMenuRepository;
import com.sts.sinorita.svc.role.repository.BfmPortalRepository;
import com.sts.sinorita.svc.role.repository.BfmRolePortalRepository;
import com.sts.sinorita.svc.role.repository.PortalRepository;
import com.sts.sinorita.svc.role.service.ValidationService;
import com.sts.sinorita.svc.role.utils.LogEvent;
import com.sts.sinorita.svc.role.utils.MessageUtil;
import com.sts.sinorita.svc.role.utils.ValidationUtils;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortalService {
  Logger log = LoggerFactory.getLogger(PortalService.class);

  // ====> Service
  @Autowired
  ProdRoleService prodRoleService;

  @Autowired
  LogService logService;
  
  @Autowired
  PortalMenuService portalMenuService;

  @Autowired
  private ValidationService validationService;

  // =====> Repository
  @Autowired
  private BfmRolePortalRepository bfmRolePortalRepository;

  @Autowired
  private PortalRepository portalRepository;

  @Autowired
  private BfmPortalRepository bfmPortalRepository;

  @Autowired
  private BfmPortalChannelRepository bfmPortalChannelRepository;

  @Autowired
  private BfmPortalBizParamRepository bfmPortalBizParamRepository;

  @Autowired
  private BfmPortalMenuRepository bfmPortalMenuRepository;

  @Autowired
  private BfmDirRepository bfmDirRepository;

  // =====> Mapper
  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private BfmPortalMapper bfmPortalMapper;

  @Autowired
  private  EntityManager em;

  public ResponseEntity<CustomeResponse> grantPortalToRoleNews(Long roleId, List<PortalDto> list) {
    log.info("grantPortalToRoleNew begin ...");
    String comments = "";
    try {
      addPortalToRoleNew(roleId, list);
      ProdRolesDto roleDto = prodRoleService.queryRoleById(roleId.intValue());
      StringBuffer sb = new StringBuffer("");
      for (PortalDto portalDto : list) {
        sb.append(portalDto.getPortalName()).append(",");
        comments = prodRoleService.concatComments(sb, roleDto.getRoleCode(), "portal");
        logService.addAuthLog(LogEvent.GRANT_PORTAL_TO_ROLE, comments, 0);
      }
    } catch (Exception e) {
      logService.addAuthLog(LogEvent.GRANT_PORTAL_TO_ROLE, comments, 1);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    log.debug("grantPortalToRoleNew end ...");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomeResponse(201, "success", null));

  }

  public int addPortalToRoleNew(Long roleId, List<PortalDto> list) {
    return addRolePortal(roleId, list);
  }

  public int addRolePortal(Long roleId, List<PortalDto> list) {
    LocalDate now = LocalDate.now();
    if (list.isEmpty()) {
      return 0;
    }
    String state = "A";
    List<BfmRolePortal> listRolePortal = new ArrayList<>();
    for (PortalDto dto : list) {
      BfmRolePortal rolePortalDto = new BfmRolePortal();
      BfmRolePortalId portalId = new BfmRolePortalId();
      portalId.setRoleId(roleId);
      portalId.setPortalId(dto.getPortalId());
      rolePortalDto.setId(portalId);
      rolePortalDto.setState(state);
      rolePortalDto.setStateDate(now);
      listRolePortal.add(rolePortalDto);
    }
    bfmRolePortalRepository.saveAll(listRolePortal);
    return 0;
  }

  public ResponseEntity<CustomeResponse> ungrantPortalToRole(Long roleId, List<PortalDto> list) {
    String comments = "";
    try {
      removePortalFromRole(roleId, list);
      ProdRolesDto roleDto = prodRoleService.queryRoleById(roleId.intValue());
      StringBuffer sb = new StringBuffer("");
      for (PortalDto portalDto : list)
        sb.append(portalDto.getPortalName()).append(",");

      comments = String.format("Cancel portal %s from role %s", new Object[] { sb.substring(0, sb.length() - 1), roleDto.getRoleCode() });
      logService.addAuthLog(LogEvent.UNGRANT_PORTAL_TO_ROLE, comments, 0);
    } catch (Exception e) {
      logService.addAuthLog(LogEvent.UNGRANT_PORTAL_TO_ROLE, comments, 1);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    log.debug("ungrantPortalToRole end ...");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomeResponse(201, "success", null));

  }

  public void removePortalFromRole(Long roleId, List<PortalDto> portalList) {
    List<Long> listPortalId = new ArrayList<>();
    for (PortalDto portalDto : portalList) {
      if (portalDto.getPortalId() != null) {
        listPortalId.add(portalDto.getPortalId());
      }
    }
    List<RolePortalProjection> list = bfmRolePortalRepository.selectRolePortalsByRoleIdAndPortalList(roleId, listPortalId);
    List<RolePortalDto> listRole = roleMapper.toListRolePortalDto(list);
    // recordRolePortalHis(listRole);
    portalRepository.delRolePortalByRoleId(roleId, listPortalId);
  }

  public ResponseEntity<CustomeResponse> querySubDirListByParentInPortal(Long partyId, Long portalId) {
    List<PortalMenuDto> querySubDirListByParentInPortal = portalMenuService.selectSubDirListByParentInPortal(partyId, portalId);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomeResponse(201, "success", querySubDirListByParentInPortal));
  }

  public ResponseEntity<CustomeResponse> queryRoleMenuListByPartyId(Long partyId, Long portalId) {
    List<PortalMenuDto> list = portalMenuService.selectMenuListByPartyIdRoleId(partyId, portalId);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomeResponse(201, "success", list));
  }

  public void recordRolePortalHis(List<RolePortalDto> rolePortals) {
    LocalDateTime date = LocalDateTime.now();
    List<RolePortalHisDto> rolePortalHisDtos = new ArrayList<>();
    for (RolePortalDto rolePortalDto : rolePortals) {
      RolePortalHisDto rolePortalHisDto = new RolePortalHisDto();
      BeanUtils.copyProperties(rolePortalDto, rolePortalHisDto);
      rolePortalHisDto.setCreatedDate(date);
      rolePortalHisDto.setUpdateDate(date);
      rolePortalHisDto.setOperatorType("D");
      // rolePortalHisDto.setRecId(this.sequenceService.getSequenceId("T_BFM_ROLE_PORTAL_HIS_ID_SEQ"));
      rolePortalHisDtos.add(rolePortalHisDto);
      // RolePortalHis
    }
    // this.rolePortalHisMapper.batchInsert(rolePortalHisDtos);
  }

  public List<PortalDto> queryPortalList() {
    return roleMapper.toListPortalDto(portalRepository.queryPortalList());
  }

  public ResponseEntity<CustomeResponse> qryPortalListByMenuId(PagingRequestDto pagingRequest, Long partyId, String type, Long spId, Integer isTypeMenu) {
    Pageable pageable = pagingRequest.getPage() != null ? PageRequest.of(Math.max(pagingRequest.getPage() - 1, 0), pagingRequest.getSize(), Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy())) : Pageable.unpaged();

    var data = bfmPortalMenuRepository.qryPortalListByMenuId(pageable, partyId, type, spId, isTypeMenu);
    var result = data.getContent().stream().map(bfmPortalMapper::toQryPortalListByMenuIdResponseDto).toList();

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }

  // public ResponseEntity<CustomeResponse> queryCompListNoPortal(Long roleId) {
  // return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200,
  // "success", queryRoleComponentPrivList(roleId)));
  // }

  // public List<ProdComponentPrivDto> queryCompListNoMenuId(Long roleId){
  // return
  // roleMapper.toListProdComponentPrivDto(rolePrivsRepository.selectCompListByMenuIdRoleId(null,
  // roleId));
  // }


  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addPortal(PortalRequestDto portalRequestDto) {
    List<FilterCondition> filters = new ArrayList<>();
    filters.add(new FilterCondition("PORTAL_NAME", "UPPER", "=", portalRequestDto.getPortalName()));
    filters.add(new FilterCondition("STATE", "NONE", "=", "A"));
    filters.add(new FilterCondition("APP_ID", "NONE", "=", portalRequestDto.getAppId()));
    filters.add(new FilterCondition("SP_ID", "NONE", "=", portalRequestDto.getSpId()));
    
    if(validationService.isSameValueWithCustomFilters("BFM_PORTAL", filters, null, null))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00046"));

    if(portalRequestDto.getIconUrl() == null)
      portalRequestDto.setIconUrl("test.jpg");

    BfmPortal addBfmPortal = bfmPortalMapper.toEntityBfmPortal(portalRequestDto);
    addBfmPortal.setState("A");
    addBfmPortal.setStateDate(LocalDateTime.now());
    bfmPortalRepository.save(addBfmPortal);

    addPortalContactChannel(addBfmPortal.getPortalId().longValue(), portalRequestDto.getContactChannelId() == null ? null : Long.parseLong(portalRequestDto.getContactChannelId()));

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, "created", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> modPortal(PortalRequestDto portalRequestDto) {
    List<FilterCondition> filters = new ArrayList<>();
    filters.add(new FilterCondition("PORTAL_NAME", "UPPER", "=", portalRequestDto.getPortalName()));
    filters.add(new FilterCondition("STATE", "NONE", "=", "A"));
    filters.add(new FilterCondition("APP_ID", "NONE", "=", portalRequestDto.getAppId()));
    filters.add(new FilterCondition("SP_ID", "NONE", "=", portalRequestDto.getSpId()));
    
    if(validationService.isSameValueWithCustomFilters("BFM_PORTAL", filters, "PORTAL_ID", portalRequestDto.getPortalId()))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00046"));

    if(portalRequestDto.getIconUrl() == null)
      portalRequestDto.setIconUrl("test.jpg");

    BfmPortal modBfmPortal = bfmPortalRepository.findById(portalRequestDto.getPortalId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found."));
    bfmPortalMapper.updateEntityBfmPortalFromPortalRequestDto(portalRequestDto, modBfmPortal);
    bfmPortalRepository.save(modBfmPortal);

    addOrModPortalContactChannel(modBfmPortal.getPortalId().longValue(), portalRequestDto.getContactChannelId() == null ? null : Long.parseLong(portalRequestDto.getContactChannelId()));

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> modSeq(Long portalId, List<PortalSeqRequestDto> portalRequestDto) {
    ValidationUtils validator = new ValidationUtils();
    validator.isNotNullMany(new Object[][] {
      { portalRequestDto, "portalRequestDto", "portalRequestDto cannot be null." },
    });
    validator.validate();

    for (PortalSeqRequestDto portalSeq : portalRequestDto) {
      Long partyId = portalSeq.getPartyId();
      Long oldSeq = portalSeq.getOldSeq().longValue();
      Long newSeq = portalSeq.getSeq().longValue();

      bfmPortalMenuRepository.updateSeqNative(portalId, partyId, oldSeq, newSeq, "A");
    }

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> delPortal(Long portalId) {
    ValidationUtils validator = new ValidationUtils();
    validator.isNotNullMany(new Object[][] {
        { portalId, "portalId", "portalId cannot be null." },
    });
    validator.validate();

    Map<String, Object> checkValidation = new HashMap<>();
    checkValidation.put("PORTAL_ID", portalId);
    if(validationService.checkReferenceExists("BFM_PORTAL_MENU", checkValidation, null, null))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00021"));

    if(validationService.checkReferenceExists("BFM_USER_PORTAL", checkValidation, null, null))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00036"));
    
    if(validationService.checkReferenceExists("BFM_ROLE_PORTAL", checkValidation, null, null))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00016"));

    bfmPortalChannelRepository.deleteById(portalId);

    delPortalBizParamByPortalId(portalId);

    BfmPortal delBfmPortal = bfmPortalRepository.findById(portalId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found."));
    delBfmPortal.setState("X");
    delBfmPortal.setStateDate(LocalDateTime.now());
    bfmPortalRepository.save(delBfmPortal);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(204, "deleted", null));
  }

  @SuppressWarnings("unchecked")
  private List<PartyMenuRequestDto> getDirChild (long partyId) {
    String sql = "SELECT A.DIR_ID, A.PARENT_ID, A.DIR_NAME FROM BFM_DIR A WHERE A.STATE = 'A' AND A.PARENT_ID = :parentId ORDER BY A.DIR_ID";
    List<Object[]> rows = (List<Object[]>) em.createNativeQuery(sql).setParameter("parentId", partyId).getResultList();

    List<PartyMenuRequestDto> result = new ArrayList<>();
    for (Object[] r : rows) {
      PartyMenuRequestDto dto = new PartyMenuRequestDto();
      dto.setPartyId(((Number) r[0]).longValue());
      dto.setParentId(((Number) r[1]).longValue());
      dto.setPartyName((String) r[2]);
      dto.setUrl("");
      dto.setType("0");
      dto.setAddCascade(true);
      result.add(dto);
    }
    return result;
  }

  private void addPortalContactChannel(Long portalId, Long contactChannelId) {
    BfmPortalChannel bfmPortalChannel = new BfmPortalChannel();
    bfmPortalChannel.setPortalId(portalId);
    bfmPortalChannel.setContactChannelId(contactChannelId);
    bfmPortalChannelRepository.save(bfmPortalChannel);
    return;
  }

  private void addWithCascade(
    PartyMenuRequestDto current,
    Long parentPartyId,
    AddDirMenuToPortalRequest req,
    List<BfmPortalMenu> batchList
  ) {
    // create current node
      BfmPortalMenuId id = new BfmPortalMenuId();
      id.setSeq(validationService.getNextVal("T_BFM_PORTAL_MENU_ID_SEQ"));
      id.setPortalId(req.getPortalId());
      id.setPartyId(current.getPartyId());

      BfmPortalMenu menu = new BfmPortalMenu();
      menu.setId(id);
      menu.setParentId(parentPartyId);
      menu.setType(current.getType().toString());
      menu.setState("A");
      menu.setStateDate(LocalDateTime.now());
      menu.setSpId(req.getSpId());

      batchList.add(menu);

      // 🔥 recursive cascade
      if ("0".equals(current.getType()) && Boolean.TRUE.equals(current.getAddCascade())) {
          List<PartyMenuRequestDto> children = getDirCascade(current.getPartyId());
          List<PartyMenuRequestDto> dirChild = getDirChild(current.getPartyId());
          List<PartyMenuRequestDto> join = new ArrayList<>();

          join.addAll(children);
          join.addAll(dirChild);

          for (PartyMenuRequestDto child : join) {
              addWithCascade(child, current.getPartyId(), req, batchList);
          }
      }
  }



  private void addOrModPortalContactChannel(Long portalId, Long contactChannelId) {
    Map<String, Object> checkValidation = new HashMap<>();
    checkValidation.put("PORTAL_ID", portalId);
    if(validationService.isSameValue("BFM_PORTAL_CHANNEL", checkValidation)) {
      updatePortalContactChannel(portalId, contactChannelId);
      return;
    }

    addPortalContactChannel(portalId, contactChannelId);
    return;
  }

  private void updatePortalContactChannel(Long portalId, Long contactChannelId) {
    BfmPortalChannel existing = bfmPortalChannelRepository.findById(portalId).orElse(null);
    if (existing != null) {
      existing.setContactChannelId(contactChannelId);
      bfmPortalChannelRepository.save(existing);
    }
    return;
  }

  private void delPortalBizParamByPortalId(Long portalId) {
    bfmPortalBizParamRepository.deleteById(portalId);
    return;
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addDirMenuToPortal(AddDirMenuToPortalRequest req) {
    ValidationUtils validator = new ValidationUtils();
    validator.isNotNullMany(new Object[][] {
        { req, "req", "req cannot be null." },
    });
    validator.validate();

    List<BfmPortalMenu> batchList = new ArrayList<>();
    for (PartyMenuRequestDto item : req.getPartyList()) {
    addWithCascade(item, req.getParentId(), req, batchList);
    }
    // for (PartyMenuRequestDto item : req.getPartyList()) {
    //     List<PartyMenuRequestDto> cascadeChildren = new ArrayList<>();

    //     if ("0".equals(item.getType()) && Boolean.TRUE.equals(item.getAddCascade())) {
    //       cascadeChildren = getDirCascade(item.getPartyId());
    //     }

    //     // tidak ada cascade → pakai parent item sendiri
    //     if (cascadeChildren.isEmpty()) {
    //       cascadeChildren.add(item);
    //     }

    //     // looping semua child
    //     for (PartyMenuRequestDto child : cascadeChildren) {
    //       BfmPortalMenuId bfmPortalMenuId = new BfmPortalMenuId();
    //       bfmPortalMenuId.setSeq(validationService.getNextVal("T_BFM_PORTAL_MENU_ID_SEQ"));
    //       bfmPortalMenuId.setPortalId(req.getPortalId());
    //       bfmPortalMenuId.setPartyId(child.getPartyId());

    //       BfmPortalMenu menu = new BfmPortalMenu();
    //       menu.setId(bfmPortalMenuId);
    //       menu.setParentId(req.getParentId());
    //       menu.setType(child.getType().toString());
    //       menu.setState("A");
    //       menu.setStateDate(LocalDateTime.now());
    //       menu.setSpId(req.getSpId());

    //       batchList.add(menu);
    //     }
    // }

        // BATCH INSERT
    bfmPortalMenuRepository.saveAll(batchList);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> delDirMenuFromPortal(Long partyId, Long seq, Long portalId) {
    ValidationUtils validator = new ValidationUtils();
    validator.isNotNullMany(new Object[][] {
        { partyId, "partyId", "partyId cannot be null." },
        { seq, "seq", "seq cannot be null." },
        { portalId, "portalId", "portalId cannot be null." }
    });
    validator.validate();

    List<BfmPortalMenu> batchList = bfmPortalMenuRepository.findAllByParentIdAndState(partyId, "A");
    
    if(batchList.isEmpty()) {
      deleteBfmPortalMenuRepository(partyId, seq, portalId);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomeResponse(400, "Item has child", null));
    }
    // List<BfmPortalMenu> exBfmPortalMenus = bfmPortalMenuRepository.findByIdPortalId(portalId);
    // for (BfmPortalMenu bfmPortalMenu : exBfmPortalMenus) {
    // deleteCascade(partyId, seq, portalId);
    // }

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(204, "no content", null));
  }

  // private void deleteCascade(
  //   Long partyId, Long seq, Long portalId
  // ){
  //   List<BfmPortalMenu> batchList = bfmPortalMenuRepository.findAllByParentId(partyId);
  //   deleteBfmPortalMenuRepository(partyId, seq, portalId);
  //   for(BfmPortalMenu item : batchList){
  //     deleteCascade(item.getId().getPartyId(), item.getId().getSeq(), item.getId().getPortalId());
  //   }
  // }

  private void deleteBfmPortalMenuRepository (
    Long partyId, Long seq, Long portalId
  ) {
    BfmPortalMenu exBfmPortalMenu = bfmPortalMenuRepository.findByIdPortalIdAndIdPartyIdAndIdSeqAndState(portalId, partyId, seq, "A").orElse(null);
    // log.info("ini ex {} ini portal {} ini party {} ini seq {}", exBfmPortalMenu, portalId, partyId, seq);
      if(exBfmPortalMenu != null) {
        exBfmPortalMenu.setState("X");
        exBfmPortalMenu.setStateDate(LocalDateTime.now());
        bfmPortalMenuRepository.save(exBfmPortalMenu);
      }
  }

  @SuppressWarnings("unchecked")
  public List<PartyMenuRequestDto> getDirCascade(Long partyId) {
    String sql = "SELECT B.MENU_ID, B.DIR_ID, A.PRIV_NAME, A.URL, A.IS_HOLD FROM BFM_PRIV A, BFM_MENU_DIR B WHERE A.PRIV_ID = B.MENU_ID AND A.STATE = 'A' AND B.DIR_ID IN (:parentId) ORDER BY A.PRIV_ID";
    List<Object[]> rows = (List<Object[]>) em.createNativeQuery(sql).setParameter("parentId", partyId).getResultList();

    List<PartyMenuRequestDto> result = new ArrayList<>();
    for (Object[] r : rows) {
      PartyMenuRequestDto dto = new PartyMenuRequestDto();
      dto.setPartyId(((Number) r[0]).longValue());
      dto.setParentId(((Number) r[1]).longValue());
      dto.setPartyName((String) r[2]);
      dto.setUrl((String) r[3]);
      dto.setType("1");
      result.add(dto);
    }
    return result;
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> bindMenuToPortals(BindMenuToPortalsRequestDto bindMenuToPortalsRequestDto) {
    LocalDateTime now = LocalDateTime.now();
    String type = bindMenuToPortalsRequestDto.getType();

    if (bindMenuToPortalsRequestDto.getAddPortalList() != null && !bindMenuToPortalsRequestDto.getAddPortalList().isEmpty())
      handlerMenuAndDirInsert(bindMenuToPortalsRequestDto);

    if (bindMenuToPortalsRequestDto.getDelPortalList() != null && !bindMenuToPortalsRequestDto.getDelPortalList().isEmpty())
      if ("0".equalsIgnoreCase(type)) {
        List<PortalRequestDto> delPortalList = bindMenuToPortalsRequestDto.getDelPortalList();
        for (PortalRequestDto delBo : delPortalList) {
          delBo.setState("X");
          delBo.setStateDate(now);
          if(delBo.getPortalId() == null)
            delBo.setPortalId(bindMenuToPortalsRequestDto.getPortalId());
          if(delBo.getSeq() == null)
          delBo.setSeq(bindMenuToPortalsRequestDto.getSeq());

          BfmPortalMenu exBfmPortalMenu = bfmPortalMenuRepository.findByIdPortalIdAndIdPartyIdAndIdSeqAndState(delBo.getPortalId(), bindMenuToPortalsRequestDto.getPartyId(), delBo.getSeq(), "A").orElse(null);
          if(exBfmPortalMenu != null) {
            exBfmPortalMenu.setState("X");
            exBfmPortalMenu.setStateDate(LocalDateTime.now());
            bfmPortalMenuRepository.save(exBfmPortalMenu);
          }
          // FindAll
          // List<BfmPortalMenu> exBfmPortalMenus = bfmPortalMenuRepository.findByIdPortalId(delBo.getPortalId());
          // for (BfmPortalMenu eBfmPortalMenu : exBfmPortalMenus) {
          //   eBfmPortalMenu.setState("X");
          //   eBfmPortalMenu.setStateDate(now);
          // }
          // bfmPortalMenuRepository.saveAll(exBfmPortalMenus);
        } 
      } else {
        BfmPortalMenu updatPortalMenu = bfmPortalMenuRepository.findByIdPortalIdAndIdPartyIdAndIdSeqAndState(bindMenuToPortalsRequestDto.getPortalId(), bindMenuToPortalsRequestDto.getPartyId(), bindMenuToPortalsRequestDto.getSeq(), "A").orElse(null);
        if(updatPortalMenu != null) {
          updatPortalMenu.setState("X");
          updatPortalMenu.setStateDate(now);
          bfmPortalMenuRepository.save(updatPortalMenu);
        }
      } 
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }

  public void handlerMenuAndDirInsert(BindMenuToPortalsRequestDto req) {
    Long partyId = req.getPartyId();
    String type = req.getType();
    List<PortalRequestDto> portalList = req.getAddPortalList();
    
    req.setState("A");
    req.setStateDate(LocalDateTime.now());

    if ("0".equalsIgnoreCase(type)) {
      List<DirNodeRequestDto> allDirs = getAllDirList();
      List<DirNodeRequestDto> parents = findParents(partyId, allDirs);
      List<BfmPortalMenu> toInsert = new ArrayList<>();

      if (!parents.isEmpty()) {
        for (PortalRequestDto p : portalList) {
          for (DirNodeRequestDto child : parents) {
            Long seq = nextSeq();
            BfmPortalMenuId id = BfmPortalMenuId.builder().portalId(p.getPortalId()).partyId(child.getId()).seq(seq).build();
            BfmPortalMenu pm = BfmPortalMenu.builder().id(id).type(type).parentId(child.getParentId()).state("A").stateDate(LocalDateTime.now()).spId(req.getSpId()).build();
            toInsert.add(pm);
          }
        }
      } else {
        for (PortalRequestDto p : portalList) {
          Long seq = nextSeq();
          BfmPortalMenuId id = BfmPortalMenuId.builder().portalId(p.getPortalId()).partyId(partyId).seq(seq).build();
          BfmPortalMenu pm = BfmPortalMenu.builder().id(id).type(type).parentId(req.getParentId()).state("A").stateDate(LocalDateTime.now()).build();
          toInsert.add(pm);
        }
      }

      if (!toInsert.isEmpty()) {
        bfmPortalMenuRepository.saveAll(toInsert);
      }
    } else {
      List<BfmPortalMenu> toInsert = new ArrayList<>();

      for (PortalRequestDto p : portalList) {
        Long seq = nextSeq();
        BfmPortalMenuId id = BfmPortalMenuId.builder().portalId(p.getPortalId()).partyId(partyId).seq(seq).build();
        BfmPortalMenu pm = BfmPortalMenu.builder().id(id).type(type).parentId(req.getParentId()).state("A").stateDate(LocalDateTime.now()).build();
        toInsert.add(pm);
      }

      if (!toInsert.isEmpty()) {
        bfmPortalMenuRepository.saveAll(toInsert);
      }
    }
  }

  // =====> Helper
  public List<DirNodeRequestDto> getAllDirList() {
    List<BfmDir> dirs = bfmDirRepository.findByStateOrderByDirId("A");
    List<DirNodeRequestDto> result = new ArrayList<>();
    for (BfmDir d : dirs) {
      result.add(DirNodeRequestDto.builder().id(d.getDirId()).parentId(d.getParentId()).name(d.getDirName()).build());
    }
    return result;
  }

  public List<DirNodeRequestDto> findParents(Long childId, List<DirNodeRequestDto> dirMenuList) {
    DirNodeRequestDto child = null;
    for (DirNodeRequestDto e : dirMenuList) {
      if (Objects.equals(childId, e.getId())) {
        child = e;
        break;
      }
    }
    if (child == null) {
      return Collections.emptyList();
    }
    List<DirNodeRequestDto> finalDirList = new ArrayList<>();
    findParentsRecursive(child, dirMenuList, finalDirList);
    return finalDirList;
  }

  private void findParentsRecursive(DirNodeRequestDto child, List<DirNodeRequestDto> dirMenuList, List<DirNodeRequestDto> finalList) {
    finalList.add(child);
    if (child.getParentId() == null) {
      return;
    }
    for (DirNodeRequestDto e : dirMenuList) {
      if (Objects.equals(child.getParentId(), e.getId())) {
        findParentsRecursive(e, dirMenuList, finalList);
        return;
      }
    }
  }

  public List<BfmDir> selectDirListByChildIdCascade(Long partyId) {
    List<DirNodeRequestDto> finalDirList = findParents(partyId, getAllDirList());
    if (finalDirList.isEmpty()) {
      return Collections.emptyList();
    }
    List<Long> ids = finalDirList.stream().map(DirNodeRequestDto::getId).collect(Collectors.toList());
    return bfmDirRepository.findAllActiveByIdsOrdered(ids);
  }

  // utility: konversi null->empty (contoh mapping)
  private String nullToEmpty(String s) {
    return s == null ? "" : s;
  }
  
  private synchronized Long nextSeq() {
    return validationService.getNextVal("T_BFM_PORTAL_MENU_ID_SEQ");
  }

  public ResponseEntity<BaseResponseDto> qryListDirMenusPortal(Long portalId, Long spId) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, queryListMenusData(portalId, spId)));
    }

     private List<DirMenusDto> queryListMenusData(Long portalId, Long spId) {
        return bfmPortalMapper.toDirMenus(portalRepository.queryDirMenusList(portalId, spId));
    }
}
