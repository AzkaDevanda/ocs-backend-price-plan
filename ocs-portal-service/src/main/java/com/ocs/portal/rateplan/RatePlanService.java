package com.ocs.portal.rateplan;

import com.ocs.portal.ValidationService;
import com.ocs.portal.common.MessageService;
import com.ocs.portal.constant.HttpStatusConstant;
import com.ocs.portal.dto.request.*;
import com.ocs.portal.dto.request.priceplan.*;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.dto.response.priceplan.ZoneMapResponse;
import com.ocs.portal.entity.*;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.mapper.pricePlan.rateplan.QryRatePlanMapper;
import com.ocs.portal.mapper.pricePlan.rateplan.QryRatePlanZoneAndMappingUnitMapper;
import com.ocs.portal.mapper.pricePlan.rateplan.QryRatePlanZoneMapper;
import com.ocs.portal.mapper.pricePlan.rateplan.ReservationRuleMapper;
import com.ocs.portal.projection.pricePlan.rateplan.ModRePricePlanProjection;
import com.ocs.portal.repository.*;
import com.ocs.portal.util.StringUtil;
import com.ocs.portal.validation.ValidationHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatePlanService {
    private static final Logger log = LoggerFactory.getLogger(RatePlanService.class);
    private static final Logger logger = LoggerFactory.getLogger(RatePlanService.class);

    @Autowired
    private RatePlanRepository ratePlanRepository;

    @Autowired
    private ReAttrRepository reAttrRepository;

    @Autowired
    private RatePlanMappingRepository ratePlanMappingRepository;

    @Autowired
    private RatePlanZoneRepository ratePlanZoneRepository;

    @Autowired
    private RePricePlanRepository rePricePlanRepository;

    @Autowired
    private RatePlanCatalogElementRepository ratePlanCatalogElementRepository;

    @Autowired
    private ZoneMapRepository zoneMapRepository;

    @Autowired
    private MappingDesTypeMasterRepository mappingDesTypeMasterRepository;

    @Autowired
    private MappingSrcTypeMasterRepository mappingSrcTypeMasterRepository;

    @Autowired
    private MappingRepository mappingRepository;

    @Autowired
    private ScriptTempletRepository scriptTempletRepository;

    @Autowired
    private RefValueRepository refValueRepository;

    @Autowired
    private SimpleParamDefineRepository simpleParamDefineRepository;

    @Autowired
    private TableParamDefineRepository tableParamDefineRepository;

    @Autowired
    private PriceParamRepository priceParamRepository;

    @Autowired
    private RatePlanParamRepository ratePlanParamRepository;

    @Autowired
    private PricePlanParamRepository pricePlanParamRepository;

    @Autowired
    private RefValueToOfferVerRepository refValueToOfferVerRepository;

    @Autowired
    private QryRatePlanMapper qryRatePlanMapper;

    @Autowired
    private QryRatePlanZoneMapper qryRatePlanZoneMapper;

    @Autowired
    private QryRatePlanZoneAndMappingUnitMapper qryRatePlanZoneAndMappingUnitMapper;

    @Autowired
    private ReservationRuleMapper reservationRuleMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    private ValidationService validationService;




    private RefValueFormulaDto buildRefValueFormula (RefValueDto refValueDto){
        if(refValueDto == null || !"4".equals(refValueDto.getRefValueType()) || StringUtil.isEmpty(refValueDto.getValueString())){
            return null;
        }
        RefValueFormulaDto refValueFormulaDto = new RefValueFormulaDto();
        logger.debug("formula valueString=[{}], refValueId=[{}]", new Object[] { refValueDto.getValueString(), refValueDto.getRefValueId() });
        String[] itemArr = StringUtil.findAll(refValueDto.getValueString(),"[\\+\\*\\(\\)/-]|\\d+");
        if(itemArr != null && itemArr.length > 0){
            for(String item : itemArr){
                if(!StringUtil.isEmpty(item)){
                    if(StringUtil.isNumeric(item.trim())){
                        refValueFormulaDto.addOperand(qryRefValue(Long.valueOf(item.trim()), Boolean.valueOf(false)));
                    }
                    else{
                        refValueFormulaDto.addOperator(item.trim());
                    }
                }
            }
        }
        return  refValueFormulaDto;
    }

    public RefValueExtendDto qryRefValue(Long refValueId, Boolean ignoreState){
        RefValueDto refValueDto = qryRefValueDto(refValueId, ignoreState);
        if(refValueDto == null){
            log.debug("Fail to find refValue by refValueId=[{}]",new Object(),refValueId);
        }
        RefValueExtendDto refValueExtendDto = reservationRuleMapper.refValueExtendDto(refValueDto);
        if(refValueExtendDto.getRefValueType().equals(4)){
            RefValueFormulaDto refValueFormulaDto = buildRefValueFormula((RefValueDto) refValueExtendDto);
            refValueExtendDto.setFormula(refValueFormulaDto);
        } else {
            if(refValueExtendDto.getRefValueType().equals(2)){
                SimpleParamDefineDto simpleParamDefineDto = qrySimpleParamDefineById(Math.toIntExact(refValueExtendDto.getSimpleParamId()));
                if(simpleParamDefineDto == null){
                    refValueExtendDto = null;
                } else{
                    refValueExtendDto.setParamName(simpleParamDefineDto.getParamName());
                }
            } else if(refValueExtendDto.getRefValueType().equals(3)){
                TableParamDefineDto tableParamDefineDto = qryTableParamDefine(refValueExtendDto.getTableParamId());
                if(tableParamDefineDto == null){
                    refValueExtendDto = null;
                } else{
                    refValueExtendDto.setParamName(tableParamDefineDto.getParamName());
                }
            }
        }
        return refValueExtendDto;
    }


    public int delPriceParamByPriceIdAndParamId(Long priceId, Long paramId){
        if(priceId == null || priceId.longValue() == -1L){
            return 0;
        }
        int refValCnt = qryRefValueCntByPriceAndParam(priceId, paramId);
        int delCnt = 0;
        if(refValCnt == 0){
            logger.debug("current param[id={}] is referenced by [{}] refValue(s) within this price[id={}].", new Object[] { paramId,
                    Integer.valueOf(refValCnt), priceId });
            PriceParamDto priceParamDto = qryPriceParamByPriceIdAndParamId(priceId, paramId);
            if(priceParamDto == null){
                return  delCnt;
            }
            delCnt = priceParamRepository.delPriceParamByPriceIdAndParamId(priceId, paramId);
        }
        return delCnt;
    }

    public int delRatePlanParamByRatePlanIdAndParamId(Long ratePlanId, Long paramId){
        if(ratePlanId == null){
            return 0;
        }
        int refValCnt = qryRefValueCntByRatePlanAndParam(ratePlanId, paramId);
        int delCnt = 0;
        if(refValCnt == 0){
            RatePlanParamDto ratePlanParamDto = qryRatePlanParamByRatePlanIdAndParamId(ratePlanId, paramId);
            if(ratePlanParamDto == null){
                return delCnt;
            }
            delCnt = ratePlanParamRepository.delRatePlanParamByRatePlanIdAndParamId(ratePlanId, paramId);
        }
        return  delCnt;
    }

    public int delPricePlanParamByOfferVerIdAndParamId(Long offerVerId, Long paramId){
        int refValCnt = qryRefValueCntByOfferVerAndParam(offerVerId, paramId);
        int delCnt = 0;
        if(refValCnt == 0){
            PricePlanParamDto pricePlanParamDto = qryPricePlanParamByOfferVerAndParamId(offerVerId, paramId);
            if(pricePlanParamDto == null){
                return delCnt;
            }
            delCnt = pricePlanParamRepository.delByOfferVerIdAndParamId(offerVerId, paramId);
        }
        return delCnt;
    }

    public PriceParamDto qryPriceParamByPriceIdAndParamId(Long priceId, Long paramId){
        PriceParamDto[] priceParamDtos = priceParamRepository.qryPriceParam(priceId, paramId);
        PriceParamDto targetParam = null;
        if(priceParamDtos != null && priceParamDtos.length > 0){
            targetParam = priceParamDtos[0];
        }
        return targetParam;
    }

    public RatePlanParamDto qryRatePlanParamByRatePlanIdAndParamId(Long ratePlanId, Long paramId){
        RatePlanParamDto[] ratePlanParamDtos = ratePlanParamRepository.qryRatePlanParam(ratePlanId, paramId);
        RatePlanParamDto targetParam = null;
        if(ratePlanParamDtos != null && ratePlanParamDtos.length > 0){
            targetParam = ratePlanParamDtos[0];
        }
        return  targetParam;
    }

    public PricePlanParamDto qryPricePlanParamByOfferVerAndParamId(Long offerVerId, Long paramId){
        PricePlanParamDto[] pricePlanParamDtos = pricePlanParamRepository.qryPricePlanParam(offerVerId, paramId);
        PricePlanParamDto targetParam = null;
        if(pricePlanParamDtos != null && pricePlanParamDtos.length > 0){
            targetParam = pricePlanParamDtos[0];
        }
        return targetParam;
    }

    public int qryRefValueCntByPriceAndParam(Long priceId, Long paramId){
        int cnt = 0;
        if (priceId == null || paramId == null)
            return cnt;
        RefValueDto[] refValueList = refValueRepository.qryRefValueByParamIdAndPriceId(paramId, priceId);
        if (refValueList != null && refValueList.length > 0)
            for (RefValueDto item : refValueList) {
                if (!"X".equals(item.getState()))
                    cnt++;
            }
        return cnt;
    }

    public int qryRefValueCntByRatePlanAndParam(Long ratePlanId, Long paramId){
        int cnt = 0;
        if (ratePlanId == null || paramId == null)
            return cnt;
        RefValueDto[] refValueList = refValueRepository.qryRefValueByParamIdAndRatePlanId(paramId, ratePlanId);
        if (refValueList != null && refValueList.length > 0)
            for (RefValueDto item : refValueList) {
                if (!"X".equals(item.getState()))
                    cnt++;
            }
        return cnt;
    }

    public int qryRefValueCntByOfferVerAndParam(Long offerVerId, Long paramId){
        int cnt = 0;
        if (offerVerId == null || paramId == null)
            return cnt;
        RefValueDto[] refValueList = refValueRepository.qryRefValueByParamIdAndOfferVerId(paramId, offerVerId);
        if (refValueList != null && refValueList.length > 0)
            for (RefValueDto item : refValueList) {
                if (!"X".equals(item.getState()))
                    cnt++;
            }
        return cnt;
    }

    public int delRefValueToOfferVerRelation(Long offerVerId, Long refValueId){
        logger.debug("delRefValueToOfferVerRelation, offerVerId=[{}], refValueId=[{}]", new Object[] { offerVerId, refValueId });
        return refValueToOfferVerRepository.delRefValueToOfferVer(refValueId, offerVerId);
    }

    public void updateParamRelationOnDelRefValue(RefValueDto refValueDto){
        if(refValueDto == null){
            return;
        }
        Long paramId = null;
        if(refValueDto.getRefValueType().equals(2)){
            paramId = refValueDto.getSimpleParamId();
        } else if(refValueDto.getRefValueType().equals(3)){
            paramId = refValueDto.getTableParamId();
        }
        if(paramId == null){
            logger.debug("deleted refValue does not refer to any param.");
            return;
        }
        delPriceParamByPriceIdAndParamId(refValueDto.getSimpleParamId(), paramId);
        delRatePlanParamByRatePlanIdAndParamId(refValueDto.getRatePlanId(), paramId);
        delPricePlanParamByOfferVerIdAndParamId(refValueDto.getOfferVerId(), paramId);
    }

    public int updateOfferVerRelationOnDelRefValue(RefValueDto refValueDto){
        if(refValueDto == null){
            logger.debug("inputed refValueDto is null, exit.");
            return 0;
        }
        return delRefValueToOfferVerRelation(null, refValueDto.getRefValueId());
    }

    public int delRefValueDto(Long refValueId){
        RefValueDto oldRefValueDto = qryRefValueDto(refValueId, false);
        if(oldRefValueDto == null){
            return 0;
        }
        int cnt = refValueRepository.updateState(refValueId,"X");
        updateParamRelationOnDelRefValue(oldRefValueDto);
        updateOfferVerRelationOnDelRefValue(oldRefValueDto);
        return cnt;
    }

    private void cascadeDelRefValueInFormula(RefValueFormulaDto formula){
        if(formula == null){
            return;
        }
        List<RefValueExtendDto> refValueExtendDto = formula.getAllLevelRefValueInFormula();
        for(RefValueExtendDto refVal : refValueExtendDto){
            delRefValueDto(refVal.getRefValueId());
        }
    }

    public void delRefValue (Long refValueId){
        RefValueExtendDto oldRefValue = qryRefValue(refValueId, false);
        if(oldRefValue == null){
            logger.debug("RefValue by refValueId=[{}] does not exist.", new Object[] { refValueId });
            return;
        }
        if(oldRefValue.getRefValueType().equals(4)){
            RefValueFormulaDto refValueFormulaDto = oldRefValue.getFormula();
            cascadeDelRefValueInFormula(refValueFormulaDto);
        }
        delRefValueDto(refValueId);
    }

    private static String replaceNewRefValueFun(String ruleScript, String startStr, String endStr, String targetStr) {
        if (StringUtil.isEmpty(ruleScript) || ruleScript.indexOf(startStr) < 0)
            return ruleScript;
        StringBuffer sb = new StringBuffer();
        int startIndex = ruleScript.indexOf(startStr) + startStr.length();
        String subFirstStr = ruleScript.substring(ruleScript.indexOf(startStr) + startStr.length(), ruleScript.length());
        String subSecondStr = subFirstStr.substring(0, subFirstStr.indexOf(endStr));
        int endIndex = startIndex + subSecondStr.length();
        if (endIndex < ruleScript.length()) {
            sb.append(ruleScript.substring(0, startIndex)).append(targetStr).append(ruleScript.substring(endIndex, ruleScript.length()));
            return sb.toString();
        }
        return ruleScript;
    }

    public void addRefValueInRuleScript(ModRePricePlanDto modRePricePlanDto, String inputRefValueBoField, Long priceId, Long ratePlanId){
        if(modRePricePlanDto == null){
            return;
        }
        String funPrefix = modRePricePlanDto.getFunPrefix();
        if(StringUtil.isEmpty(funPrefix)){
            funPrefix = "r.event.";
        }
        String ruleScript = modRePricePlanDto.getRuleScript();
        String scriptPage = modRePricePlanDto.getScriptPage();
        if(StringUtil.isEmpty(ruleScript)){
            return;
        }
        List<RefValueExtendDto> refValueList = modRePricePlanDto.getInputRefValueBoField();
        if(refValueList != null && !refValueList.isEmpty()){
            for(RefValueExtendDto refValueBo : refValueList){
                addRefValueBo(refValueBo,priceId,ratePlanId);
                ruleScript = replaceNewRefValueFun(ruleScript, "r.event.GetProrMonthFee(", ")", String.valueOf(refValueBo
                        .getRefValueId()));
                ruleScript = ruleScript.replace("&" + refValueBo.getPythonParamName() + "&", "r.event.GetValueByRefID(" + refValueBo
                        .getRefValueId() + ")");
                if(StringUtil.isNotEmpty(scriptPage)){
                    scriptPage = scriptPage.replace("#" + refValueBo.getPythonParamName() + "#",String.valueOf(refValueBo.getRefValueId()));
                }
            }
        }
    }

    public Long addRefValueBo(RefValueExtendDto refValueExtendDto, Long priceId, Long ratePlanId){
        if(refValueExtendDto == null){
            return null;
        }
        if(priceId != null){
            refValueExtendDto.setPriceId(priceId);
        }
        if(ratePlanId != null){
            refValueExtendDto.setRatePlanId(ratePlanId);
        }
        addRefValue(refValueExtendDto);
        return refValueExtendDto.getRefValueId();
    }

    public void addRefValue(RefValueExtendDto refValueExtendDto){
        if(refValueExtendDto == null){
            return;
        }
        if(refValueExtendDto.getRefValueType().equals(4)){
            RefValueFormulaDto formula = refValueExtendDto.getFormula();
            cascadeAddRefValueInFormula(formula,refValueExtendDto.getRatePlanId(),refValueExtendDto.getPriceId());
            refValueExtendDto.setValueString(formula.getRefValueFormulaStr());
        }
        addRefValueDto((RefValueDto) refValueExtendDto);
    }

    private void cascadeAddRefValueInFormula(RefValueFormulaDto formula, Long ratePlanId, Long priceId) {
        if (formula == null)
            return;
        logger.debug("cascadeAddRefValueInFormula, formula=[{}]", new Object[] { formula.getRefValueFormulaStr() });
        List<RefValueExtendDto> refValList = formula.getAllLevelRefValueInFormula();
        for (RefValueExtendDto refVal : refValList) {
            refVal.setRatePlanId(ratePlanId);
            refVal.setPriceId(priceId);
            addRefValueDto((RefValueDto)refVal);
        }
    }

    @Transactional(rollbackFor =  Exception.class)
    public void addRefValueDto(RefValueDto refValueDto){
        if (refValueDto == null)
            return;
        Long refValueId = validationService.getNextValFromDual("REF_VALUE_ID_SEQ");
        LocalDate now = LocalDate.now();
        refValueDto.setState("A");
        refValueDto.setStateDate(now);
        refValueDto.setCreatedDate(now);
        refValueDto.setRefValueId(refValueId);
        RefValue refValue = reservationRuleMapper.toEntity(refValueDto);
        refValueRepository.save(refValue);
        checkRefValueAfterChange(refValueDto);
        updateParamRelationOnAddRefValue(refValueDto);
        updateOfferVerRelationOnAddRefValue(refValueDto);
    }

    public void checkRefValueAfterChange(RefValueDto refValueDto){
        if (refValueDto == null)
            return;
        logger.debug("checkRefValueAfterChange, RefValue[{}]", new Object[] { refValueDto });
        SimpleParamDefineDto simpleParamDto = null;
        TableParamDefineDto tableParamDto = null;
        if ("2".equals(refValueDto.getRefValueType())) {
            simpleParamDto = qrySimpleParamDefineById(Math.toIntExact(refValueDto.getSimpleParamId()));
        } else if ("3".equals(refValueDto.getRefValueType())) {
            tableParamDto = qryTableParamDefine(refValueDto.getTableParamId());
        }
        if (simpleParamDto != null && "P".equals(simpleParamDto.getIsGlobal())) {
            long refCnt = refValueRepository.qryRefCntByMultipleOfferVer(simpleParamDto.getSimpleParamId()).longValue();
            if (refCnt > 1L)
                throw new ValidationHandler(messageService.getMessage("S-PRD-50968"));
        }
        if (tableParamDto != null && "P".equals(tableParamDto.getIsGlobal())) {
            long refCnt = refValueRepository.qryRefCntByMultipleOfferVer(tableParamDto.getTableParamId()).longValue();
            if (refCnt > 1L)
                throw new ValidationHandler(messageService.getMessage("S-PRD-50968"));
        }
    }

    public void updateParamRelationOnAddRefValue(RefValueDto refValDto) {
        if (refValDto == null)
            return;
        Long paramId = null;
        String paramType = "";
        if ("2".equals(refValDto.getRefValueType())) {
            paramType = "1";
            paramId = refValDto.getSimpleParamId();
        } else if ("3".equals(refValDto.getRefValueType())) {
            paramType = "2";
            paramId = refValDto.getTableParamId();
        }
        if (paramId == null)
            return;
        updatePriceParam(refValDto, paramId, paramType);
        updateRatePlanParam(refValDto, paramId, paramType);
        updatePricePlanParam(refValDto, paramId, paramType);
    }

    private void updatePriceParam(RefValueDto refValDto, Long paramId, String paramType) {
        if (refValDto.getPriceId() != null && refValDto.getPriceId() > 0) {
            PriceParamDto priceParamDto = new PriceParamDto();
            priceParamDto.setPriceId(refValDto.getPriceId());
            priceParamDto.setSpId(refValDto.getSpId());
            priceParamDto.setParamType(paramType);
            if ("1".equals(paramType)) {
                priceParamDto.setSimpleParamId(paramId);
            } else {
                priceParamDto.setTableParamId(paramId);
            }
            addPriceParam(priceParamDto);
        }
    }

    @Transactional(rollbackFor =  Exception.class)
    public void addPriceParam(PriceParamDto priceParamDto) {
        if (priceParamDto == null){
            return;
        }
        Long paramId = priceParamDto.getSimpleParamId();
        if ("2".equals(priceParamDto.getParamType()))
            paramId = priceParamDto.getTableParamId();
        if (!isPriceParamExist(priceParamDto.getPriceId(), paramId)) {
            priceParamDto.setPriceParamId(validationService.getNextValFromDual("TARIFF_PARAM_MAPPING_ID_SEQ"));
            PriceParam priceParam = reservationRuleMapper.toEntityPriceParam(priceParamDto);
            priceParamRepository.save(priceParam);
        } else {
            logger.debug("PriceParam already exists. priceId=[{}], paramId=[{}]", new Object[] { priceParamDto.getPriceId(), paramId });
        }
    }

    public boolean isPriceParamExist(Long priceId, Long paramId) {
        PriceParamExtendDto[] paramArr = qryPriceParamByPrceId(priceId);
        boolean isExist = false;
        if (paramArr != null && paramArr.length > 0)
            for (PriceParamExtendDto param : paramArr) {
                if (param.getParamId().equals(paramId)) {
                    isExist = true;
                    break;
                }
            }
        return isExist;
    }

    public PriceParamExtendDto[] qryPriceParamByPrceId (Long priceId){
        PriceParamDto[] priceParamDtoList = priceParamRepository.qryPriceParam(priceId,null);
        List<PriceParamExtendDto> priceParamList = null;
        if(priceParamDtoList != null && priceParamDtoList.length > 0){
            priceParamList = new ArrayList<>();
            PriceParamExtendDto priceParam = null;
            PriceParamDto[] arrayOfPriceParamDto = priceParamDtoList;
            int i = arrayOfPriceParamDto.length;
            byte b = 0;
            while(true) {
                if(b < i){
                    PriceParamDto paramDto = arrayOfPriceParamDto[b];
                    priceParam = new PriceParamExtendDto();
                    priceParam.setPriceParamId(paramDto.getPriceParamId());
                    priceParam.setPriceId(paramDto.getPriceId());
                    priceParam.setParamType(paramDto.getParamType());
                    if ("1".equals(paramDto.getParamType())) {
                        if(paramDto.getSimpleParamId() == null) {
                            this.logger.debug("NOTE: fail to get paramId: paramDto=[{}]", new Object[] { paramDto });
                        } else{
                            SimpleParamDefineDto simpleParamDefineDto = qrySimpleParamDefineById(Math.toIntExact(paramDto.getSimpleParamId()));
                            if("X".equals(simpleParamDefineDto.getState())){
                                this.logger.debug("NOTE: this simple param is invalid, paramId=[{}]", new Object[] { simpleParamDefineDto.getSimpleParamId() });
                            } else{
                                priceParam.setParamId(paramDto.getSimpleParamId());
                                priceParam.setParamName(simpleParamDefineDto.getParamName());
                                priceParam.setParamCode(simpleParamDefineDto.getParamCode());
                                priceParam.setDefValue(simpleParamDefineDto.getDefValue());
                                priceParam.setIsGlobal(simpleParamDefineDto.getIsGlobal());
                                priceParamList.add(priceParam);
                            }
                        }
                        continue;
                    }
                    if("2".equals(paramDto.getParamType())){
                        if(paramDto.getTableParamId() == null) {
                            this.logger.debug("NOTE: fail to get paramId: paramDto=[{}]", new Object[] { paramDto });
                        } else{
                            TableParamDefineDto tableParamDefineDto = qryTableParamDefine(paramDto.getTableParamId());
                            if("X".equals(tableParamDefineDto.getState())){
                                this.logger.debug("NOTE: this table param is invalid, paramId=[{}]", new Object[] { tableParamDefineDto.getTableParamId() });
                            } else{
                                priceParam.setParamId(paramDto.getTableParamId());
                                priceParam.setParamName(tableParamDefineDto.getParamName());
                                priceParam.setParamCode(tableParamDefineDto.getParamCode());
                                priceParam.setIsGlobal(tableParamDefineDto.getIsGlobal());
                                priceParamList.add(priceParam);
                            }
                        }
                        continue;
                    }
                } else {
                    break;
                }
                priceParamList.add(priceParam);
                b++;
            }
        }
        if (priceParamList != null && !priceParamList.isEmpty())
            return priceParamList.<PriceParamExtendDto>toArray(new PriceParamExtendDto[0]);
        return null;
    }

    private void updateRatePlanParam(RefValueDto refValDto, Long paramId, String paramType) {
        if (null != refValDto.getRatePlanId()) {
            RatePlanParamDto ratePlanParamDto = new RatePlanParamDto();
            ratePlanParamDto.setRatePlanId(refValDto.getRatePlanId());
            ratePlanParamDto.setSpId(refValDto.getSpId());
            ratePlanParamDto.setParamType(paramType);
            if ("1".equals(paramType)) {
                ratePlanParamDto.setSimpleParamId(paramId);
            } else {
                ratePlanParamDto.setTableParamId(paramId);
            }
            addRatePlanParam(ratePlanParamDto);
        }
    }

    @Transactional(rollbackFor =  Exception.class)
    public void addRatePlanParam(RatePlanParamDto ratePlanParamDto){
        if(ratePlanParamDto == null){
            return;
        }
        Long paramId = ratePlanParamDto.getSimpleParamId();
        if ("2".equals(ratePlanParamDto.getParamType()))
            paramId = ratePlanParamDto.getTableParamId();
        if (!isRatePlanParamExist(ratePlanParamDto.getRatePlanId(), paramId)) {
            ratePlanParamDto.setRatePlanParamId(validationService.getNextValFromDual("TARIFF_PARAM_MAPPING_ID_SEQ"));
            RatePlanParam ratePlanParam = reservationRuleMapper.toEntityRatePlanParam(ratePlanParamDto);
            ratePlanParamRepository.save(ratePlanParam);
        } else {
            logger.debug("RatePlanParam already exits. ratePlanId=[{}], paramId=[{}]", new Object[] { ratePlanParamDto.getRatePlanId(), paramId });
        }
    }

    public boolean isRatePlanParamExist(Long ratePlanId, Long paramId) {
        RatePlanParamExtendDto[] paramArr = qryRatePlanParamByRatePlanId(ratePlanId);
        boolean isExist = false;
        if (paramArr != null && paramArr.length > 0)
            for (RatePlanParamExtendDto param : paramArr) {
                if (param.getParamId() != null && param.getParamId().equals(paramId)) {
                    isExist = true;
                    break;
                }
            }
        return isExist;
    }

    public RatePlanParamExtendDto[] qryRatePlanParamByRatePlanId(Long ratePlanId) {
        RatePlanParamDto[] ratePlanParamDtoList = ratePlanParamRepository.qryRatePlanParam(ratePlanId,null);
        List<RatePlanParamExtendDto> ratePlanParamList = null;
        if(ratePlanParamDtoList != null && ratePlanParamDtoList.length > 0){
            ratePlanParamList = new ArrayList<>();
            RatePlanParamExtendDto ratePlanParam = null;
            RatePlanParamDto[] arrayOfRatePlanParamDto = ratePlanParamDtoList;
            int i = arrayOfRatePlanParamDto.length;
            byte b = 0;
            while(true){
                if(b  > i){
                    RatePlanParamDto paramDto = arrayOfRatePlanParamDto[b];
                    ratePlanParam = new RatePlanParamExtendDto();
                    ratePlanParam.setRatePlanId(ratePlanId);
                    ratePlanParam.setRatePlanParamId(paramDto.getRatePlanParamId());
                    ratePlanParam.setParamType(paramDto.getParamType());
                    if("1".equals(paramDto.getParamType())){
                        if(paramDto.getSimpleParamId() == null){
                            this.logger.debug("NOTE: fail to get paramId: paramDto=[{}]", new Object[] { paramDto });
                        } else {
                            SimpleParamDefineDto simpleParamDefineDto = qrySimpleParamDefineById(Math.toIntExact(paramDto.getSimpleParamId()));
                            if("X".equals(simpleParamDefineDto.getState())){
                                this.logger.debug("NOTE: this simple param is invalid, paramId=[{}]", new Object[] { simpleParamDefineDto.getSimpleParamId() });
                            } else {
                                ratePlanParam.setParamId(paramDto.getSimpleParamId());
                                ratePlanParam.setParamName(simpleParamDefineDto.getParamName());
                                ratePlanParam.setParamCode(simpleParamDefineDto.getParamCode());
                                ratePlanParam.setDefValue(simpleParamDefineDto.getDefValue());
                                ratePlanParam.setIsGlobal(simpleParamDefineDto.getIsGlobal());
                                ratePlanParamList.add(ratePlanParam);
                            }
                        }
                        continue;
                    }
                    if("2".equals(paramDto.getParamType())){
                        if(paramDto.getTableParamId() == null){
                            this.logger.debug("NOTE: fail to get paramId: paramDto=[{}]", new Object[] { paramDto });
                        } else {
                            TableParamDefineDto tableParamDefineDto = qryTableParamDefine(paramDto.getTableParamId());
                            if("X".equals(tableParamDefineDto.getState())){
                                this.logger.debug("NOTE: this table param is invalid, paramId=[{}]", new Object[] { tableParamDefineDto.getTableParamId() });
                            } else {
                                ratePlanParam.setParamId(paramDto.getTableParamId());
                                ratePlanParam.setParamName(tableParamDefineDto.getParamName());
                                ratePlanParam.setParamCode(tableParamDefineDto.getParamCode());
                                ratePlanParam.setIsGlobal(tableParamDefineDto.getIsGlobal());
                                ratePlanParamList.add(ratePlanParam);
                            }
                        }
                        continue;
                    }
                } else {
                    break;
                }
                ratePlanParamList.add(ratePlanParam);
                b++;
            }
        }
        if (ratePlanParamList != null && !ratePlanParamList.isEmpty())
            return ratePlanParamList.<RatePlanParamExtendDto>toArray(new RatePlanParamExtendDto[0]);
        return null;
    }

    private void updatePricePlanParam(RefValueDto refValDto, Long paramId, String paramType) {
        if (refValDto.getOfferVerId() != null) {
            PricePlanParamDto pricePlanParamDto = new PricePlanParamDto();
            pricePlanParamDto.setInsideFlag("Y");
            pricePlanParamDto.setOfferVerId(refValDto.getOfferVerId());
            pricePlanParamDto.setParamType(paramType);
            pricePlanParamDto.setSpId(refValDto.getSpId());
            if ("1".equals(paramType)) {
                pricePlanParamDto.setSimpleParamId(paramId);
            } else {
                pricePlanParamDto.setTableParamId(paramId);
            }
            addPricePlanParam(pricePlanParamDto);
        }
    }

    public void addPricePlanParam(PricePlanParamDto pricePlanParamDto){
        if (pricePlanParamDto == null)
            return;
        logger.debug("addPricePlanParam: [{}]", new Object[] { pricePlanParamDto });
        Long paramId = pricePlanParamDto.getSimpleParamId();
        if ("2".equals(pricePlanParamDto.getParamType()))
            paramId = pricePlanParamDto.getTableParamId();
        PricePlanParamDto oldPricePlanParam = qryPricePlanParamByOfferVerAndParamId(pricePlanParamDto.getOfferVerId(), paramId);
        if (oldPricePlanParam == null) {
            pricePlanParamDto.setPricePlanParamId(validationService.getNextValFromDual("TARIFF_PARAM_MAPPING_ID_SEQ"));
            PricePlanParam pricePlanParam = reservationRuleMapper.toEntityPricePlanParam(pricePlanParamDto);
            pricePlanParamRepository.save(pricePlanParam);
        } else if (oldPricePlanParam.getInsideFlag().equals(pricePlanParamDto.getInsideFlag())) {
            pricePlanParamDto.setPricePlanParamId(oldPricePlanParam.getPricePlanParamId());
            modPricePlanParam(pricePlanParamDto);
        }
    }

    public void modPricePlanParam(PricePlanParamDto pricePlanParamDto) {
        if (pricePlanParamDto == null)
            return;
        PricePlanParam pricePlanParam = pricePlanParamRepository.findById(Math.toIntExact(pricePlanParamDto.getPricePlanParamId())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatusConstant.NOT_FOUND_MESSAGE));
        reservationRuleMapper.updateEntityFromDto(pricePlanParamDto,pricePlanParam);
        pricePlanParamRepository.save(pricePlanParam);
    }

    public void updateOfferVerRelationOnAddRefValue(RefValueDto refValueDto){
        if(refValueDto == null){
            return;
        }
        RefValueToOfferVerDto refValueToOfferVerDto = null;
        if(refValueDto.getOfferVerId() == null || refValueDto.getOfferVerId().longValue() == -1L){
            if(refValueDto.getRatePlanId() != null && refValueDto.getRatePlanId().longValue() != -1L){
                RatePlanMappingDto[] ratePlanMappingArr = ratePlanMappingRepository.qryRatePlanMappingByRatePlanId(refValueDto.getRatePlanId());
                for(RatePlanMappingDto ratePlanMappingDto : ratePlanMappingArr){
                    refValueToOfferVerDto = new RefValueToOfferVerDto();
                    refValueToOfferVerDto.setOfferVerId(ratePlanMappingDto.getOfferVerId());
                    refValueToOfferVerDto.setRefValueId(refValueDto.getRefValueId());
                    refValueToOfferVerDto.setSpId(refValueDto.getSpId());
                    addRefValueToOfferVer(refValueToOfferVerDto);
                }
            } else {
                refValueToOfferVerDto = new RefValueToOfferVerDto();
                refValueToOfferVerDto.setOfferVerId(Long.valueOf(-1L));
                refValueToOfferVerDto.setRefValueId(refValueDto.getRefValueId());
                refValueToOfferVerDto.setSpId(refValueDto.getSpId());
                addRefValueToOfferVer(refValueToOfferVerDto);
            }
        } else {
            refValueToOfferVerDto = new RefValueToOfferVerDto();
            refValueToOfferVerDto.setOfferVerId(refValueDto.getOfferVerId());
            refValueToOfferVerDto.setRefValueId(refValueDto.getRefValueId());
            refValueToOfferVerDto.setSpId(refValueDto.getSpId());
            addRefValueToOfferVer(refValueToOfferVerDto);
        }
    }

    private void addRefValueToOfferVer(RefValueToOfferVerDto refValueToOfferVerDto) {
        if (refValueToOfferVerDto == null)
            return ;
        if (refValueToOfferVerRepository.qryRefValueToOfferVer(refValueToOfferVerDto.getRefValueId(), refValueToOfferVerDto
                .getOfferVerId()) != null) {
            logger.debug("the same RefValueToOfferVerDto exist. Exit without adding.");
            return;
        }
        reservationRuleMapper.toEntityRefValueOfferVer(refValueToOfferVerDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateScriptPage(ModRePricePlanDto dto) {

        if (dto == null || dto.getReId() == null || dto.getOfferVerId() == null) {
            return 0;
        }
        byte[] scriptPageBytes = null;
        if (StringUtil.isNotEmpty(dto.getScriptPage())) {
            scriptPageBytes = dto.getScriptPage()
                    .getBytes(StandardCharsets.UTF_8);
        }
        return rePricePlanRepository.updateScriptPage(scriptPageBytes, dto.getReId(), dto.getOfferVerId()
        );
    }


    public void modScriptPage(ModRePricePlanDto modRePricePlanDto) {
        updateScriptPage(modRePricePlanDto);
    }


    @Transactional(rollbackFor =  Exception.class)
    public ResponseEntity<CustomeResponse> modRePricePlan(ModRePricePlanDto modRePricePlanDto){
        Optional<ModRePricePlanProjection> modRePricePlanProjection = rePricePlanRepository.findByReIdAndOfferVerId(modRePricePlanDto.getReId(), modRePricePlanDto.getOfferVerId());

        if(!modRePricePlanProjection.isEmpty() && modRePricePlanProjection != null){
            String oldRuleScript = modRePricePlanDto.getRuleScript();
            if(oldRuleScript != null){
                String refValIdPatter = "(?<=r.event.GetValueByRefID\\()\\d+(?=\\))";
                String[] refValIdStrArr = StringUtil.findAll(oldRuleScript, refValIdPatter);
                if(refValIdStrArr != null && refValIdStrArr.length > 0){
                    Long refValueId = null;
                    for (String refValueIdStr : refValIdStrArr) {
                        refValueId = Long.valueOf(refValueIdStr);
                        delRefValue(refValueId);
                    }
                }
            }
        }
        addRefValueInRuleScript(modRePricePlanDto,"PYTHON_REF_VALUE",null,null);
        if(modRePricePlanProjection == null){
            RePricePlan rePricePlan = reservationRuleMapper.toEntityRePricePlan(modRePricePlanDto);
            rePricePlanRepository.save(rePricePlan);
        } else{
            RePricePlan rePricePlan = rePricePlanRepository.findById_ReIdAndId_OfferVerId(modRePricePlanDto.getReId(), modRePricePlanDto.getOfferVerId()).orElseThrow(() -> new ValidationHandler("RE_PRICE_PLAN not found"));;
            reservationRuleMapper.updateEntityRePricePlan(modRePricePlanDto, rePricePlan);
            rePricePlanRepository.save(rePricePlan);
        }
        modScriptPage(modRePricePlanDto);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }




    @Transactional
    public BaseResponseDto addRatePlan(RatePlanDto ratePlanDto) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        Integer offerVerId = ratePlanDto.getOfferVerId();
        Integer reId = ratePlanDto.getReId();
        // Validasi jumlah maksimum rate plan per price plan version
        long maxCount = 10L; // tergantung konfigurasi database
        if (reId != null && offerVerId != null) {
            Long count = ratePlanRepository.countByReIdAndOfferVerId(reId, offerVerId);
            if (count != null && count >= maxCount) {
                throw new ValidationHandler(messageService.getMessage("S-PRD-51101"));
            }
        }

        try {
//
            // INSERT RATE_PLAN
            // builder set entity
            RatePlan ratePlan = RatePlan.builder()
                    .reId(ratePlanDto.getReId())
                    .offerVerId(ratePlanDto.getOfferVerId())
                    .ratePlanName(ratePlanDto.getRatePlanName())
                    .ratePlanCode(ratePlanDto.getRatePlanCode())
                    .ratePlanType(ratePlanDto.getRatePlanType())
                    .remarks(ratePlanDto.getRemarks())
                    .templateFlag(ratePlanDto.getTemplateFlag() != null ? (char) ("Y".equals(ratePlanDto.getTemplateFlag()) ? 'Y' : 'N') : 'N')
                    .spId(ratePlanDto.getSpId()).build();
            ratePlanRepository.save(ratePlan);
            logger.info("::: RATE PLAN successfully saved into database ::");
            Integer ratePlanId = ratePlan.getId();

//

            // INSERT RATE_PLAN_MAPPING
            RatePlanMappingId ratePlanMappingId = new RatePlanMappingId();
            ratePlanMappingId.setRatePlanId(ratePlanId);
            ratePlanMappingId.setOfferVerId(ratePlan.getOfferVerId());
            ratePlanMappingId.setReId(ratePlan.getReId());
            RatePlanMapping ratePlanMapping = new RatePlanMapping();
            ratePlanMapping.setId(ratePlanMappingId);
            ratePlanMapping.setSpId(ratePlanDto.getSpId());

            // PRIORITY
            Integer priority = ratePlanMappingRepository.getMaxPriority();
            ratePlanMapping.setPriority(priority != null ? priority + 1 : 1);
            ratePlanMappingRepository.save(ratePlanMapping);
            logger.info("::: RATE PLAN MAPPING successfully saved into database ::");

            baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
            baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
            baseResponseDto.setData(ratePlanDto);
        } catch (Exception e) {
            log.error("Error saat menambahkan rate plan: ", e);
//            throw new ValidationHandler(EnumRC.CREATE_FAILED.getMessage());
        }
        return baseResponseDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> updateRatePlan(Integer ratePlanId, UpdateRatePlanDto dto) {

        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

        ratePlan.setRatePlanName(dto.getRatePlanName());
        ratePlan.setRatePlanCode(dto.getRatePlanCode());
        ratePlan.setRemarks(dto.getRemarks());
        ratePlanRepository.save(ratePlan);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, ratePlan));

    }

    @Transactional
    public ResponseEntity<CustomeResponse> updatePriorityRatePlan(Integer ratePlanId, Integer newPriority) {

        RatePlanMapping mapping = ratePlanMappingRepository.findByRatePlanId(ratePlanId);

        if (mapping == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(404, HttpStatusConstant.NOT_FOUND_MESSAGE, mapping));
        }

        Integer oldPriority = mapping.getPriority();

        if (oldPriority.equals(newPriority)) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, messageService.getMessage("S-RES-70004"), null));
        }

        if (newPriority < oldPriority) {
            // Geser item lain ke atas
            ratePlanMappingRepository.shiftPriorityUp(newPriority, oldPriority);
        } else {
            // Geser item lain ke bawah
            ratePlanMappingRepository.shiftPriorityDown(oldPriority, newPriority);
        }

        // Update priority item utama
        mapping.setPriority(newPriority);
        ratePlanMappingRepository.save(mapping);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    public BaseResponseDto getRatePlanById(Integer ratePlanId) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        baseResponseDto.setData(ratePlan);
        return baseResponseDto;
    }

    public ResponseEntity<CustomeResponse> getRatePlanByOfferVerId(Integer offerVerId, Integer reId, Integer spId, String keyWords) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        var data = ratePlanRepository.findRatePlanByOfferId(offerVerId, reId, spId, keyWords)
                .stream()
                .map(qryRatePlanMapper::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }


    @Transactional
    public ResponseEntity<CustomeResponse> deleteRatePlan(Integer ratePlanId) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        RatePlanMapping ratePlanMapping = ratePlanMappingRepository.findByRatePlanId(ratePlanId);

        ratePlanMappingRepository.callRatePlanMappingStoreProcedure(ratePlanMapping.getId().getRatePlanId(), ratePlanMapping.getId().getReId(), ratePlanMapping.getId().getOfferVerId());
        ratePlanRepository.findById(ratePlanId).ifPresent(ratePlan -> ratePlanRepository.callDeleteRatePlanProcedure(ratePlan.getId()));

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    public SimpleParamDefineDto qrySimpleParamDefineById(Integer simpleParamId){
        return simpleParamDefineRepository.findById(simpleParamId).filter(entity -> !"X".equals(entity.getState())).map(reservationRuleMapper::toDto).orElse(null);
    }

    public TableParamDefineDto qryTableParamDefine(Long tableParamId){
        return tableParamDefineRepository.findById(tableParamId).filter(entity -> !"X".equals(entity.getState())).map(reservationRuleMapper::toDto).orElse(null);
    }

    public RefValueDto qryRefValueDto (Long refValueId, Boolean ignoreState){
        RefValueDto refValueDto = refValueRepository.qryRefValue(refValueId);
        if(refValueDto != null && (ignoreState.booleanValue() || refValueDto.getState().equals("A"))){
            return refValueDto;
        }
        return null;
    }


}

