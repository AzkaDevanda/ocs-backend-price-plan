package com.ocs.portal.priceplan;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocs.portal.common.DateUtilBase;
import com.ocs.portal.common.MessageService;
import com.ocs.portal.constant.HttpStatusConstant;
import com.ocs.portal.constant.SortDirection;
import com.ocs.portal.dto.request.PricePlanPriorityRequest;
import com.ocs.portal.dto.request.PricePlanRequestDto;
import com.ocs.portal.dto.request.PricePlanUpdateDto;
import com.ocs.portal.dto.request.priceplan.VersionDto;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.dto.response.PricePlanResponseDto;
import com.ocs.portal.dto.response.offerver.OfferVerDetailResponse;
import com.ocs.portal.dto.response.priceplan.CreatePriceplanResponse;
import com.ocs.portal.dto.response.priceplan.GetDetailOfferAndVersion;
import com.ocs.portal.dto.response.priceplan.OfferCatQryResponse;
import com.ocs.portal.entity.*;
import com.ocs.portal.repository.*;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.mapper.pricePlan.CopyFromMapper;
import com.ocs.portal.mapper.pricePlan.OfferVerMappers;
import com.ocs.portal.mapper.pricePlan.QryAcctPricePlanMapper;
import com.ocs.portal.mapper.pricePlan.SubsPricePlanMapper;
import com.ocs.portal.projection.configitem.ConfigItemParamProjection;
import com.ocs.portal.projection.pricePlan.OfferVerProjection;
import com.ocs.portal.repository.offer.OfferAttrRepository;
import com.ocs.portal.repository.offer.OfferCategoryMemRepository;
import com.ocs.portal.utils.Hellper;
import com.ocs.portal.validation.ValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Transactional(rollbackFor = { Exception.class, RuntimeException.class, IllegalArgumentException.class })
@Service
@Slf4j
public class PricePlanService {

  @Autowired
  PricePlanRepository pricePlanRepository;

  @Autowired
  SubsPricePlanRepository subsPricePlanRepository;

  @Autowired
  AcctPricePlanRepository acctPricePlanRepository;

  @Autowired
  OfferRepository offerRepository;
  @Autowired
  PricePlanParamRepository pricePlanParamRepository;
  @Autowired
  PricePlanTypeRepository pricePlanTypeRepository;
  Hellper hellper;
  @Autowired
  OfferCategoryRepository offerCategoryRepository;
  @Autowired
  OfferCategoryMemRepository offerCategoryMemRepository;
  boolean isUniq = false;
  @Autowired
  SubsPricePlanMapper subsPricePlanMapper;
  @Autowired
  MessageService messageService;
  @Autowired
  DateUtilBase dateUtilBase;
  @Autowired
  OfferAttrRepository offerAttrRepository;
  @Autowired
  AttrRepository attrRepository;
  @Autowired
  ConfigItemRepository configItemRepository;
  @Autowired
  OfferVerMappers offerVerMapper;
  @Autowired
  private OfferVerRepository offerVerRepository;
  @Autowired
  private RePpRecurringRepository rePpRecurringRepository;
  @Autowired
  private CopyFromMapper copyFromMapper;
  @Value("${com.sts.sinorita.spid:0}")
  private int spId;
  @Value("${com.sts.sinorita.spid:A}")
  private Character state;
  @Value("${com.sts.sinorita.applylevel:S}")
  private Character applyLevel;
  private Integer offerVerId = 0;
  @Autowired
  private QryAcctPricePlanMapper qryAcctPricePlanMapper;

  public static Date convertLocalDateToDate(LocalDate localDate) {
    // Convert LocalDate to LocalDateTime at the start of the day
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  public BaseResponseDto createPricePlan(PricePlanRequestDto pricePlanRequestDto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    // validasi doble data
    Optional<Offer> offer = offerRepository.findOfferByNameOfferTypeState(pricePlanRequestDto.getOfferName(),
        pricePlanRequestDto.getOfferType().charAt(0), state);

    if (offer.isPresent()) {
      // baseResponseDto.setCode(EnumRC.ALREADY_EXIST.getRESPONSE_CODE());
      // baseResponseDto.setMessage(EnumRC.ALREADY_EXIST.getMessage());
      throw new ValidationHandler(EnumRC.ALREADY_EXIST.getMessage());
    } else {
      baseResponseDto.setData(addPricePlan(pricePlanRequestDto));
      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    }

    return baseResponseDto;
  }

  public BaseResponseDto getPricePlans(Character applyLevel, int page, int size) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    Pageable pageable = PageRequest.of(page, size);

    Page<Object[]> resultPage;

    if (applyLevel == null || applyLevel.equals('S')) {
      resultPage = offerRepository.selectAllPricePlan(pageable);
    } else if (applyLevel.equals('A')) {
      resultPage = offerRepository.selectAllAcctPricePlan(pageable);
    } else {
      resultPage = Page.empty(pageable);
    }

    return getBaseResponseDto(baseResponseDto, resultPage);
  }

  public BaseResponseDto getPricePlanByType(Character applyLevel, int page, int size, Character type, String jsonFilter,
      String order_field, SortDirection order_direction) {

    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Map<String, String> filters = new HashMap<>();

    if (jsonFilter != null && !jsonFilter.isEmpty()) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        filters = mapper.readValue(jsonFilter, new TypeReference<Map<String, String>>() {
        });
      } catch (Exception e) {
        throw new RuntimeException("Invalid filter JSON", e);
      }
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<Object[]> resultPage;

    if (applyLevel.equals('S') || applyLevel.equals('A')) {
      resultPage = offerRepository.findAllWithDynamicFilter(applyLevel, type, filters.get("key"),
          filters.get("value"), order_field, order_direction.toString(), pageable);
    } else {
      resultPage = Page.empty(pageable);
    }

    return getBaseResponseDto(baseResponseDto, resultPage);
  }

  public ResponseEntity<CustomeResponse> getPricePlanByOfferId(Integer offerId, Character applyLevel) {
    Optional<Object[]> optionalOffer = offerRepository.findOfferDetailByPricePlanIdAndApplyLevel(offerId, applyLevel);

    if (optionalOffer.isEmpty()) {
      throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
    }

    Object[] data = optionalOffer.get();
    GetDetailOfferAndVersion dto = new GetDetailOfferAndVersion();
    dto.setOfferId((Integer) data[0]);
    dto.setPricePlanName((String) data[1]);
    dto.setPricePlanCode((String) data[2]);
    dto.setPricePlanType((Character) data[3]);
    dto.setRemarks((String) data[4]);
    dto.setEffDate(data[5] != null ? ((Timestamp) data[5]).toLocalDateTime().toLocalDate() : null);
    dto.setExpDate(data[6] != null ? ((Timestamp) data[6]).toLocalDateTime().toLocalDate() : null);
    dto.setApplyLevel(((Character) data[8]));
    dto.setServType((Integer) data[9]);
    dto.setServTypeName((String) data[10]);
    dto.setNetworkType((Character) data[11]);
    dto.setNetworkTypeName((String) data[12]);

    List<OfferVer> result = offerVerRepository.getVersionByOfferId(offerId);

    List<OfferVerDetailResponse> versionList = result.stream()
        .map(row -> {
          OfferVerDetailResponse verDto = new OfferVerDetailResponse();
          verDto.setOfferVerId(row.getId());
          verDto.setEffDate(row.getEffDate());
          verDto.setExpDate(row.getExpDate());
          return verDto;
        }).toList();

    dto.setOfferVerList(versionList);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, dto));
  }

  @NotNull
  private BaseResponseDto getBaseResponseDto(BaseResponseDto baseResponseDto, Page<Object[]> resultPage) {
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setData(resultPage.map(row -> {
      PricePlanResponseDto dto = new PricePlanResponseDto();
      // dto.setOfferId(((BigDecimal) row[0]).intValue());
      dto.setOfferId((Integer) row[0]);
      dto.setPricePlanName((String) row[1]);
      dto.setPricePlanCode((String) row[2]);
      dto.setPricePlanType((String) row[3]);

      LocalDate effDate = row[4] != null ? ((Timestamp) row[4]).toLocalDateTime().toLocalDate() : null;
      LocalDate expDate = row[5] != null ? ((Timestamp) row[5]).toLocalDateTime().toLocalDate() : null;

      String formatted = String.format(
          "%s - %s",
          effDate != null ? effDate.toString() : "",
          expDate != null ? expDate.toString() : "");
      dto.setValidPeriod(formatted);
      dto.setApplyLevel(((Character) row[6]));
      return dto;
    }));
    return baseResponseDto;
  }

  private CreatePriceplanResponse addPricePlan(PricePlanRequestDto pricePlanRequestDto) {
    CreatePriceplanResponse createPriceplanResponse = new CreatePriceplanResponse();
    if (null == pricePlanRequestDto.getOfferType()) {
      pricePlanRequestDto.setOfferType("4");
    }
    // insert offer
    Offer offer = addOffer(pricePlanRequestDto);
    log.info(":: create offer success ::");

    // dimatikan
    // if (pricePlanRequestDto.getOfferVerId() == 0) {
    // //add offer ver id
    // OfferVer offerVer = new OfferVer();
    // offerVer.setOfferId(offer.getId());
    // offerVer.setEffDate(pricePlanRequestDto.getVersionValidPeriod());
    //
    // if (pricePlanRequestDto.getVersionExpPeriod() != null) {
    // offerVer.setExpDate(pricePlanRequestDto.getVersionExpPeriod());
    // }
    // OfferVer offerVer1 = offerVerRepository.save(offerVer);
    // pricePlanRequestDto.setOfferVerId(offerVer1.getId());
    // }

    // insert priceplan
    PricePlan pricePlan = savePricePlan(offer, pricePlanRequestDto);
    log.info(":: create pricePlan success ::");
    // query OfferCatgAndMemDto
    List<OfferCatQryResponse> list = selectPricePlanDefaultCatg(pricePlanRequestDto);
    OfferCatGMem offerCatgMem = new OfferCatGMem();
    if (!list.isEmpty()) {
      for (OfferCatQryResponse offerCatQryResponse : list) {
        if ("PricePlan Default".equals(offerCatQryResponse.getOfferCatGName())) {
          offerCatgMem.setOfferCatgId(offerCatQryResponse.getOfferCatGId().longValue());
          offerCatgMem.setOfferId(offer.getId());
          offerCatgMem.setSpId(offer.getSpId());
        }
      }
    } else {
      OfferCategory offerCatgDto = new OfferCategory();
      offerCatgDto.setOfferCatgName("PricePlan Default");
      offerCatgDto.setOfferCatgType(pricePlanRequestDto.getOfferType().charAt(0));
      offerCatgDto.setEffDate(pricePlanRequestDto.getBaseValidPeriod());
      if (pricePlanRequestDto.getExpBaseValidPeriod() != null) {
        offerCatgDto.setExpDate(pricePlanRequestDto.getExpBaseValidPeriod());
      }
      offerCatgDto.setOfferCatgClass('A');
      offerCatgDto.setSpId(offer.getSpId());
      offerCatgDto.setState(state);
      offerCatgDto.setStateDate(LocalDate.now());
      offerCatgDto.setCreatedDate(LocalDate.now());

      OfferCategory offerCatg = offerCategoryRepository.save(offerCatgDto);
      log.info(":: create offerCatgDto success ::");
      offerCatgMem.setOfferCatgId(offerCatg.getOfferCatgId().longValue());
      offerCatgMem.setOfferId(offer.getId());
      offerCatgMem.setEffDate(convertLocalDateToDate(pricePlanRequestDto.getBaseValidPeriod()));
      if (pricePlanRequestDto.getExpBaseValidPeriod() != null) {
        offerCatgMem.setExpDate(convertLocalDateToDate(pricePlanRequestDto.getExpBaseValidPeriod()));
      }
      offerCatgMem.setSpId(offer.getSpId());
    }
    // save to addMemOfCatg
    offerCategoryMemRepository.save(offerCatgMem);
    log.info(":: success create offerCatgMem ::");
    String applyLevel = pricePlanRequestDto.getApplyLevel();
    if (applyLevel.equals("S")) {
      SubsPricePlan subsPricePlan = new SubsPricePlan();
      subsPricePlan.setId(pricePlan.getId());
      subsPricePlan.setPricePlanType(pricePlanRequestDto.getPricePlanType());
      subsPricePlan.setSpId(offer.getSpId());
      subsPricePlanRepository.save(subsPricePlan);
    } else if (applyLevel.equals("A")) {
      AcctPricePlan acctPricePlan = new AcctPricePlan();
      acctPricePlan.setId(pricePlan.getId());
      acctPricePlan.setPricePlanType(pricePlanRequestDto.getPricePlanType());
      acctPricePlan.setSpId(offer.getSpId());
      acctPricePlanRepository.save(acctPricePlan);
    }

    log.info("PRICE_PLAN_VER_ID  ::  {} ", offer.getId());
    // createPriceplanResponse.setOfferVerId(pricePlanRequestDto.getOfferVerId());
    if (pricePlanRequestDto.getVersion() != null) {
      addVersionCPFrom(pricePlanRequestDto, offer.getId());
    }
    createPriceplanResponse.setOfferVerId(getOfferVerId());
    return createPriceplanResponse;
  }

  private void addVersionCPFrom(PricePlanRequestDto pricePlanRequestDto, Integer offerId) {

    String sourceFrom = pricePlanRequestDto.getVersion().getSourceFrom();
    if ("0".equals(sourceFrom) && pricePlanRequestDto.getVersion().getOldPricePlanVerId() != null) {
      copyPricePlanVerNoPrefix(pricePlanRequestDto, offerId);
    } else if ("1".equals(sourceFrom) && pricePlanRequestDto.getVersion().getOldPricePlanVerId() != null) {
      sharePricePlanVer(pricePlanRequestDto);
    } else {
      log.info(":: addPricePlanVer ::");
      addPricePlanVer(pricePlanRequestDto.getVersion(), offerId);
    }
    // dict.set("PRICE_PLAN_VER_ID", dictVer.getLong("OFFER_VER_ID"));
  }

  public ResponseEntity<CustomeResponse> addPriceplanVersion(VersionDto dict, Integer offerId) {
    addPricePlanVer(dict, offerId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, getOfferVerId()));
  }

  public ResponseEntity<CustomeResponse> editPriceplanVersion(VersionDto dict, Integer offerId, Integer offerVerId) {
    modPricePlanVer(dict, offerId, offerVerId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, getOfferVerId()));
  }

  public void modPricePlanVer(VersionDto dict, Integer offerId, Integer offerVerId) {
    Optional<OfferVerProjection> offerVerOptional = offerVerRepository.findOneByOfferId(offerId, offerVerId);
    if (offerVerOptional.isEmpty()) {
      throw new ValidationHandler("OfferVer not found");
    }
    // OfferVer offerVer = offerVerMapper.toOfferVer(offerVerOptional.get());
    OfferVer offerVer = offerVerRepository.findById(offerVerId)
        .orElseThrow(() -> new ValidationHandler("OfferVer not found"));
    // OfferVer offerVer =
    // offerVerRepository.findByOfferId(offerId,offerVerId).orElseThrow(()-> new
    // ValidationHandler("OfferVer not found"));
    String offerVerState = offerVer.getState().toString();
    Optional<ConfigItemParamProjection> configParam = configItemRepository.findConfigItem("PRODUCT", "PRODUCT_CONFIG",
        "IS_USE_PRICE_PLAN_VER_STATE");
    String offerVerStateFlag = "N";
    if (configParam.isPresent()) {
      offerVerStateFlag = configParam.get().getDefaultValue();
    }
    offerVer.setExpDate(dict.getExpDate());
    offerVer.setEffDate(dict.getEffDate());

    boolean canCache = ("A".equals(offerVerState) || "G".equals(offerVerState));
    if (("N".equals(offerVerStateFlag) || ("Y".equals(offerVerStateFlag) && canCache))
        && checkOfferVerDate(offerVer)) {
      log.info(":: modPricePlanVer checkOfferVerDate ::");
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageService.getMessage("S-PRD-50933"));
    }
    compOfferDateAndVerDate(offerVer);
    offerVerRepository.save(offerVer);
    log.info(":: modPricePlanVer success ::");
  }

  public ResponseEntity<CustomeResponse> deletePricePlanVersion(Integer offerVerId) {
    delPricePlanVer(offerVerId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, getOfferVerId()));

  }

  private void delPricePlanVer(Integer offerVerId) {

    if (offerVerRepository.isReferenceRePricePlan(offerVerId).isPresent()) {
      log.warn(":: isReferenceRePricePlan ::");
      throw new ValidationHandler(MessageService.getMessage("S-PRD-01024"));
    }
    if (offerVerRepository.isReferenceDP(offerVerId).isPresent()) {
      log.warn(":: isReferenceDP ::");
      throw new ValidationHandler(MessageService.getMessage("S-PRD-01025"));
    }
    if (offerVerRepository.isReferenceTriggerAcm(offerVerId).isPresent()) {
      log.warn(":: isReferenceTriggerAcm ::");
      throw new ValidationHandler(MessageService.getMessage("S-PRD-01027"));
    }
    if (offerVerRepository.isReferenceBalTrigger(offerVerId).isPresent()) {
      log.warn(":: isReferenceBalTrigger ::");
      throw new ValidationHandler(MessageService.getMessage("S-PRD-01028"));
    }
    if (offerVerRepository.isReferenceROfferVer(offerVerId).isPresent()) {
      log.warn(":: isReferenceROfferVer ::");
      throw new ValidationHandler(MessageService.getMessage("S-PRD-51105"));
    }
    offerVerRepository.deleteOfferVer(offerVerId);
    log.info(":: delPricePlanVer success ::");

  }

  private void validateEffDate(OfferVer offerVer, VersionDto dict) {
    if (offerVer.getEffDate() != null) {
      if (offerVer.getEffDate().isAfter(dict.getEffDate())) {
        log.warn(":: Effective date must not be after expiry date ::");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Effective date must not be after expiry date.");
      }
    }
    log.info("expired date " + offerVer.getExpDate());
    if (offerVer.getExpDate() != null && dict.getExpDate() != null) {
      if (offerVer.getExpDate().isAfter(dict.getExpDate())) {
        log.warn(":: ExpDate date 1 must not be after expiry date ::");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Effective date must not be after expiry date.");
      }
    }
  }

  private void addPricePlanVer(VersionDto dict, Integer offerId) {
    log.info("addPricePlanVer");
    List<OfferVer> offerVerDto = offerVerRepository.findByOffer(offerId);
    OfferVer offerVer = new OfferVer();

    if (!offerVerDto.isEmpty()) {
      if (dict.getOldPricePlanVerId() != null) {
        offerVer.setRefOfferVerId(dict.getOldPricePlanVerId());
        // offerVer.setRefOfferVerId(offerVerDto.get().getId());
      }
      for (OfferVer offerVer1 : offerVerDto) {
        validateEffDate(offerVer1, dict);
      }
    }

    Optional<ConfigItemParamProjection> configParam = configItemRepository.findConfigItem("PRODUCT", "PRODUCT_CONFIG",
        "IS_USE_PRICE_PLAN_VER_STATE");
    String offerVerStateFlag = "N";
    if (configParam.isPresent()) {
      offerVerStateFlag = configParam.get().getDefaultValue();
    }

    // if ("N".equalsIgnoreCase(offerVerStateFlag) &&
    // checkOfferVerDate(offerVer)){//offerVerDto
    // log.debug("N . equalsIgnoreCase(offerVerStateFlag) &&
    // checkOfferVerDate(offerVerDto)");
    // throw new ValidationHandler(messageService.getMessage("S-PRD-50933"));
    // }
    // OfferVer offerVer = new OfferVer();

    // offerVer.setState('A');
    // offerVer.setExpDate(dict.getVersionExpPeriod());
    // if(dict.getExpDate() != null){
    //
    // }

    offerVer.setExpDate(dict.getExpDate());
    offerVer.setEffDate(dict.getEffDate());
    offerVer.setSpId(0);
    offerVer.setOfferId(offerId);
    compOfferDateAndVerDate(offerVer);
    if ("N".equalsIgnoreCase(offerVerStateFlag) && checkOfferVerDate(offerVer)) {
      log.debug("N . equalsIgnoreCase(offerVerStateFlag) && checkOfferVerDate(offerVerDto)");
      throw new ResponseStatusException(HttpStatus.CONFLICT, MessageService.getMessage("S-PRD-50933"));
    }
    Integer seq = offerVerRepository.findMaxSeqByOfferId(offerId);
    if (seq == null) {
      seq = 0;
    }
    offerVer.setSeq(seq + 1);
    offerVerRepository.save(offerVer);

    setOfferVerId(offerVer.getId());
    if ("Y".equals(offerVerStateFlag)) {
      offerVerRepository.updateState(offerVer.getId(), 'M');
    } else {
      offerVerRepository.updateState(offerVer.getId(), 'A');
    }
    log.info("PRICE_PLAN_VER_ID : " + offerVer.getId());
    log.info("OFFER_VER_ID" + offerVer.getId());
  }

  public void sharePricePlanVer(PricePlanRequestDto pricePlanRequestDto) {
    log.info("sharePricePlanVer");
    Long sharePricePlanVerId = pricePlanRequestDto.getVersion().getOldPricePlanVerId().longValue();
    OfferVer offerVer = offerVerRepository.findOfferVerById(pricePlanRequestDto.getVersion().getOldPricePlanVerId())
        .orElseThrow(() -> new ValidationHandler(MessageService.getMessage("S-PRD-01052")));

    OfferVer offerVer1 = new OfferVer();
    offerVer1.setEffDate(offerVer.getEffDate());
    Integer seq = offerVerRepository.getNextOfferVerId();
    if (seq == null) {
      seq = 0;
    }
    offerVer1.setSeq(seq + 1);
    offerVer1.setSpId(offerVer.getSpId());
    offerVer1.setOfferId(offerVer.getOfferId());
    offerVer1.setExpDate(offerVer.getExpDate());
    offerVer1.setEffDate(offerVer.getEffDate());
    offerVer1.setState(offerVer.getState());
    Optional<ConfigItemParamProjection> configParam = configItemRepository.findConfigItem("PRODUCT", "PRODUCT_CONFIG",
        "IS_USE_PRICE_PLAN_VER_STATE");
    String offerVerStateFlag = "N";
    if (configParam.isPresent()) {
      offerVerStateFlag = configParam.get().getDefaultValue();
    }
    if ("N".equals(offerVerStateFlag) && checkOfferVerDate(offerVer1)) {
      boolean a = checkOfferVerDate(offerVer1);
      log.info(String.valueOf(a));
      log.info("sharePricePlanVer checkOfferVerDate");
      throw new ValidationHandler(MessageService.getMessage("S-PRD-50933"));
    }
    compOfferDateAndVerDate(offerVer1);
    offerVerRepository.save(offerVer1);
    setOfferVerId(offerVer1.getId());
    try {
      if ("Y".equals(offerVerStateFlag)) {
        offerVerRepository.updateState(offerVer1.getId(), 'M');
      } else {
        offerVerRepository.updateState(offerVer1.getId(), 'A');
      }
    } catch (Exception e) {
      throw new ValidationHandler(MessageService.getMessage("S-PRD-01053"));
    }
    if ("Y".equals(pricePlanRequestDto.getVersion().getIsCopyOfferAttr())) {
      copyPricePlanAttr(offerVer1.getOfferId().longValue(), offerVer.getId().longValue());
      log.info("copyPricePlanAttr success insert");
    }
  }

  // Implement Transactional for flush
  @Transactional
  public void copyPricePlanVerNoPrefix(PricePlanRequestDto pricePlanRequestDto, Integer offerId) {
    Long oldPricePlanVerId = pricePlanRequestDto.getVersion().getOldPricePlanVerId().longValue();
    OfferVer oldOfferVerDto = offerVerRepository
        .findOfferVerById(pricePlanRequestDto.getVersion().getOldPricePlanVerId())
        .orElseThrow(() -> new ValidationHandler(MessageService.getMessage("S-PRD-01052")));
    log.info("copy from not prefix ");
    // log.info(new Gson().toJson(oldOfferVerDto));

    if (ObjectUtils.isNotEmpty(oldOfferVerDto.getRefOfferVerId())) {
      log.debug("not empty");
      if (oldOfferVerDto.getRefOfferVerId().longValue() > 0L) {
        oldOfferVerDto = offerVerRepository.findOfferVerById(oldOfferVerDto.getRefOfferVerId().intValue()).get();
        if (oldOfferVerDto == null || oldOfferVerDto.getId() == null) {
          throw new ValidationHandler(MessageService.getMessage("S-PRD-01052"));
        }
        oldPricePlanVerId = oldOfferVerDto.getId().longValue();
      }
    }
    OfferVer offerVer1 = new OfferVer();
    offerVer1.setEffDate(oldOfferVerDto.getEffDate());
    Integer seq = offerVerRepository.getNextSeq(offerVer1.getSeq());
    // log.info("sequence before validation: " + seq);
    if (seq == null) {
      seq = 1;
    }
    // log.info("sequence after validation: " + seq);
    offerVer1.setSeq(seq);
    offerVer1.setSpId(oldOfferVerDto.getSpId());
    offerVer1.setOfferId(offerId);
    offerVer1.setEffDate(oldOfferVerDto.getEffDate());
    offerVer1.setExpDate(oldOfferVerDto.getExpDate());
    offerVer1.setState(oldOfferVerDto.getState());
    // set refOfferVerId with user request
    offerVer1.setRefOfferVerId(oldPricePlanVerId.intValue());
    Optional<ConfigItemParamProjection> configParam = configItemRepository.findConfigItem("PRODUCT", "PRODUCT_CONFIG",
        "IS_USE_PRICE_PLAN_VER_STATE");
    String offerVerStateFlag = "N";
    // if (configParam.isPresent()){
    // offerVerStateFlag = configParam.get().getDefaultValue();
    // }
    // if ("N".equals(offerVerStateFlag) && checkOfferVerDate(offerVer1)){
    // log.info("checkOfferVerDate");
    // throw new ValidationHandler(messageService.getMessage("S-PRD-50933"));
    // }
    log.info("compOfferDateAndVerDate ");
    compOfferDateAndVerDate(offerVer1);
    offerVerRepository.save(offerVer1);
    // Implement flush for problem data not found when calling Procedure
    offerVerRepository.flush();
    setOfferVerId(offerVer1.getId());
    log.info("offerVer1: " + offerVer1.getId());
    log.info("offerVer1 success ");
    String prefix = "";
    String postfix = "";
    if (!pricePlanRequestDto.getVersion().getPrefix().isEmpty()) {
      prefix = pricePlanRequestDto.getVersion().getPrefix();
    }
    if (!pricePlanRequestDto.getVersion().getPostfix().isEmpty()) {
      postfix = pricePlanRequestDto.getVersion().getPostfix();
    }

    try {
      log.info("call copy from ....");
      // log.info("offer with repo: " + offerVer1); For debug offer
      // log.info("oldPricePlanVer: " + oldPricePlanVerId);
      pricePlanRepository.callCopyPricePlanVer(oldPricePlanVerId, offerVer1.getId().longValue(), prefix, postfix);
      if ("Y".equals(offerVerStateFlag)) {
        log.info("updateState Flag Y  : : 'M'");
        offerVerRepository.updateState(offerVer1.getId(), 'M');
      } else {
        log.info("updateState Flag N  : : 'A'");
        offerVerRepository.updateState(offerVer1.getId(), 'A');
      }
    } catch (Exception e) {
      log.info("copyPricePlanVerSuccess insert exception : {}", MessageService.getMessage("S-PRD-01053"));
      throw new ValidationHandler(MessageService.getMessage("S-PRD-01053"));
    }
    // if ("Y".equals(pricePlanRequestDto.getVersion().getIsCopyOfferAttr())){
    // copyPricePlanAttr(offerVer1.getOfferId().longValue(),
    // oldOfferVerDto.getId().longValue());
    // log.info("copyPricePlanAttr success insert");
    // }
  }

  private void copyPricePlanAttr(Long destOfferId, Long srcOfferId) {
    List<OfferAttr> offerAttrList = offerAttrRepository.selectOfferAttrById(srcOfferId.intValue());
    log.info("::: copyPricePlanAttr :::");
    Attr attrDto = attrRepository.findByCode("EXP_OFFER_PUBLISH_STATE").orElseThrow(
        () -> new ValidationHandler(MessageService.getMessage("S-PRD-01053")));

    if (!offerAttrList.isEmpty()) {
      for (OfferAttr offerAttr : offerAttrList) {
        if (attrDto != null && offerAttr.getId().equals(attrDto.getId()))
          continue;
        offerAttr.getId().setOfferId(destOfferId.intValue());
        // insertOfferAttr(offerAttr);
        OfferAttr offerAttr1 = new OfferAttr();
        offerAttr1.setId(offerAttr.getId());
        offerAttr1.setSpId(offerAttr.getSpId());
        offerAttr1.setDefaultValue(offerAttr.getDefaultValue());
        offerAttr1.setDispOrder(offerAttr.getDispOrder());
        offerAttr1.setOperationTypes(offerAttr.getOperationTypes());
        offerAttrRepository.save(offerAttr1);
      }
    }

  }

  public boolean checkOfferVerDate(OfferVer offerVerDto) {
    log.debug("checkSubsPlanVerDate In, subsPlanVerDto=[{}]", offerVerDto);
    boolean result = offerVerRepository.isOfferVerDateConflict(offerVerDto);
    if (!result) {
      long offerVerId = offerVerRepository.checkWithMaxVer(offerVerDto);
      if (offerVerId == 0L)
        return true;
      LocalDate expDate = offerVerDto.getEffDate();
      offerVerRepository.updateOfferVer(expDate, (int) offerVerId);
    }
    return false;
  }

  public int compOfferDateAndVerDate(OfferVer offerVerDto) {
    Long offerId = offerVerDto.getOfferId().longValue();
    Optional<Offer> offerDto = offerRepository.getOfferById(offerId.intValue());
    log.info("offerdto isPresent : " + offerDto.isPresent());
    if (offerDto.isPresent()) {
      log.info("offerdto getExpDate : " + offerDto.get().getExpDate());
    }
    log.info("offerVerDto getExpDate : " + offerVerDto.getExpDate());
    log.info("offerVerDto getEffDate : " + offerVerDto.getEffDate());
    if (offerDto.isPresent() && offerDto.get().getExpDate() != null) {

      if (offerVerDto.getExpDate() != null) {
        log.info("masuk sini : " + offerVerDto.getEffDate().isAfter(offerVerDto.getExpDate()));
        if (offerVerDto.getEffDate().isAfter(offerVerDto.getExpDate())) {
          throw new ValidationHandler(MessageService.getMessage("S-PRD-50933"));
        }

        // if(DateUtilBase.compare(Date.from(offerVerDto.getExpDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
        // Date.from(offerVerDto.getExpDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
        // == 0){
        // // log.warn("compOfferDateAndVerDate ERR Code S-PRD-51032 :: {} ",
        // messageService.getMessage("S-PRD-51032"));
        // //S-PRD-51032 -> not foound in properties
        // log.warn(" compOfferDateAndVerDate original rc S-PRD-51032 .");
        //// throw new ValidationHandler(messageService.getMessage("S-PRD-51032"));
        // throw new ValidationHandler(messageService.getMessage("S-PRD-50933"));
        // }

      }
    } else {
      if (offerVerDto.getExpDate() != null) {
        log.info("masuk sini : " + offerVerDto.getEffDate().isAfter(offerVerDto.getExpDate()));
        if (offerVerDto.getEffDate().isAfter(offerVerDto.getExpDate())) {
          throw new ValidationHandler(MessageService.getMessage("S-PRD-50933"));
        }
        // throw new ValidationHandler(messageService.getMessage("S-PRD-50933"));
      }
    }
    return 0;
  }

  public ResponseEntity<CustomeResponse> updatePricePlan(Long offerId, PricePlanUpdateDto pricePlanUpdateDto) {
    if (offerId == null) {
      throw new ValidationHandler(EnumRC.BAD_REQUEST.getMessage());
    }
    Optional<Offer> optionalOffer = offerRepository.findById(Math.toIntExact(offerId));
    if (optionalOffer.isEmpty()) {
      throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
    }
    Offer offer = optionalOffer.get();
    if (optionalOffer.isPresent()) {
      offer.setOfferName(pricePlanUpdateDto.getOfferName());
      offer.setOfferType(pricePlanUpdateDto.getOfferType().charAt(0));
      offer.setOfferCode(pricePlanUpdateDto.getPricePlanCode());
      // validate EFFDate and EXPDate
      if (pricePlanUpdateDto.getExpBaseValidPeriod() != null
          && pricePlanUpdateDto.getBaseValidPeriod().isAfter(pricePlanUpdateDto.getExpBaseValidPeriod())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new CustomeResponse(400, "Effective date must not be after expiration date.", null));
      } else {
        offer.setEffDate(pricePlanUpdateDto.getBaseValidPeriod());
        offer.setExpDate(pricePlanUpdateDto.getExpBaseValidPeriod());
      }
      offer.setComments(pricePlanUpdateDto.getRemarks());
      offerRepository.save(offer);
    }

    // PricePlan pricePlan = new PricePlan();
    // pricePlan.setId(offer.getId());
    // pricePlan.setSpId(0);
    // pricePlan.setServType(pricePlanUpdateDto.getServiceType());
    // pricePlan.setIsPackage('N');
    // Integer priority = pricePlanRepository.selectMaxPriority();
    // pricePlan.setPriority(priority);
    // pricePlan.setApplyLevel(pricePlanUpdateDto.getApplyLevel().charAt(0));
    // pricePlanRepository.save(pricePlan);
    savePricePlanUpdate(offer, pricePlanUpdateDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public BaseResponseDto updatePricePlanByPriority(PricePlanPriorityRequest dto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Integer pricePlanId = dto.getPricePlanId();
    Integer newPriority = dto.getNewPriority();

    Optional<PricePlan> pricePlan = pricePlanRepository.findById(pricePlanId);
    if (pricePlan.isEmpty()) {
      throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
    }
    Integer oldPriority = pricePlan.get().getPriority();
    if (oldPriority.equals(newPriority)) {
      throw new ValidationHandler(MessageService.getMessage("S-RES-70005"));
    }

    Integer beginPriority = (oldPriority < newPriority) ? oldPriority + 1 : newPriority;
    Integer endPriority = (oldPriority < newPriority) ? newPriority : oldPriority - 1;
    Integer addNum = (oldPriority < newPriority) ? -1 : 1;

    log.info("Updating priority: pricePlanId={}, oldPriority={}, newPriority={}", pricePlanId, oldPriority,
        newPriority);
    log.info("Updating range: beginPriority={}, endPriority={}, addNum={}", beginPriority, endPriority, addNum);

    // int updatedRows =
    pricePlanRepository.updatePricePlanPriorityByArr(beginPriority, endPriority, addNum);
    // int updatedCurrent =
    pricePlanRepository.updatePricePlanPriority(pricePlanId, newPriority);

    // if (updatedRows > 0 && updatedCurrent == 1) {
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    // }
    log.info("Final Response: message={}, code={}", baseResponseDto.getMessage(), baseResponseDto.getCode());

    return baseResponseDto;
  }

  public BaseResponseDto updatePricePlanPriorityById(Integer pricePlanId,
      PricePlanPriorityRequest pricePlanPriorityRequest) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Integer newPriority = pricePlanPriorityRequest.getNewPriority();

    Optional<PricePlan> pricePlan = pricePlanRepository.findById(pricePlanId);
    if (pricePlan.isEmpty()) {
      throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
    }
    pricePlanRepository.updatePricePlanPriority(pricePlanId, newPriority);

    log.info("Final Response: message={}, code={}", baseResponseDto.getMessage(), baseResponseDto.getCode());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    return baseResponseDto;
  }

  private Offer addOffer(PricePlanRequestDto pricePlanRequestDto) {
    String offerCode = pricePlanRequestDto.getPricePlanCode();
    Offer offer = new Offer();
    offer.setSpId(spId);
    offer.setOfferType(pricePlanRequestDto.getOfferType().charAt(0));
    offer.setOfferName(pricePlanRequestDto.getOfferName());
    offer.setOfferCode(offerCode);
    offer.setEffType("B");
    offer.setEffDate(pricePlanRequestDto.getBaseValidPeriod());
    offer.setExpDate(pricePlanRequestDto.getExpBaseValidPeriod());
    offer.setComments(pricePlanRequestDto.getRemarks());
    offer.setState(state);
    offer.setStateDate(LocalDate.now());
    offer.setCreatedDate(LocalDate.now());
    return offerRepository.save(offer);
  }

  private PricePlan savePricePlan(Offer offer, PricePlanRequestDto pricePlanRequestDto) {
    PricePlan pricePlan = new PricePlan();
    pricePlan.setId(offer.getId());
    pricePlan.setSpId(offer.getSpId());
    pricePlan.setServType(pricePlanRequestDto.getServiceType());
    pricePlan.setIsPackage('N');
    Integer priority = pricePlanRepository.selectMaxPriority();
    pricePlan.setPriority(priority);
    pricePlan.setApplyLevel(pricePlanRequestDto.getApplyLevel().charAt(0));
    return pricePlanRepository.save(pricePlan);
  }

  private PricePlan savePricePlanUpdate(Offer offer, PricePlanUpdateDto pricePlanUpdateDto) {
    PricePlan pricePlan = new PricePlan();
    pricePlan.setId(offer.getId());
    pricePlan.setSpId(0);
    pricePlan.setServType(pricePlanUpdateDto.getServiceType());
    pricePlan.setIsPackage('N');
    Integer priority = pricePlanRepository.selectMaxPriority();
    pricePlan.setPriority(priority);
    pricePlan.setApplyLevel(pricePlanUpdateDto.getApplyLevel().charAt(0));
    String applyLevel = pricePlanUpdateDto.getApplyLevel();
    if (applyLevel.equals("S")) {
      SubsPricePlan subsPricePlan = subsPricePlanRepository.findById(pricePlan.getId()).orElse(null);
      if (subsPricePlan != null) {
        subsPricePlan.setPricePlanType(pricePlanUpdateDto.getPricePlanType());
        subsPricePlan.setSpId(offer.getSpId());
        subsPricePlanRepository.save(subsPricePlan);
      }
    } else if (applyLevel.equals("A")) {
      AcctPricePlan acctPricePlan = acctPricePlanRepository.findById(pricePlan.getId()).orElse(null);
      if (acctPricePlan != null) {
        acctPricePlan.setPricePlanType(pricePlanUpdateDto.getPricePlanType());
        acctPricePlan.setSpId(offer.getSpId());
        acctPricePlanRepository.save(acctPricePlan);
      }
    }

    return pricePlanRepository.save(pricePlan);
  }

  private List<OfferCatQryResponse> selectPricePlanDefaultCatg(PricePlanRequestDto pricePlanRequestDto) {
    List<Object[]> list = offerRepository.selectPricePlanDefaultCatg("PricePlan Default",
        pricePlanRequestDto.getOfferType().charAt(0), null, null,
        pricePlanRequestDto.getSpId(), null);

    List<OfferCatQryResponse> list1 = new ArrayList<>();
    for (Object[] row : list) {
      OfferCatQryResponse dto = new OfferCatQryResponse(
          (Integer) row[0],
          (String) row[1],
          (Integer) row[2],
          (String) row[3],
          (Date) row[4],
          (Date) row[5],
          (String) row[6],
          (Character) row[7],
          (Character) row[8],
          (Character) row[9]);
      list1.add(dto);
    }
    return list1;
  }

  @Transactional
  public ResponseEntity<CustomeResponse> deletePricePlan(Integer pricePlanId) {

    Offer offer = offerRepository.findById(pricePlanId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    if (!pricePlanRepository.existsById(offer.getId())) {
      throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
    } else {
      offerRepository.updateOfferState('X', Long.valueOf(offer.getId()));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> getSubsPricePlan(String pricePlanName, String pricePlanCode,
      Character pricePlanType, Integer pricePlanId, Integer spId,
      String order_field, SortDirection order_direction, Integer paging, Integer size) {

    int page = Math.max(paging - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(order_direction.toString()), order_field);
    Pageable pageable = PageRequest.of(page, size, sort);

    var data = offerRepository.findSubsPricePlan(pricePlanName, pricePlanCode, pricePlanType, pricePlanId, spId,
        pageable);

    var result = data.getContent().stream().map(subsPricePlanMapper::toDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,
        result, data.getTotalElements(), data.getTotalPages()));

  }

  public ResponseEntity<CustomeResponse> getAcctPricePlan(String pricePlanName, String pricePlanCode,
      Character pricePlanType, Integer pricePlanId, Integer spId,
      String order_field, SortDirection order_direction, Integer paging, Integer size) {
    int page = Math.max(paging - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(order_direction.toString()), order_field);
    Pageable pageable = PageRequest.of(page, size, sort);

    var data = offerRepository.findAcctPricePlan(pricePlanName, pricePlanType, pricePlanCode, spId, pageable);

    var result = data.getContent().stream().map(qryAcctPricePlanMapper::toDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,
        result, data.getTotalElements(), data.getTotalPages()));
  }

  public Integer getOfferVerId() {
    return offerVerId;
  }

  public void setOfferVerId(Integer offerVerId) {
    this.offerVerId = offerVerId;
  }

  public ResponseEntity<CustomeResponse> getCopyFormList(String showNotEffOfferver, Integer spId,
      String pricePlanName) {
    var data = pricePlanRepository.getCopyFromList(showNotEffOfferver, spId, pricePlanName)
        .stream()
        .map(copyFromMapper::toDto)
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }
}
