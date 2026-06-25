package com.ocs.portal.priceplan;


import com.ocs.portal.dto.request.TimeSpanNameDto;
import com.ocs.portal.dto.request.UpdateAcmCalcDto;
import com.ocs.portal.dto.request.UpdateAcmUpDto;
import com.ocs.portal.dto.request.UpdateRankPriceDto;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.dto.response.priceVer.GetListAcmCalc;
import com.ocs.portal.dto.response.priceVer.GetListAcmPriceDto;
import com.ocs.portal.dto.response.priceVer.GetListRankPrice;
import com.ocs.portal.dto.response.rankUp.RatableResourceResponse;
import com.ocs.portal.entity.*;
import com.ocs.portal.repository.*;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.mapper.pricePlan.price.QryUpRuleMapper;
import com.ocs.portal.validation.ValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RankPriceService {

    @Autowired
    RefValueRepository refValueRepository;

    @Autowired
    RankUpRepository rankUpRepository;

    @Autowired
    AcmUpRepository acmUpRepository;

    @Autowired
    CalculateRepository calculateRepository;

    @Autowired
    PriceTaxRepository priceTaxRepository;

    @Autowired
    TimeSpanUpRepository timeSpanUpRepository;

    @Autowired
    RatableResourceRepository ratableResourceRepository;

    @Autowired
    AcmCalcRepository acmCalcRepository;

    @Autowired
    TimeSpanRepository timeSpanRepository;

    @Autowired
    UpRuleRepository upRuleRepository;

    @Autowired
    QryUpRuleMapper qryUpRuleMapper;

    @Autowired
    private OpRepository opRepository;

//    private static RefValue getRefValueid(AcmCalcDto acmCalcDto) {
//        RefValue refValue = new RefValue();
//        Integer ratePlanId = refValue.getId();
//
//        refValue.setPriceId(Long.valueOf(acmCalcDto.getPriceId()));
//        refValue.setRatePlanId(acmCalcDto.getRatePlanId());
//        refValue.setOfferVerId(acmCalcDto.getOfferVerId());
//        refValue.setValueString(String.valueOf(acmCalcDto.getResourceId()));
//        refValue.setSpId(0);
//        refValue.setRatePrecision('0');
//        refValue.setRefValueType('5');
//        refValue.setState(acmCalcDto.getState());
//        refValue.setCreatedDate(acmCalcDto.getCreatedDate());
//        refValue.setStateDate(acmCalcDto.getStateDate());
//        refValue.setStaffId(acmCalcDto.getStaffId());
//
//        return refValue;
//    }

//    private static RefValue getRefValue(AcmUpDto acmUpDto) {

    /// /        RefValue refValue = new RefValue();
    /// /        Integer ratePlanId = refValue.getRatePlanId();
    /// /        Integer inputRatePlanId = acmUpDto.getRatePlanId();
    /// /
    /// /        if ((ratePlanId == null || ratePlanId == -1) && inputRatePlanId != null && inputRatePlanId != -1) {
    /// /            refValue.setRatePlanId(inputRatePlanId);
    /// /        }
    /// /
    /// /        refValue.setPriceId(Long.valueOf(acmUpDto.getPriceId()));
    /// /        refValue.setOfferVerId(acmUpDto.getOfferVerId());
    /// /        refValue.setValueString(String.valueOf(acmUpDto.getPrice()));
    /// /        ;
    /// /        refValue.setSpId(0);
    /// /        refValue.setRatePrecision('0');
    /// /        refValue.setRefValueType('1');
    /// /        refValue.setState(acmUpDto.getState());
    /// /        refValue.setCreatedDate(LocalDate.now());
    /// /
    /// /        return refValue;
    /// /    }

//    public BaseResponseDto addRankUp(RankDto rankDto) {
//        BaseResponseDto baseResponseDto = new BaseResponseDto();
//        RefValue refValue = refValueRepository.selectRefValueByValueString(rankDto.getRefValue()).orElseThrow(() -> new ValidationHandler(EnumRC.DATA_NOT_FOUND.getMessage()));
//        refValue.setPriceId(rankDto.getPriceId().longValue());
//        Long inputRatePlanId = rankDto.getRatePlanId().longValue();
//        if ((refValue.getRatePlanId() == null || refValue.getRatePlanId() == -1L) && inputRatePlanId != null && inputRatePlanId != -1L) {
//            refValue.setRatePlanId(rankDto.getRatePlanId());
//        }
//        addRefValue(refValue);
//        RankUp rankUp = new RankUp();
//        rankUp.setUpId(rankUp.getUpId());
//        rankUp.setTimeSpanUpId(rankDto.getTimeSpanUpId());
//        rankUp.setSpId(0);
//        rankUp.setRate(refValue.getId());
//        rankUp.setOffset(rankDto.getRangeEffVal());
//        rankUp.setDuration(rankDto.getRangeExpVal());
//        rankUp.setRum(rankDto.getCalUnit().longValue());
//        rankUp.setAdjustMethod(rankDto.getAdjustMethod());
//        rankUpRepository.save(rankUp);
//        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
//        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
//        return baseResponseDto;
//    }
    public BaseResponseDto getRankPriceByPriceId(Integer priceId) {

        BaseResponseDto baseResponseDto = new BaseResponseDto();

        List<Object[]> rankUps = rankUpRepository.getRankUpByPriceId(priceId);

        if (rankUps.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        List<GetListRankPrice> result = rankUps.stream().map(obj -> {
            GetListRankPrice getListRankPrice = new GetListRankPrice();
            getListRankPrice.setRankUpId((Integer) obj[0]);
            getListRankPrice.setTimeSpanUpId((Integer) obj[1]);
            getListRankPrice.setTimeSpanName((String) obj[2]);
            getListRankPrice.setEffValue((Integer) obj[3]);
            getListRankPrice.setExpValue((Integer) obj[4]);
            getListRankPrice.setAdjustMethod((Character) obj[5]);
            getListRankPrice.setPrice((String) obj[6]);
            getListRankPrice.setCalculateUnit((Long) obj[7]);
            getListRankPrice.setTimeSpanUpPriority((Integer) obj[8]);
            return getListRankPrice;
        }).toList();

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        baseResponseDto.setData(result);
        return baseResponseDto;

    }

    public BaseResponseDto updateRankPrice(Integer rankUpId, UpdateRankPriceDto updateRankPriceDto) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        RankUp rankUp = rankUpRepository.findById(rankUpId).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

        rankUp.setTimeSpanUpId(updateRankPriceDto.getTimeSpanId());
        rankUp.setOffset(updateRankPriceDto.getOffSet());
        rankUp.setDuration(updateRankPriceDto.getDuration());
        rankUp.setAdjustMethod(updateRankPriceDto.getAdjustMethod());
        rankUp.setRum(updateRankPriceDto.getCalculateUnit());
        rankUpRepository.save(rankUp);

        RefValue refValue = refValueRepository.findById(rankUp.getRate()).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
        refValue.setValueString(updateRankPriceDto.getPrice());
        refValueRepository.save(refValue);

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return baseResponseDto;

    }

//    private void cascadeAddRefValueInFormula(RefValueFormula formula, Long ratePlanId, Long priceId) throws BaseAppException {
//        if (formula != null) {
//            for (RefValue refVal : formula.getAllLevelRefValueInFormula()) {
//                refVal.setRatePlanId(ratePlanId);
//                refVal.setPriceId(priceId);
//                this.addRefValueDto(refVal);
//            }
//        }
//    }

    public BaseResponseDto deleteRankPrice(Integer rankUpId) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        rankUpRepository.findById(rankUpId).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

        rankUpRepository.deleteById(rankUpId);

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return baseResponseDto;

    }

    private void addRefValue(RefValue refValue) {
        if (refValue != null) {
//           if ("4".equals(refValue.getRefValueType())) {
//               RefValueFormula formula = refValue.getFormula();
//               this.cascadeAddRefValueInFormula(formula, refValue.getRatePlanId().longValue(), refValue.getPriceId());
            refValue.setValueString("1");
//           }
            saveRefValue(refValue);
        }
    }

//    public BaseResponseDto addAcmUp(AcmUpDto acmUpDto) {
//        BaseResponseDto baseResponseDto = new BaseResponseDto();
//
//        RefValue refValue = getRefValue(acmUpDto);
//        refValueRepository.save(refValue);
//
//        AcmUp acmUp = new AcmUp();
//
//        acmUp.setRefValueId(refValue.getId());
//        acmUp.setSpId(0);
//        acmUp.setTimeSpanUpId(acmUpDto.getTimeSpanUpId());
//        acmUp.setRate(refValue.getId());
//        acmUp.setUpId(acmUpDto.getPriceId());
//        acmUp.setResourceId(acmUpDto.getResourceId());
//        acmUp.setEffValue(acmUpDto.getRangeEffVal());
//        acmUp.setExpValue(acmUpDto.getRangeExpVal());
//        acmUp.setRum(acmUpDto.getCalUnit());
//        acmUp.setAdjustMethod(acmUpDto.getAdjustMethod());
//
//        acmUpRepository.save(acmUp);
//        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
//        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
//        return baseResponseDto;
//
//    }

    public BaseResponseDto getListAcmPriceByPriceId(Integer priceId) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        List<Object[]> acmUps = acmUpRepository.getListAcmPriceByPriceId(priceId);

        if (acmUps.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        List<GetListAcmPriceDto> result = acmUps.stream().map(obj -> {
            GetListAcmPriceDto getListAcmPrice = new GetListAcmPriceDto();
            getListAcmPrice.setTimeSpanUpId((Integer) obj[0]);
            getListAcmPrice.setTimeSpanName((String) obj[1]);
            getListAcmPrice.setEffValue((Long) obj[2]);
            getListAcmPrice.setExpValue((Long) obj[3]);
            getListAcmPrice.setAcctItemTypeId((Integer) obj[4]);
            getListAcmPrice.setAccumulationType((String) obj[5]);
            getListAcmPrice.setAdjustMethod((Character) obj[6]);
            getListAcmPrice.setPrice((String) obj[7]);
            getListAcmPrice.setCalculateUnit((Long) obj[8]);
            getListAcmPrice.setTimeSpanUpPriority((Integer) obj[9]);
            return getListAcmPrice;
        }).toList();

        baseResponseDto.setData(result);
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());

        return baseResponseDto;
    }

    public BaseResponseDto updateAcmUp(Integer id, UpdateAcmUpDto acmUpDto) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        AcmUp acmUp = acmUpRepository.findById(id).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

        acmUp.setTimeSpanUpId(acmUpDto.getTimeSpanUpId());
        acmUp.setEffValue(acmUpDto.getRangeEffVal());
        acmUp.setExpValue(acmUpDto.getRangeExpVal());
        acmUp.setResourceId(acmUpDto.getResourceId());
        acmUp.setAdjustMethod(acmUpDto.getAdjustMethod());
        acmUp.setRum(acmUpDto.getCalUnit());
        acmUpRepository.save(acmUp);

        Optional<RefValue> optionalRefValue = refValueRepository.findById(acmUp.getRate());
        RefValue refValue = optionalRefValue.get();
        refValue.setValueString(String.valueOf(acmUpDto.getUp()));
        refValueRepository.save(refValue);

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return baseResponseDto;

    }

    public BaseResponseDto deleteAcmUp(Integer id) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        AcmUp acmUp = acmUpRepository.findById(id).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
        acmUpRepository.delete(acmUp);

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return baseResponseDto;
    }

//    public BaseResponseDto addAcmCalc(AcmCalcDto acmCalcDto) {
//        BaseResponseDto baseResponseDto = new BaseResponseDto();
//        RefValue refValue = getRefValueid(acmCalcDto);
//        refValueRepository.save(refValue);
//
//        AcmCalc acmCalcc = new AcmCalc();
//
//        acmCalcc.setRefValueId(refValue.getId());
//        acmCalcc.setResourceId(acmCalcDto.getResourceId());
//        acmCalcc.setUpId(acmCalcDto.getPriceId());
//        acmCalcc.setRum(acmCalcDto.getCalcUnit().longValue());
//        acmCalcc.setSpId(0);
//        acmCalcc.setTimeSpanUpId(null);
//
//        acmCalcRepository.save(acmCalcc);
//        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
//        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
//        return baseResponseDto;
//    }

    public BaseResponseDto getListAcmCalcByPriceId(Integer priceId) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        List<Object[]> list = acmCalcRepository.getAcmCalcByPriceId(priceId);

        if (list.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        List<GetListAcmCalc> result = list.stream().map(obj -> {
            GetListAcmCalc getListAcmCalc = new GetListAcmCalc();
            getListAcmCalc.setAcctItemTypeId((Integer) obj[0]);
            getListAcmCalc.setAccumulationType((String) obj[1]);
            getListAcmCalc.setCalculateUnit((Long) obj[2]);
            getListAcmCalc.setTimeSpanUpId((Integer) obj[3]);
            getListAcmCalc.setTimeSpanName((String) obj[4]);
            getListAcmCalc.setTimeSpanUpPriority((Integer) obj[5]);
            return getListAcmCalc;
        }).toList();

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        baseResponseDto.setData(result);

        return baseResponseDto;
    }

    public BaseResponseDto updateAcmCalc(Integer id, UpdateAcmCalcDto acmCalcDto) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        AcmCalc acmCalc = acmCalcRepository.findById(id).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

        acmCalc.setResourceId(acmCalcDto.getResourceId());
        acmCalc.setRum(acmCalcDto.getCalcUnit().longValue());
        acmCalcRepository.save(acmCalc);

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return baseResponseDto;
    }

    public BaseResponseDto deleteAcmCalc(Integer id) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        AcmCalc acmCalc = acmCalcRepository.findById(id).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
        acmCalcRepository.delete(acmCalc);

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return baseResponseDto;
    }

    private int saveRefValue(RefValue refValueDto) {

        if (refValueDto == null) {
            return 0;
        } else {
            refValueDto.setState('A');
            LocalDate localDate = LocalDate.now();
            refValueDto.setStateDate(localDate);
            refValueDto.setCreatedDate(localDate);
            RefValue refValue = refValueRepository.save(refValueDto);

//            this.checkRefValueAfterChange(refValueDto);
//            TariffTemplateParamManager paramMgr = new TariffTemplateParamManager();
//            paramMgr.updateParamRelationOnAddRefValue(refValueDto);
//            this.updateOfferVerRelationOnAddRefValue(refValueDto);
            return refValue.getId();
        }


    }

//        private void addAcmUpCalc(RankDto rankDto){
//
//                RefValue refValue = refValueRepository.selectRefValueByValueString(rankDto.getRefValue()).orElseThrow(() -> new ValidationHandler(EnumRC.DATA_NOT_FOUND.getMessage()));
//                refValue.setPriceId(rankDto.getPriceId().longValue());
//                Long inputRatePlanId = rankDto.getRatePlanId().longValue();
//                if ((refValue.getRatePlanId() == null || refValue.getRatePlanId() == -1L) && inputRatePlanId != null && inputRatePlanId != -1L) {
//                    refValue.setRatePlanId(rankDto.getRatePlanId());
//                }
//                AcmCalc calc = new AcmCalc();
//                calc.setSpId(0);
//                calc.setUpId(rankDto.getPriceId());
//                calc.setRefValueId(refValue.getId());
//                calc.setRum(rankDto.getCalUnit().longValue());
//                calculateRepository.save(calc);
//       }

    public BaseResponseDto getResource() {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        List<RatableResource> list = ratableResourceRepository.findAll();

        if (list.isEmpty()) {
            throw new ValidationHandler(EnumRC.DATA_NOT_FOUND.getMessage());
        }
        List<RatableResourceResponse> responseList = list.stream().map(obj -> {
            RatableResourceResponse response = new RatableResourceResponse();
            response.setId(obj.getId());
            response.setResourceName(obj.getResourceName());
            response.setAcmType(obj.getAcmType());
            return response;
        }).toList();

        baseResponseDto.setData(responseList);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

        log.info("Completed getResource with response code: {}", baseResponseDto.getCode());

        return baseResponseDto;
    }

    public BaseResponseDto getTimeSpan() {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        List<TimeSpanNameDto> list = timeSpanRepository.getTimeSpanName();

        baseResponseDto.setData(list);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return baseResponseDto;
    }

    public ResponseEntity<CustomeResponse> qryUpRule(Integer priceId){
        var data = upRuleRepository.qryUpRule(priceId, 0)
                .stream().map(qryUpRuleMapper::toDto);

        return ResponseEntity.ok(new CustomeResponse(200, EnumRC.SUCCESS.getMessage(), data));
    }

    public ResponseEntity<CustomeResponse> qryOp(Integer priceId){
        var data = opRepository.qryOp(priceId, 0)
                .stream().map(qryUpRuleMapper::toDto);

        return ResponseEntity.ok(new CustomeResponse(200, EnumRC.SUCCESS.getMessage(), data));

    }
}
