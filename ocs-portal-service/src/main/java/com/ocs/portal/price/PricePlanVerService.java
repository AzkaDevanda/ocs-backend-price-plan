package com.ocs.portal.price;

import com.ocs.portal.Rateplan.RatePlanService;
import com.ocs.portal.common.MessageService;
import com.ocs.portal.constant.HttpStatusConstant;
import com.ocs.portal.dto.request.*;
import com.ocs.portal.dto.response.priceVer.*;
import com.ocs.portal.entity.*;
import com.ocs.portal.mapper.pricePlan.price.*;
import com.ocs.portal.repository.*;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.mapper.pricePlan.AccumulationTypeMapper;
import com.ocs.portal.projection.pricePlan.price.PriceRatingProjection;
import com.ocs.portal.projection.pricePlan.price.QryTimeUnitListProjection;
import com.ocs.portal.utils.GeneratorXml;
import com.ocs.portal.validation.ValidationHandler;
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

    // ADD SUBSCRIPTION PRICE
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

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(400, e.getMessage(), null));
        }
        return createSuccessResponse();
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

        return createSuccessResponse();
    }
//1 price&benefit 8 nyimpen acct item type 5 resouce id
// re -> eventl

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

//    if (mappingUnitRepository.countMappingUnitByRatePlanId(ratePlanId) > 0) {
////      mappingService.getMappingPriceVerAndPrice(ratePlanId);
////
////      baseResponseDto.setData(mappingService.getMappingPriceVerAndPrice(ratePlanId));
//      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
//      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
//
//      return baseResponseDto;
//    }

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
            String formatted = String.format("%s / %d %s ", p.getValueString(),
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

}