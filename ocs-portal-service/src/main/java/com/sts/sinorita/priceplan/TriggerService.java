package com.sts.sinorita.priceplan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.ConstantString;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.constant.SortDirection;
import com.sts.sinorita.dto.request.*;
import com.sts.sinorita.dto.request.priceplan.treshold.*;
import com.sts.sinorita.dto.request.priceplan.trigger.*;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.BwfStepResponse;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.trigger.*;
import com.sts.sinorita.entity.*;
import com.sts.sinorita.entity.embeddable.AcmSubEventId;
import com.sts.sinorita.entity.embeddable.BalAdviceId;
import com.sts.sinorita.entity.embeddable.BalSubsEventId;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.mapper.pricePlan.trigger.*;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.BalAdviceProjection;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.BalBenefitProjection;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.BalanceTresholdProjection;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.DynReAttrProjection;
import com.sts.sinorita.projection.pricePlan.trigger.*;
import com.sts.sinorita.projection.trigger.*;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.utils.GeneratorXml;
import com.sts.sinorita.utils.GsonCustom;
import com.sts.sinorita.utils.Hellper;
import com.sts.sinorita.utils.LoggerPortal;
import com.sts.sinorita.validation.ValidationHandler;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
public class TriggerService {
  @Autowired
  private GsonCustom gson;
  @Autowired
  private LoggerPortal logger;
  @Autowired
  private AcmTriggerRepository acmTriggerRepository;
  @Autowired
  private AcmThresholdRepository acmThresholdRepository;
  @Autowired
  private AcmBillShockRuleRepository acmBillShockRuleRepository;
  @Autowired
  private AcmPcrfRepository acmPcrfRepository;
  @Autowired
  private PeriodRepository periodRepository;
  @Autowired
  private SubBalTypeRepository subBalTypeRepository;
  @Autowired
  private AcmBenefitRepository acmBenefitRepository;
  @Autowired
  private AcmAdviceRepository acmAdviceRepository;
  @Autowired
  private SubsEventRepository subsEventRepository;
  @Autowired
  private PricePlanRepository pricePlanRepository;
  @Autowired
  private BalTriggerRepository balTriggerRepository;
  @Autowired
  private AdvanceRuleTriggerRepository advanceRuleTriggerRepository;
  @Autowired
  private GeneratorXml generatorXml;
  @Autowired
  private ScriptTempletRepository scriptTempletRepository;
  @Autowired
  private Hellper hellper;
  @Autowired
  private TriggerTypeRepository triggerTypeRepository;
  @Autowired
  private BalanceTresholdRepository balanceTresholdRepository;
  @Autowired
  private SubBalTypeLimitRepository subBalTypeLimitRepository;
  @Autowired
  private BalAdviceRepository balAdviceRepository;
  @Autowired
  private BalSubEventRepository balSubEventRepository;
  @Autowired
  private MessageService messageService;
  @Autowired
  private BalPcrfRepository balPcrfRepository;
  @Autowired
  private BwfStepRepository bwfStepRepository;
  @Autowired
  private BwfWorkFlowRepository bwfWorkFlowRepository;
  @Autowired
  private BwfNodeRepository bwfNodeRepository;
  @Autowired
  private BwfActionRepository bwfActionRepository;
  @Autowired
  private BwfSysActionRepository bwfSysActionRepository;
  @Autowired
  private BwfCondGroupRepository bwfConGroupRepository;
  @Autowired
  private BwfCondRepository bwfCondRepository;
  @Autowired
  private SortOperatorRepository sortOperatorRepository;
  @Autowired
  private ZoneMapRepository zoneMapRepository;
  @Autowired
  private ReAttrRepository reAttrRepository;
  @Autowired
  private BwfMapper bwfMapper;
  @Autowired
  private AcmSubsEventRepository acmSubsEventRepository;
  @Autowired
  private BwfCondGroupRepository bwfCondGroupRepository;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private AcmTriggerMapper acmTriggerMapper;
  @Autowired
  private BalTriggerMapper balTriggerMapper;
  @Autowired
  private QryAcmThresholdMapper qryAcmThresholdMapper;
  @Autowired
  private SubBalTypeLimitMapper subBalTypeLimitMapper;
  @Autowired
  private BalSubsEventMapper balSubsEventMapper;
  @Autowired
  private QryAcmSubsEventMapper qryAcmSubsEventMapper;
  @Autowired
  private BalanceBenefitRepository balanceBenefitRepository;
  @Autowired
  private NotifyParamsRepository notifyParamsRepository;
  @Autowired
  private NotifyParamsMapper notifyParamsMapper;
  @Autowired
  private AdviceTypeRepository adviceTypeRepository;
  
  private Integer seq;

  public ResponseEntity<CustomeResponse> getAcmTriggerByOfferVerId (Integer offerVerId, String order_field, SortDirection order_direction, Integer paging, Integer size) {
    int page = Math.max(paging - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(order_direction.toString()), order_field);
    Pageable pageable = PageRequest.of(page, size, sort);

    var data = acmTriggerRepository.findAcmTriggerByOfferVerId(offerVerId, pageable);
    var result = data.getContent().stream().map(acmTriggerMapper::toDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }

  public BaseResponseDto createTriggerAccumulation (AccumulationTriggerDto accumulationTriggerDto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    AcmTrigger acmTrigger = new AcmTrigger();
    acmTrigger.setState('A');

    acmTrigger.setTriggerType(accumulationTriggerDto.getTriggerMode());
    acmTrigger.setExpDate(accumulationTriggerDto.getExpiryDate());
    acmTrigger.setOfferVerId(accumulationTriggerDto.getOfferVerId());
    acmTrigger.setResourceId(accumulationTriggerDto.getAccumulationType());
    acmTrigger.setDestination(accumulationTriggerDto.getDestination());
    acmTrigger.setSpId(0);
    if (accumulationTriggerDto.getEffectiveDate() == null) {
      acmTrigger.setEffDate(accumulationTriggerDto.getEffectiveDate());
    } else {
      acmTrigger.setEffDate(LocalDateTime.now());
    }
    if (accumulationTriggerDto.getExpiryDate() != null) {
      acmTrigger.setExpDate(accumulationTriggerDto.getExpiryDate());
    }
    if (accumulationTriggerDto.getStateDate() == null) {
      acmTrigger.setStateDate(LocalDate.now());
    } else {
      acmTrigger.setStateDate(accumulationTriggerDto.getStateDate().toLocalDate());
    }
    acmTriggerRepository.save(acmTrigger);
//        acmTrigger.setRefValueId(0);
    getLoggerInfo("::: ACM TRIGGER successfully saved into database ::");
    baseResponseDto.setData(acmTrigger);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;
  }

  public BaseResponseDto updateAcmTrigger (Integer acmTriggerId, UpdateAcmTriggerDto dto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    AcmTrigger acmTrigger = acmTriggerRepository.findById(acmTriggerId).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    acmTrigger.setEffDate(dto.getEffectiveDate());
    acmTrigger.setExpDate(dto.getExpiryDate());
    acmTrigger.setResourceId(dto.getAccumulationType());
    acmTrigger.setTriggerType(dto.getTriggerType());
    acmTrigger.setDestination(dto.getDestination());
    acmTriggerRepository.save(acmTrigger);

    getLoggerInfo("::: ACM TRIGGER successfully updated ::");

    baseResponseDto.setData(acmTrigger);
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());

    return baseResponseDto;

  }

  public BaseResponseDto deleteAcmTrigger (Integer acmTriggerId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    List<AcmThreshold> list = acmThresholdRepository.findByTriggerId(acmTriggerId);
    if (list.isEmpty()) {
      acmTriggerRepository.deleteAcmTriggerById(acmTriggerId);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "is referenced by Threshold");
    }
    return baseResponseDto;
  }

  public ResponseEntity<CustomeResponse> createThressholdAcm (AcmThresholdTrigerDto acmThresholdTrigerDto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    if (acmThresholdRepository.doFindByTriggerIdAndValue(acmThresholdTrigerDto.getTriggerId(), acmThresholdTrigerDto.getThreshold().longValue()).isPresent()) {
      baseResponseDto.setCode(EnumRC.ALREADY_EXIST.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage("AcmThreshold Trigger ID : " + acmThresholdTrigerDto.getTriggerId() + " And value : " + acmThresholdTrigerDto.getThreshold() + EnumRC.ALREADY_EXIST.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomeResponse(400, baseResponseDto.getMessage(), null));
    } else {
      AcmThreshold acmThresshold = new AcmThreshold();
      acmThresshold.setReAttr(acmThresholdTrigerDto.getFeature());
      acmThresshold.setTriggerId(acmThresholdTrigerDto.getTriggerId());
      if (acmThresholdTrigerDto.getTriggerBy().equalsIgnoreCase(ConstantString.THRESHOLD)) {
        acmThresshold.setValue(acmThresholdTrigerDto.getThreshold().longValue());
      } else {
        acmThresshold.setValue((long) 0);
        acmThresshold.setRatio(acmThresholdTrigerDto.getThreshold());
      }
      if (acmThresholdTrigerDto.getInterval() != null) {
        acmThresshold.setInterval(acmThresholdTrigerDto.getInterval().longValue());
      }
      acmThresshold.setSpId(0);
      if (acmThresholdTrigerDto.getThresholdAttribute() != null) {
        acmThresshold.setOnOffAttr(Integer.parseInt(acmThresholdTrigerDto.getThresholdAttribute()));
      }
      acmThresholdRepository.save(acmThresshold);
      getLoggerInfo("::: ACM AcmThreshold successfully saved into database ::");

//            baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
//            baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
      AcmPcrf acmPcrf = new AcmPcrf();
      //tunggu table acm_pcrf pastikan
      if (acmThresholdTrigerDto.getTriggerPCRF().equals('Y')) {
        acmPcrf = new AcmPcrf();
        acmPcrf.setSpId(acmThresshold.getSpId().longValue());
        acmPcrf.setAcmThresholdId(acmThresshold.getId().longValue());
        acmPcrf.setTriggerMode(acmThresholdTrigerDto.getTriggerMode());
        acmPcrfRepository.save(acmPcrf);
        getLoggerInfo("::: ACM AcmPcrf successfully saved into database ::");
      }
      ResponseThreshold responseThreshold = new ResponseThreshold(acmThresshold, acmPcrf);
      baseResponseDto.setData(responseThreshold);
      if (acmThresholdTrigerDto.getBillshockRuleId() != null) {
        addAcmBillShockRule(acmThresholdTrigerDto, acmThresshold);
      }

    }
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> getAcmThreshold (Integer triggerId, Integer acmThresholdId, Integer spId) {
//        var data = acmThresholdRepository.qryAcmThreshold(triggerId, acmThresholdId, spId)
//                .stream()
//                .map(qryAcmThresholdMapper::toDto)
//                .toList();
    List<QryAcmThresholdProjection> list = acmThresholdRepository.qryAcmThreshold(triggerId, acmThresholdId, spId);
    List<QryAcmThresholdResponseDto> data = qryAcmThresholdMapper.toListDto(list);
    List<QryAcmThresholdResponseDto> response = new ArrayList<>();
    for (QryAcmThresholdResponseDto qryAcmThresholdResponseDto : data) {
      if (qryAcmThresholdResponseDto.getRatio() != 0) {
        qryAcmThresholdResponseDto.setTriggerBy("ratio");
      } else {
        qryAcmThresholdResponseDto.setTriggerBy("threshold");
        qryAcmThresholdResponseDto.setRatio(null);
      }
      response.add(qryAcmThresholdResponseDto);
    }
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, response));
  }

  public BaseResponseDto updateThreshold (Integer acmThresholdId, UpdateThresholdDto dto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    AcmThreshold acmThreshold = acmThresholdRepository.findById(acmThresholdId).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    if (dto.getTriggerBy().equalsIgnoreCase(ConstantString.THRESHOLD)) {
      acmThreshold.setValue(dto.getValue());
      acmThreshold.setRatio(null);
    } else {
      acmThreshold.setValue((long) 0);
      acmThreshold.setRatio(dto.getRatio());
    }
    if (dto.getInterval() != null) {
      acmThreshold.setInterval(dto.getInterval().longValue());
    }

    acmThreshold.setOnOffAttr(dto.getOnOffAttr());
    acmThreshold.setReAttr(dto.getFeature());
    acmThresholdRepository.save(acmThreshold);
    getLoggerInfo("::: ACM AcmThreshold successfully saved into database ::");

    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());

    return baseResponseDto;
  }

  @Transactional
  public ResponseEntity<CustomeResponse> deleteAcmThreshold (Long acmThresholdId) {
    if (acmThresholdId == null) {
      throw new ValidationHandler("ACM_THRESHOLD_ID cannot be null");
    }

    if (acmAdviceRepository.isReferencedInAdvice(acmThresholdId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-PRD-01038"));
    }
    if (acmBenefitRepository.isReferencedInBenefit(acmThresholdId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-PRD-01039"));
    }
    if (acmSubsEventRepository.isReferencedInSubsEvent(acmThresholdId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-PRD-01040"));
    }

    boolean isNotEmpty = acmPcrfRepository.isNotEmpty(acmThresholdId);

    if (isNotEmpty) {
      acmPcrfRepository.delAcmPcrf(acmThresholdId);
    }
    acmBillShockRuleRepository.delAcmBillShockRule(acmThresholdId);
    acmThresholdRepository.deleteByThresholdId(acmThresholdId);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity<CustomeResponse> addAcmBenefit (TriggerBenefitDto dict) {
    Period period = new Period();
    period.setSpId(0);

    if (dict.getPeriodType() != null) {
      if (dict.getPeriodType().equalsIgnoreCase("absolute")) {
        period.setAbsEffDate(dict.getAbsoluteEffectiveDate());
        period.setAbsExpDate(dict.getAbsoluteExpiryDate());
      } else {
        if (dict.getEffUnit() != null) {
          period.setRelEffUnit(dict.getEffUnit().charAt(0));
        }
        if (dict.getExpUnit() != null) {
          period.setRelExpUnit(dict.getExpUnit().charAt(0));
        }
        if (dict.getRelativeEffectiveTime() != null) {
          period.setRelEffTime(LocalTime.parse(dict.getRelativeEffectiveTime()));
        }
        if (dict.getRelativeExpiryTime() != null) {
          period.setRelExpTime(LocalTime.parse(dict.getRelativeExpiryTime()));
        }
        if (dict.getOffsetOfEffectiveDate() != null) {
          period.setRelEffOffset(dict.getOffsetOfEffectiveDate());
        }
        if (null != dict.getDurationOfAvailability()) {
          //mohon d tanyakan apakah dari getDurationOfAvailability ?
          period.setRelExpOffset(dict.getDurationOfAvailability());
        }
      }
      periodRepository.save(period);
      log.info("save period success");
    }


    SubBalType subBalType = new SubBalType();
    subBalType.setPeriodId(period.getId());
    subBalType.setLimitSubs(dict.getSubscriberOnly());
    subBalType.setSpId(0);
    if (dict.getRelativePeriodUnit() != null) {
      log.debug("getRelativePeriodUnit :" + dict.getRelativePeriodUnit());
      subBalType.setPeriodRelUnit(dict.getRelativePeriodUnit().charAt(0)); //D/H/M
    }
    if (dict.getExtendRule() != null) {
      log.debug("getExtendRule :" + dict.getExtendRule());
      subBalType.setExtendRule(dict.getExtendRule().charAt(0));
    }
    if (dict.getMaximumDays() != null) {
      subBalType.setMaxDays(dict.getMaximumDays());
    }
    subBalType.setAcctResId(dict.getAccountBalanceType());
    if (dict.getCycleCeilLimit() != null) {
      subBalType.setCeilLimit(dict.getCycleCeilLimit().longValue());
    }
    if (dict.getDailyCeilLimit() != null) {
      subBalType.setDailyCeilLimit(dict.getDailyCeilLimit().longValue());
    }
    if (dict.getOffsetOfAbsoluteExpiry() != null) {
      subBalType.setAbsExpOffset(dict.getOffsetOfAbsoluteExpiry().longValue());
    }

    subBalTypeRepository.save(subBalType);
    log.info("save subBalType success");

    if (subBalType.getAcctResId() != null) {
      for (Integer acctItemTypeId : dict.getResultAccountItemType()) {
        SubBalTypeLimit subBalTypeLimit = new SubBalTypeLimit();
        SubBalTypeLimitId subBalTypeLimitId = new SubBalTypeLimitId();
        subBalTypeLimitId.setSubBalTypeId(subBalType.getId());
        subBalTypeLimitId.setAcctItemTypeId(acctItemTypeId);
        subBalTypeLimit.setId(subBalTypeLimitId);
        subBalTypeLimit.setSpId(0);
        subBalTypeLimitRepository.save(subBalTypeLimit);
      }
    }


    List<AcmBenefit> list = acmBenefitRepository.findAcmBenefitByTreshold(dict.getThresholdId(), subBalType.getId());
    if (list.isEmpty()) {
      AcmBenefit acmBenefit1 = new AcmBenefit();
      AcmBenefitId acmBenefitId = new AcmBenefitId();
      acmBenefitId.setAcmThresholdId(dict.getThresholdId());
      acmBenefitId.setSubBalTypeId(subBalType.getId());
      acmBenefit1.setId(acmBenefitId);
      acmBenefit1.setValue(dict.getBenefitValue().intValue());
      acmBenefit1.setSpId(0);
      acmBenefitRepository.save(acmBenefit1);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomeResponse(400, MessageService.getMessage("S-PRD-01045"), null));
    }

    return ResponseEntity.status(HttpStatus.CREATED).
      body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> listAcmBenefit (Integer thresholdId, Integer subBalTypeId, Integer spId) {
//        var data = acmBenefitRepository.qryAcmBenefitList(thresholdId,subBalTypeId, spId)
//                .stream()
//                .map(qryAcmBenefitMapper::toDto)
//                .collect(Collectors.toList());

    List<QryAcmBenefitProjection> list = acmBenefitRepository.qryAcmBenefitList(thresholdId, subBalTypeId, spId);


    return ResponseEntity.status(HttpStatus.CREATED).
      body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, list));
  }

  public ResponseEntity<CustomeResponse> qrySubBalTypeLimit (Integer subBalTypeId, Integer spId) {
    var data = subBalTypeLimitRepository.qrySubBalTypeLimit(subBalTypeId, spId)
      .stream()
      .map(subBalTypeLimitMapper::toQrySubBalTypeLimitResponseDto)
      .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.CREATED).
      body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public BaseResponseDto updateAcmBenefit (Integer subBalTypeId, TriggerBenefitDto dto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    Optional<GetSubBalTypeIdPeriodIdProjection> ids = subBalTypeRepository.findSubBalTypeIdAndPeriodId(subBalTypeId);

    SubBalType subBalType = subBalTypeRepository.findById(subBalTypeId).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
    subBalType.setAcctResId(dto.getAccountBalanceType());

    if (dto.getSubscriberOnly() != null) {
      subBalType.setLimitSubs(dto.getSubscriberOnly());
    }

    if (dto.getExtendRule() != null) {
      log.debug("getExtendRule :" + dto.getExtendRule());
      subBalType.setExtendRule(dto.getExtendRule().charAt(0));
    }
    if (dto.getMaximumDays() != null) {
      subBalType.setMaxDays(dto.getMaximumDays());
    }
    subBalType.setAcctResId(dto.getAccountBalanceType());
    if (dto.getCycleCeilLimit() != null) {
      subBalType.setCeilLimit(dto.getCycleCeilLimit().longValue());
    }
    if (dto.getDailyCeilLimit() != null) {
      subBalType.setDailyCeilLimit(dto.getDailyCeilLimit().longValue());
    }
    if (dto.getPeriodType().equalsIgnoreCase("absolute")) {
      if (dto.getOffsetOfAbsoluteExpiry() != null) {
        subBalType.setAbsExpOffset(dto.getOffsetOfAbsoluteExpiry().longValue());
        subBalType.setPeriodRelUnit(null);
      }
    } else {
      if (dto.getRelativePeriodUnit() != null) {
        log.debug("getRelativePeriodUnit :" + dto.getRelativePeriodUnit());
        subBalType.setPeriodRelUnit(dto.getRelativePeriodUnit().charAt(0)); //D/H/M
      }
    }

    AcmBenefit acmBenefit = acmBenefitRepository.findBySubBalTypeId(subBalTypeId);
    acmBenefit.setValue(dto.getBenefitValue().intValue());

    Period period = periodRepository.findById(ids.get().getPeriodId()).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

    if (dto.getAbsoluteEffectiveDate() == null && dto.getAbsoluteExpiryDate() == null) {


      if (dto.getDayOffset() != null) {
        period.setDayOffset(dto.getDayOffset());
      }
    } else if (dto.getAbsoluteEffectiveDate() != null && dto.getAbsoluteExpiryDate() != null) {
      period.setAbsEffDate(dto.getAbsoluteEffectiveDate());
      period.setAbsExpDate(dto.getAbsoluteExpiryDate());
    }

    if (dto.getPeriodType() != null) {
      if (dto.getPeriodType().equalsIgnoreCase("absolute")) {

        if (dto.getAbsoluteEffectiveDate() != null) {
          period.setAbsEffDate(dto.getAbsoluteEffectiveDate());
        }
        if (dto.getAbsoluteExpiryDate() != null) {
          period.setAbsExpDate(dto.getAbsoluteExpiryDate());
        }
        if (null != dto.getOffsetOfAbsoluteExpiry()) {
          period.setAbsExpDate(LocalDate.ofEpochDay(dto.getOffsetOfAbsoluteExpiry()));
        }
        period.setRelEffTime(null);
        period.setRelExpTime(null);

        period.setRelEffOffset(null);
        period.setRelExpOffset(null);
        period.setRelEffUnit(null);
        period.setRelExpUnit(null);
        period.setRelEffTime(null);
        period.setRelExpTime(null);

      } else {
        period.setAbsEffDate(null);
        period.setAbsExpDate(null);

        if (dto.getOffsetOfEffectiveDate() != null) {
          period.setRelEffOffset(dto.getOffsetOfEffectiveDate());
        }
        if (null != dto.getDurationOfAvailability()) {
          //mohon d tanyakan apakah dari getDurationOfAvailability ?
          period.setRelExpOffset(dto.getDurationOfAvailability());
        }
        if (dto.getEffUnit() != null) {
          period.setRelEffUnit(dto.getEffUnit().charAt(0));
        }
        if (dto.getExpUnit() != null) {
          period.setRelExpUnit(dto.getExpUnit().charAt(0));
        }
        if (dto.getRelativeEffectiveTime() != null) {
          period.setRelEffTime(LocalTime.parse(dto.getRelativeEffectiveTime()));
        }
        if (dto.getRelativeExpiryTime() != null) {
          period.setRelExpTime(LocalTime.parse(dto.getRelativeExpiryTime()));
        }
      }
    }
    subBalTypeRepository.save(subBalType);
    periodRepository.save(period);

    if (subBalType.getAcctResId() != null) {
      for (Integer acctItemTypeId : dto.getResultAccountItemType()) {
        subBalTypeLimitRepository.deleteBySubBalTypeId(subBalType.getId());

        SubBalTypeLimit subBalTypeLimit = new SubBalTypeLimit();
        SubBalTypeLimitId subBalTypeLimitId = new SubBalTypeLimitId();
        subBalTypeLimitId.setSubBalTypeId(subBalType.getId());
        subBalTypeLimitId.setAcctItemTypeId(acctItemTypeId);
        subBalTypeLimit.setId(subBalTypeLimitId);
        subBalTypeLimit.setSpId(0);
        subBalTypeLimitRepository.save(subBalTypeLimit);
      }
    }

//        if (dto.getResultAccountItemType() != null && !dto.getResultAccountItemType().isEmpty()) {
//
//            // Ambil semua limit berdasarkan subBalTypeId
//            List<SubBalTypeLimit> subBalTypeLimits = subBalTypeLimitRepository.findBySubBalTypeIds(subBalTypeId);
//
//            // Loop dan update jika acctItemTypeId cocok
//            if (!dto.getResultAccountItemType().isEmpty() && dto.getResultAccountItemType() != null) {
//                // Update field yang ingin diubah
//                subBalTypeLimitRepository.deleteBySubBalTypeId(subBalTypeId);
//                for (Integer acctItemTypeId : dto.getResultAccountItemType()) {
//                    SubBalTypeLimit subBalTypeLimitUpdate = new SubBalTypeLimit();
//                    SubBalTypeLimitId subBalTypeLimitId = new SubBalTypeLimitId();
//                    subBalTypeLimitId.setSubBalTypeId(subBalTypeId);
//                    subBalTypeLimitId.setAcctItemTypeId(acctItemTypeId);
//                    subBalTypeLimitUpdate.setId(subBalTypeLimitId);
//                    subBalTypeLimitUpdate.setSpId(0);
//                    subBalTypeLimitRepository.save(subBalTypeLimitUpdate);
//                }
//            }
//
//        }


    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;

  }

  private void addAcmBillShockRule (AcmThresholdTrigerDto acmThresholdTrigerDto, AcmThreshold acmThresshold) {
    //butuh BillshockRule.
    AcmBillShockRule acmBillShockRule = new AcmBillShockRule();
    acmBillShockRule.setAcmThresholdId(acmThresshold.getId().longValue());
    acmBillShockRule.setBillShockRuleId(acmThresholdTrigerDto.getBillshockRuleId().longValue());
    int spId = 0;
    acmBillShockRule.setSpId((long) spId);
    if (ObjectUtils.isNotEmpty(acmThresholdTrigerDto.getBillshockRuleId())) {
      acmBillShockRuleRepository.save(acmBillShockRule);
      getLoggerInfo("::: AcmBillShockRule successfully saved into database ::");
    }
  }

  public BaseResponseDto doAddSubEventNotif (TriggerNotificationDto triggerNotificationDto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());

    boolean acmAdviceOptional = acmAdviceRepository.existsByDynamicConditions(
      triggerNotificationDto.getThresholdId(),
      Integer.valueOf(triggerNotificationDto.getNotifType()),
      null,
      triggerNotificationDto.getNotifParamId()
    );

    if (acmAdviceOptional) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-PRD-04051"));
    }

    AcmAdvice acmAdvice = new AcmAdvice();
    acmAdvice.setAcmThresholdId(triggerNotificationDto.getThresholdId());
    if (triggerNotificationDto.getNotifType() != null) {
      acmAdvice.setAdviceType(Integer.valueOf(triggerNotificationDto.getNotifType()));
    }
    if (triggerNotificationDto.getNotifParamId() != null) {
      acmAdvice.setNotifyParamsId(triggerNotificationDto.getNotifParamId());
    }
    if (triggerNotificationDto.getTriggerMode() != null) {
      acmAdvice.setTriggerMode(triggerNotificationDto.getTriggerMode());
    }
    if (triggerNotificationDto.getSpId() != null) {
      acmAdvice.setSpId(triggerNotificationDto.getSpId());
    } else {
      acmAdvice.setSpId(0);
    }
//        acmAdviceRepository.save(acmAdvice);
    acmAdviceRepository.insertAdvice(Optional.ofNullable(acmAdvice.getAcmThresholdId()).orElse(null),
      Optional.ofNullable(acmAdvice.getAdviceType()).orElse(null)
      , Optional.ofNullable(acmAdvice.getAdviceEventId()).orElse(null),
      Optional.ofNullable(acmAdvice.getSpId()).orElse(null)
      , Optional.ofNullable(acmAdvice.getTriggerMode()).orElse(null),
      Optional.ofNullable(acmAdvice.getNotifyParamsId()).orElse(null)
    );

    return baseResponseDto;

  }

  public BaseResponseDto doAddSubEventTrigger (SubEventTriggerDto subEventTriggerDto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    SubsEvent subsEvent = new SubsEvent();
    subsEvent.setSubsEventId(subEventTriggerDto.getSubEventId().longValue());
    subsEvent.setEventName(subEventTriggerDto.getSubsEventName());
    subsEvent.setComments(subEventTriggerDto.getSubsEventName());
    subsEvent.setEventCode(subEventTriggerDto.getPriceplanCode());
    Integer priority = pricePlanRepository.selectMaxPriority();
    subsEvent.setPriority(priority);
    subsEvent.setEventCatg(subEventTriggerDto.getEventCatG());//? I/F/B/H/D/F/K/H
    subsEvent.setObjProdState(subEventTriggerDto.getObjProdState());//? B/A/G
    subsEventRepository.save(subsEvent);

    return baseResponseDto;
  }

  public BaseResponseDto doAddBallTrigger (BallTriggerDto ballTriggerDto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    LocalDate localDate = LocalDate.now();
//        if (ballTriggerDto.getStateDate() == null) {
//            ballTriggerDto.setStateDate(localDate);
//        }

    if (ballTriggerDto.getEffDate() == null) {
      ballTriggerDto.setEffDate(localDate);
    }

    ballTriggerDto.setState("A");

    String acctResIdList = ballTriggerDto.getAcctResIdAsString();  //ballTriggerDto.getBalanceType().stream().map(BallTriggerDto.BalanceType::getAcctResId).collect(Collectors.joining(","));

    BalTrigger balTrigger = new BalTrigger();

    balTrigger.setTriggerType(ballTriggerDto.getTriggerMode().charAt(0));
    balTrigger.setAcctResIdList(acctResIdList);
    balTrigger.setEffDate(ballTriggerDto.getEffDate());
    balTrigger.setSpId(0);
    balTrigger.setDestination(ballTriggerDto.getDestination() == null ? null : ballTriggerDto.getDestination().charAt(0));
    balTrigger.setOfferVerId(ballTriggerDto.getOfferVerId());
    if (ballTriggerDto.getExpDate() != null) {
      balTrigger.setExpDate(ballTriggerDto.getExpDate());
    }
    balTrigger.setIsLimitBalance(ballTriggerDto.getIsLimit().charAt(0));
    balTrigger.setStateDate(LocalDate.now());
    balTrigger.setState(ballTriggerDto.getState().charAt(0));

    balTriggerRepository.save(balTrigger);

    EnumRC enumRC = EnumRC.getByMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(String.valueOf(EnumRC.SUCCESS.getRESPONSE_CODE()));
    baseResponseDto.setMessage(enumRC.getMessage());
    return baseResponseDto;
  }

  public BaseResponseDto newTriggerRule (AdvanceTriggerRuleDto advanceTriggerRuleDto) {
    BaseResponseDto response = new BaseResponseDto();
    LocalDate currentDatetime = LocalDate.now();

    try {
      TriggerRule triggerRule = new TriggerRule();
      BigDecimal triggerId = advanceRuleTriggerRepository.getSequenceTriggerRule();
      triggerRule.setTriggerId(triggerId.intValue());
      triggerRule.setSeq(triggerId.intValue());
      triggerRule.setOfferVerId(advanceTriggerRuleDto.getOfferVerId());
      triggerRule.setEffDate(advanceTriggerRuleDto.getEffDate());
      if (advanceTriggerRuleDto.getExpDate() != null) {
        triggerRule.setExpDate(advanceTriggerRuleDto.getExpDate());
      }
      triggerRule.setState(ConstantString.ACTIVE);
      triggerRule.setStateDate(currentDatetime);
      triggerRule.setSpId(0);

      byte[] scriptPage;
      // Set data tambahan dari triggerRule BO
//            if (!advanceTriggerRuleDto.getTriggerRule().getRuleScript().isEmpty()) {
//                scriptPage = generatorXml.getXmlScriptPage(advanceTriggerRuleDto.getTriggerRule().getScriptTempletId(), advanceTriggerRuleDto.getTriggerRule().getScriptPage());
//                triggerRule.setScriptPage(scriptPage);
//                triggerRule.setRuleScript(generatorXml.getNewScriptRule(advanceTriggerRuleDto.getTriggerRule().getRuleScript(), scriptPage));
//                triggerRule.setScriptTempletId(advanceTriggerRuleDto.getTriggerRule().getScriptTempletId());
//            } else {
//                triggerRule.setRuleScript(advanceTriggerRuleDto.getTriggerRule().getRuleScript());
//                triggerRule.setScriptPage(new byte[0]);
//            }

      if (advanceTriggerRuleDto.getTriggerRule() != null) {
        if (advanceTriggerRuleDto.getTriggerRule().getScriptPage() == null) {
          triggerRule.setRuleScript(advanceTriggerRuleDto.getTriggerRule().getRuleScript());
          triggerRule.setScriptPage(new byte[0]);
        } else {
          scriptPage = generatorXml.getXmlScriptPage(advanceTriggerRuleDto.getTriggerRule().getScriptTempletId(), advanceTriggerRuleDto.getTriggerRule().getScriptPage());
          triggerRule.setScriptPage(scriptPage);
          triggerRule.setRuleScript(generatorXml.getNewScriptRule(advanceTriggerRuleDto.getTriggerRule().getRuleScript(), scriptPage));
          triggerRule.setScriptTempletId(advanceTriggerRuleDto.getTriggerRule().getScriptTempletId());
        }
      }

      // Insert ke database
      advanceRuleTriggerRepository.save(triggerRule);
      ModelMapper modelMapper = new ModelMapper();
      TriggerRuleResponseDto dto = modelMapper.map(triggerRule, TriggerRuleResponseDto.class);

      if (triggerRule.getScriptPage() != null) {
        dto.setScriptPage(hellper.getStringFromByte(triggerRule.getScriptPage()));
      }

      getLoggerInfo("::Trigger Rule :: " + gson.doJsonString(dto));
      getLoggerInfo(":: TriggerRule successfully saved into database ::");
      // Set response sukses
      response.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      response.setMessage("Trigger rule created successfully.");
      response.setData(dto);

    } catch (Exception e) {
      response.setCode(EnumRC.CREATE_FAILED.getRESPONSE_CODE().toString());
      response.setMessage("Error while creating trigger rule: " + e.getMessage());
    }
    return response;
  }

  @Transactional
  public ResponseEntity<CustomeResponse> deleteAcmBenefit (Integer tresholdId, Integer subBalTypeId) {
    acmBenefitRepository.deleteAcmBenefit(tresholdId, subBalTypeId);
    subBalTypeLimitRepository.deleteBySubBalTypeId(subBalTypeId);
    delSubBalType(subBalTypeId);
    logger.loggerInfo(TriggerService.class, "success delete acm benefit");
    return ResponseEntity.ok(new CustomeResponse(HttpStatus.OK.value(), EnumRC.SUCCESS.getMessage(), null));
  }

  private void delSubBalType (Integer subBalTypeId) {
    if (subBalTypeRepository.isReferencedInAcmBenefit(subBalTypeId).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "S-PRD-01033");
    }

    if (subBalTypeRepository.isReferencedInBalBenefit(subBalTypeId).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "S-PRD-01034");
    }

    if (subBalTypeRepository.isReferencedInEventBenefit(subBalTypeId).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "S-PRD-01035");
    }

    if (subBalTypeRepository.isReferencedInSubBalTypeLimit(subBalTypeId).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "S-PRD-01036");
    }

    subBalTypeRepository.deleteById(subBalTypeId);
  }

  public BaseResponseDto editTriggerRule (Integer triggerId, Integer seq, AdvanceTriggerRuleDto advanceTriggerRuleDto) {
    BaseResponseDto response = new BaseResponseDto();
    LocalDate currentDatetime = LocalDate.now();

    try {
      TriggerRule triggerRule = advanceRuleTriggerRepository.findOneByTriggerIdAndSeq(triggerId, seq).orElseThrow(() -> new ValidationHandler(MessageService.getMessage("S-ACT-00464")));
      triggerRule.setOfferVerId(advanceTriggerRuleDto.getOfferVerId());
      triggerRule.setEffDate(advanceTriggerRuleDto.getEffDate());
      if (advanceTriggerRuleDto.getExpDate() != null) {
        triggerRule.setExpDate(advanceTriggerRuleDto.getExpDate());
      }
      triggerRule.setState(ConstantString.ACTIVE);
      triggerRule.setStateDate(currentDatetime);
      triggerRule.setSpId(0);

      byte[] scriptPage;
      // Set data tambahan dari triggerRule BO
//            if (!advanceTriggerRuleDto.getTriggerRule().getRuleScript().isEmpty()) {
//                scriptPage = generatorXml.getXmlScriptPage(advanceTriggerRuleDto.getTriggerRule().getScriptTempletId(), advanceTriggerRuleDto.getTriggerRule().getScriptPage());
//                triggerRule.setScriptPage(scriptPage);
//                triggerRule.setRuleScript(generatorXml.getNewScriptRule(advanceTriggerRuleDto.getTriggerRule().getRuleScript(), scriptPage));
//                triggerRule.setScriptTempletId(advanceTriggerRuleDto.getTriggerRule().getScriptTempletId());
//            }

      if (advanceTriggerRuleDto.getTriggerRule().getScriptPage() == null) {
        triggerRule.setRuleScript(advanceTriggerRuleDto.getTriggerRule().getRuleScript());
        triggerRule.setScriptPage(new byte[0]);
        triggerRule.setScriptTempletId(null);

      } else {
        scriptPage = generatorXml.getXmlScriptPage(advanceTriggerRuleDto.getTriggerRule().getScriptTempletId(), advanceTriggerRuleDto.getTriggerRule().getScriptPage());
        triggerRule.setScriptPage(scriptPage);
        triggerRule.setRuleScript(generatorXml.getNewScriptRule(advanceTriggerRuleDto.getTriggerRule().getRuleScript(), scriptPage));
        triggerRule.setScriptTempletId(advanceTriggerRuleDto.getTriggerRule().getScriptTempletId());
      }


      // Insert ke database
      advanceRuleTriggerRepository.save(triggerRule);
      ModelMapper modelMapper = new ModelMapper();
      TriggerRuleResponseDto dto = modelMapper.map(triggerRule, TriggerRuleResponseDto.class);

      if (triggerRule.getScriptPage() != null) {
        dto.setScriptPage(hellper.getStringFromByte(triggerRule.getScriptPage()));
      }

      getLoggerInfo("::Trigger Rule :: " + gson.doJsonString(dto));
      getLoggerInfo(":: TriggerRule successfully saved into database ::");

      // Set response sukses
      response.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      response.setMessage("Trigger rule created successfully.");
      response.setData(dto);

    } catch (Exception e) {
      response.setCode(EnumRC.CREATE_FAILED.getRESPONSE_CODE().toString());
      response.setMessage("Error while creating trigger rule: " + e.getMessage());
    }
    return response;
  }

  public void deleteTriggerRule (Integer triggerId, Integer seq) {
    advanceRuleTriggerRepository.deleteByTriggerIdAndSeq(triggerId, seq);
    logger.loggerInfo(TriggerService.class, "Delete Trigger by Seq SUCCESS");
  }

  public BaseResponseDto getTriggerRuleList (String filter, String order_field, SortDirection order_direction, Integer offerVerId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    try {
      Character state = null;
      Integer spId = null;

      if (filter != null && !filter.isBlank()) {
        Map<String, Object> jsonMap = objectMapper.readValue(filter, new TypeReference<>() {
        });
        if (jsonMap.get("state") != null) {
          state = jsonMap.get("state").toString().charAt(0);
        }
        if (jsonMap.get("spId") != null) {
          spId = (Integer) jsonMap.get("spId");
        }
      }

      List<TriggerRuleResponseDto> triggerRuleList = advanceRuleTriggerRepository.findTriggerRules(offerVerId, order_field, order_direction.toString(), state, spId);

      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
      baseResponseDto.setData(triggerRuleList);
      return baseResponseDto;

    } catch (Exception e) {
      baseResponseDto.setCode(EnumRC.BAD_REQUEST.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage("Invalid JSON format: " + e.getMessage());
      return baseResponseDto;
    }
  }

  public ResponseEntity<CustomeResponse> getBalTriggerList (Integer offerVerId, String order_field, SortDirection order_direction, Integer paging, Integer size) {

    int page = Math.max(paging - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(order_direction.toString()), order_field);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<BalTriggerProjection> data = balTriggerRepository.getBalTriggerByOfferVerId(null, offerVerId, pageable);

    var result = data.getContent().stream().map(balTriggerMapper::toDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }

  public BaseResponseDto getTriggerTypeList () {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<GetTriggerTypeProjection> triggerTypeList = triggerTypeRepository.getTriggerTypeList();

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setData(triggerTypeList);
    return baseResponseDto;
  }

  private void getLoggerInfo (String msg) {
    logger.loggerInfo(TriggerService.class, msg);
  }

  public BaseResponseDto getListScriptTempletById (Integer id) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    Optional<ScriptTemplet> scriptTemplet = scriptTempletRepository.findScriptTempletByScriptTempletId(id);
    ScriptTempletListDto scriptTemplatListDto = new ScriptTempletListDto();
    scriptTemplatListDto.setTemplateId(scriptTemplet.get().getId());
    scriptTemplatListDto.setScriptTempletName(scriptTemplet.get().getScriptTempletName());
    scriptTemplatListDto.setTempletContent(hellper.getStringFromByte(scriptTemplet.get().getTempletContent()));
    scriptTemplatListDto.setTempletTypeScript(hellper.getStringFromByte(scriptTemplet.get().getTempletParam()));
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setData(scriptTemplatListDto);
    return baseResponseDto;
  }

  public BaseResponseDto doUpdateBalanceTrigger (Integer triggerId, BallTriggerDto triggerBalanceDto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    BalTrigger balTrigger = balTriggerRepository.findById(triggerId).orElseThrow(() -> new ValidationHandler("BalTrigger not found"));
    String acctResIdList = triggerBalanceDto.getAcctResIdAsString();

    balTrigger.setStateDate(LocalDate.now());
    if (triggerBalanceDto.getEffDate() != null) {
      balTrigger.setEffDate(triggerBalanceDto.getEffDate());
    }

    balTrigger.setTriggerType(triggerBalanceDto.getTriggerMode().charAt(0));
    balTrigger.setAcctResIdList(acctResIdList);
    balTrigger.setEffDate(triggerBalanceDto.getEffDate());
    if (triggerBalanceDto.getExpDate() != null) {
      balTrigger.setExpDate(triggerBalanceDto.getExpDate());
    }

    balTrigger.setIsLimitBalance(triggerBalanceDto.getIsLimit().charAt(0));
    balTrigger.setState(triggerBalanceDto.getState().charAt(0));
    balTrigger.setDestination(triggerBalanceDto.getDestination() == null ? null : triggerBalanceDto.getDestination().charAt(0));
    balTriggerRepository.save(balTrigger);

    EnumRC enumRC = EnumRC.getByMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setCode(String.valueOf(EnumRC.SUCCESS.getRESPONSE_CODE()));
    baseResponseDto.setMessage(enumRC.getMessage());
    return baseResponseDto;

  }

  /**
   * Balance Treshold =======================
   */
  public ResponseEntity<CustomeResponse> addBalTreshold (BalThresholdRequestDto balThresholdRequestDto) {
    BalThreshold balThreshold = new BalThreshold();
    if (balThresholdRequestDto.getTriggerId() != null) {
      balThreshold.setTriggerId(balThresholdRequestDto.getTriggerId());
    }

    if (balThresholdRequestDto.getInterval() != null) {
      balThreshold.setInterval(balThresholdRequestDto.getInterval().longValue());
    }
    if (balThresholdRequestDto.getTriggerBy().equalsIgnoreCase("Threshold")) {
      if (balThresholdRequestDto.getValue() != null) {
        balThreshold.setValue(balThresholdRequestDto.getValue().longValue());
        balThreshold.setRatio(null);
      }
    } else {
      balThreshold.setValue(0L);
      if (balThresholdRequestDto.getValue() != null) {
        balThreshold.setRatio(balThresholdRequestDto.getValue());
      }
    }

    if (balThresholdRequestDto.getReAttr() != null) {
      balThreshold.setReAttr(balThresholdRequestDto.getReAttr());
    }
    if (balThresholdRequestDto.getSpId() != null) {
      balThreshold.setSpId(balThresholdRequestDto.getSpId());
    }
    balanceTresholdRepository.save(balThreshold);

    if (balThresholdRequestDto.getTouchPcrf() != null) {
      if (balThresholdRequestDto.getTouchPcrf().equalsIgnoreCase("Y")) {
        BalPcrf pcrf = new BalPcrf();
        pcrf.setBalThresholdId(balThreshold.getId().longValue());
        pcrf.setTriggerMode(balThresholdRequestDto.getTriggerMode());
        pcrf.setSpId(balThresholdRequestDto.getSpId());
        balPcrfRepository.save(pcrf);
      }
    }
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, balThreshold));

  }

  public ResponseEntity<CustomeResponse> editBalTreshold (Integer BalTresholdId, BalThresholdRequestDto balThresholdRequestDto) {
    BalThreshold balThreshold = balanceTresholdRepository.selectBalThreshold(BalTresholdId).orElseThrow(() -> new ValidationHandler("BalThreshold not found"));
    if (balThresholdRequestDto.getTriggerId() != null) {
      balThreshold.setTriggerId(balThresholdRequestDto.getTriggerId());
    }

    if (balThresholdRequestDto.getInterval() != null) {
      balThreshold.setInterval(balThresholdRequestDto.getInterval().longValue());
    }
    if (balThresholdRequestDto.getTriggerBy().equalsIgnoreCase("Threshold")) {
      if (balThresholdRequestDto.getValue() != null) {
        balThreshold.setValue(balThresholdRequestDto.getValue().longValue());
      }
      balThreshold.setRatio(null);
    } else {
      balThreshold.setValue(0L);
      if (balThresholdRequestDto.getValue() != null) {
        balThreshold.setRatio(balThresholdRequestDto.getValue());
      }
    }
    if (balThresholdRequestDto.getInterval() != null) {
      balThreshold.setInterval(balThresholdRequestDto.getInterval().longValue());
    }
    if (balThresholdRequestDto.getReAttr() != null) {
      balThreshold.setReAttr(balThresholdRequestDto.getReAttr());
    }
    if (balThresholdRequestDto.getSpId() != null) {
      balThreshold.setSpId(balThresholdRequestDto.getSpId());
    }
    balanceTresholdRepository.save(balThreshold);

    if (balThresholdRequestDto.getTouchPcrf() != null) {
      if (balThresholdRequestDto.getTouchPcrf().equalsIgnoreCase("Y")) {
        Optional<BalPcrf> pcrf = balPcrfRepository.findByTrehold(balThreshold.getId());
        if (pcrf.isEmpty()) {
          BalPcrf pcrfNew = new BalPcrf();
          pcrfNew.setBalThresholdId(balThreshold.getId().longValue());
          pcrfNew.setTriggerMode(balThresholdRequestDto.getTriggerMode());
          pcrfNew.setSpId(balThresholdRequestDto.getSpId());
          balPcrfRepository.save(pcrfNew);
        } else {
          pcrf.get().setBalThresholdId(balThreshold.getId().longValue());
          pcrf.get().setTriggerMode(balThresholdRequestDto.getTriggerMode());
          pcrf.get().setSpId(balThresholdRequestDto.getSpId());
          balPcrfRepository.save(pcrf.get());
        }

      }
    }
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, balThreshold));

  }

  public BaseResponseDto doGetBalanceTresholdDetail (Integer triggerId, Integer tresholdId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    List<BalanceTresholdProjection> balance = balanceTresholdRepository.getBalanceTreshold(triggerId, 0, tresholdId);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    if (balance != null) {
      List<TriggerThresholdDto> list = balTriggerMapper.toListTriggerThresholdDto(balance);
      List<TriggerThresholdDto> response = new ArrayList<>();
      for (TriggerThresholdDto thrDto : list) {
        if (null != thrDto.getRatio() && !thrDto.getRatio().equalsIgnoreCase("0")) {
          thrDto.setTriggerBy("ratio");
        } else {
          thrDto.setTriggerBy("threshold");
          thrDto.setRatio(null);
        }
//                if (thrDto.getPcrf().equals(0)){
//                    thrDto.setTouchPcrf(false);
//                }else {
//                    thrDto.setTouchPcrf(true);
//                }
        response.add(thrDto);
      }

      baseResponseDto.setData(response);
    } else {
      baseResponseDto.setMessage(EnumRC.DATA_NOT_FOUND.getMessage());
      baseResponseDto.setCode(EnumRC.DATA_NOT_FOUND.getRESPONSE_CODE().toString());
    }

    return baseResponseDto;
  }

  public BaseResponseDto getBalBenefit (Integer tresholdId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    List<BalBenefitProjection> balance = balanceTresholdRepository.getBalBenefit(tresholdId, 0);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    if (balance.isEmpty()) {
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setData(balance);
    } else {
      baseResponseDto.setData(balance);
    }

    return baseResponseDto;
  }

  public void deleteBalTreshold (Integer thresholdId, boolean isPcrf) {
    List<BalBenefitProjection> balance = balanceTresholdRepository.getBalBenefit(thresholdId, 0);
    if (!balance.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-CFG-86031"));
    } else {
      balTriggerRepository.deleteBalTriggerByTresholdId(thresholdId);
      log.info("delete balt treshold successfully");
    }

    if (isPcrf) {
      getLoggerInfo("is Pcrf");
      log.info("is pcrf");
//            if (balance != null) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-86031"));
//            }else {
      balTriggerRepository.deletePcrfTriggerByTresholdId(thresholdId);
      log.info("delete balt treshold successfully");
//            }
    }
    getLoggerInfo("success ");
  }

  public BaseResponseDto getBalAdvice (Integer tresholdId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    List<BalAdviceProjection> balance = balanceTresholdRepository.getListBalAdvice(tresholdId, 0);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    if (balance.isEmpty()) {
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setData(balance);
    } else {
      baseResponseDto.setData(balance);
    }

    return baseResponseDto;
  }

  public BaseResponseDto getListSubBalEvent (Integer tresholdId) {
    List<BalSubsEventProjection> list = balSubEventRepository.getListSubBalEvent(tresholdId, 0);
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    if (list != null) {
      baseResponseDto.setData(list);
      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    } else {
      baseResponseDto.setCode(EnumRC.DATA_NOT_FOUND.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(MessageService.getMessage("MML_1005"));
    }
    return baseResponseDto;
  }

  public BaseResponseDto getListDynAttr (String reAttrName, Integer reType) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    List<DynReAttrProjection> balance = balanceTresholdRepository.getListDynAttrProjection(reAttrName, reType, 0);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    if (balance.isEmpty()) {
      baseResponseDto.setMessage(EnumRC.DATA_NOT_FOUND.getMessage());
      baseResponseDto.setCode(EnumRC.DATA_NOT_FOUND.getRESPONSE_CODE().toString());
    } else {
      baseResponseDto.setData(balance);
    }

    return baseResponseDto;
  }

  public ResponseEntity<CustomeResponse> getListAcmAdvice (Integer acmThersholdId, Integer spId) {

//        var data = acmAdviceRepository.qryAcmAdvice(acmThersholdId, spId)
//                .stream()
//                .map(qryAcmAdviceMapper::toDto)
//                .toList();

    List<QryAcmAdviceProjection> list = acmAdviceRepository.qryAcmAdvice(acmThersholdId, spId);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, list));
  }

  public BaseResponseDto addBalBenefit (TriggerBenefitDto balThresholdDto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    Period period = new Period();
    period.setSpId(0);
    if (balThresholdDto.getPeriodType() != null) {
      if (balThresholdDto.getPeriodType().equalsIgnoreCase("absolute")) {
        period.setAbsEffDate(balThresholdDto.getAbsoluteEffectiveDate());
        period.setAbsExpDate(balThresholdDto.getAbsoluteExpiryDate());
      } else {
        if (balThresholdDto.getEffUnit() != null) {
          period.setRelEffUnit(balThresholdDto.getEffUnit().charAt(0));
        }
        if (balThresholdDto.getExpUnit() != null) {
          period.setRelExpUnit(balThresholdDto.getExpUnit().charAt(0));
        }
        if (balThresholdDto.getRelativeEffectiveTime() != null) {
          period.setRelEffTime(LocalTime.parse(balThresholdDto.getRelativeEffectiveTime()));
        }
        if (balThresholdDto.getRelativeExpiryTime() != null) {
          period.setRelExpTime(LocalTime.parse(balThresholdDto.getRelativeExpiryTime()));
        }
        if (balThresholdDto.getOffsetOfEffectiveDate() != null) {
          period.setRelEffOffset(balThresholdDto.getOffsetOfEffectiveDate());
        }
        if (null != balThresholdDto.getDurationOfAvailability()) {
          //mohon d tanyakan apakah dari getDurationOfAvailability ?
          period.setRelExpOffset(balThresholdDto.getDurationOfAvailability());
        }
      }
    }

    if (balThresholdDto.getDayOffset() != null) {
      period.setDayOffset(balThresholdDto.getDayOffset());
    }
    periodRepository.save(period);

    SubBalType subBalType = new SubBalType();
    subBalType.setPeriodId(period.getId());
    subBalType.setLimitSubs(balThresholdDto.getSubscriberOnly());
    subBalType.setSpId(0);
    subBalType.setAcctResId(balThresholdDto.getAccountBalanceType());
    if (balThresholdDto.getMaximumDays() != null) {
      subBalType.setMaxDays(balThresholdDto.getMaximumDays());
    }
    if (balThresholdDto.getRelativePeriodUnit() != null) {
      log.debug("getRelativePeriodUnit :" + balThresholdDto.getRelativePeriodUnit());
      subBalType.setPeriodRelUnit(balThresholdDto.getRelativePeriodUnit().charAt(0));
    }
    if (balThresholdDto.getExtendRule() != null) {
      log.debug("getExtendRule :" + balThresholdDto.getExtendRule());
      subBalType.setExtendRule(balThresholdDto.getExtendRule().charAt(0));
    }
    if (balThresholdDto.getCycleCeilLimit() != null) {
      subBalType.setCeilLimit(balThresholdDto.getCycleCeilLimit().longValue());
    }
    if (balThresholdDto.getDailyCeilLimit() != null) {
      subBalType.setDailyCeilLimit(balThresholdDto.getDailyCeilLimit().longValue());
    }
    if (balThresholdDto.getOffsetOfAbsoluteExpiry() != null) {
      subBalType.setAbsExpOffset(balThresholdDto.getOffsetOfAbsoluteExpiry().longValue());
    }

//        if (ObjectUtils.isNotEmpty(balThresholdDto.getPriority())) {
//            subBalType.setPriority(balThresholdDto.getSubBalType().getPriority());
//        }
    subBalTypeRepository.save(subBalType);

    if (subBalType.getAcctResId() != null) {
      for (Integer acctItemTypeId : balThresholdDto.getResultAccountItemType()) {
        SubBalTypeLimit subBalTypeLimit = new SubBalTypeLimit();
        SubBalTypeLimitId subBalTypeLimitId = new SubBalTypeLimitId();
        subBalTypeLimitId.setSubBalTypeId(subBalType.getId());
        subBalTypeLimitId.setAcctItemTypeId(acctItemTypeId);
        subBalTypeLimit.setId(subBalTypeLimitId);
        subBalTypeLimit.setSpId(0);
        this.subBalTypeLimitRepository.save(subBalTypeLimit);
      }
    }

        /* dto memakai acm.
        if (!balThresholdDto.getSubBalType().getSubBalTypeLimitList().isEmpty()) {
            List<SubBalTypeLimitDto> subBalTypeLimitList = balThresholdDto.getSubBalType().getSubBalTypeLimitList();
            if (subBalTypeLimitList != null && !subBalTypeLimitList.isEmpty()) {
                for (SubBalTypeLimitDto subBalTypeLimit : subBalTypeLimitList) {
                    SubBalTypeLimit subBalTypeLimit1 = new SubBalTypeLimit();
                    SubBalTypeLimitId subBalTypeLimitId = new SubBalTypeLimitId();
                    subBalTypeLimitId.setSubBalTypeId(subBalType.getId());
                    subBalTypeLimitId.setAcctItemTypeId(subBalTypeLimit.getAcctItemTypeId());
                    subBalTypeLimit1.setSpId(subBalTypeLimit.getSpId());
                    subBalTypeLimit1.setId(subBalTypeLimitId);
                    subBalTypeLimitRepository.save(subBalTypeLimit1);
                    getLoggerInfo("subBalTypeLimit1 success save");
                }

            }
        }*/
    if (balanceTresholdRepository.selectBalBenefit(balThresholdDto.getThresholdId(),
      subBalType.getId()).isPresent()) {
      new ValidationHandler(MessageService.getMessage("S-PRD-01048"));
    }
    BalBenefit balBenefit = new BalBenefit();
    BalBenefitId id = new BalBenefitId();
    id.setBalThresholdId(balThresholdDto.getThresholdId());
    id.setSubBalTypeId(subBalType.getId());
    balBenefit.setId(id);

    balBenefit.setSpId(0);
    balBenefit.setValue(balThresholdDto.getBenefitValue().longValue());
    balanceBenefitRepository.save(balBenefit);

    return baseResponseDto;
  }

  public BaseResponseDto modBalBenefit (Integer subBalTypeId, TriggerBenefitDto balanceTreshold) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    Optional<GetSubBalTypeIdPeriodIdProjection> ids = subBalTypeRepository.findSubBalTypeIdAndPeriodId(subBalTypeId);

    BalBenefit balBenefit = balanceBenefitRepository.findBySubTresholdAndSubBalType(balanceTreshold.getThresholdId(), subBalTypeId)
      .orElseThrow(() -> new ValidationHandler(MessageService.getMessage("S-PRD-01048")));
    balBenefit.setValue(balanceTreshold.getBenefitValue().longValue());
    balBenefit.setSpId(0);
    balanceBenefitRepository.save(balBenefit);
    logger.loggerInfo(TriggerService.class, "edit balBenefit success");

    Period period = periodRepository.findById(ids.get().getPeriodId()).orElseThrow(() -> new ValidationHandler("Period " + EnumRC.NOT_FOUND.getMessage()));

    SubBalType subBalType = subBalTypeRepository.findById(subBalTypeId).orElseThrow(() -> new ValidationHandler("SubBalBenefit not found"));

    if (balanceTreshold.getSubscriberOnly() != null) {
      subBalType.setLimitSubs(balanceTreshold.getSubscriberOnly());
    }
    if (balanceTreshold.getAccountBalanceType() != null) {
      subBalType.setAcctResId(balanceTreshold.getAccountBalanceType());
    }
    if (balanceTreshold.getMaximumDays() != null) {
      subBalType.setMaxDays(balanceTreshold.getMaximumDays());
    }
    if (balanceTreshold.getRelativePeriodUnit() != null) {
      log.debug("getRelativePeriodUnit :" + balanceTreshold.getRelativePeriodUnit());
      subBalType.setPeriodRelUnit(balanceTreshold.getRelativePeriodUnit().charAt(0));
    }
    if (balanceTreshold.getExtendRule() != null) {
      log.debug("getExtendRule :" + balanceTreshold.getExtendRule());
      subBalType.setExtendRule(balanceTreshold.getExtendRule().charAt(0));
    }
    if (balanceTreshold.getCycleCeilLimit() != null) {
      subBalType.setCeilLimit(balanceTreshold.getCycleCeilLimit().longValue());
    }
    if (balanceTreshold.getDailyCeilLimit() != null) {
      subBalType.setDailyCeilLimit(balanceTreshold.getDailyCeilLimit().longValue());
    }
    if (balanceTreshold.getOffsetOfAbsoluteExpiry() != null) {
      subBalType.setAbsExpOffset(balanceTreshold.getOffsetOfAbsoluteExpiry().longValue());
    }

    subBalTypeRepository.save(subBalType);
    logger.loggerInfo(TriggerService.class, "edit subBalType success");
    subBalTypeLimitRepository.deleteSubBalTypeId(subBalType.getId());

    if (subBalType.getAcctResId() != null) {
      for (Integer acctItemTypeId : balanceTreshold.getResultAccountItemType()) {
        SubBalTypeLimit subBalTypeLimit = new SubBalTypeLimit();
        SubBalTypeLimitId subBalTypeLimitId = new SubBalTypeLimitId();
        subBalTypeLimitId.setSubBalTypeId(subBalType.getId());
        subBalTypeLimitId.setAcctItemTypeId(acctItemTypeId);
        subBalTypeLimit.setId(subBalTypeLimitId);
        subBalTypeLimit.setSpId(0);
        this.subBalTypeLimitRepository.save(subBalTypeLimit);
      }
    }
        /*
        if (!balanceTreshold.getSubBalTypeLimitList().isEmpty()) {
            List<SubBalTypeLimitDto> subBalTypeLimitList = balanceTreshold.getSubBalType().getSubBalTypeLimitList();
            if (subBalTypeLimitList != null && !subBalTypeLimitList.isEmpty()) {
                for (SubBalTypeLimitDto subBalTypeLimit : subBalTypeLimitList) {
                    SubBalTypeLimit subBalTypeLimit1 = new SubBalTypeLimit();
                    subBalTypeLimit1.setSpId(subBalTypeLimit.getSpId());
                    SubBalTypeLimitId subBalTypeLimitId = new SubBalTypeLimitId();
                    subBalTypeLimitId.setSubBalTypeId(subBalType.getId());
                    subBalTypeLimitId.setAcctItemTypeId(subBalTypeLimit.getAcctItemTypeId());
                    subBalTypeLimit1.setId(subBalTypeLimitId);
                    subBalTypeLimitRepository.save(subBalTypeLimit1);
                    getLoggerInfo("subBalTypeLimit1 success save");
                }

            }
        }*/

    if (balanceTreshold.getAbsoluteExpiryDate() == null && balanceTreshold.getAbsoluteExpiryDate() == null) {
      if (balanceTreshold.getOffsetOfEffectiveDate() != null) {
        period.setRelEffOffset(balanceTreshold.getOffsetOfEffectiveDate());
      }

      if (balanceTreshold.getEffUnit() != null) {
        period.setRelEffUnit(balanceTreshold.getRelativePeriodUnit().charAt(0));
      }

      if (balanceTreshold.getDurationOfAvailability() != null) {
        period.setRelExpOffset(balanceTreshold.getDurationOfAvailability());
      }

      if (balanceTreshold.getExpUnit() != null) {
        period.setRelExpUnit(balanceTreshold.getExpUnit().charAt(0));
      }

      if (balanceTreshold.getRelativePeriodUnit() != null) {
        subBalType.setPeriodRelUnit(balanceTreshold.getRelativePeriodUnit().charAt(0));
      }
    } else if (balanceTreshold.getAbsoluteExpiryDate() != null && balanceTreshold.getAbsoluteEffectiveDate() != null) {
      period.setAbsEffDate(balanceTreshold.getAbsoluteEffectiveDate());
      period.setAbsExpDate(balanceTreshold.getAbsoluteExpiryDate());
    }

    if (balanceTreshold.getPeriodType() != null) {
      if (balanceTreshold.getPeriodType().equalsIgnoreCase("absolute")) {
        period.setAbsEffDate(balanceTreshold.getAbsoluteEffectiveDate());
        period.setAbsExpDate(balanceTreshold.getAbsoluteExpiryDate());

        period.setRelEffUnit(null);
        period.setRelExpUnit(null);
        period.setRelEffTime(null);
        period.setRelExpTime(null);
        period.setRelEffOffset(null);
        //mohon d tanyakan apakah dari getDurationOfAvailability ?
        period.setRelExpOffset(null);
      } else {
        period.setAbsEffDate(null);
        period.setAbsExpDate(null);

        if (balanceTreshold.getEffUnit() != null) {
          period.setRelEffUnit(balanceTreshold.getEffUnit().charAt(0));
        }
        if (balanceTreshold.getExpUnit() != null) {
          period.setRelExpUnit(balanceTreshold.getExpUnit().charAt(0));
        }
        if (balanceTreshold.getRelativeEffectiveTime() != null) {
          period.setRelEffTime(LocalTime.parse(balanceTreshold.getRelativeEffectiveTime()));
        }
        if (balanceTreshold.getRelativeExpiryTime() != null) {
          period.setRelExpTime(LocalTime.parse(balanceTreshold.getRelativeExpiryTime()));
        }
        if (balanceTreshold.getOffsetOfEffectiveDate() != null) {
          period.setRelEffOffset(balanceTreshold.getOffsetOfEffectiveDate());
        }
        if (null != balanceTreshold.getDurationOfAvailability()) {
          //mohon d tanyakan apakah dari getDurationOfAvailability ?
          period.setRelExpOffset(balanceTreshold.getDurationOfAvailability());
        }
      }
    }
    this.periodRepository.save(period);

    return baseResponseDto;
  }

  public BaseResponseDto deleteBalBenefit (Long periodId, Integer balThresholdId, Integer subBalTypeId) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    Period period = periodRepository.findById(periodId.intValue()).orElseThrow(() -> new ValidationHandler("Period not found"));

    BalBenefit balBenefit = balanceBenefitRepository.findBySubTresholdAndSubBalType(balThresholdId, subBalTypeId)
      .orElseThrow(() -> new ValidationHandler(MessageService.getMessage("S-PRD-01048")));

    SubBalType subBalType = subBalTypeRepository.findById(subBalTypeId).orElseThrow(() -> new ValidationHandler("SubBalBenefit not found"));
    Optional<SubBalTypeLimit> subBalTypeLimit = subBalTypeLimitRepository.findBySubBalTypeId(subBalTypeId);
    if (subBalTypeLimit.isPresent()) {
      subBalTypeLimitRepository.delete(subBalTypeLimit.get());
    }
//        subBalTypeLimitRepository.deleteSubBalTypeId(balanceTreshold.getSubBalType().getSubBalTypeId());

    subBalTypeRepository.delete(subBalType);
    periodRepository.delete(period);
    balanceBenefitRepository.delete(balBenefit);

    return baseResponseDto;
  }

  public ResponseEntity<CustomeResponse> getBalSubsEvent (Long balThreeShold, Integer spId) {
    var data = subsEventRepository.findBalSubsEvents(balThreeShold, spId)
      .stream()
      .map(balSubsEventMapper::toDto)
      .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> getAcmSubsEvent (Integer acmThreshold, Integer spId) {
    var data = acmSubsEventRepository.qryAcmSubsEvent(acmThreshold, spId)
      .stream()
      .map(qryAcmSubsEventMapper::toDto)
      .toList();

    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> addBalAdvice (TriggerNotificationDto triggerNotificationDto) {
    BalAdvice balAdvice = new BalAdvice();
    BalAdviceId balAdviceId = new BalAdviceId();
    Integer notifTypeInt = triggerNotificationDto.getNotifType() != null ? Integer.valueOf(triggerNotificationDto.getNotifType()) : null;
    /*------*/
//        Integer notifTypeInt = null;
    String triggerNotification = triggerNotificationDto.getTriggerNotification();
    Integer eventTypeInt = null;
    if (triggerNotification != null) {
      if (triggerNotification.equalsIgnoreCase("notifType")) {
        notifTypeInt = triggerNotificationDto.getNotifType() != null ? Integer.valueOf(triggerNotificationDto.getNotifType()) : null;
      } else {
        eventTypeInt = triggerNotificationDto.getNotifType() != null ? Integer.valueOf(triggerNotificationDto.getNotifType()) : null;
      }
    }

    BalAdvice balExist = balAdviceRepository.findByBalThresholdId(triggerNotificationDto.getThresholdId(), notifTypeInt, eventTypeInt, PageRequest.of(0, 1))
      .stream().findFirst().orElse(null);
    if (balExist != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-PRD-04057"));
    }
    if (triggerNotificationDto.getTriggerNotification().equalsIgnoreCase("notifType")) {
      if (triggerNotificationDto.getNotifType() != null) {
        balAdviceId.setAdviceType(Integer.valueOf(triggerNotificationDto.getNotifType()));
      }
    } else {
      if (triggerNotificationDto.getNotifType() != null) {
        balAdvice.setAdviceEventId(Integer.valueOf(triggerNotificationDto.getNotifType()));
      }
    }

    if (triggerNotificationDto.getNotifParamId() != null) {
      balAdvice.setNotifyParamsId(triggerNotificationDto.getNotifParamId());
    }
    if (triggerNotificationDto.getTriggerMode() != null) {
      balAdvice.setTriggerMode(triggerNotificationDto.getTriggerMode());
    }
    if (triggerNotificationDto.getSpId() != null) {
      balAdvice.setSpId(triggerNotificationDto.getSpId());
    } else {
      balAdvice.setSpId(0);
    }
    balAdviceId.setBalThresholdId(triggerNotificationDto.getThresholdId());
    balAdvice.setId(balAdviceId);

    balAdviceRepository.save(balAdvice);
    logger.loggerInfo(TriggerService.class, "add balAdvice success");
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> modBalAdvice (Integer oldTresholdId, Integer oldNotifType, TriggerNotificationDto triggerNotificationDto) {
    Integer notifTypeInt = null;
    Integer eventTypeInt = null;
    BalAdviceId balAdviceId = new BalAdviceId();

//        BalAdvice balAdvice = balAdviceRepository.findByBalThresholdId(triggerNotificationDto.getThresholdId(), notifTypeInt)
//                .orElseThrow(() -> new ValidationHandler(messageService.getMessage("S-ACT-00466")));
    /*-----------*/

    String triggerNotification = triggerNotificationDto.getTriggerNotification();
    Integer oldNotifTypeInt = null;
    Integer oldEventTypeInt = null;

    if (triggerNotification != null) {
      if (triggerNotification.equalsIgnoreCase("notifType")) {
        notifTypeInt = triggerNotificationDto.getNotifType() != null ? Integer.valueOf(triggerNotificationDto.getNotifType()) : null;

        oldNotifTypeInt = oldNotifType != null ? oldNotifType : null;
      } else {
        eventTypeInt = triggerNotificationDto.getNotifType() != null ? Integer.valueOf(triggerNotificationDto.getNotifType()) : null;

        oldEventTypeInt = oldNotifType != null ? oldNotifType : null;
      }
    }
    BalAdvice oldBalAdvice = balAdviceRepository.findByBalThresholdId(oldTresholdId, oldNotifTypeInt, oldEventTypeInt, PageRequest.of(0, 1))
      .stream().findFirst().orElse(null);

    BalAdvice newBalCheck = balAdviceRepository.findByBalThresholdId(triggerNotificationDto.getThresholdId(), notifTypeInt, eventTypeInt, PageRequest.of(0, 1))
      .stream().findFirst().orElse(null);

    if (newBalCheck != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-PRD-04057"));
    }

    BalAdvice balAdvice = new BalAdvice();
//        BalAdvice balAdvice = balAdviceRepository.findByBalThresholdId(triggerNotificationDto.getThresholdId(), notifTypeInt, eventTypeInt, PageRequest.of(0, 1))
//                .stream().findFirst().orElse(null);

    if (triggerNotificationDto.getTriggerNotification().equalsIgnoreCase("notifType")) {
      if (triggerNotificationDto.getNotifType() != null) {
        balAdviceId = new BalAdviceId();
        balAdviceId.setAdviceType(Integer.valueOf(triggerNotificationDto.getNotifType()));
        balAdviceId.setBalThresholdId(triggerNotificationDto.getThresholdId());
        balAdvice.setId(balAdviceId);
        balAdvice.setAdviceEventId(null);
      }
    } else {
      if (triggerNotificationDto.getNotifType() != null) {
        balAdviceId = new BalAdviceId();
//              untuk  eventtype
        balAdviceId.setAdviceType(null);
        balAdviceId.setBalThresholdId(triggerNotificationDto.getThresholdId());
        balAdvice.setId(balAdviceId);
        balAdvice.setAdviceEventId(Integer.valueOf(triggerNotificationDto.getNotifType()));
      }
    }
    if (triggerNotificationDto.getNotifParamId() != null) {
      balAdvice.setNotifyParamsId(triggerNotificationDto.getNotifParamId());
    }
    if (triggerNotificationDto.getTriggerMode() != null) {
      balAdvice.setTriggerMode(triggerNotificationDto.getTriggerMode());
    }
    if (triggerNotificationDto.getSpId() != null) {
      balAdvice.setSpId(triggerNotificationDto.getSpId());
    } else {
      balAdvice.setSpId(0);
    }
    balAdviceRepository.save(balAdvice);

    balAdviceRepository.delete(oldBalAdvice);
    logger.loggerInfo(TriggerService.class, "edit balAdvice success");
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> delBalAdvice (Integer balThresholdId, String notifType, String triggerNotification) {
    Integer notifTypeInt = null;
    Integer eventTypeInt = null;
    if (triggerNotification != null) {
      if (triggerNotification.equalsIgnoreCase("notifType")) {
        notifTypeInt = notifType != null ? Integer.valueOf(notifType) : null;
      } else {
        eventTypeInt = notifType != null ? Integer.valueOf(notifType) : null;
      }
    }
    BalAdvice balAdvice = balAdviceRepository.findByBalThresholdId(balThresholdId, notifTypeInt, eventTypeInt, PageRequest.of(0, 1))
      .stream().findFirst().orElse(null);
    if (balAdvice == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-PRD-00466"));
    }
    balAdviceRepository.delete(balAdvice);
    logger.loggerInfo(TriggerService.class, "delete balAdvice success");
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> addAcmSubEvent (SubsEventDto trigerEventAcm) {
    createAcmSubEvent(trigerEventAcm);
    log.info("addAcmSubEvent success");
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  private void createAcmSubEvent (SubsEventDto trigerEventAcm) {
    AcmSubsEvent acmSubsEvent = new AcmSubsEvent();
    AcmSubEventId acmSubEventId = new AcmSubEventId();
    acmSubEventId.setAcmThresholdId(trigerEventAcm.getAcmThresholdId());
    acmSubEventId.setSubsEventId(trigerEventAcm.getSubsEventId());
    acmSubsEvent.setAntibillShock(trigerEventAcm.getAntibillShock());
    acmSubsEvent.setSpId(trigerEventAcm.getSpId());
    acmSubsEvent.setId(acmSubEventId);
    if (ObjectUtils.isNotEmpty(trigerEventAcm.getTriggerMode())) {
      acmSubsEvent.setTriggerMode(trigerEventAcm.getTriggerMode());
    }
    if (trigerEventAcm.getNotifyParamsId() != null) {
      acmSubsEvent.setNotifyParamsId(trigerEventAcm.getNotifyParamsId());
    }
    if (trigerEventAcm.getExtAttr() != null) {
      acmSubsEvent.setExtAttr(trigerEventAcm.getExtAttr());
    }
      acmSubsEventRepository.save(acmSubsEvent);
  }

  public ResponseEntity<CustomeResponse> updateAcmEvent (SubsEventDto trigerEventAcm) {
    AcmSubsEvent acmSubsEvent = acmSubsEventRepository.findByTresholdIdAndSubsEventId(trigerEventAcm.getAcmThresholdId(), trigerEventAcm.getSubsEventId())
      .orElseThrow(() -> new ValidationHandler(MessageService.getMessage("S-ACT-00464")));

//        AcmSubEventId acmSubEventId = new AcmSubEventId();
//        acmSubsEvent.getId().setAcmThresholdId(trigerEventAcm.getAcmThresholdId());
    acmSubsEvent.setSpId(trigerEventAcm.getSpId());
//        acmSubsEvent.setSubsEventId(trigerEventAcm.getSubsEventId());
    acmSubsEvent.setTriggerMode(trigerEventAcm.getTriggerMode());
    if (trigerEventAcm.getNotifyParamsId() != null) {
      acmSubsEvent.setNotifyParamsId(trigerEventAcm.getNotifyParamsId());
    }
    if (trigerEventAcm.getExtAttr() != null) {
      acmSubsEvent.setExtAttr(trigerEventAcm.getExtAttr());
    }
    acmSubsEvent.setAntibillShock(trigerEventAcm.getAntibillShock());
    acmSubsEventRepository.save(acmSubsEvent);
    log.info("updateAcmEvent success");
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> deleteAcmEvent (Integer tresholdId, Integer subEventId) {
    acmSubsEventRepository.deleteSubEvent(tresholdId, subEventId);
    log.info("deleteAcmEvent success");
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> addBalSubEvent (TrigerEventAcm trigerEventAcm) {
    BalSubsEvent subsEvent = new BalSubsEvent();
    BalSubsEventId balSubsEventId = new BalSubsEventId();
    balSubsEventId.setSubsEventId(trigerEventAcm.getSubsEventId());
    balSubsEventId.setBalThresholdId(trigerEventAcm.getBalThresholdId());
    subsEvent.setId(balSubsEventId);

    subsEvent.setTriggerMode(trigerEventAcm.getTriggerMode().charAt(0));
    subsEvent.setSpId(trigerEventAcm.getSpId());
    subsEvent.setAntibillShock(trigerEventAcm.getAntibillShock().charAt(0));
    subsEvent.setExtAttr(trigerEventAcm.getExtAttr());
    balSubEventRepository.save(subsEvent);
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> editBalSubEvent (TrigerEventAcm trigerEventAcm) {
    BalSubsEvent subsEvent = balSubEventRepository.findBalByBalThresholdId(
      trigerEventAcm.getBalThresholdId(), trigerEventAcm.getSubsEventId()).orElseThrow(
      () -> new ValidationHandler(MessageService.getMessage("S-ACT-00466")));
    // subsEvent.setSubsEventId(trigerEventAcm.getSubsEventId());
    subsEvent.setTriggerMode(trigerEventAcm.getTriggerMode().charAt(0));
    subsEvent.setSpId(trigerEventAcm.getSpId());
    subsEvent.setAntibillShock(trigerEventAcm.getAntibillShock().charAt(0));
    subsEvent.setExtAttr(trigerEventAcm.getExtAttr());
    balSubEventRepository.save(subsEvent);
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> deleteBalSubEvent (Integer tresholdId, Integer subEventId) {
    BalSubsEvent subsEvent = balSubEventRepository.findBalByBalThresholdId(
      tresholdId, subEventId).orElseThrow(() -> new ValidationHandler(MessageService.getMessage("S-ACT-00466")));
    balSubEventRepository.delete(subsEvent);
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> delAcmAdvice (AdviceDelReqDto dict) {
    log.info("delAcmAdvice");
    AdviceRequestDto adviceRequestDto = new AdviceRequestDto();
    adviceRequestDto.setThresholdId(dict.getAcmThresholdId());
    adviceRequestDto.setSpId(dict.getSpId());
    adviceRequestDto.setNotifType(String.valueOf(dict.getAdviceType()));
    deleteAcmAdv(adviceRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  public ResponseEntity<CustomeResponse> modifAcmAdvice (AdviceRequestDto dict) {
    modAcmAdvice(dict);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  private void deleteAcmAdv (AdviceRequestDto dict) {
    acmAdviceRepository.deleteAcmAdvice(dict.getThresholdId(), Integer.valueOf(dict.getNotifType()));
  }

  @Transactional
  public void modAcmAdvice (AdviceRequestDto dict) {
    log.info("modAcmAdvice in progress..");

    List<AcmAdvice> existingAcmAdvice = acmAdviceRepository.getAcmAdvice(dict.getThresholdId());

    if (existingAcmAdvice.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00466"));
    }


    acmAdviceRepository.deleteByDynamicConditions(
      dict.getThresholdId(),
      Integer.valueOf(dict.getOldNotifType()),
      dict.getOldAdviceEventId(),
      dict.getOldNotifParamId()
    );


    boolean existsAcmAdvice = acmAdviceRepository.existsByDynamicConditions(
      dict.getThresholdId(),
      Integer.valueOf(dict.getOldNotifType()),
      dict.getOldAdviceEventId(),
      dict.getOldNotifParamId()
    );

    if (existsAcmAdvice) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00467"));
    }

    AcmAdvice acmAdvice = new AcmAdvice();
    acmAdvice.setAcmThresholdId(dict.getThresholdId());
    if (dict.getNotifType() != null) {
      acmAdvice.setAdviceType(Integer.valueOf(dict.getNotifType()));
    }
    if (dict.getNotifParamId() != null) {
      acmAdvice.setNotifyParamsId(dict.getNotifParamId());
    }
    if (dict.getTriggerMode() != null) {
      acmAdvice.setTriggerMode(dict.getTriggerMode());
    }
    if (dict.getSpId() != null) {
      acmAdvice.setSpId(dict.getSpId());
    } else {
      acmAdvice.setSpId(0);
    }
    acmAdviceRepository.insertAdvice(Optional.ofNullable(acmAdvice.getAcmThresholdId()).orElse(null),
      Optional.ofNullable(acmAdvice.getAdviceType()).orElse(null)
      , Optional.ofNullable(acmAdvice.getAdviceEventId()).orElse(null),
      Optional.ofNullable(acmAdvice.getSpId()).orElse(null)
      , Optional.ofNullable(acmAdvice.getTriggerMode()).orElse(null),
      Optional.ofNullable(acmAdvice.getNotifyParamsId()).orElse(null)
    );

    log.info("modAcmAdvice success");
  }

  public ResponseEntity<CustomeResponse> deleteBalTrigger (Integer triggerId) {
    delBalTrigger(triggerId);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
  }

  private void delBalTrigger (Integer triggerId) {
    if (triggerId == null) {
      throw new ValidationHandler("TRIGGER_ID can not be null.");
    }
    if (balTriggerRepository.fieldIsReferencedBalTreshold(triggerId).isPresent()) {
      throw new ValidationHandler(MessageService.getMessage("S-PRD-04050"));
    }
    balTriggerRepository.deleteBalTriggerByTriggerId(triggerId);
  }

  /*
      untuk advance rule action
   */
  public ResponseEntity<CustomeResponse> addBwfStep4TriggerRule (BwfTriggerRuleRequestDto bwfStepDto) {
    BwfWorkflow addBwfWorkflow = addBwfWorkflow(bwfStepDto);
    BwfNode bwfNode = addBwfNode(bwfStepDto, addBwfWorkflow.getId());
    addBwfStep(bwfStepDto, bwfNode.getId());
    //update trigger rule
    if (bwfStepDto.getSeq() != null) {
      seq = bwfStepDto.getSeq();
    } else {
      seq = 1;
    }
    advanceRuleTriggerRepository.updateTriggerRule(addBwfWorkflow.getId(), bwfStepDto.getTriggerId(), bwfStepDto.getSeq());

    return ResponseEntity.status(HttpStatus.CREATED).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

  private BwfWorkflow addBwfWorkflow (BwfTriggerRuleRequestDto ruleRequestDTO) {
    log.info("addBwfWorkflow");
    if (bwfWorkFlowRepository.getMaxWorkFLowByName(ruleRequestDTO.getSortRuleName()).isPresent()) {
      throw new ValidationHandler(MessageService.getMessage("S-CHG-00014"));
    }
    BwfWorkflow bwfWorkflow = new BwfWorkflow();
    try {
      Integer id = bwfWorkFlowRepository.getMaxWorkflowId() + 1;
      bwfWorkflow.setId(id);
      bwfWorkflow.setWorkflowName(ruleRequestDTO.getSortRuleName());
      bwfWorkflow.setSpId(ruleRequestDTO.getSpId());
      bwfWorkflow.setWorkflowType('T');
      bwfWorkFlowRepository.save(bwfWorkflow);
      log.info("addBwfWorkflow success");
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ValidationHandler(MessageService.getMessage("S-CHG-00011"));
    }
    return bwfWorkflow;
  }

  public BwfNode addBwfNode (BwfTriggerRuleRequestDto dict, Integer workFlowId) {

    if (bwfNodeRepository.getBwfNodeByNodeName(dict.getSortRuleName()).isPresent()) {
      throw new ValidationHandler(MessageService.getMessage("S-CHG-00019"));
    }
    BwfNode bwfNode = new BwfNode();
    try {

      bwfNode.setId(bwfNodeRepository.getMaxNodeId() + 1);
      bwfNode.setSpId(dict.getSpId());
      bwfNode.setNodeName(dict.getSortRuleName());
      bwfNode.setWorkflowId(workFlowId);
      bwfNode.setFirstNode('Y');
      bwfNodeRepository.save(bwfNode);
      log.info("addBwfNode success");
    } catch (Exception ex) {
      throw new ValidationHandler(MessageService.getMessage("S-CHG-00016"));
    }
    return bwfNode;
  }

  public void addBwfStep (BwfTriggerRuleRequestDto bwfStepDto, Integer nodeId) {
    log.info("addBwfStep");
    try {
      if (bwfStepRepository.findByRuleName(bwfStepDto.getSortRuleName()).isPresent()) {
        throw new ValidationHandler(MessageService.getMessage("S-CHG-00041"));
      }
      BwfStep bwfStep = new BwfStep();
      bwfStep.setSortRuleName(bwfStepDto.getSortRuleName());
      bwfStep.setExecOrder(1);
      if (bwfStepDto.getEffDate() != null) {
        bwfStep.setEffDate(bwfStepDto.getEffDate());
      }
      bwfStep.setExpDate(LocalDate.now());
      if (bwfStepDto.getExpDate() != null) {
        bwfStep.setExpDate(bwfStepDto.getExpDate());
      }
      bwfStep.setSpId(bwfStepDto.getSpId());
      if (nodeId != null) {
        bwfStep.setNodeId(nodeId);
      }
      if (null != bwfStepDto.getNodeId()) {
        bwfStep.setNodeId(bwfStepDto.getNodeId());
      }
      if (null != bwfStepDto.getComments()) {
        bwfStep.setComments(bwfStepDto.getComments());
      }

      bwfStep.setId(bwfStepRepository.getNextStepId().intValue());
      bwfStepRepository.save(bwfStep);
      log.info("addBwfStep success");

      addBwfCondGroup(bwfStepDto.getBwfCondGroupList(), bwfStep.getId().longValue(), bwfStep.getSpId(), bwfStepDto.getComments());

      addBwfAction(bwfStepDto, bwfStep.getId(), bwfStep.getSpId());
      if (bwfStepDto.getBwfSysActionDto() != null) {
        Integer seq = 1;
        if (bwfStepDto.getSeq() != null) {
          seq = bwfStepDto.getSeq();
        }
        addBwfSysAction(bwfStepDto, bwfStep.getId(), seq);
      }

    } catch (Exception ex) {
      throw new ValidationHandler(MessageService.getMessage("S-CHG-00040"));
    }

  }

  @Transactional
  public ResponseEntity<CustomeResponse> modBwfStep (Integer stepId, BwfTriggerRuleRequestDto bwfStepDto) {

    BwfStep olfBwfStep = bwfStepRepository.selectBwfStep(stepId).orElseThrow(() ->
      new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-CHG-00051")));

    try {
      Integer oldExecOrder = olfBwfStep.getExecOrder();
      List<FindBwfCondGroupByStepIdProjection> oo = bwfStepRepository.findBwfCondGroupByStepId(stepId);
      if (!oo.isEmpty()) {
        for (FindBwfCondGroupByStepIdProjection findBwfCondGroupByStepIdProjection : oo) {
          bwfCondRepository.deleteByCondGroupId(findBwfCondGroupByStepIdProjection.getCondGroupId());
        }
        bwfCondGroupRepository.deleteByStepId(stepId);
      }

      addBwfCondGroup(bwfStepDto.getBwfCondGroupList(), olfBwfStep.getId().longValue(), olfBwfStep.getSpId(), bwfStepDto.getComments());
      bwfActionRepository.deleteByStepId(stepId);
      addBwfAction(bwfStepDto, stepId, olfBwfStep.getSpId());
      bwfSysActionRepository.deleteByStepId(stepId);
      addBwfSysAction(bwfStepDto, stepId, bwfStepDto.getSeq());

      olfBwfStep.setNodeId(bwfStepDto.getNodeId());
      olfBwfStep.setSortRuleName(bwfStepDto.getSortRuleName());
      olfBwfStep.setExecOrder(oldExecOrder);
      olfBwfStep.setEffDate(bwfStepDto.getEffDate());
      olfBwfStep.setExpDate(bwfStepDto.getExpDate());
      olfBwfStep.setComments(bwfStepDto.getComments());
      bwfStepRepository.save(olfBwfStep);
      log.info("modBwfStep success");
      // TODO : OUTPUT_NODE_ID dan COMMENTS belum ada di dto jadi saya abaikan (AZKA)
//            bwfStepRepository.updateBwfStep(
//                    bwfStepDto.getNodeId(),
//                    bwfStepDto.getSortRuleName(),
//                    oldExecOrder,
//                    bwfStepDto.getEffDate(),
//                    bwfStepDto.getExpDate(),
//                    bwfStepDto.getSpId(),
//                    stepId
//            );

      return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));

    } catch (Exception e) {
      throw new RuntimeException("Update BWF Step gagal: " + e.getMessage(), e);
    }
  }

  void addBwfSysAction (BwfTriggerRuleRequestDto bwfStepDto, Integer stepId, Integer seq) {
    log.info("addBwfSysAction");
    BwfSysAction bwfSysAction = new BwfSysAction();

    if (bwfStepDto.getBwfSysActionDto() != null) {
      // TODO : di existing generate id pakai dari next SYS_ACTION_ID ditambah satu sudah ditambahkan querynya (AZKA)
      bwfSysAction.setId(bwfSysActionRepository.getNextSysActionId());

      bwfSysAction.setSysActionName(bwfStepDto.getBwfSysActionDto().getSysActionName());
      bwfSysAction.setComments(bwfStepDto.getBwfSysActionDto().getComments());
      bwfSysAction.setSpId(bwfStepDto.getBwfSysActionDto().getSpId());
      bwfSysAction.setStepId(stepId);
      bwfSysAction.setScriptTempletId(bwfStepDto.getBwfSysActionDto().getScriptTempletId());
      bwfSysAction.setExtScript(bwfStepDto.getBwfSysActionDto().getExtScript());
      String scriptPage = bwfStepDto.getBwfSysActionDto().getScriptPage();
      bwfSysAction.setScriptPage(scriptPage != null ? scriptPage.getBytes() : new byte[0]);
      bwfSysActionRepository.save(bwfSysAction);
    }

    log.info("addBwfSysAction success");
  }

  private void addBwfAction (BwfTriggerRuleRequestDto bwfStepDto, Integer stepId, Integer spId) {
    log.info("addBwfAction");
    List<BwfAction> listBwf = new ArrayList<>();
    if (bwfStepDto.getBwfActionList() != null) {
      for (BwfActionDto bwfActionDto : bwfStepDto.getBwfActionList()) {
        BwfAction bwfAction = new BwfAction();
        BwfActionId actionId = new BwfActionId();
        actionId.setStepId(stepId);
        if (bwfStepDto.getSeq() != null) {
          actionId.setSeq(bwfStepDto.getSeq());
        } else {
          actionId.setSeq(1);
        }
        bwfAction.setId(actionId);
        bwfAction.setSpId(spId);
        if (bwfActionDto.getSrcReAttr() != null) {
          bwfAction.setSrcReAttr(bwfActionDto.getSrcReAttr());
        }
        if (bwfActionDto.getObjReAttr() != null) {
          bwfAction.setObjReAttr(bwfActionDto.getObjReAttr());
        }
        if (bwfActionDto.getFunction() != null) {
          bwfAction.setFunction(bwfActionDto.getFunction());
        }
        if (bwfActionDto.getParam1() != null) {
          bwfAction.setParam1(bwfActionDto.getParam1());
        }
        if (bwfActionDto.getParam2() != null) {
          bwfAction.setParam2(bwfActionDto.getParam2());
        }
        if (bwfActionDto.getSpId() != null) {
          bwfAction.setSpId(bwfActionDto.getSpId());
        }
        listBwf.add(bwfAction);
      }
      if (listBwf.size() >= 1) {
        bwfActionRepository.saveAll(listBwf);
        log.info("addBwfAction success");
      }
      log.info("addBwfAction end");
    }

  }

  public ResponseEntity<CustomeResponse> selectBwfStepById (Integer stepId) {

    Optional<BwfStep> bwfStep = bwfStepRepository.selectBwfStepById(stepId);
    BwfStepResponse bwfStepResponse = bwfMapper.toBwfStepResponse(bwfStep.get());
        /*
            query BwfGroup
         */
    List<FindBwfCondGroupByStepIdProjection> group = bwfStepRepository.findBwfCondGroupByStepId(stepId);

    // Group otomatis berdasarkan condGroupId
    Map<Long, List<FindBwfCondGroupByStepIdProjection>> groupedByCondGroupId =
      group.stream().collect(Collectors.groupingBy(FindBwfCondGroupByStepIdProjection::getCondGroupId));
    //bwfCondGroupList
    List<BwfCondGroupDto> bwfCondGroupDtoList = new ArrayList<>();
    // Loop semua hasil grouping
    int count = 0;
    for (Map.Entry<Long, List<FindBwfCondGroupByStepIdProjection>> entry : groupedByCondGroupId.entrySet()) {
      Integer condGroupId = Math.toIntExact(entry.getKey());
      List<FindBwfCondGroupByStepIdProjection> items = entry.getValue();

      log.info("COND_GROUP_ID: " + condGroupId);
      items.forEach(item -> log.info("   -> " + item));

      List<BwfCondDto> listCOndDto = bwfMapper.toBwfCondDto(items);
      log.info("SortOperator" + listCOndDto.get(count).getSortOperator());
      Optional<SortOperator> sortOperator = sortOperatorRepository.findBySortOperator(listCOndDto.get(count).getSortOperator());
      listCOndDto.get(count).setSortOperatorName(sortOperator.get().getSortOperatorName());
      log.info("grouplist : " + group.size());
      BwfCondGroupDto bwfCondGroupList = new BwfCondGroupDto();
      log.info("group :" + listCOndDto.size());
      bwfCondGroupList.setBwfCondList(listCOndDto);
      bwfCondGroupDtoList.add(bwfCondGroupList);
      bwfStepResponse.setBwfCondGroupList(bwfCondGroupDtoList);
    }

        /*
            query BwfAction
        */
    List<BwfAction> listAction = bwfActionRepository.getListBwfAction(stepId.longValue());
    List<BwfActionDto> listActDto = bwfMapper.toBwfActionDtoList(listAction);
    bwfStepResponse.setBwfActionList(listActDto);

        /*
            query BwfSysAction
         */
    BwfSysActionDto listSysActionRc = null;
    Optional<BwfSysAction> listSysAction = bwfSysActionRepository.findByStepId(stepId);
    if (listSysAction.isPresent()) {
      listSysActionRc = bwfMapper.toBwfSysAction(listSysAction.get());
    }
    bwfStepResponse.setBwfSysActionDto(listSysActionRc);

    return bwfStep.map(step -> ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", bwfStepResponse)))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", new BwfStep())));
  }

  public ResponseEntity<CustomeResponse> getNotifyParamsId (Character notifyType, Integer spId) {
    var data = notifyParamsRepository.findNotifyParamsId(notifyType, spId)
      .stream()
      .map(notifyParamsMapper::dto)
      .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> getNotifyParams (String adviceCatg, Integer spId) {
    var data = adviceTypeRepository.findNotifyParamsList(adviceCatg, spId)
      .stream()
      .map(notifyParamsMapper::notifyParamsListDto)
      .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }


  private void addBwfCondGroup (List<BwfCondGroupDto> bwfCondGroupList, Long stepId, Integer spId, String comment) {
    logger.loggerInfo(TriggerService.class, "addBwfCondGroup");
    if (bwfCondGroupList != null)
//            for (int i = 0; i < bwfCondGroupList.size(); i++) {
      for (BwfCondGroupDto bwfGroup : bwfCondGroupList) {
        log.info("BwfCondGroupDto list");
        BwfCondGroup bwfCondGroup = new BwfCondGroup();
        bwfCondGroup.setStepId(stepId.intValue());
        bwfCondGroup.setSpId(spId);
        bwfCondGroup.setId(bwfConGroupRepository.findMaxId() + 1);
        bwfConGroupRepository.save(bwfCondGroup);

        for (BwfCondDto bwfCondDto : bwfGroup.getBwfCondList()) {
          log.info("getBwfCondList");
          BwfCond bwfCond = new BwfCond();
//                    Integer seq = bwfCondRepository.getMaxBwfCondId() + 1;
          BwfCondId bwfCondId = new BwfCondId();
          bwfCondId.setCondGroupId(bwfCondGroup.getId());
          bwfCondId.setSeq(bwfCondDto.getSeq());
          bwfCond.setIsConst(bwfCondDto.getIsConst());
          if (bwfCondDto.getZoneId() != null) {
            bwfCond.setZoneId(bwfCondDto.getZoneId());
          }
//                    if (bwfCondDto.getIsConst().equalsIgnoreCase("Y")) {
          if (bwfCondDto.getOperand() != null) {
            bwfCond.setOperand(bwfCondDto.getOperand());
          }
//                    }
//                    if (bwfCondDto.getIsConst().equalsIgnoreCase("N")) {
          if (bwfCondDto.getFunction() != null) {
            bwfCond.setFunction(bwfCondDto.getFunction());
          }
          if (bwfCondDto.getParam1() != null) {
            bwfCond.setParam1(bwfCondDto.getParam1());
          }
          if (bwfCondDto.getParam2() != null) {
            bwfCond.setParam2(bwfCondDto.getParam2());
          }
          if (bwfCondDto.getRParam1() != null) {
            bwfCond.setRParam1(bwfCondDto.getRParam1());
          }
          if (bwfCondDto.getRParam2() != null) {
            bwfCond.setRParam2(bwfCondDto.getRParam2());
          }
//                    }
          if (bwfCondDto.getRFunction() != null) {
            bwfCond.setRFunction(bwfCondDto.getRFunction());
          }

          bwfCond.setId(bwfCondId);
          bwfCond.setSpId(spId);
          bwfCond.setReAttr(bwfCondDto.getReAttr());
          bwfCond.setRReAttr(bwfCondDto.getRReAttr());
          if (bwfCondDto.getSortOperator() != null) {
            bwfCond.setSortOperator(bwfCondDto.getSortOperator());
            SortOperator sortOperator = new SortOperator();
            sortOperator.setSortOperator(bwfCondDto.getSortOperator());
            if (sortOperatorRepository.findBySortOperator(bwfCondDto.getSortOperator()).isEmpty()) {
              logger.loggerInfo(TriggerService.class, "addBwfCondGroup insert sortOperator");
              sortOperatorRepository.save(sortOperator);
            }
          }
          bwfCondRepository.save(bwfCond);
        }
      }
  }

  public ResponseEntity<CustomeResponse> qrySubsEvent () {
    List<QrySubsEvent> list = subsEventRepository.qrySubsEvent();
    return ResponseEntity.status(HttpStatus.OK)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, list));
  }

  public ResponseEntity<CustomeResponse> qrySortFunction () {
    List<SortFunctionProjection> list = bwfStepRepository.qrySortFunction(null, null, null, null);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
  }

  public ResponseEntity<CustomeResponse> qrySortOperator () {
    List<SortOperatorProjection> list = bwfStepRepository.qrySortOperator();
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
  }

  public ResponseEntity<CustomeResponse> qrySysActionFunction () {
    List<SysActionFunctionProjection> list = bwfStepRepository.qrySysActionFunction();
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
  }

  public ResponseEntity<CustomeResponse> qryZoneMap () {
    List<ZoneMapProjection> list = zoneMapRepository.qryZoneMap(null, null, (long) 0);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
  }

  public ResponseEntity<CustomeResponse> qryReAttrList () {
    List<ReAttrProjection> list = reAttrRepository.qryReAttrList(null, (long) 0);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
  }

  public ResponseEntity<CustomeResponse> qryBwfStepByWorkflow (Long nodeId, Long workFlowId, String sortRuleName) {
    List<StepProjection> list = bwfWorkFlowRepository.findStepsWithDetails(nodeId, workFlowId, sortRuleName);
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
  }

  public ResponseEntity<CustomeResponse> findActiveAdviceEvents () {
    List<AdviceEventProjection> list = acmAdviceRepository.findActiveAdviceEvents();
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list));
  }


  @Getter
  @Setter
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private class ResponseThreshold {
    private AcmThreshold acmThreshold;
    private AcmPcrf acmPcrf;
  }
}
