package com.ocs.portal.priceplan;

import com.ocs.portal.common.MessageService;
import com.ocs.portal.constant.HttpStatusConstant;
import com.ocs.portal.dto.request.ModPricePriorityRequest;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.EventBenefit;
import com.ocs.portal.entity.Op;
import com.ocs.portal.entity.Price;
import com.ocs.portal.entity.RefValue;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.mapper.pricePlan.price.PriceMapper;
import com.ocs.portal.repository.*;
import com.ocs.portal.validation.ValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private PriceVerRepository priceVerRepository;
    @Autowired
    private UpRepository upRepository;
    @Autowired
    private RpRepository rpRepository;
    @Autowired
    private DepositPriceRepository depositPriceRepository;
    @Autowired
    private OpRepository opRepository;
    @Autowired
    private RefValueRepository refValueRepository;
    @Autowired
    private PriceTemplateMappingReRepository priceTemplateMappingReRepository;
    @Autowired
    private PriceTaxRepository priceTaxRepository;
    @Autowired
    private PriceCatalogElementRepository priceCatalogElementRepository;
    @Autowired
    private EventBenefitRepository eventBenefitRepository;
    @Autowired
    private AcmRepository acmRepository;
    @Autowired
    private AcmRuleRepository acmRuleRepository;
    @Autowired
    private AcmRefRepository acmRefRepository;
    @Autowired
    private AcmTimeSpanRepository acmTimeSpanRepository;
    @Autowired
    private ReEbMappingPriceRepository reEbMappingPriceRepository;
    @Autowired
    private SubBalTypeRepository subBalTypeRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    PriceMapper priceMapper;

    Logger  logger = LoggerFactory.getLogger(PriceService.class);

    @Transactional
    public CustomeResponse modifyPricePriority(ModPricePriorityRequest modPricePriorityRequest) {
        Long priceId = modPricePriorityRequest.getPriceId();
        Integer oldPriority = modPricePriorityRequest.getOldPriority();
        Integer newPriority = modPricePriorityRequest.getNewPriority();
        Integer priceVerId = modPricePriorityRequest.getPriceVerId();

        if (oldPriority.equals(newPriority)) {
            throw new ValidationHandler(EnumRC.BAD_REQUEST.getMessage());
        }

        int beginPriority, endPriority, addNum;

        if (oldPriority < newPriority) {
            beginPriority = oldPriority + 1;
            endPriority = newPriority;
            addNum = -1;
        } else {
            beginPriority = newPriority;
            endPriority = oldPriority - 1;
            addNum = 1;
        }

        int nArr = priceRepository.updatePricePriorityByArr(addNum,beginPriority, endPriority, priceVerId);
        int n = priceRepository.updatePricePriority(priceId, newPriority);

        Price price = priceRepository.findById(priceId).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
        price.setPriority(newPriority);
        priceRepository.save(price);

        if (nArr < 0 && n != 1) {
            throw new ValidationHandler(EnumRC.BAD_REQUEST.getMessage());
        }

        CustomeResponse baseResponseDto = new CustomeResponse(EnumRC.SUCCESS.getRESPONSE_CODE(),EnumRC.SUCCESS.getMessage(),null);

        return baseResponseDto;
    }

    @Transactional
    public CustomeResponse modifyBenefitPriority(ModPricePriorityRequest modPricePriorityRequest) {
        Long priceId = modPricePriorityRequest.getPriceId();
        Integer oldPriority = modPricePriorityRequest.getOldPriority();
        Integer newPriority = modPricePriorityRequest.getNewPriority();
        Integer priceVerId = modPricePriorityRequest.getPriceVerId();

        if (oldPriority.equals(newPriority)) {
            throw new ValidationHandler(EnumRC.BAD_REQUEST.getMessage());
        }

        int beginPriority, endPriority, addNum;

        if (oldPriority < newPriority) {
            beginPriority = oldPriority + 1;
            endPriority = newPriority;
            addNum = -1;
        } else {
            beginPriority = newPriority;
            endPriority = oldPriority - 1;
            addNum = 1;
        }

        int nArr = eventBenefitRepository.updateBenefitPriorityByArr(addNum,beginPriority, endPriority, priceVerId);
        //int n = priceRepository.updatePricePriority(priceId, newPriority);

        EventBenefit eventBenefit = eventBenefitRepository.findById(priceId.intValue()).orElseThrow(() -> new ValidationHandler("EventBenefit not found"));
        eventBenefit.setPriority(newPriority);

        if (nArr < 0) {
            logger.warn("updateBenefitPriorityByArr failed..");
            throw new ValidationHandler(EnumRC.BAD_REQUEST.getMessage());
        }

        CustomeResponse baseResponseDto = new CustomeResponse(EnumRC.SUCCESS.getRESPONSE_CODE(),EnumRC.SUCCESS.getMessage(),null);
        return baseResponseDto;
    }

    @Transactional
    public ResponseEntity<CustomeResponse> deletePrice(Integer priceId, Integer priceVerId, Character reType) {

        List<RefValue> refValues = refValueRepository.findByPriceId(Long.valueOf(priceId));

        if (refValues.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        for (RefValue refValue : refValues) {
            refValue.setState('X');
        }

        refValueRepository.saveAll(refValues);

        if (reType.equals('1')) {
            upRepository.deleteUpRuleByUpId(priceId);
            upRepository.deleteRankUpByUpId(priceId);
            upRepository.deleteAcmUpByUpId(priceId);
            upRepository.deleteAcmCalcByUpId(priceId);
            upRepository.deleteTimeSpanUpByUpId(priceId);
            upRepository.deleteUpByUpId(priceId);
        } else if (reType.equals('9')) {
            rpRepository.deleteById(priceId);
            priceRepository.deleteById(Long.valueOf(priceId));
        } else if (reType.equals('3')) {
            Optional<Op> delOp = opRepository.findById(priceId);
            if (delOp.isPresent()) {
                opRepository.deleteById(priceId);
            }
        }
        upRepository.deleteUpByUpId(priceId);
        priceTemplateMappingReRepository.deleteByPriceId(priceId);
        priceCatalogElementRepository.deletePriceCatalogElementByPriceId(priceId);
        priceTaxRepository.deletePriceTaxByPriceId(priceId);
        priceRepository.deleteById(Long.valueOf(priceId));

        if (priceVerId != null) {
            int priceCount = priceRepository.selectPriceCountByPriceVer(priceVerId);
            if (priceCount == 0) {
                priceVerRepository.deleteById(priceVerId);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    // TODO : TABLE RefValue DATANYA BERBEDA
//    private void clearRefValueOfPrice(Long priceId, List<Long> retainedRefValIdList) {
//        List<Long> refValueIdKeep = new ArrayList<>();
//        if (retainedRefValIdList != null && !retainedRefValIdList.isEmpty()) {
//            for (Long id : retainedRefValIdList) {
//                RefValue refValue = refValueRepository.findById(Math.toIntExact(id)).orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));
//                refValueIdKeep.addAll(refValue.getre());
//            }
//        }
//    }

    @Transactional
    public ResponseEntity<CustomeResponse> deleteAcm(Integer priceId, Integer priceVerId) {

        List<RefValue> refValues = refValueRepository.findByPriceId(Long.valueOf(priceId));
        if (refValues.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        for (RefValue refValue : refValues) {
            refValue.setState('X');
        }
        refValueRepository.saveAll(refValues);

        acmRepository.deleteAcmRefByPriceVerId(priceVerId);
        acmRepository.deleteAcmRuleByPriceVerId(priceVerId);
        acmRepository.deleteAcmTimeSpanByPriceVerId(priceVerId);
        acmRepository.deleteAcmByPriceVerId(priceVerId);

        if (priceVerId != null) {
            int priceCount = priceRepository.selectPriceCountByPriceVer(priceVerId);
            if (priceCount == 0) {
                priceVerRepository.deleteById(priceVerId);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional
    public ResponseEntity<CustomeResponse> deleteBenefit(Integer priceId, Integer priceVerId, Integer subBalTypeId) {
        List<RefValue> refValues = refValueRepository.findByPriceId(Long.valueOf(priceId));
        if (refValues.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        for (RefValue refValue : refValues) {
            refValue.setState('X');
        }
        refValueRepository.saveAll(refValues);

        priceTemplateMappingReRepository.deleteByPriceId(priceId);
        priceCatalogElementRepository.deletePriceCatalogElementByPriceId(priceId);
        reEbMappingPriceRepository.deleteReEbMappingBranchByPriceId(priceId);
        Optional<EventBenefit> eventBenefit = eventBenefitRepository.findById(priceId);
        if (eventBenefit.isPresent()) {
            eventBenefitRepository.deleteById(priceId);
        }

        if (priceVerId != null) {
            int priceCount = eventBenefitRepository.selectPriceCountByPriceVer(priceVerId);
            if (priceCount == 0) {
                priceVerRepository.deleteById(priceVerId);
            }
        }

        if (subBalTypeRepository.isReferencedInAcmBenefit(subBalTypeId).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomeResponse(403,
                    HttpStatusConstant.BAD_REQUEST_MESSAGE, messageService.getMessage("S-PRD-01033")));
        }

        if (subBalTypeRepository.isReferencedInBalBenefit(subBalTypeId).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomeResponse(403, messageService.getMessage("S-PRD-01034"), null));
        }

        if (subBalTypeRepository.isReferencedInEventBenefit(subBalTypeId).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomeResponse(403, messageService.getMessage("S-PRD-01035"), null));
        }

        if (subBalTypeRepository.isReferencedInSubBalTypeLimit(subBalTypeId).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomeResponse(403, messageService.getMessage("S-PRD-01036"), null));
        }

        subBalTypeRepository.deleteBySubBalType(subBalTypeId);


        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));

    }


    public ResponseEntity<CustomeResponse> getPriceRating(Integer ratePlanId, Integer mappingId, Integer priceVerId, Long priceId,
                                                              Long priceIdSelf, Integer spId, Integer parentPriceId, Integer srcPriceId, Character shareFlag){
        var data = priceRepository.findPricesByRatePlanId(ratePlanId, mappingId, priceVerId, priceId, priceIdSelf, spId, parentPriceId, srcPriceId, shareFlag)
                                  .stream()
                                  .map(priceMapper::toDto)
                                  .toList();


        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

}
