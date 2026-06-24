package com.sts.sinorita.svc.role.service.roles;

import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.BfmDir;
import com.sts.sinorita.entity.BfmMenu;
import com.sts.sinorita.entity.embeddable.BfmMenuDirId;
import com.sts.sinorita.entity.pot.BfmMenuDir;
import com.sts.sinorita.svc.role.constant.HttpStatusConstant;
import com.sts.sinorita.svc.role.dto.request.common.PagingRequestDto;
import com.sts.sinorita.svc.role.dto.request.dirMenu.DirMenuRequestDto;
import com.sts.sinorita.svc.role.dto.request.dirMenu.DirRequestDto;
import com.sts.sinorita.svc.role.dto.request.dirMenu.MenuItemRequestDto;
import com.sts.sinorita.svc.role.dto.request.dirMenu.MenuRequestDto;
import com.sts.sinorita.svc.role.dto.request.dirMenu.PrivRequestDto;
import com.sts.sinorita.svc.role.dto.response.DirDto;
import com.sts.sinorita.svc.role.dto.response.LazyTreeNodeDto;
import com.sts.sinorita.svc.role.dto.response.QryMenuListByDirIdResponseDto;
import com.sts.sinorita.svc.role.mapper.BfmDirMapper;
import com.sts.sinorita.svc.role.mapper.BfmMenuMapper;
import com.sts.sinorita.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.projection.DirMenuProjection;
import com.sts.sinorita.svc.role.projection.DirProjection;
import com.sts.sinorita.svc.role.repository.BfmDirRepository;
import com.sts.sinorita.svc.role.repository.BfmMenuDirRepository;
import com.sts.sinorita.svc.role.repository.BfmMenuRepository;
import com.sts.sinorita.svc.role.repository.BfmPortalMenuRepository;
import com.sts.sinorita.svc.role.repository.BfmPrivRepository;
import com.sts.sinorita.svc.role.service.ValidationService;
import com.sts.sinorita.svc.role.utils.MessageUtil;
import com.sts.sinorita.svc.role.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DirService {
  // =====> Repository
  @Autowired
  private BfmDirRepository bfmDirRepository;

  @Autowired
  private BfmPrivRepository bfmPrivRepository;

  @Autowired
  private BfmMenuDirRepository bfmMenuDirRepository;

  @Autowired
  private BfmMenuRepository bfmMenuRepository;

  @Autowired
  private BfmPortalMenuRepository bfmPortalMenuRepository;

  // ====> Service
  @Autowired
  private ValidationService validationService;

  @Autowired
  private PrivService privService;

  // ====> Mapper
  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private BfmDirMapper bfmDirMapper;

  @Autowired
  private BfmMenuMapper bfmMenuMapper;

  public ResponseEntity<CustomeResponse> queryDirListByParentId(Long parentId) {
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", selectDirByParentId(parentId)));
  }

  public ResponseEntity<CustomeResponse> qryMenuListByDirId(Long dirId) {
    log.info("masuk sini ga sih?");
    List<QryMenuListByDirIdResponseDto> data = bfmMenuRepository.qryMenuListByDirId("M", dirId, null, null, null).stream().map(bfmMenuMapper::toQryMenuListByDirIdResponseDto).toList();
    log.info("ini hasil dari datanya : {}", data);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", data));
  }

  public List<DirDto> selectDirByParentId(Long parentId) {
    if (parentId == 0) {
      parentId = null;
    }
    return roleMapper.toListDirDto(bfmDirRepository.selectDirByParentId(parentId));
  }

  public ResponseEntity<CustomeResponse> getAllDirsOrMenu(Long parentId) {
    List<DirProjection> dirs = bfmDirRepository.findChildren(parentId);
    List<LazyTreeNodeDto> result = new ArrayList<>();

    // Tambahkan DIR nodes
    for (DirProjection d : dirs) {
      boolean hasChildDir = bfmDirRepository.findChildren(d.getDirId()).size() > 0;
      boolean hasMenu = bfmPrivRepository.findMenuByDirId(d.getDirId()).size() > 0;

      result.add(new LazyTreeNodeDto(d.getDirId(), d.getParentId(), d.getDirName(), d.getDirCode(), "0", null, d.getIconUrl(), null, null, (hasChildDir || hasMenu)));
    }

    if (parentId != null) {
      List<DirMenuProjection> menus = bfmPrivRepository.findMenuByDirId(parentId);
      for (DirMenuProjection m : menus) {
        result.add(new LazyTreeNodeDto(m.getMenuId(), m.getDirId(), m.getPrivName(), m.getPrivCode(), "1", m.getUrl(), m.getIconUrl(), m.getIsHold(), m.getIsAuthorized(), false));
      }
    }

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", result));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addDir(DirRequestDto dirRequestDto) {
    Map<String, Object> checkValidation = new HashMap<>();
    checkValidation.put("DIR_NAME", dirRequestDto.getDirName());
    if(validationService.isSameValue("BFM_DIR", checkValidation))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00043"));

    BfmDir addBfmDir = bfmDirMapper.toEntityBfmDir(dirRequestDto);
    addBfmDir.setState("A");
    addBfmDir.setStateDate(LocalDate.now());
    bfmDirRepository.save(addBfmDir);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, "created", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> modDir(DirRequestDto dirRequestDto) {
    Map<String, Object> checkValidation = new HashMap<>();
    checkValidation.put("DIR_NAME", dirRequestDto.getDirName());
    checkValidation.put("SP_ID", dirRequestDto.getSpId());
    checkValidation.put("STATE", "A");
    if(validationService.isSameValueNotId("BFM_DIR", checkValidation, "DIR_ID", dirRequestDto.getDirId()))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00043"));

    BfmDir modBfmDir = bfmDirRepository.findById(dirRequestDto.getDirId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found."));
    modBfmDir.setDirName(dirRequestDto.getDirName());
    modBfmDir.setIconUrl(dirRequestDto.getIconUrl());
    bfmDirRepository.save(modBfmDir);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> delDir(Long dirId, Long spId) {
    ValidationUtils validator = new ValidationUtils();
    validator.isNotNullMany(new Object[][] {
        { dirId, "dirId", "dirId cannot be null." }
    });
    validator.validate();

    Map<String, Object> checkValidation = new HashMap<>();
    checkValidation.put("PARTY_ID", dirId);
    checkValidation.put("SP_ID", spId);
    checkValidation.put("STATE", "A");
    if(validationService.isSameValue("BFM_PORTAL_MENU", checkValidation))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00034"));

    List<Long> dirIds =  bfmDirRepository.findByStateAndParentId("A", dirId).stream().map(BfmDir::getDirId).toList();
    bfmMenuDirRepository.deleteByIdDirIdIn(dirIds);

    List<BfmDir> exDirs =  bfmDirRepository.findAllDescendantIds(dirId);
    for (BfmDir dir : exDirs) {
      dir.setState("X");
      dir.setStateDate(LocalDate.now());
    }
    bfmDirRepository.saveAll(exDirs);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(204, "no content", null));
  }

  // ========> MENU <=========

  public ResponseEntity<CustomeResponse> qryMenuList(PagingRequestDto pagingRequestDto, String url, String privName) {
    Pageable pageable = pagingRequestDto.getPage() != null ? PageRequest.of(Math.max(pagingRequestDto.getPage() - 1, 0), pagingRequestDto.getSize(), Sort.by(Sort.Direction.fromString(pagingRequestDto.getSortDirection()), pagingRequestDto.getSortBy())) : Pageable.unpaged();

    var data = bfmPrivRepository.qryMenuList(pageable, url, privName);
    var result = data.getContent();

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }

  public ResponseEntity<CustomeResponse> qryAllMenuList(@ModelAttribute PagingRequestDto pagingRequestDto, @RequestParam(required = false) String state, @RequestParam(required = false) Long appId, @RequestParam(required = false) String privName, @RequestParam(required = false) String url) {
    Pageable pageable = pagingRequestDto.getPage() != null ? PageRequest.of(Math.max(pagingRequestDto.getPage() - 1, 0), pagingRequestDto.getSize(), Sort.by(Sort.Direction.fromString(pagingRequestDto.getSortDirection()), pagingRequestDto.getSortBy())) : Pageable.unpaged();

    var data = bfmMenuRepository.qryAllMenuList(pageable, state, appId, privName, url);
    var result = data.getContent().stream().map(bfmMenuMapper::toQryNoDirMenuListResponseDto).toList();

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }

  public ResponseEntity<CustomeResponse> qryNoDirMenuList(PagingRequestDto pagingRequestDto, String state, Long appId, String privName, String url) {
    Pageable pageable = pagingRequestDto.getPage() != null ? PageRequest.of(Math.max(pagingRequestDto.getPage() - 1, 0), pagingRequestDto.getSize(), Sort.by(Sort.Direction.fromString(pagingRequestDto.getSortDirection()), pagingRequestDto.getSortBy())) : Pageable.unpaged();

    var data = bfmMenuRepository.qryNoDirMenuList(pageable, state, appId, privName, url);
    var result = data.getContent().stream().map(bfmMenuMapper::toQryNoDirMenuListResponseDto).toList();

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addMenu(MenuRequestDto menuRequestDto) {
    PrivRequestDto privRequest = menuRequestDto.getPriv();
    privRequest.setPrivType("M");
    privRequest.setPrivName(menuRequestDto.getMenuName());

    PrivRequestDto addPriv = privService.addPriv(privRequest);
    System.out.println("Ini hasil dari addPriv : " + addPriv);

    if (StringUtils.isEmpty(menuRequestDto.getIconUrl()) || menuRequestDto.getIconUrl() == null)
      menuRequestDto.setIconUrl("test.jpg");
    
    BfmMenu addMenu = bfmDirMapper.toEntityBfmMenu(menuRequestDto);
    addMenu.setMenuId(addPriv.getPrivId());
    addMenu.setState("A");
    addMenu.setStateDate(LocalDate.now());
    bfmMenuRepository.save(addMenu);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(201, "created", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> modMenu(MenuRequestDto menuRequestDto) {
    ValidationUtils validator = new ValidationUtils();
    validator.isNotNullMany(new Object[][] {
        { menuRequestDto.getMenuId(), "menuId", "menuId cannot be null." },
        { menuRequestDto.getPriv().getPrivId(), "privId", "privId cannot be null." }
    });
    validator.validate();

    privService.modPriv(menuRequestDto.getPriv());
    if (StringUtils.isEmpty(menuRequestDto.getIconUrl()))
      menuRequestDto.setIconUrl("test.jpg");

    BfmMenu exMenu = bfmMenuRepository.findById(menuRequestDto.getMenuId()).orElse(null);
    if (exMenu != null) {
      exMenu.setIconUrl(menuRequestDto.getIconUrl());
      bfmDirMapper.updateEntityBfmMenuFromMenuRequestDto(menuRequestDto, exMenu);
      bfmMenuRepository.save(exMenu);
    }

    privService.modPriv(menuRequestDto.getPriv());

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> delMenu(Long menuId) {
    ValidationUtils validator = new ValidationUtils();
    validator.isNotNullMany(new Object[][] {
        { menuId, "menuId", "menuId cannot be null." },
    });
    validator.validate();

    Map<String, Object> checkValidation = new HashMap<>();
    checkValidation.put("MENU_ID", menuId);
    if(validationService.checkReferenceExists("BFM_MENU_DIR", checkValidation, null, null))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00021"));

    checkValidation.clear();
    checkValidation.put("PARTY_ID", menuId);
    if(validationService.checkReferenceExists("BFM_PORTAL_MENU", checkValidation, null, null))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00036"));
    
    checkValidation.clear();
    checkValidation.put("MENU_ID", menuId);
    if(validationService.checkReferenceExists("BFM_COMPONENT_PRIV", checkValidation, null, null))
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00016"));

    privService.delPriv(menuId, "M");

    BfmMenu exMenu = bfmMenuRepository.findById(menuId).orElse(null);
    if (exMenu != null) {
      exMenu.setState("X");
      exMenu.setStateDate(LocalDate.now());
      bfmMenuRepository.save(exMenu);
    }
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(204, "no content", null));
  }

  // =====> Dir Menu Manager
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addDirMenu(DirMenuRequestDto dirMenuRequestDto) {
    List<MenuItemRequestDto> menuItemList = dirMenuRequestDto.getMenuList();
    ValidationUtils validator = new ValidationUtils();
    validator.isNotNullMany(new Object[][] {
        { menuItemList, "menuItemList", "menuItemList cannot be null." },
    });
    validator.validate();

    LocalDateTime now = LocalDateTime.now();

    Long spId = dirMenuRequestDto.getSpId() == null ? 0 : dirMenuRequestDto.getSpId();

    List<BfmMenuDir> addBfmMenuDirs = new ArrayList<>();
    for (MenuItemRequestDto menuItem : menuItemList) {
      BfmMenuDirId dirMenuId = new BfmMenuDirId();
      dirMenuId.setDirId(dirMenuRequestDto.getDirId());
      dirMenuId.setMenuId(menuItem.getMenuId());

      BfmMenuDir dirMenu = new BfmMenuDir();
      dirMenu.setId(dirMenuId);
      dirMenu.setState("A");
      dirMenu.setStateDate(now);
      dirMenu.setSpId(spId);

      addBfmMenuDirs.add(dirMenu);
    }

    bfmMenuDirRepository.saveAll(addBfmMenuDirs);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> delDirMenuFromDir(Long dirId, List<Long> menuIds, Long spId) {
    Long count = bfmPortalMenuRepository.countDirMenuUsedInPortal(dirId, menuIds, spId);
    if (count > 0)
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageUtil.getMessage("S-USR-00036"));

    bfmMenuDirRepository.deleteDirMenu(dirId, menuIds);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }
}
