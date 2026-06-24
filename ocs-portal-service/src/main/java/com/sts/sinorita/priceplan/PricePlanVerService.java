package com.sts.sinorita.priceplan;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.*;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.priceVer.*;
import com.sts.sinorita.entity.*;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.mapper.pricePlan.AccumulationTypeMapper;
import com.sts.sinorita.mapper.pricePlan.price.*;
import com.sts.sinorita.projection.pricePlan.price.PriceRatingProjection;
import com.sts.sinorita.projection.pricePlan.price.QryTimeUnitListProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.utils.GeneratorXml;
import com.sts.sinorita.validation.ValidationHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class PricePlanVerService {
  private static final Logger logger = LoggerFactory.getLogger(RatePlanService.class);
  @Autowired
  GeneratorXml generatorXml;
  @Autowired
  RankUpRepository rankUpRepository;
  @Autowired
  AcmUpRepository acmUpRepository;
  @Autowired
  AcmCalcRepository acmCalcRepository;
  @Autowired
  MessageService messageService;
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private OfferVerRepository offerVerRepository;
  @Autowired
  private PriceVerRepository priceVerRepository;
  @Autowired
  private PriceRepository priceRepository;
  @Autowired
  private RePricePlanRepository rePricePlanRepository;
  @Autowired
  private DpRepository dpRepository;
  @Autowired
  private AcmTriggerRepository acmTriggerRepository;
  @Autowired
  private BalTriggerRepository balTriggerRepository;
  @Autowired
  private RatePlanRepository ratePlanRepository;
  @Autowired
  private TimeSpanUpRepository timeSpanUpRepository;
  @Autowired
  private RefValueRepository refValueRepository;
  @Autowired
  private UpRuleRepository upRuleRepository;
  @Autowired
  private PeriodRepository periodRepository;
  @Autowired
  private SubBalTypeRepository subBalTypeRepository;
  @Autowired
  private EventBenefitRepository eventBenefitRepository;
  @Autowired
  private ReAttrRepository reAttrRepository;
  @Autowired
  private RpRepository rpRepository;
  @Autowired
  private ScriptTempletRepository scriptTempletRepository;
  @Autowired
  private UpRepository upRepository;
  @Autowired
  private MappingUnitRepository mappingUnitRepository;
  @Autowired
  private MappingService mappingService;
  @Autowired
  private ReRepository reRepository;
  @Autowired
  private RatableResourceRepository ratableResourceRepository;
  @Autowired
  private AcmTimeSpanRepository acmTimeSpanRepository;
  @Autowired
  private AcmRepository acmRepository;
  @Autowired
  private AcmRefRepository acmRefRepository;
  @Autowired
  private AcmRuleRepository acmRuleRepository;
  @Autowired
  private TimeUnitRepository timeUnitRepository;
  // =====> Mapper <====
  @Autowired
  private AccumulationTypeMapper accumulationTypeMapper;
  @Autowired
  private PriceRatingMapper priceRatingMapper;
  @Autowired
  private QryAcmTimeSpanMapper qryAcmTimeSpanMapper;
  @Autowired
  private AcmListMapper acmListMapper;
  @Autowired
  private QryAcmRefMapper qryAcmRefMapper;
  @Autowired
  private QryAcmRuleMapper qryAcmRuleMapper;
  @Autowired
  private GetDetailBenefitMapper getDetailBenefitMapper;
  @Autowired
  private GetRecurringPriceDetailMapper getRecurringPriceDetailMapper;
  @Autowired
  private QryEventBenefitMapper qryEventBenefitMapper;
  @Autowired
  private QryPriceVerMapper qryPriceVerMapper;
  @Autowired
  private OpRepository opRepository;

  public ResponseEntity<CustomeResponse> validatePriceVersionExpDate(Integer ratePlanId) {

    if (priceVerRepository.countByRatePlanId(ratePlanId) == 0) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    if (priceVerRepository.existsExpDateIsNullRaw(ratePlanId) == 1) {
      // ada EXP_DATE yang null
      return ResponseEntity.status(HttpStatus.OK)
          .body(new CustomeResponse(400, "Please fill the expiration date.", null));
    }

    PriceVer priceVer = priceVerRepository.getExpDatePriceVerByRatePlanId(ratePlanId);

    if (priceVer == null) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, priceVer));
  }

  // ADD USAGE & SUBSCRIPTION PRICE
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addPricePlanVer(Character reType, PricePlanVerRequestDto dto) {
    try {
      // validate EFFDate and EXPDate
      if (dto.getExpDate() != null && dto.getEffDate().isAfter(dto.getExpDate())) {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(400, "Effective date must not be after expiration date.", null));
      }

      PriceVer priceVer = createOrGetPriceVer(dto);
      logger.info(String.valueOf(priceVer.getId()));
      logger.info("::: PRICE VER successfully saved into database ::");

      Integer priceVerId = priceVer.getId();

      Integer priority = 1; // default priority nya
      if (priceVerId != null) {
        priority = priceRepository.getNextPriority(priceVerId);
      }

      RefValue refValue = new RefValue();
      refValue.setRatePlanId(dto.getRatePlanId());
      refValue.setOfferVerId(dto.getOfferVerId());
      refValue.setRefValueType('1');
      refValue.setState('A');
      refValue.setRatePrecision(0L);
      refValue.setValueString(dto.getPrice());
      refValue.setCreatedDate(LocalDate.now());
      refValue.setStateDate(LocalDate.now());

      refValue.setSpId(0);
      refValueRepository.save(refValue);
      logger.info("::: REF VALUE successfully saved into database ::");

      Price price = new Price();
      price.setPriceVerId(priceVerId);
      price.setPriceName(dto.getPriceName());
      price.setAcctItemTypeId(dto.getAcctItemTypeId());
      price.setReAttr(dto.getReAttr());
      price.setValue(Long.valueOf(refValue.getId()));
      price.setSpId(0);
      price.setShareFlag('N');
      price.setPriceExit('N');
      price.setPriority(priority);
      price.setPayIndicator(dto.getPayIndicator());
      price.setRum(dto.getRum());
      price.setComments(dto.getComments());
      priceRepository.save(price);
      logger.info("::: PRICE successfully saved into database ::");

      RefValue refValue2 = new RefValue();
      refValue2.setPriceId(price.getId());
      refValue2.setRatePlanId(dto.getRatePlanId());
      refValue2.setOfferVerId(dto.getOfferVerId());
      refValue2.setRefValueType('8');
      refValue2.setState('A');
      refValue2.setRatePrecision(0L);
      refValue2.setValueString(price.getAcctItemTypeId().toString());
      refValue2.setCreatedDate(LocalDate.now());
      refValue2.setStateDate(LocalDate.now());
      refValue2.setSpId(0);
      refValueRepository.save(refValue2);
      logger.info("::: REF VALUE (Acct Item Type Id Param) successfully saved into database ::");

      priceRepository.insertAcctItemTypeIdParam(refValue2.getId(), Math.toIntExact(price.getId()));

      refValueRepository.insertValue(Math.toIntExact(price.getId()), refValue.getId());
      refValue.setPriceId(price.getId());

      Up up = new Up();
      up.setId(Math.toIntExact(price.getId()));
      up.setSpId(0);
      upRepository.save(up);
      logger.info("::: UP successfully saved into database ::");

      List<TimeSpanUp> savedTimeSpan = new ArrayList<>();

      if (dto.getTimeSpanUp() != null && !dto.getTimeSpanUp().isEmpty()) {
        for (TimeSpanUpRequest timeSpanUpRequest : dto.getTimeSpanUp()) {
          TimeSpanUp timeSpanUp = createTimeSpanUp(timeSpanUpRequest, price.getId(), dto.getOfferVerId(), dto.getRatePlanId());
          TimeSpanUp savedTimeSpanUp = timeSpanUpRepository.save(timeSpanUp);
          savedTimeSpan.add(savedTimeSpanUp);
        }
        logger.info("::: TIME SPAN UP successfully saved into database ::");
      }

      if (dto.getRankUp() != null && !dto.getRankUp().isEmpty()) {
        for (RankDto rankDto : dto.getRankUp()) {
          createRankUp(rankDto, price.getId(), dto.getOfferVerId(), dto.getRatePlanId(), savedTimeSpan);
        }
        logger.info("::: RANK UP successfully saved into database ::");

      }

      if (dto.getAccumulationPrice() != null && !dto.getAccumulationPrice().isEmpty()) {
        for (AcmUpDto acmUpDto : dto.getAccumulationPrice()) {
          createAcmUp(acmUpDto, price.getId(), dto.getOfferVerId(), dto.getRatePlanId(), savedTimeSpan);
        }
        logger.info("::: RANK UP successfully saved into database ::");
      }

      if (dto.getAccumulationCalculation() != null && !dto.getAccumulationCalculation().isEmpty()) {
        for (AcmCalcDto acmCalcDto : dto.getAccumulationCalculation()) {
          createAcmCalc(acmCalcDto, price.getId(), dto.getOfferVerId(), dto.getRatePlanId(), savedTimeSpan);
        }
      }

      if (dto.getExpressionPrice() != null) {
        createExpressionPriceUp(dto.getExpressionPrice(), price.getId().intValue(), reType);
      }

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(400, e.getMessage(), null));
    }
    return createSuccessResponse();
  }

  private TimeSpanUp createTimeSpanUp(TimeSpanUpRequest timeSpanUpRequest, Long priceId, Integer offerVerId,
      Integer ratePlanId) {

    TimeSpanUp timeSpanUp = new TimeSpanUp();
    timeSpanUp.setTimeSpanId(timeSpanUpRequest.getTimeSpanId());
    timeSpanUp.setAdjustMethod(timeSpanUpRequest.getCalculationMethod());

    List<RefValue> refValues = refValueRepository.findByPriceId(priceId);
    if (!refValues.isEmpty()) {
      for (RefValue refValue : refValues) {
        refValue.setState('X');
      }
      refValueRepository.saveAll(refValues);
    }

    RefValue refValueTimeSpanUp = new RefValue();
    refValueTimeSpanUp.setRefValueType('1');
    refValueTimeSpanUp.setValueString(timeSpanUpRequest.getPrice());
    refValueTimeSpanUp.setCreatedDate(LocalDate.now());
    refValueTimeSpanUp.setState('A');
    refValueTimeSpanUp.setStateDate(LocalDate.now());
    refValueTimeSpanUp.setSpId(0);
    refValueTimeSpanUp.setPriceId(priceId);
    refValueTimeSpanUp.setRatePlanId(ratePlanId);
    refValueTimeSpanUp.setOfferVerId(offerVerId);
    refValueRepository.save(refValueTimeSpanUp);

    timeSpanUp.setRate(refValueTimeSpanUp.getId().longValue());
    timeSpanUp.setUpId(priceId);
    timeSpanUp.setRum(timeSpanUpRequest.getCalculationUnit());
    timeSpanUp.setSpId(0L);
    timeSpanUp.setTimeSpanUpMode("0");
    timeSpanUp.setPriority(timeSpanUpRequest.getPriority());

    return timeSpanUpRepository.save(timeSpanUp);
  }

  private RankUp createRankUp(RankDto rankDto, Long priceId, Integer offerVerId, Integer ratePlanId,
      List<TimeSpanUp> savedTimeSpan) {
    RankUp rankUp = new RankUp();
    rankUp.setUpId(rankUp.getUpId());
    if (rankDto.getTimeSpanUpId() == null) {
      rankUp.setTimeSpanUpId(null);
    } else {
      rankUp.setTimeSpanUpId(savedTimeSpan.get(rankDto.getTimeSpanUpId()).getTimeSpanUpId().intValue());
    }

    List<RefValue> refValues = refValueRepository.findByPriceId(priceId);
    if (!refValues.isEmpty()) {
      for (RefValue refValue : refValues) {
        refValue.setState('X');
      }
      refValueRepository.saveAll(refValues);
    }

    RefValue refValueRankUp = new RefValue();
    refValueRankUp.setRefValueType('1');
    refValueRankUp.setValueString(rankDto.getPrice());
    refValueRankUp.setCreatedDate(LocalDate.now());
    refValueRankUp.setState('A');
    refValueRankUp.setStateDate(LocalDate.now());
    refValueRankUp.setSpId(0);
    refValueRankUp.setPriceId(priceId);
    refValueRankUp.setRatePlanId(ratePlanId);
    refValueRankUp.setOfferVerId(offerVerId);
    refValueRepository.save(refValueRankUp);

    rankUp.setSpId(0);
    rankUp.setUpId(priceId.intValue());
    rankUp.setRate(refValueRankUp.getId());
    rankUp.setOffset(rankDto.getRangeEffVal());
    rankUp.setDuration(rankDto.getRangeExpVal());
    rankUp.setRum(rankDto.getCalculationUnit().longValue());
    rankUp.setAdjustMethod(rankDto.getAdjustMethod());

    return rankUpRepository.save(rankUp);
  }

  private AcmUp createAcmUp(AcmUpDto acmUpDto, Long priceId, Integer offerVerId, Integer ratePlanId, List<TimeSpanUp> savedTimeSpan) {
    AcmUp acmUp = new AcmUp();
    acmUp.setSpId(0);
    if (acmUpDto.getTimeSpanUpId() == null) {
      acmUp.setTimeSpanUpId(null);
    } else {
      acmUp.setTimeSpanUpId(savedTimeSpan.get(acmUpDto.getTimeSpanUpId()).getTimeSpanUpId().intValue());
    }

    List<RefValue> refValues = refValueRepository.findByPriceId(priceId);
    if (!refValues.isEmpty()) {
      for (RefValue refValue : refValues) {
        refValue.setState('X');
      }
      refValueRepository.saveAll(refValues);
    }
    RefValue refValueRankUp = new RefValue();
    refValueRankUp.setRefValueType('1');
    refValueRankUp.setValueString(acmUpDto.getPrice());
    refValueRankUp.setCreatedDate(LocalDate.now());
    refValueRankUp.setState('A');
    refValueRankUp.setStateDate(LocalDate.now());
    refValueRankUp.setSpId(0);
    refValueRankUp.setPriceId(priceId);
    refValueRankUp.setRatePlanId(ratePlanId);
    refValueRankUp.setOfferVerId(offerVerId);
    refValueRepository.save(refValueRankUp);

    RefValue refValueRankUpResource = new RefValue();
    refValueRankUpResource.setPriceId(priceId);
    refValueRankUpResource.setRatePlanId(ratePlanId);
    refValueRankUpResource.setOfferVerId(offerVerId);
    refValueRankUpResource.setValueString(String.valueOf(acmUpDto.getAcctItemTypeId()));
    refValueRankUpResource.setSpId(0);
    refValueRankUpResource.setRatePrecision(0L);
    refValueRankUpResource.setRefValueType('5');
    refValueRankUpResource.setState('A');
    refValueRankUpResource.setCreatedDate(LocalDate.now());
    refValueRankUpResource.setStateDate(LocalDate.now());
    refValueRepository.save(refValueRankUpResource);

    acmUp.setRate(refValueRankUp.getId());
    acmUp.setUpId(Math.toIntExact(priceId));
    acmUp.setResourceId(acmUpDto.getAcctItemTypeId());
    acmUp.setEffValue(acmUpDto.getRangeEffVal());
    acmUp.setExpValue(acmUpDto.getRangeExpVal());
    acmUp.setRum(acmUpDto.getCalculateUnit());
    acmUp.setRefValueId(refValueRankUpResource.getId());
    acmUp.setAdjustMethod(acmUpDto.getAdjustMethod());

    return acmUpRepository.save(acmUp);
  }

  private AcmCalc createAcmCalc(AcmCalcDto acmCalcDto, Long priceId, Integer offerVerId, Integer ratePlanId,
      List<TimeSpanUp> savedTimeSpan) {
    AcmCalc acmCalc = new AcmCalc();

    List<RefValue> refValues = refValueRepository.findByPriceId(priceId);
    if (!refValues.isEmpty()) {
      for (RefValue refValue : refValues) {
        refValue.setState('X');
      }
      refValueRepository.saveAll(refValues);
    }

    RefValue refValueCalc = new RefValue();

    refValueCalc.setPriceId(priceId);
    refValueCalc.setRatePlanId(ratePlanId);
    refValueCalc.setOfferVerId(offerVerId);
    refValueCalc.setValueString(String.valueOf(acmCalcDto.getAcctItemTypeId()));
    refValueCalc.setSpId(0);
    refValueCalc.setRatePrecision(0L);
    refValueCalc.setRefValueType('5');
    refValueCalc.setState('A');
    refValueCalc.setCreatedDate(LocalDate.now());
    refValueCalc.setStateDate(LocalDate.now());
    refValueRepository.save(refValueCalc);

    acmCalc.setRefValueId(refValueCalc.getId());
    acmCalc.setResourceId(acmCalcDto.getAcctItemTypeId());
    acmCalc.setUpId(priceId.intValue());
    acmCalc.setRum(acmCalcDto.getCalculateUnit());
    acmCalc.setSpId(0);
    if (acmCalcDto.getTimeSpanUpId() == null) {
      acmCalc.setTimeSpanUpId(null);
    } else {
      acmCalc.setTimeSpanUpId(savedTimeSpan.get(acmCalcDto.getTimeSpanUpId()).getTimeSpanUpId().intValue());
    }

    return acmCalcRepository.save(acmCalc);
  }

  private void createExpressionPriceUp(ExpressionPriceRequest dto, Integer priceId, Character reType) {
    try {
      Integer priority = upRuleRepository.findNextPriority(priceId);
      byte[] scriptPage;
      if (reType.equals('3')) {
        Op op = new Op();
        op.setId(priceId);
        op.setScriptTempletId(dto.getScriptTempletId());
        op.setRuleComments(dto.getRuleComment());
        if (dto.getJsonScriptPage() == null) {
          op.setRuleScript(dto.getRuleScript());
          op.setScriptPage(new byte[0]);
        } else {
          scriptPage = generatorXml.getXmlScriptPage(dto.getScriptTempletId(), dto.getJsonScriptPage());
          op.setScriptPage(scriptPage);
          op.setRuleScript(generatorXml.getNewScriptRule(dto.getRuleScript(), scriptPage));
        }
        op.setSpId(0);
        opRepository.save(op);
      } else if (reType.equals('1')) {
        // Integer priority =
        // upRuleRepository.findNextPriority(Math.toIntExact(priceId));
        UpRule upRule = new UpRule();
        upRule.setUpId(priceId.longValue());
        upRule.setScriptTempletId(dto.getScriptTempletId());
        upRule.setRuleComments(dto.getRuleComment());
        if (dto.getJsonScriptPage() == null) {
          upRule.setRuleScript(dto.getRuleScript());
          upRule.setScriptPage(new byte[0]);
        } else {
          scriptPage = generatorXml.getXmlScriptPage(dto.getScriptTempletId(), dto.getJsonScriptPage());
          upRule.setScriptPage(scriptPage);
          upRule.setRuleScript(generatorXml.getNewScriptRule(dto.getRuleScript(), scriptPage));
        }
        upRule.setSpId(0);
        upRule.setPriority(priority);

        upRuleRepository.save(upRule);
      }
      logger.info("SUCCESS INSERT EXPRESSION PRICE");

    } catch (Exception e) {
      throw new ValidationHandler("ERROR CREATING EXPRESSION PRICE: " + e.getMessage());
    }

  }

  private void updateExpressionPriceUp(ExpressionPriceRequest dto, Integer priceId, Character reType) {
    log.info("updateExpressionPriceUp");
    try {
      String reTypes = reType.toString().trim();
      if (reTypes.equalsIgnoreCase("3")) {
        Optional<Op> op = opRepository.findById(priceId);
        if (op.isPresent()) {
          byte[] scriptPage;
          op.get().setId(priceId);
          op.get().setScriptTempletId(dto.getScriptTempletId());
          op.get().setRuleComments(dto.getRuleComment());
          if (dto.getJsonScriptPage() == null) {
            op.get().setRuleScript(dto.getRuleScript());
            op.get().setScriptPage(new byte[0]);
          } else {
            scriptPage = generatorXml.getXmlScriptPage(dto.getScriptTempletId(), dto.getJsonScriptPage());
            op.get().setScriptPage(scriptPage);
            op.get().setRuleScript(generatorXml.getNewScriptRule(dto.getRuleScript(), scriptPage));
          }
          op.get().setSpId(0);
          opRepository.save(op.get());
          logger.info("SUCCESS UPDATE EXPRESSION PRICE");
        }
      } else if (reTypes.equalsIgnoreCase("1")) {
        Integer priority = upRuleRepository.findNextPriority(priceId);
        log.info("reTypes " + reTypes);
        UpRule upRule = new UpRule();
        byte[] scriptPage;
        upRule.setUpId(priceId.longValue());
        upRule.setScriptTempletId(dto.getScriptTempletId());
        upRule.setRuleComments(dto.getRuleComment());
        if (dto.getJsonScriptPage() == null) {
          upRule.setRuleScript(dto.getRuleScript());
          upRule.setScriptPage(new byte[0]);
        } else {
          scriptPage = generatorXml.getXmlScriptPage(dto.getScriptTempletId(), dto.getJsonScriptPage());
          upRule.setScriptPage(scriptPage);
          upRule.setRuleScript(generatorXml.getNewScriptRule(dto.getRuleScript(), scriptPage));
        }
        upRule.setSpId(0);
        upRule.setPriority(priority);
        upRuleRepository.save(upRule);
        logger.info("SUCCESS UPDATE EXPRESSION PRICE");
      } else {
        logger.info("NEW EXPRESSION PRICE ON UPDATE PRICE");
        this.createExpressionPriceUp(dto, priceId, reType);
      }

    } catch (Exception e) {
      throw new ValidationHandler("ERROR UPDATE EXPRESSION PRICE: " + e.getMessage());
    }

  }

  private PriceVer createOrGetPriceVer(PricePlanVerRequestDto request) {
    Integer priceVerId = request.getPriceVerId();

    if (priceVerId != null && priceVerId > 0) {
      PriceVer priceVer = new PriceVer();
      priceVer.setId(priceVerId);
      return priceVer;
    }

    PriceVer priceVer = new PriceVer();
    priceVer.setRatePlanId(request.getRatePlanId());
    priceVer.setEffDate(request.getEffDate());
    priceVer.setMappingId(request.getMappingId());
    priceVer.setExpDate(request.getExpDate());
    priceVer.setSpId(0);
    return priceVerRepository.save(priceVer);
  }

  public ResponseEntity<CustomeResponse> listReAttrForPrice() {
    List<ReAttrListDto> list = reAttrRepository.findReAttrList();

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, list));
  }

  public ResponseEntity<CustomeResponse> updatePriceVer(Integer priceVerId, UpdatePriceVerDto dto) {

    Optional<PriceVer> optionalPriceVer = priceVerRepository.findById(priceVerId);
    if (optionalPriceVer.isEmpty()) {
      throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
    }

    // validate EFFDate and EXPDate
    if (dto.getEffectiveDate() != null && dto.getExpiredDate() != null) {
      if (dto.getEffectiveDate().isAfter(dto.getExpiredDate())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Effective date must not be after expiration date.");
      }
    }

    if (!priceDateSpanIsCorrect(priceVerId, dto)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-PRD-01030"));
    }

    PriceVer priceVer = optionalPriceVer.get();
    priceVer.setEffDate(dto.getEffectiveDate());
    priceVer.setExpDate(dto.getExpiredDate());

    priceVerRepository.save(priceVer);
    logger.info("::: PRICE VER successfully updated into database ::");

    return createSuccessResponse();
  }

  private boolean priceDateSpanIsCorrect(Integer priceVerId, UpdatePriceVerDto dto) {
    boolean isCorrect = false;

    LocalDate maxDate = LocalDate.of(9999, 12, 31);

    List<PriceVer> overlaps;

    if (dto.getExpiredDate() == null) {
      overlaps = priceVerRepository.findConflictsWithoutExpDate(dto.getEffectiveDate(), dto.getRatePlanId(),
          dto.getMappingId(), priceVerId);
    } else {
      overlaps = priceVerRepository.findConflictsWithExpDate(dto.getEffectiveDate(), dto.getExpiredDate(),
          maxDate, dto.getRatePlanId(), dto.getMappingId(), priceVerId);
    }

    if (overlaps.isEmpty()) {
      isCorrect = true;
    }

    return isCorrect;
  }

  public BaseResponseDto getPriceVerDetail(Integer priceVerId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Optional<PriceVer> optionalPriceVer = priceVerRepository.findById(priceVerId);
    if (optionalPriceVer.isEmpty()) {
      throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
    }

    PriceVer priceVer = optionalPriceVer.get();

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setData(priceVer);
    return baseResponseDto;
  }

  public ResponseEntity<CustomeResponse> deletePrice(Long priceId) {
    if (priceId.equals(null)) {
      log.info("priceId is null");
      return ResponseEntity.status(HttpStatus.OK)
          .body(new CustomeResponse(200, EnumRC.BAD_REQUEST.getMessage(), null));
    }

    priceRepository.deleteByPriceId(priceId);
    logger.info("::: PRICE successfully deleted from database ::");

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> updatePrice(Long id, Character reType, PricePlanVerUpdateDto dto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Price price = priceRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "price not found"));

    Optional<RefValue> optionalRefValue = refValueRepository.findById(Math.toIntExact(price.getValue()));
    RefValue refValue = optionalRefValue.get();
    refValue.setValueString(String.valueOf(dto.getPrice()));
    refValue.setCreatedDate(LocalDate.now());
    refValue.setStateDate(LocalDate.now());

    refValueRepository.save(refValue);
    logger.info("::: REF VALUE successfully updated into database ::");

    Optional<RefValue> acctItemTypeIdParam = refValueRepository.findById(price.getAcctItemTypeIdParam());
    RefValue refValue1 = acctItemTypeIdParam.get();
    refValue1.setValueString(String.valueOf(dto.getAcctItemTypeId()));
    refValue1.setCreatedDate(LocalDate.now());
    refValue1.setStateDate(LocalDate.now());
    refValueRepository.save(refValue1);

    price.setPriceName(dto.getPriceName());
    price.setAcctItemTypeId(dto.getAcctItemTypeId());
    price.setReAttr(dto.getReAttr());
    price.setValue(Long.valueOf(refValue.getId()));
    price.setPayIndicator(dto.getPayIndicator());
    price.setRum(dto.getRum());
    price.setComments(dto.getComments());

    priceRepository.save(price);
    logger.info("::: PRICE successfully updated into database ::");

    upRuleRepository.deleteByUpId(Math.toIntExact(id));
    upRepository.deleteRankUpByUpId(Math.toIntExact(id));
    upRepository.deleteAcmUpByUpId(Math.toIntExact(id));
    upRepository.deleteAcmCalcByUpId(Math.toIntExact(id));
    timeSpanUpRepository.deleteByUpId(Math.toIntExact(id));
    if (reType.equals('3')) {
      opRepository.deleteById(Math.toIntExact(id));
    }

    List<TimeSpanUp> savedTimeSpan = new ArrayList<>();

    if (dto.getTimeSpanUp() != null && !dto.getTimeSpanUp().isEmpty()) {
      for (TimeSpanUpRequest timeSpanUpRequest : dto.getTimeSpanUp()) {
        TimeSpanUp timeSpanUp = createTimeSpanUp(timeSpanUpRequest, price.getId(), refValue.getOfferVerId(),
            refValue.getRatePlanId());

        TimeSpanUp savedTimeSpanUp = timeSpanUpRepository.save(timeSpanUp);

        savedTimeSpan.add(savedTimeSpanUp);
      }
      logger.info("::: TIME SPAN UP successfully saved into database ::");
    }

    if (dto.getRankUp() != null && !dto.getRankUp().isEmpty()) {
      for (RankDto rankDto : dto.getRankUp()) {
        RankUp rankUp = new RankUp();
        // rankUp.setUpId(rankUp.getUpId().intValue());
        if (rankDto.getTimeSpanUpId() == null) {
          rankUp.setTimeSpanUpId(null);
        } else {
          rankUp.setTimeSpanUpId(savedTimeSpan.get(rankDto.getTimeSpanUpId() - 1).getTimeSpanUpId().intValue());
        }

        List<RefValue> refValues = refValueRepository.findByPriceId(price.getId());
        if (!refValues.isEmpty()) {
          for (RefValue selectedRefValue : refValues) {
            selectedRefValue.setState('X');
          }
          refValueRepository.saveAll(refValues);
        }

        RefValue refValueRankUp = new RefValue();
        refValueRankUp.setRefValueType('1');
        refValueRankUp.setValueString(rankDto.getPrice());
        refValueRankUp.setCreatedDate(LocalDate.now());
        refValueRankUp.setState('A');
        refValueRankUp.setStateDate(LocalDate.now());
        refValueRankUp.setSpId(0);
        refValueRankUp.setPriceId(price.getId());
        refValueRankUp.setRatePlanId(refValues.get(0).getRatePlanId());
        refValueRankUp.setOfferVerId(refValues.get(0).getOfferVerId());
        refValueRepository.save(refValueRankUp);

        rankUp.setSpId(0);
        rankUp.setUpId(price.getId().intValue());
        rankUp.setRate(refValueRankUp.getId());
        rankUp.setOffset(rankDto.getRangeEffVal());
        rankUp.setDuration(rankDto.getRangeExpVal());
        rankUp.setRum(rankDto.getCalculationUnit().longValue());
        rankUp.setAdjustMethod(rankDto.getAdjustMethod());

        rankUpRepository.save(rankUp);
      }
      logger.info("::: RANK UP successfully saved into database ::");

    }

    if (dto.getAccumulationPrice() != null && !dto.getAccumulationPrice().isEmpty()) {
      for (AcmUpDto acmUpDto : dto.getAccumulationPrice()) {
        AcmUp acmUp = new AcmUp();
        acmUp.setSpId(0);
        if (acmUpDto.getTimeSpanUpId() == null) {
          acmUp.setTimeSpanUpId(null);
        } else {
          acmUp.setTimeSpanUpId(savedTimeSpan.get(acmUpDto.getTimeSpanUpId() - 1).getTimeSpanUpId().intValue());
        }

        List<RefValue> refValues = refValueRepository.findByPriceId(price.getId());
        if (!refValues.isEmpty()) {
          for (RefValue selectedRefValue : refValues) {
            selectedRefValue.setState('X');
          }
          refValueRepository.saveAll(refValues);
        }
        RefValue refValueRankUp = new RefValue();
        refValueRankUp.setRefValueType('1');
        refValueRankUp.setValueString(acmUpDto.getPrice());
        refValueRankUp.setCreatedDate(LocalDate.now());
        refValueRankUp.setState('A');
        refValueRankUp.setStateDate(LocalDate.now());
        refValueRankUp.setSpId(0);
        refValueRankUp.setPriceId(refValues.get(0).getPriceId());
        refValueRankUp.setRatePlanId(refValues.get(0).getRatePlanId());
        refValueRankUp.setOfferVerId(refValues.get(0).getOfferVerId());
        refValueRepository.save(refValueRankUp);

        RefValue refValueRankUpResource = new RefValue();
        refValueRankUpResource.setPriceId(price.getId());
        refValueRankUpResource.setRatePlanId(refValues.get(0).getRatePlanId());
        refValueRankUpResource.setOfferVerId(refValues.get(0).getOfferVerId());
        refValueRankUpResource.setValueString(String.valueOf(acmUpDto.getAcctItemTypeId()));
        refValueRankUpResource.setSpId(0);
        refValueRankUpResource.setRatePrecision(0L);
        refValueRankUpResource.setRefValueType('5');
        refValueRankUpResource.setState('A');
        refValueRankUpResource.setCreatedDate(LocalDate.now());
        refValueRankUpResource.setStateDate(LocalDate.now());
        refValueRepository.save(refValueRankUpResource);

        acmUp.setRate(refValueRankUp.getId());
        acmUp.setUpId(Math.toIntExact(price.getId()));
        acmUp.setResourceId(acmUpDto.getAcctItemTypeId());
        acmUp.setEffValue(acmUpDto.getRangeEffVal());
        acmUp.setExpValue(acmUpDto.getRangeExpVal());
        acmUp.setRum(acmUpDto.getCalculateUnit());
        acmUp.setRefValueId(refValueRankUpResource.getId());
        acmUp.setAdjustMethod(acmUpDto.getAdjustMethod());

        acmUpRepository.save(acmUp);
      }
      logger.info("::: RANK UP successfully saved into database ::");

    }

    if (dto.getAccumulationCalculation() != null && !dto.getAccumulationCalculation().isEmpty()) {
      for (AcmCalcDto acmCalcDto : dto.getAccumulationCalculation()) {
        AcmCalc acmCalc = new AcmCalc();

        List<RefValue> refValues = refValueRepository.findByPriceId(price.getId());
        if (!refValues.isEmpty()) {
          for (RefValue selectedRefValue : refValues) {
            refValue.setState('X');
          }
          refValueRepository.saveAll(refValues);
        }

        RefValue refValueCalc = new RefValue();

        refValueCalc.setPriceId(price.getId());
        refValueCalc.setRatePlanId(refValues.get(0).getRatePlanId());
        refValueCalc.setOfferVerId(refValues.get(0).getOfferVerId());
        refValueCalc.setValueString(String.valueOf(acmCalcDto.getAcctItemTypeId()));
        refValueCalc.setSpId(0);
        refValueCalc.setRatePrecision(0L);
        refValueCalc.setRefValueType('5');
        refValueCalc.setState('A');
        refValueCalc.setCreatedDate(LocalDate.now());
        refValueCalc.setStateDate(LocalDate.now());
        refValueRepository.save(refValueCalc);

        acmCalc.setRefValueId(refValueCalc.getId());
        acmCalc.setResourceId(acmCalcDto.getAcctItemTypeId());
        acmCalc.setUpId(price.getId().intValue());
        acmCalc.setRum(acmCalcDto.getCalculateUnit());
        acmCalc.setSpId(0);
        if (acmCalcDto.getTimeSpanUpId() == null) {
          acmCalc.setTimeSpanUpId(null);
        } else {
          acmCalc.setTimeSpanUpId(savedTimeSpan.get(acmCalcDto.getTimeSpanUpId() - 1).getTimeSpanUpId().intValue());
        }

        acmCalcRepository.save(acmCalc);
      }
    }

    if (dto.getExpressionPrice() != null) {
      createExpressionPriceUp(dto.getExpressionPrice(), price.getId().intValue(), reType);
    }

    return createSuccessResponse();

  }

  public ResponseEntity<CustomeResponse> getPriceDetailById(Long id) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Optional<PriceRatingProjection> priceList1 = priceRepository.getPriceDetailById(id);
    if (priceList1.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new CustomeResponse(200, HttpStatusConstant.NOT_FOUND_MESSAGE, priceList1));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, priceList1));
  }

  // ADD RECURRING PRICE
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addRecurringPrice(RecurringPriceRequest request) {

    // validate EFFDate and EXPDate
    if (request.getExpDate() != null && request.getEffDate().isAfter(request.getExpDate())) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new CustomeResponse(400, "Effective date must not be after expiration date.", null));
    }

    PriceVer priceVer = createPriceVer(request);
    logger.info("::: PRICE VER successfully saved into database ::");

    Integer priceVerId = priceVer.getId();

    int priority = 1; // default priority nya
    if (priceVerId != null) {
      priority = priceRepository.getNextPriority(priceVerId);
    }

    RefValue refValue = crateRefValue(request);
    logger.info("::: REF VALUE successfully saved into database ::");

    Price price = createPrice(request, priceVerId, refValue.getId(), priority);
    logger.info("::: PRICE successfully saved into database ::");

    refValueRepository.insertValue(Math.toIntExact(price.getId()), refValue.getId());
    refValue.setPriceId(price.getId());

    Character newConnection = request.getNewConnection();
    Character termination = request.getTermination();
    Character normal = request.getNormal();
    Character inAdvanced = request.getInAdvance();
    Integer roundMode = request.getRoundMode();

    String formattedString = "";
    if (request.getRpPriceUnit() != null) {
      Rp rp = new Rp();
      if (request.getRpPriceUnit().equals('C')) {
        formattedString = String.format(
            "NEWCONNECTION='%s';TERMINATION='%s';NORMAL='%s';INADVANCE='%s';PRICEBYDAY=%d;PRICEBYCYCLE=%s;AMOUNT=%d;ROUNDMODE=%d;",
            newConnection,
            termination,
            normal,
            inAdvanced,
            -1,
            String.format("r.event.GetValueByRefID(%d)", refValue.getId()),
            request.getCalculateUnit(),
            roundMode);
      } else if (request.getRpPriceUnit().equals('D')) {
        formattedString = String.format(
            "NEWCONNECTION='%s';TERMINATION='%s';NORMAL='%s';INADVANCE='%s';PRICEBYDAY=%s;PRICEBYCYCLE=%d;AMOUNT=%d;ROUNDMODE=%d;",
            newConnection,
            termination,
            normal,
            inAdvanced,
            String.format("r.event.GetValueByRefID(%d)", refValue.getId()),
            -1,
            request.getCalculateUnit(),
            roundMode);
      }

      rp.setId(Math.toIntExact(price.getId()));
      rp.setParam(formattedString);
      rp.setSpId(0);
      rpRepository.save(rp);
      logger.info("::: RP successfully saved into database ::");

    }

    if (request.getExpressionPrice() != null) {
      try {
        Rp rp = new Rp();
        rp.setId(Math.toIntExact(price.getId()));
        rp.setScriptTempletId(request.getExpressionPrice().getScriptTempletId());
        rp.setRuleComments(request.getExpressionPrice().getAdvancedBenefitRemarks());
        rp.setParam(formattedString);
        if (request.getExpressionPrice().getJsonScriptPage() == null) {
          rp.setRuleScript(request.getExpressionPrice().getRuleScript());
          rp.setScriptPage(new byte[0]);
        } else {
          rp.setScriptPage(generatorXml.getXmlScriptPage(request.getExpressionPrice().getScriptTempletId(),
              request.getExpressionPrice().getJsonScriptPage()));
          rp.setRuleScript(generatorXml.getNewScriptRule(request.getExpressionPrice().getRuleScript(),
              rp.getScriptPage()));
        }
        rp.setSpId(0);
        rpRepository.save(rp);
        logger.info("::: RP successfully saved into database ::");
      } catch (Exception e) {
        throw new ValidationHandler(EnumRC.BAD_REQUEST.getMessage());
      }
    }

    return createSuccessResponse();
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> updateRecurringPrice(Long priceId, UpdateRecurringPriceDto dto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Price price = priceRepository.findById(priceId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
    price.setPriceName(dto.getPriceName());
    price.setAcctItemTypeId(dto.getResultAccountItemType());
    price.setPayIndicator(dto.getPayIndicator());
    price.setComments(dto.getRemarks());
    price.setCreditLimit(dto.getCreditLimit());
    price.setRum(dto.getCalculateUnit());

    priceRepository.save(price);

    RefValue refValue = refValueRepository.findById(Math.toIntExact(price.getValue()))
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
    refValue.setValueString(dto.getPrice());
    refValueRepository.save(refValue);

    Rp rp = rpRepository.findById(Math.toIntExact(priceId))
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
    Character newConnection = dto.getNewConnection();
    Character termination = dto.getTermination();
    Character normal = dto.getNormal();
    Character inAdvanced = dto.getInAdvance();
    Integer roundMode = dto.getRoundMode();
    String formattedString = null;

    if (dto.getExpressionPrice() == null) {
      rp.setScriptTempletId(null);
      rp.setRuleComments(null);
      rp.setRuleScript(null);
      rp.setScriptPage(new byte[0]);

      rp.setScriptPage(null);
      rp.setRuleScript(null);

      rp.setSpId(0);
      if (dto.getRpPriceUnit().equals('C')) {
        formattedString = String.format(
            "NEWCONNECTION='%s';TERMINATION='%s';NORMAL='%s';INADVANCE='%s';PRICEBYDAY=%d;PRICEBYCYCLE=%s;AMOUNT=%d;ROUNDMODE=%d;",
            newConnection,
            termination,
            normal,
            inAdvanced,
            -1,
            String.format("r.event.GetValueByRefID(%d)", refValue.getId()),
            dto.getCalculateUnit(),
            roundMode);
      } else if (dto.getRpPriceUnit().equals('D')) {
        formattedString = String.format(
            "NEWCONNECTION='%s';TERMINATION='%s';NORMAL='%s';INADVANCE='%s';PRICEBYDAY=%s;PRICEBYCYCLE=%d;AMOUNT=%d;ROUNDMODE=%d;",
            newConnection,
            termination,
            normal,
            inAdvanced,
            String.format("r.event.GetValueByRefID(%d)", refValue.getId()),
            -1,
            dto.getCalculateUnit(),
            roundMode);
      }
      rp.setId(Math.toIntExact(price.getId()));
      rp.setParam(formattedString);
      rp.setSpId(0);
      rpRepository.save(rp);
    } else {
      try {
        rp.setScriptTempletId(dto.getExpressionPrice().getScriptTempletId());
        rp.setRuleComments(dto.getExpressionPrice().getAdvancedBenefitRemarks());
        if (dto.getExpressionPrice().getJsonScriptPage() == null) {
          rp.setRuleScript(dto.getExpressionPrice().getRuleScript());
          rp.setScriptPage(new byte[0]);
        } else {
          rp.setScriptPage(generatorXml.getXmlScriptPage(dto.getExpressionPrice().getScriptTempletId(),
              dto.getExpressionPrice().getJsonScriptPage()));
          rp.setRuleScript(generatorXml.getNewScriptRule(dto.getExpressionPrice().getRuleScript(),
              rp.getScriptPage()));
        }
        rp.setSpId(0);
        rpRepository.save(rp);
        logger.info("::: RP successfully saved into database ::");
      } catch (Exception e) {
        throw new ValidationHandler(e.getMessage());
      }
    }

    return createSuccessResponse();
  }

  public ResponseEntity<CustomeResponse> getDetailRecurringPrice(Long priceId) {
    var data = priceRepository.getRecurringPriceDetail(priceId).map(getRecurringPriceDetailMapper::toDtoManual);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public Price createPrice(RecurringPriceRequest dto, Integer priceVerId, Integer refValueId, int priority) {
    Price price = new Price();
    price.setPriceVerId(priceVerId);
    price.setPriceName(dto.getPriceName());
    price.setAcctItemTypeId(dto.getResultAccountItemType());
    price.setValue(Long.valueOf(refValueId));
    price.setPayIndicator(dto.getPayIndicator());
    price.setCreditLimit(dto.getCreditLimit());
    price.setRum(dto.getCalculateUnit());

    price.setSpId(0);
    price.setShareFlag('N');
    price.setPriceExit('N');
    price.setPriority(priority);
    price.setComments(dto.getRemarks());
    return priceRepository.save(price);
  }

  public RefValue crateRefValue(RecurringPriceRequest request) {
    RefValue refValue = new RefValue();
    refValue.setRatePlanId(request.getRatePlanId());
    refValue.setOfferVerId(request.getOfferVerId());
    refValue.setRefValueType('1');
    refValue.setState('A');
    refValue.setRatePrecision(0L);
    refValue.setValueString(request.getPrice());

    refValue.setCreatedDate(LocalDate.now());
    refValue.setStateDate(LocalDate.now());
    refValue.setSpId(0);
    return refValueRepository.save(refValue);
  }

  public PriceVer createPriceVer(RecurringPriceRequest request) {
    Integer priceVerId = request.getPriceVerId();

    if (priceVerId != null && priceVerId != 0) {
      PriceVer priceVer = new PriceVer();
      priceVer.setId(priceVerId);
      return priceVer;
    }

    PriceVer priceVer = new PriceVer();
    priceVer.setRatePlanId(request.getRatePlanId());
    priceVer.setMappingId(request.getMappingId());
    priceVer.setEffDate(request.getEffDate());
    priceVer.setExpDate(request.getExpDate());
    priceVer.setSpId(0);
    return priceVerRepository.save(priceVer);
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addAccumulationPrice(InsertAccumulationPriceDto dto) {
    PriceVer priceVer = createOrGetPriceVer(dto, true);
    logger.info("::: PRICE VER successfully saved into database :::");

    Acm acm = createAccumulationPrice(dto, priceVer.getId(), true);
    logger.info("::: ACM successfully saved into database");

    // ref value acm price
    RefValue refValueAcmPrice = createRefValueAcmPrice(dto, acm.getPriceId(), acm, true);

    logger.info("::: REF VALUE ACM PRICE successfullt saved into database");

    RefValue refValueAcmPrice2 = createRefValueAcmPriceResourceId(dto, acm.getPriceId(), acm, true);

    logger.info("::: REF VALUE ACM PRICE successfullt saved into database");

    acmRepository.insertValue(refValueAcmPrice.getId(), acm.getId());

    acmRepository.insertRefValueId(refValueAcmPrice2.getId(), acm.getId());

    // ACM TIME SPAN
    List<AcmTimeSpan> savedTimeSpans = new ArrayList<>();

    if (dto.getTimeSpanAccumulation() != null && !dto.getTimeSpanAccumulation().isEmpty()) {
      for (InsertTimeSpanAccumulationDto timeSpanReq : dto.getTimeSpanAccumulation()) {
        // Buat object
        AcmTimeSpan acmTimeSpan = createAccumulationTimeSpan(timeSpanReq, priceVer.getId());

        // Simpan acmTimeSpan ke DB, asumsi kamu punya method save di repository
        AcmTimeSpan savedAcmTimeSpan = acmTimeSpanRepository.save(acmTimeSpan);
        savedTimeSpans.add(savedAcmTimeSpan); // simpan ke list

        // Buat dan simpan refValue
        RefValue refValueAcmTimeSpan = createRefValueAcmTimeSpan(dto, timeSpanReq, acm.getPriceId());
        refValueRepository.save(refValueAcmTimeSpan);

        // Mapping ke table relasi (atau update field foreign key)
        acmTimeSpanRepository.insertValue(refValueAcmTimeSpan.getId(), savedAcmTimeSpan.getId());
      }
      logger.info("::: ACM TIME SPAN successfully saved into database");
    }

    // ACM REF
    if (dto.getReferenceAccumulation() != null && !dto.getReferenceAccumulation().isEmpty()) {
      for (InsertReferenceAccumulationDto acmRefReq : dto.getReferenceAccumulation()) {
        RefValue refValueAcmRef = createRefValueAcmRef(dto, acmRefReq, acm.getPriceId());
        refValueRepository.save(refValueAcmRef);

        if (acmRefReq.getAcmTimeSpanId() == null) {
          AcmRef acmRef = createAccumulationReference(acmRefReq, priceVer.getId(), refValueAcmRef.getId(),
              null);
          acmRefRepository.save(acmRef);
        } else {
          AcmRef acmRef = createAccumulationReference(acmRefReq, priceVer.getId(), refValueAcmRef.getId(),
              savedTimeSpans.get(acmRefReq.getAcmTimeSpanId()).getId());
          acmRefRepository.save(acmRef);
        }
        // acmRefRepository.insertValue(refValueAcmRef.getId(), acmRef.getId());

        // insert ref value id

      }
      logger.info("::: ACM TIME SPAN successfully saved into database");
    }

    // ACM RULE
    if (dto.getExpressionPrice() != null) {
      AcmRule acmRule = createAccumulationRule(dto.getExpressionPrice(), priceVer.getId());
      acmRuleRepository.save(acmRule);
      logger.info("::: ACM RULE successfully saved into database");
    }
    return createSuccessResponse();
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> updateAccumulationPrice(InsertAccumulationPriceDto dto) {

    PriceVer priceVer = createOrGetPriceVer(dto, false);
    logger.info("::: PRICE VER successfully saved into database :::");

    Acm acm = createAccumulationPrice(dto, priceVer.getId(), false);
    logger.info("::: ACM successfully saved into database");

    // ref value acm price
    RefValue refValueAcmPrice = createRefValueAcmPrice(dto, acm.getPriceId(), acm, false);

    logger.info("::: REF VALUE ACM PRICE successfullt saved into database");

    RefValue refValueAcmPrice2 = createRefValueAcmPriceResourceId(dto, acm.getPriceId(), acm, false);

    logger.info("::: REF VALUE ACM PRICE successfullt saved into database");

    acmRepository.insertValue(refValueAcmPrice.getId(), acm.getId());

    acmRepository.insertRefValueId(refValueAcmPrice2.getId(), acm.getId());

    // ACM TIME SPAN
    List<AcmTimeSpan> savedTimeSpans = new ArrayList<>();

    acmRefRepository.deleteAcmRef(dto.getPriceVerId());
    acmTimeSpanRepository.deleteAcmTimeSpan(dto.getPriceVerId());
    acmRuleRepository.deleteAcmRule(dto.getPriceVerId());

    if (dto.getTimeSpanAccumulation() != null && !dto.getTimeSpanAccumulation().isEmpty()) {
      for (InsertTimeSpanAccumulationDto timeSpanReq : dto.getTimeSpanAccumulation()) {
        AcmTimeSpan acmTimeSpan = createAccumulationTimeSpan(timeSpanReq, priceVer.getId());
        AcmTimeSpan savedAcmTimeSpan = acmTimeSpanRepository.save(acmTimeSpan);
        savedTimeSpans.add(savedAcmTimeSpan);

        RefValue refValueAcmTimeSpan = createRefValueAcmTimeSpan(dto, timeSpanReq, acm.getPriceId());
        refValueRepository.save(refValueAcmTimeSpan);

        acmTimeSpanRepository.insertValue(refValueAcmTimeSpan.getId(), acmTimeSpan.getId());
      }
      logger.info("::: ACM TIME SPAN successfully saved into database");
    }

    // ACM REF
    if (dto.getReferenceAccumulation() != null && !dto.getReferenceAccumulation().isEmpty()) {
      for (InsertReferenceAccumulationDto acmRefReq : dto.getReferenceAccumulation()) {

        RefValue refValueAcmRef = createRefValueAcmRef(dto, acmRefReq, acm.getPriceId());
        refValueRepository.save(refValueAcmRef);

        if (acmRefReq.getAcmTimeSpanId() == null) {
          AcmRef acmRef = createAccumulationReference(acmRefReq, priceVer.getId(), refValueAcmRef.getId(),
              null);
          acmRefRepository.save(acmRef);
        } else {
          AcmRef acmRef = createAccumulationReference(acmRefReq, priceVer.getId(), refValueAcmRef.getId(),
              savedTimeSpans.get(acmRefReq.getAcmTimeSpanId() - 1).getId());
          acmRefRepository.save(acmRef);
        }
        // acmRefRepository.insertValue(refValueAcmRef.getId(), acmRef.getId());
        // insert ref value id
      }
      logger.info("::: ACM TIME SPAN successfully saved into database");
    }

    // ACM RULE
    if (dto.getExpressionPrice() != null) {

      AcmRule acmRule = createAccumulationRule(dto.getExpressionPrice(), priceVer.getId());
      acmRuleRepository.save(acmRule);
      logger.info("::: ACM RULE successfully saved into database");
    }

    return createSuccessResponse();
  }

  private RefValue createRefValueAcmPriceResourceId(InsertAccumulationPriceDto request, Long priceId, Acm acm,
      boolean create) {
    RefValue refValue = new RefValue();
    if (create) {
      logger.info("createRefValueAcmPriceResourceId");
      refValue.setRefValueType('5');
      refValue.setValueString(String.valueOf(request.getResourceId()));
      refValue.setCreatedDate(LocalDate.now());
      refValue.setState('A');
      refValue.setStateDate(LocalDate.now());
      refValue.setSpId(0);
      refValue.setPriceId(priceId);
      refValue.setRatePlanId(request.getRatePlanId());
      refValue.setOfferVerId(request.getOfferVerId());
      refValueRepository.save(refValue);
    } else {
      logger.info("update RefValueAcmPriceResourceId");

      List<RefValue> refValueList = refValueRepository.findByPriceId(priceId);
      if (ObjectUtils.isEmpty(request.getTemplateId())) {
        // template = 'N'
        // clear refValue;
        for (RefValue ref : refValueList) {
          ref.setState('X');
          refValueRepository.save(ref);
          logger.info("::: RefValue successfully deleted.. is Template ID exist !");
        }
      }
      // refValue = refValueRepository.findById(acm.getRefValueId()).orElseThrow(() ->
      // new ValidationHandler("RefValue Notf found"));
      refValue.setRefValueType('5');
      refValue.setValueString(String.valueOf(request.getResourceId()));
      refValue.setCreatedDate(LocalDate.now());
      refValue.setState('A');
      refValue.setStateDate(LocalDate.now());
      refValue.setSpId(0);
      refValue.setPriceId(priceId);
      refValue.setRatePlanId(request.getRatePlanId());
      refValue.setOfferVerId(request.getOfferVerId());
      refValueRepository.save(refValue);
    }
    return refValue;
  }

  private RefValue createRefValueAcmRef(InsertAccumulationPriceDto acmReq, InsertReferenceAccumulationDto request,
      Long priceId) {
    RefValue refValue = new RefValue();

    refValue.setRefValueType('1');
    refValue.setValueString(request.getAccumulation());
    refValue.setCreatedDate(LocalDate.now());
    refValue.setState('A');
    refValue.setStateDate(LocalDate.now());
    refValue.setSpId(0);
    refValue.setPriceId(priceId);
    refValue.setRatePlanId(acmReq.getRatePlanId());
    refValue.setOfferVerId(acmReq.getOfferVerId());

    return refValue;
  }

  private RefValue createRefValueAcmTimeSpan(InsertAccumulationPriceDto acmReq, InsertTimeSpanAccumulationDto request,
      Long priceId) {
    RefValue refValue = new RefValue();

    refValue.setRefValueType('1');
    refValue.setValueString(request.getValueString());
    refValue.setCreatedDate(LocalDate.now());
    refValue.setState('A');
    refValue.setStateDate(LocalDate.now());
    refValue.setSpId(0);
    refValue.setPriceId(priceId);
    refValue.setRatePlanId(acmReq.getRatePlanId());
    refValue.setOfferVerId(acmReq.getOfferVerId());

    return refValue;
  }

  private RefValue createRefValueAcmPrice(InsertAccumulationPriceDto request, Long priceId, Acm acm, boolean create) {
    RefValue refValue = new RefValue();
    if (create) {
      refValue = new RefValue();
      refValue.setRefValueType('1');
      refValue.setValueString(request.getAccumulation());
      refValue.setCreatedDate(LocalDate.now());
      refValue.setState('A');
      refValue.setStateDate(LocalDate.now());
      refValue.setSpId(0);
      refValue.setPriceId(priceId);
      refValue.setRatePlanId(request.getRatePlanId());
      refValue.setOfferVerId(request.getOfferVerId());
      refValueRepository.save(refValue);
    } else {
      List<RefValue> refValueList = refValueRepository.findByPriceId(priceId);
      if (ObjectUtils.isEmpty(request.getTemplateId())) {
        // template = 'N'
        // clear refValue;
        for (RefValue ref : refValueList) {
          ref.setState('X');
          refValueRepository.save(ref);
          logger.info("::: RefValue successfully deleted.. is Template ID exist !");
        }
      }
      // refValue = refValueRepository.findById(acm.getValue()).orElseThrow(() -> new
      // ValidationHandler("::: RefValue not found!"));
      refValue.setRefValueType('1');
      refValue.setValueString(request.getAccumulation());
      refValue.setCreatedDate(LocalDate.now());
      refValue.setState('A');
      refValue.setStateDate(LocalDate.now());
      refValue.setSpId(0);

      refValue.setPriceId(priceId);
      refValue.setRatePlanId(request.getRatePlanId());
      refValue.setOfferVerId(request.getOfferVerId());
      refValueRepository.save(refValue);
    }
    return refValue;
  }

  private PriceVer createOrGetPriceVer(InsertAccumulationPriceDto request, boolean create) {
    if (create) {
      Integer priceVerId = request.getPriceVerId();
      if (priceVerId != null && priceVerId != 0) {
        PriceVer priceVer = new PriceVer();
        priceVer.setId(priceVerId);
        return priceVer;
      }

      PriceVer priceVer = new PriceVer();
      priceVer.setRatePlanId(request.getRatePlanId());
      priceVer.setMappingId(request.getMappingId());
      priceVer.setEffDate(request.getEffDate());
      priceVer.setExpDate(request.getExpDate());
      priceVer.setSpId(0);
      return priceVerRepository.save(priceVer);
    } else {
      PriceVer priceVer = priceVerRepository.findById(request.getPriceVerId())
          .orElseThrow(() -> new ValidationHandler("PriceVerId not found"));
      priceVer.setEffDate(request.getEffDate());
      priceVer.setExpDate(request.getExpDate());
      priceVer.setSpId(priceVer.getSpId());
      return priceVerRepository.save(priceVer);
    }

  }

  private Acm createAccumulationPrice(InsertAccumulationPriceDto request, Integer id, boolean create) {
    if (create) {
      Acm acm = new Acm();
      acm.setId(id);
      acm.setReAttr(request.getReAttrId());
      acm.setResourceId(request.getResourceId());
      acm.setRum(request.getCalculateUnit());
      acm.setSpId(0);
      acm.setPriceId(priceRepository.selectMaxSeq().longValue());
      if (ObjectUtils.isNotEmpty(request.getTemplateId())) {
        acm.setTemplateFlag('Y');
      } else {
        acm.setTemplateFlag('N');
      }
      acm.setComments(request.getRemarks());
      acm.setShareFlag('N');
      return acmRepository.save(acm);
    } else {
      Acm acm = acmRepository.findById(id).orElseThrow(() -> new ValidationHandler("Acm not found"));
      acm.setReAttr(request.getReAttrId());
      acm.setResourceId(request.getResourceId());
      acm.setRum(request.getCalculateUnit());
      acm.setSpId(0);
      if (ObjectUtils.isNotEmpty(request.getTemplateId())) {
        acm.setTemplateFlag('Y');
      } else {
        acm.setTemplateFlag('N');
      }
      acm.setComments(request.getRemarks());
      acm.setShareFlag('N');
      return acmRepository.save(acm);
    }
  }

  private AcmTimeSpan createAccumulationTimeSpan(InsertTimeSpanAccumulationDto request, Integer id) {
    AcmTimeSpan acmTimeSpan = new AcmTimeSpan();

    acmTimeSpan.setPriceVerId(id);
    acmTimeSpan.setAdjustMethod(request.getCalculationMethod());
    acmTimeSpan.setTimeSpanId(request.getTimeSpanId());
    acmTimeSpan.setPriority(request.getPriority());
    acmTimeSpan.setRum(request.getCalculationUnit());
    acmTimeSpan.setSpId(0);

    return acmTimeSpanRepository.save(acmTimeSpan);

  }

  private AcmRef createAccumulationReference(InsertReferenceAccumulationDto request, Integer priceVerId,
      Integer refValueId, Integer acmTimeSpanId) {

    AcmRef acmRef = new AcmRef();

    acmRef.setPriceVerId(priceVerId);
    acmRef.setAdjustMethod(request.getCalculationMethod());
    acmRef.setEffValue(request.getEffValue());
    acmRef.setExpValue(request.getExpValue());
    acmRef.setResourceId(request.getResourceId());
    acmRef.setRate(refValueId);
    acmRef.setAcmTimeSpanId(acmTimeSpanId);
    acmRef.setRum(request.getCalculateUnit());
    acmRef.setSpId(0);

    return acmRef;
  }

  private AcmRule createAccumulationRule(InsertAccumulationExpressionPriceDto request, Integer priceVerId) {

    try {
      byte[] scriptPage;
      Integer priority = acmRuleRepository.findNextPriority(priceVerId);

      AcmRule acmRule = new AcmRule();

      acmRule.setPriceVerId(priceVerId);
      acmRule.setScriptTempletId(request.getScriptTempletId());
      acmRule.setRuleComments(request.getRuleComment());
      if (request.getJsonScriptPage() == null) {
        acmRule.setRuleScript(request.getRuleScript());
        acmRule.setScriptPage(new byte[0]);
      } else {
        scriptPage = generatorXml.getXmlScriptPage(request.getScriptTempletId(), request.getJsonScriptPage());
        acmRule.setScriptPage(scriptPage);
        acmRule.setRuleScript(generatorXml.getNewScriptRule(request.getRuleScript(), scriptPage));
      }
      acmRule.setSpId(0);
      acmRule.setPriority(priority);

      return acmRule;

    } catch (Exception e) {
      throw new ValidationHandler(e.getMessage());
    }
  }

  // BENEFIT PRICE
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addBenefit(BenefitRequest request) {
    PriceVer priceVer = createOrGetPriceVer(request);

    Period period = createPeriod(request);
    logger.info("::: PERIOD successfully saved into database :: {}", period);

    SubBalType subBalType = createSubBalType(request, period.getId());
    logger.info("::: SUB BAL TYPE successfully saved into database :: {}", subBalType);

    EventBenefit eventBenefit = createEventBenefit(request, priceVer.getId(), subBalType.getId());
    logger.info("::: EVENT BENEFIT successfully saved into database :: {}", eventBenefit);

    RefValue refValue = createRefValue(request, Math.toIntExact(eventBenefit.getId()));
    refValueRepository.save(refValue);
    logger.info("::: REF VALUE successfully saved into database :: {}",  refValue);

    eventBenefitRepository.insertValue(refValue.getId(), Math.toIntExact(eventBenefit.getId()));

    return createSuccessResponse();
  }

  private PriceVer createOrGetPriceVer(BenefitRequest request) {
    Integer priceVerId = request.getPriceVerId();

    if (priceVerId != null && priceVerId != 0) {
      PriceVer priceVer = new PriceVer();
      priceVer.setId(priceVerId);
      return priceVer;
    }

    PriceVer priceVer = new PriceVer();
    priceVer.setRatePlanId(request.getRatePlanId());
    priceVer.setMappingId(request.getMappingId());
    priceVer.setEffDate(request.getEffectiveDate());
    priceVer.setExpDate(request.getExpiryDate());
    priceVer.setSpId(0);

    return priceVerRepository.save(priceVer);
  }

  private EventBenefit createEventBenefit(BenefitRequest request, Integer priceVerId, Integer subBalTypeId) {
    EventBenefit eventBenefit = new EventBenefit();

    try {
      eventBenefit.setPriceVerId(priceVerId);
      eventBenefit.setSubBalTypeId(subBalTypeId);
      eventBenefit.setPriority(eventBenefitRepository.findNextPriority(priceVerId));
      eventBenefit.setPriceName(request.getBenefitName());
      eventBenefit.setRum(request.getCalculationUnit().longValue());
      eventBenefit.setRatePrecision(0);
      eventBenefit.setReAttr(request.getReAttrId());
      eventBenefit.setCalcPrecision(0);
      eventBenefit.setTemplateFlag('N');
      eventBenefit.setShareFlag('N');
      eventBenefit.setComments(request.getRemarks());
      eventBenefit.setSpId(0);
      eventBenefit.setConfigType('0');

      if (request.getAdvanceBenefit() != null) {
        eventBenefit.setRuleComments(request.getAdvanceBenefit().getAdvancedBenefitRemarks());
        eventBenefit.setScriptTempletId(request.getAdvanceBenefit().getScriptTempletId());
        if (request.getAdvanceBenefit().getJsonScriptPage() == null) {
          eventBenefit.setRuleScript(request.getAdvanceBenefit().getRuleScript());
          eventBenefit.setScriptPage(new byte[0]);
        } else {
          byte[] scriptPage = generatorXml.getXmlScriptPage(request.getAdvanceBenefit().getScriptTempletId(),
              request.getAdvanceBenefit().getJsonScriptPage());
          eventBenefit.setRuleScript(
              generatorXml.getNewScriptRule(request.getAdvanceBenefit().getRuleScript(), scriptPage));
          eventBenefit.setScriptPage(scriptPage);
        }
      } else {
        eventBenefit.setScriptTempletId(null);
        eventBenefit.setRuleScript("");
        eventBenefit.setRuleComments(null);
        eventBenefit.setScriptPage(new byte[0]);
      }
    } catch (Exception e) {
      throw new ValidationHandler(EnumRC.CREATE_FAILED.getMessage());
    }

    return eventBenefitRepository.save(eventBenefit);
  }

  private Period createPeriod(BenefitRequest request) {
    Period period = new Period();
    period.setRelEffOffset(request.getOffsetOfEffectiveDate());
    period.setRelEffUnit(request.getOffsetOfEffectiveDateUnit());
    period.setRelExpOffset(request.getDurationOfAvailability());
    period.setRelExpUnit(request.getDurationOfAvailabilityUnit());
    period.setAbsEffDate(request.getAbsoluteEffectiveDate());
    period.setAbsExpDate(request.getAbsoluteExpiryDate());
    period.setRelEffTime(request.getRelativeEffectiveTime());
    period.setRelExpTime(request.getRelativeExpiryTime());
    period.setSpId(0);

    return periodRepository.save(period);
  }

  private SubBalType createSubBalType(BenefitRequest request, Integer periodId) {
    SubBalType subBalType = new SubBalType();
    subBalType.setPeriodId(periodId);
    subBalType.setAcctResId(request.getAcctBalanceTypeId());
    subBalType.setCeilLimit(request.getCycleCeilLimit());
    subBalType.setDailyCeilLimit(request.getDailyCeilLimit());
    subBalType.setFloorLimit(request.getCycleFloorLimit());
    subBalType.setDailyFloorLimit(request.getDailyFloorLimit());
    subBalType.setMaxDays(request.getMaximumDays());
    subBalType.setBalFlags(request.getBalFlags());
    subBalType.setPeriodRelUnit(request.getRelativePeriodUnit());
    subBalType.setLimitSubs(request.getSubscriberOnly());
    subBalType.setAbsExpOffset(request.getOffsetOfAbsoluteExpiry());
    subBalType.setSpId(0);

    return subBalTypeRepository.save(subBalType);
  }

  private RefValue createRefValue(BenefitRequest request, Integer price) {
    RefValue refValue = new RefValue();
    refValue.setRatePlanId(request.getRatePlanId());
    refValue.setOfferVerId(request.getOfferVerId());
    refValue.setPriceId(Long.valueOf(price));
    refValue.setRefValueType('1');
    refValue.setState('A');
    refValue.setValueString(request.getBenefitValue());
    refValue.setCreatedDate(LocalDate.now());
    refValue.setStateDate(LocalDate.now());
    refValue.setSpId(0);

    return refValue;
  }

  public ResponseEntity<CustomeResponse> updateBenefitPrice(Integer priceId, UpdateBenefitRequestDto request) {
    EventBenefit findEventBenefit = eventBenefitRepository.findById(priceId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
    EventBenefit eventBenefit = updateEventBenefit(findEventBenefit, request);

    SubBalType subBalType = updateSubBalType(eventBenefit.getSubBalTypeId(), request);
    subBalTypeRepository.save(subBalType);
    RefValue refValue = updateRefValueBenefit(priceId, request, Math.toIntExact(findEventBenefit.getValue()));
    refValueRepository.save(refValue);
    Period period = updatePeriod(subBalType.getPeriodId(), request);
    periodRepository.save(period);

    eventBenefit.setValue(refValue.getId());
    eventBenefitRepository.save(eventBenefit);

    return createSuccessResponse();
  }

  private RefValue updateRefValueBenefit(Integer priceId, UpdateBenefitRequestDto request, Integer eventBenefitId) {
    RefValue refValue = new RefValue();

    List<RefValue> refValueList = refValueRepository.findByPriceId(Long.valueOf(priceId));
    for (RefValue ref : refValueList) {
      ref.setState('X');
      refValueRepository.save(ref);
      logger.info("::: RefValue successfully deleted.. is Template ID exist !");
    }

    RefValue getRefValue = refValueRepository.findById(eventBenefitId)
        .orElseThrow(() -> new ValidationHandler("::: RefValue not found!"));
    refValue.setRefValueType('1');
    refValue.setValueString(request.getBenefitValue());
    refValue.setCreatedDate(LocalDate.now());
    refValue.setState('A');
    refValue.setStateDate(LocalDate.now());
    refValue.setSpId(0);

    refValue.setPriceId(Long.valueOf(priceId));
    refValue.setRatePlanId(getRefValue.getRatePlanId());
    refValue.setOfferVerId(getRefValue.getOfferVerId());
    return refValue;
  }

  private void orElseThrow(Object o) {
  }

  private EventBenefit updateEventBenefit(EventBenefit eventBenefit, UpdateBenefitRequestDto request) {

    eventBenefit.setPriceName(request.getBenefitName());
    eventBenefit.setComments(request.getRemarks());
    eventBenefit.setRum(Long.valueOf(request.getCalculationUnit()));
    eventBenefit.setReAttr(request.getReAttrId());

    try {
      eventBenefit.setPriceName(request.getBenefitName());
      eventBenefit.setRum(request.getCalculationUnit().longValue());
      eventBenefit.setRatePrecision(0);
      eventBenefit.setReAttr(request.getReAttrId());
      eventBenefit.setConfigType('0');

      if (request.getAdvanceBenefit() != null) {
        eventBenefit.setRuleComments(request.getAdvanceBenefit().getAdvancedBenefitRemarks());
        eventBenefit.setScriptTempletId(request.getAdvanceBenefit().getScriptTempletId());
        if (request.getAdvanceBenefit().getJsonScriptPage() == null) {
          eventBenefit.setRuleScript(request.getAdvanceBenefit().getRuleScript());
          eventBenefit.setScriptPage(new byte[0]);
        } else {
          byte[] scriptPage = generatorXml.getXmlScriptPage(request.getAdvanceBenefit().getScriptTempletId(),
              request.getAdvanceBenefit().getJsonScriptPage());
          eventBenefit.setRuleScript(
              generatorXml.getNewScriptRule(request.getAdvanceBenefit().getRuleScript(), scriptPage));
          eventBenefit.setScriptPage(scriptPage);
        }
      } else {
        eventBenefit.setScriptTempletId(null);
        eventBenefit.setRuleScript("");
        eventBenefit.setRuleComments(null);
        eventBenefit.setScriptPage(new byte[0]);
      }
    } catch (Exception e) {
      throw new ValidationHandler(EnumRC.CREATE_FAILED.getMessage());
    }

    return eventBenefit;
  }

  private SubBalType updateSubBalType(Integer subBalTypeId, UpdateBenefitRequestDto request) {
    SubBalType subBalType = subBalTypeRepository.findById(subBalTypeId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    subBalType.setAcctResId(request.getAcctBalanceTypeId());
    subBalType.setCeilLimit(request.getCycleCeilLimit());
    subBalType.setDailyCeilLimit(request.getDailyCeilLimit());
    subBalType.setFloorLimit(request.getCycleFloorLimit());
    subBalType.setDailyFloorLimit(request.getDailyFloorLimit());
    subBalType.setMaxDays(request.getMaximumDays());
    subBalType.setLimitSubs(request.getSubscriberOnly());
    subBalType.setBalFlags(request.getBalFlags());
    subBalType.setAbsExpOffset(request.getOffsetOfAbsoluteExpiry());

    return subBalType;
  }

  private Period updatePeriod(Integer periodId, UpdateBenefitRequestDto request) {
    Period period = periodRepository.findById(periodId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    period.setAbsEffDate(request.getAbsoluteEffectiveDate());
    period.setAbsExpDate(request.getAbsoluteExpiryDate());
    period.setRelEffOffset(request.getOffsetOfEffectiveDate());
    period.setRelEffUnit(request.getOffsetOfEffectiveDateUnit());
    period.setRelExpOffset(request.getDurationOfAvailability());
    period.setRelExpUnit(request.getDurationOfAvailabilityUnit());
    period.setRelEffTime(request.getRelativeEffectiveTime());
    period.setRelExpTime(request.getRelativeExpiryTime());

    return period;
  }

  private void updateAdvancedScript(EventBenefit eventBenefit, UpdateBenefitRequestDto request) {
    if (request.getAdvanceBenefit() != null) {
      try {
        byte[] scriptPage = generatorXml.getXmlScriptPage(request.getAdvanceBenefit().getScriptTempletId(),
            request.getAdvanceBenefit().getJsonScriptPage());
        eventBenefit.setRuleScript(
            generatorXml.getNewScriptRule(request.getAdvanceBenefit().getRuleScript(), scriptPage));
        eventBenefit.setRuleComments(request.getAdvanceBenefit().getAdvancedBenefitRemarks());
        eventBenefit.setScriptTempletId(request.getAdvanceBenefit().getScriptTempletId());
        eventBenefit.setScriptPage(scriptPage);

        eventBenefitRepository.save(eventBenefit);
      } catch (Exception e) {
        throw new ValidationHandler(EnumRC.BAD_REQUEST.getMessage());
      }
    }
  }

  private ResponseEntity<CustomeResponse> createSuccessResponse() {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> getDetailPriceBenefit(Integer priceId) {
    Optional<EventBenefit> eventBenefit = eventBenefitRepository.findById(priceId);

    if (eventBenefit.isEmpty()) {
      var data = priceRepository.getPriceDetailById(Long.valueOf(priceId)).map(priceRatingMapper::toDto);
      return ResponseEntity.status(HttpStatus.OK)
          .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    Optional<GetDetailBenefitPriceProjection> projectionOpt = eventBenefitRepository
        .findDetailBenefitPrice(priceId);

    var data = eventBenefitRepository.findDetailBenefitPrice(priceId).map(getDetailBenefitMapper::toDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public BaseResponseDto listPriceVerByRatePlan(Integer ratePlanId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
    Re re = reRepository.findById(ratePlan.getReId())
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    if (ratePlan.getRatePlanType().equals('3')) {
      return listPriceVerBenefitByRatePlanId(ratePlanId);
    }

    if (mappingUnitRepository.countMappingUnitByRatePlanId(ratePlanId) > 0) {
      mappingService.getMappingPriceVerAndPrice(ratePlanId);

      baseResponseDto.setData(mappingService.getMappingPriceVerAndPrice(ratePlanId));
      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

      return baseResponseDto;
    }

    List<PriceProjection> list = priceRepository.findPriceVerByRatePlanId(ratePlanId);
    Map<String, PriceVersionResponse> grouped = new LinkedHashMap<>();

    if (list.isEmpty()) {
      baseResponseDto.setCode(EnumRC.DATA_NOT_FOUND.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(EnumRC.DATA_NOT_FOUND.getMessage());
      return baseResponseDto;
    }

    for (PriceProjection p : list) {
      String key = p.getPriceVerId() + "|" + p.getEffDate() + "|" + p.getExpDate();

      PriceVersionResponse parent = grouped.computeIfAbsent(key, k -> {
        PriceVersionResponse res = new PriceVersionResponse();
        res.setPriceVerId(p.getPriceVerId());
        res.setEffDate(p.getEffDate());
        res.setExpDate(p.getExpDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String effDateStr = p.getEffDate() != null ? formatter.format(p.getEffDate()) : "-";
        String expDateStr = p.getExpDate() != null ? formatter.format(p.getExpDate()) : "";
        res.setDate(effDateStr + " - " + expDateStr);
        return res;
      });

      PriceItemResponse item = new PriceItemResponse();
      item.setReType(p.getReType());
      item.setPriceId(p.getPriceId());
      item.setPriceName(p.getPriceName());
      // item.setReAttrName(p.getReAttrName());
      item.setAcctItemTypeName(p.getAcctItemTypeName());
      // item.setValueString(p.getValueString());
      // item.setRum(p.getRum());
      String formatted = String.format("%s %s / %d %s ",
          p.getValueString(),
          p.getAcctItemTypeName(),
          p.getRum(),
          p.getReAttrName());

      item.setCalculateUnit(formatted);

      parent.getPrice().add(item);
    }

    baseResponseDto.setData(new ArrayList<>(grouped.values()));
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;
  }

  public BaseResponseDto listPriceVerBenefitByRatePlanId(Integer ratePlanId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<PriceBenefitProjection> flatList = eventBenefitRepository.findPriceVerByRatePlanId(ratePlanId);
    Map<String, PriceVersionResponse> grouped = new LinkedHashMap<>();

    if (flatList.isEmpty()) {
      baseResponseDto.setCode(EnumRC.NOT_FOUND.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage("Rate Plan tidak ditemukan atau tidak punya price version");
      return baseResponseDto;
    }

    for (PriceBenefitProjection p : flatList) {
      String key = p.getPriceVerId() + "|" + p.getEffDate() + "|" + p.getExpDate();

      PriceVersionResponse parent = grouped.computeIfAbsent(key, k -> {
        PriceVersionResponse res = new PriceVersionResponse();
        res.setPriceVerId(p.getPriceVerId());
        res.setEffDate(p.getEffDate());
        res.setExpDate(p.getExpDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String effDateStr = p.getEffDate() != null ? formatter.format(p.getEffDate()) : "-";
        String expDateStr = p.getExpDate() != null ? formatter.format(p.getExpDate()) : "";
        res.setDate(effDateStr + " - " + expDateStr);
        return res;
      });

      PriceItemResponse item = new PriceItemResponse();
      item.setReType(p.getReType());
      item.setPriceId(p.getPriceId());
      item.setSubBalTypeId(p.getSubBalTypeId());
      item.setPriceName(p.getPriceName());
      // item.setReAttrName(p.getReAttrName());
      // item.setAcctItemTypeName(p.getAcctItemTypeName());
      // item.setValueString(p.getValueString());
      // item.setRum(p.getRum());
      String formatted = String.format("%s / %d %s ",
          p.getValueString(),
          // p.getAcctItemTypeName(),
          p.getRum(),
          p.getReAttrName());

      item.setCalculateUnit(formatted);

      parent.getPrice().add(item);
    }

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setData(new ArrayList<>(grouped.values()));
    return baseResponseDto;
  }

  public BaseResponseDto deletePriceVer(Integer priceVerId) {
    BaseResponseDto response = new BaseResponseDto();

    if (priceVerRepository.findById(priceVerId).isEmpty()) {
      response.setCode(EnumRC.NOT_FOUND.getRESPONSE_CODE().toString());
      response.setMessage(EnumRC.NOT_FOUND.getMessage());
      return response;
    }

    priceVerRepository.callDeletePriceVerProcedure(priceVerId);

    logger.info("::: PRICE VER successfully deleted into database ::");

    response.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    response.setMessage(EnumRC.SUCCESS.getMessage());
    return response;
  }

  // public BaseResponseDto updatePrice (Long priceId, PricePlanVerRequestDto
  // pricePlanVerRequestDto){
  // BaseResponseDto baseResponseDto = new BaseResponseDto();
  //
  // try {
  // Optional<Price> optionalPrice = priceRepository.findById(priceId);
  // if (optionalPrice.isEmpty()) {
  // throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
  // }
  // Price price = optionalPrice.get();
  //
  // price.setPriceName(pricePlanVerRequestDto.getPriceName());
  // price.setAcctItemTypeId(pricePlanVerRequestDto.getResultAccountItemType());
  // price.setValue(Long.valueOf(pricePlanVerRequestDto.getValue()));
  // price.setPayIndicator(pricePlanVerRequestDto.getPayIndicator());
  // price.setReAttr(pricePlanVerRequestDto.getReAttrId());
  // price.setRum(pricePlanVerRequestDto.getRum());
  // priceRepository.save(price);
  //
  // // Jika berhasil
  // baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
  // baseResponseDto.setMessage("Update berhasil");
  // } catch (Exception e) {
  // baseResponseDto.setCode(EnumRC.BAD_REQUEST.getRESPONSE_CODE().toString());
  // baseResponseDto.setMessage("Error: " + e.getMessage());
  // e.printStackTrace();
  // }
  // return baseResponseDto;
  // }

  public BaseResponseDto sharePricePlanVer(SharePricePlanRequestDTO sharePricePlanRequestDTO) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Integer sharePricePlanVerId = sharePricePlanRequestDTO.getSharedPricePlanVerId();
    Integer offerId = sharePricePlanRequestDTO.getOfferId();

    OfferVer oldOfferVer = offerVerRepository.findById(sharePricePlanVerId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.DATA_NOT_FOUND.getMessage()));
    Integer newPricePlanVerId = offerVerRepository.getNextOfferVerId();
    Integer seq = offerVerRepository.getNextSeq(offerId);

    OfferVer newOfferVer = new OfferVer();
    BeanUtils.copyProperties(oldOfferVer, newOfferVer);
    newOfferVer.setOfferId(newPricePlanVerId);

    // OfferVer oldOfferVer =
    // offerVerRepository.findById(sharePricePlanRequestDTO.getSharedPricePlanVerId()).orElseThrow(()
    // -> new ValidationHandler(EnumRC.DATA_NOT_FOUND.getMessage()));
    // if (oldOfferVer != null && oldOfferVer.getOfferId() != null) {
    //
    // }
    //
    // Integer seq =
    // offerVerRepository.getNextSeq(sharePricePlanRequestDTO.getOfferId());
    //
    // OfferVer newOfferVer = new OfferVer();
    // newOfferVer.setRefOfferVerId(sharedPricePlanVerId);
    // newOfferVer.setOfferId(offerId);
    // newOfferVer.setSeq(seq);
    //
    // // TODO : validasi offer ver state flag, konfigurasi cache buat offer state
    // flag
    //
    // OfferVer savedOffer = offerVerRepository.save(newOfferVer);

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;
  }

  public BaseResponseDto getTimeSpanPrice(Integer priceId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<Object[]> timeSpanUpList = timeSpanUpRepository.getTimeSpanUpByPriceId(priceId);

    if (timeSpanUpList.isEmpty()) {
      throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
    }

    List<GetListTimeSpanPrice> result = timeSpanUpList.stream().map(obj -> {
      GetListTimeSpanPrice response = new GetListTimeSpanPrice();
      response.setTimeSpanUpId((Integer) obj[0]);
      response.setPriority((Integer) obj[1]);
      response.setTimeSpanUpName((String) obj[2]);
      response.setCalculationMethod((Character) obj[3]);
      response.setPrice((String) obj[4]);
      response.setCalculateUnit((Long) obj[5]);
      return response;
    }).toList();

    baseResponseDto.setData(result);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;

  }

  public BaseResponseDto deleteTimeSpanPrice(Integer timeSpanUpId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    TimeSpanUp timeSpanUp = timeSpanUpRepository.findById(timeSpanUpId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    timeSpanUpRepository.deleteById(timeSpanUpId);

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    return baseResponseDto;
  }

  public BaseResponseDto updateTimeSpanPrice(UpdateTimeSpanPriceRequest timeSpanUpRequest, Integer timeSpanUpId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    TimeSpanUp timeSpanUp = timeSpanUpRepository.findById(timeSpanUpId)
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    timeSpanUp.setTimeSpanId(timeSpanUpRequest.getTimeSpanId());
    timeSpanUp.setAdjustMethod(timeSpanUpRequest.getCalculationMethod());
    timeSpanUp.setRum(timeSpanUpRequest.getCalculateUnit());
    timeSpanUpRepository.save(timeSpanUp);

    RefValue refValue = refValueRepository.findById(Math.toIntExact(timeSpanUp.getRate()))
        .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
    refValue.setValueString(timeSpanUpRequest.getPrice());
    refValueRepository.save(refValue);

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;

  }

  // @NotNull
  // private RefValue getRefValue(TimeSpanUpRequest timeSpanUpRequest) {

  /// /
  /// / RefValue refValue =
  /// refValueRepository.selectRefValueByValueString222(timeSpanUpRequest.getPrice()).orElseThrow(()
  /// -> new ValidationHandler(EnumRC.DATA_NOT_FOUND.getMessage()));
  /// / refValue.setPriceId(Long.valueOf(timeSpanUpRequest.getPriceId()));
  /// / Integer ratePlanId = refValue.getRatePlanId();
  /// / Long inputRatePlanId = timeSpanUpRequest.getRatePlanId().longValue();
  /// /
  /// / if ((refValue.getRatePlanId() == null || refValue.getRatePlanId() == -1L)
  /// && inputRatePlanId != null && inputRatePlanId != -1L) {
  /// / refValue.setRatePlanId(timeSpanUpRequest.getRatePlanId());
  /// / }
  /// /
  // RefValue refValue = new RefValue();
  // Integer ratePlanId = refValue.getRatePlanId();
  // Integer inputRatePlanId = timeSpanUpRequest.getRatePlanId();
  //
  // if ((ratePlanId == null || ratePlanId == -1) && inputRatePlanId != null &&
  // inputRatePlanId != -1) {
  // refValue.setRatePlanId(inputRatePlanId);
  // }
  //
  // refValue.setPriceId(Long.valueOf(timeSpanUpRequest.getPriceId()));
  // refValue.setOfferVerId(timeSpanUpRequest.getOfferVerId());
  // refValue.setValueString(timeSpanUpRequest.getPrice());
  // refValue.setSpId(0);
  // refValue.setRatePrecision('0');
  // refValue.setRefValueType('1');
  // refValue.setState(timeSpanUpRequest.getState());
  // refValue.setCreatedDate(LocalDate.now());
  //
  // return refValue;
  // }
  public BaseResponseDto getScriptTemplete() {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<Object[]> responseList = scriptTempletRepository.getScriptTemplet();

    List<ScriptTempletResponse> scriptTempletResponseList = responseList.stream().map(obj -> {
      ScriptTempletResponse response = new ScriptTempletResponse();
      response.setScriptTempletId(((Integer) obj[0]).intValue());
      response.setScriptTempletName((String) obj[1]);
      return response;
    }).toList();

    baseResponseDto.setData(scriptTempletResponseList);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;
  }

  public ResponseEntity<CustomeResponse> getAccumulationTypeList() {
    var data = ratableResourceRepository.findAllAccumulationType()
        .stream()
        .map(accumulationTypeMapper::toDto)
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> doGetAccumulationList(Integer ratePlanId, Long priceId, Integer mappingId) {

    var data = acmRepository.getAcmList(ratePlanId, priceId, mappingId).stream()
        .map(acmListMapper::toDto)
        .toList();

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> doGetAcmTimeSpanList(Integer priceVerId, Integer spId) {
    var data = acmTimeSpanRepository.qryAcmTimeSpan(priceVerId, spId)
        .stream()
        .map(qryAcmTimeSpanMapper::toDto)
        .toList();

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> doGetAcmRefList(Integer priceVerId, Integer spId) {
    var data = acmRefRepository.QryAcmRef(priceVerId, spId)
        .stream()
        .map(qryAcmRefMapper::toDto)
        .toList();

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> doGetAcmExpressionList(Integer priceVerId, Integer spId) {
    var data = acmRuleRepository.qryAcmRule(priceVerId, spId)
        .map(qryAcmRuleMapper::toDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> doGetTimeUnitList(String notExact) {
    List<QryTimeUnitListProjection> data = timeUnitRepository.qryTimeUnitList(notExact);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> qryEventBenefit(Integer priceId, Integer ratePlanId, Integer priceVerId,
      Integer mappingId, Integer spId) {
    var data = eventBenefitRepository.qryEventBenefit(priceId, ratePlanId, priceVerId, mappingId, spId)
        .stream()
        .map(qryEventBenefitMapper::toDto)
        .toList();

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> qryPriceVer(Integer priceVerId, Integer ratePlanId, Integer mappingId,
      Integer spId) {
    var data = priceVerRepository.qryPriceVer(priceVerId, ratePlanId, mappingId, spId)
        .stream()
        .map(qryPriceVerMapper::toDto)
        .toList();

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

}