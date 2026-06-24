package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.offer.offerCatg.InsertOfferCatgRequest;
import com.sts.sinorita.dto.request.offer.offerCatg.UpdateOfferCatgDto;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.offer.GetListOfferCatgResponse;
import com.sts.sinorita.entity.OfferCategory;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.projection.PricePlanRelationProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.repository.offer.OfferCategoryMemRepository;
import com.sts.sinorita.repository.offer.OfferCatgRepository;
import com.sts.sinorita.utils.Hellper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OfferCatgService {

    @Autowired
    private OfferCategoryRepository offerCategoryRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    OfferCategoryMemRepository offerCategoryMemRepository;

    @Autowired
    PricePlanPackageRepository pricePlanPackageRepository;

    @Autowired
    OfferGroupMemRepository offerGroupMemRepository;

    @Autowired
    OfferCatgRepository offerCatgRepository;

    @Autowired
    OfferCatgApplyChannelRepository offerCatgApplyChannelRepository;

    @Autowired
    DependProdSpecRepository dependProdSpecRepository;

    @Autowired
    private Hellper hellper;

    @NotNull
    private static OfferCategory getOfferCategory(InsertOfferCatgRequest request, LocalDate date) {
        OfferCategory offerCategory = new OfferCategory();

        offerCategory.setOfferCatgName(request.getCategoryName());
        offerCategory.setOfferCatgCode(request.getCategoryCode());
        offerCategory.setComments(request.getRemarks());
        offerCategory.setOfferCatgType(request.getOfferType());
        offerCategory.setState('A');
        offerCategory.setStateDate(date);
        offerCategory.setOfferCatgClass('A');
        offerCategory.setSpId(0);
        offerCategory.setCreatedDate(date);
        offerCategory.setEffDate(date);
        return offerCategory;
    }

    public BaseResponseDto insertOfferCatg(InsertOfferCatgRequest request) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        try {
            LocalDate localDate = LocalDate.now();
//            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            OfferCategory offerCategory = getOfferCategory(request, localDate);
            offerCategoryRepository.save(offerCategory);

            baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
            baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
            baseResponseDto.setData(offerCategory);

            log.info("Insert offer category success: {}", offerCategory);
        } catch (Exception e) {
            log.error("Insert offer category failed. Request: {}, Error: {}", request, e.getMessage(), e);
            throw e;
        }

        return baseResponseDto;
    }

    public BaseResponseDto updateOfferCatg(Integer offerCatgId, UpdateOfferCatgDto request) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        try {
            OfferCategory offerCategory = offerCategoryRepository.findById(offerCatgId).orElseThrow(() -> new RuntimeException("Offer category not found"));
            offerCategory.setOfferCatgName(request.getCategoryName());
            offerCategory.setOfferCatgCode(request.getCategoryCode());
            offerCategory.setComments(request.getRemarks());
            offerCategoryRepository.save(offerCategory);

            baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
            baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
            baseResponseDto.setData(offerCategory);

            log.info("Update offer category success: {}", offerCategory);
        } catch (Exception e) {
            log.error("Update offer category failed. Request: {}, Error: {}", request, e.getMessage(), e);
            throw e;
        }
        return baseResponseDto;
    }

    public BaseResponseDto getOfferCatgById(Integer offerCatgId) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        try {
            OfferCategory offerCategory = offerCategoryRepository.findById(offerCatgId).orElseThrow(() -> new RuntimeException("Offer category not found"));
            UpdateOfferCatgDto updateOfferCatgRequest = new UpdateOfferCatgDto();

            updateOfferCatgRequest.setCategoryName(offerCategory.getOfferCatgName());
            updateOfferCatgRequest.setCategoryCode(offerCategory.getOfferCatgCode());
            updateOfferCatgRequest.setRemarks(offerCategory.getComments());

            baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
            baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
            baseResponseDto.setData(updateOfferCatgRequest);

            log.info("Get offer category success: {}", offerCategory);
        } catch (Exception e) {
            log.error("Get offer category failed. offerCatgId: {}, Error: {}", offerCatgId, e.getMessage(), e);
            throw e;
        }
        return baseResponseDto;
    }

    public BaseResponseDto getListOfferCatg(Character offerType) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        try {
            List<Object[]> list = offerCategoryRepository.listOfferCategory(offerType);

            List<GetListOfferCatgResponse> responseList = list.stream().map(obj -> {
                GetListOfferCatgResponse response = new GetListOfferCatgResponse();
                response.setOfferCatgId(hellper.toInt(obj[0]));
                response.setOfferCatgName((String) obj[1]);
                response.setOfferCount(hellper.toInt(obj[2]));
                return response;
            }).toList();

            baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
            baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
            baseResponseDto.setData(responseList);

            log.info("Get list offer category success for offerType '{}': {} records", offerType, responseList.size());
        } catch (Exception e) {
            log.error("Get list offer category failed for offerType '{}': {}", offerType, e.getMessage(), e);
            throw e;
        }

        return baseResponseDto;
    }

//    public BaseResponseDto deleteOfferCatg(Integer offerId, Integer offerCatgId, Integer indepProdSpecId) {
//        BaseResponseDto baseResponseDto = new BaseResponseDto();
//        if (offerCatgId.equals(null)) {
//            throw new EntityNotFoundException("OfferCatg with ID " + offerCatgId + " not found.");
//        }
//
//        offerRepository.deactivateOfferById('X', LocalDate.now(), offerId);
//        offerRepository.deleteOfferCatgMemByOfferId(offerId);
//        offerRepository.deactivateOfferGroupByIndepProdSpecId('X', LocalDate.now(), indepProdSpecId);
//        offerRepository.deactivateOfferBySubsPlanIndepProdSpecId('X', LocalDate.now(), indepProdSpecId);
//        offerCategoryRepository.updateOfferCatgState(offerCatgId);
//        offerCategoryRepository.deleteOfferCatgMemByChildId(offerCatgId);
//        offerCategoryRepository.deleteOfferCatgApplyChannelByOfferCatgId(offerCatgId);
//
//        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
//        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
//        baseResponseDto.setData(null);
//        return baseResponseDto;
//    }

    public ResponseEntity<CustomeResponse> deleteOfferCatg(Integer offerId, Integer offerCatgId, Integer indepProdSpecId) {

        offerRepository.deactivateOfferById('X', LocalDate.now(), offerId);

        offerRepository.deleteOfferCatgMemByOfferId(offerId);

        offerRepository.deactivateOfferGroupByIndepProdSpecId('X', LocalDate.now(), indepProdSpecId);

        offerRepository.deactivateOfferBySubsPlanIndepProdSpecId('X', LocalDate.now(), indepProdSpecId);

        offerCategoryRepository.updateOfferCatgState(offerCatgId);

        offerCategoryRepository.deleteOfferCatgMemByChildId(offerCatgId);

        offerCategoryRepository.deleteOfferCatgApplyChannelByOfferCatgId(offerCatgId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    public ResponseEntity<CustomeResponse> deletePricePlanOffer(Integer offerCatgId, Integer offerId){
        offerCategoryMemRepository.countOfferCatgMembers(offerCatgId);
        offerGroupMemRepository.countOfferGroupMembership(offerId);

        List<PricePlanRelationProjection> relations = pricePlanPackageRepository.checkPricePlanRelations(offerId);
        if(!relations.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CustomeResponse(400, HttpStatusConstant.BAD_REQUEST_MESSAGE, null));
        }

        offerRepository.deactivateOffer(offerId);
        offerCategoryMemRepository.deleteOfferCatgMemByOfferId(offerId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    public ResponseEntity<CustomeResponse> deletePricePlanOfferMaster(Integer offerCatgId,Integer offerId, Integer childOfferCatgId){
        offerCategoryMemRepository.countOfferCatgMembers(offerCatgId);

        offerGroupMemRepository.countOfferGroupMembership(offerId);

        List<PricePlanRelationProjection> relations = pricePlanPackageRepository.checkPricePlanRelations(offerId);
        if(!relations.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CustomeResponse(400, HttpStatusConstant.BAD_REQUEST_MESSAGE, null));
        }

        offerRepository.deactivateOffer(offerId);

        offerCategoryMemRepository.deleteOfferCatgMemByOfferId(offerId);

//        delete master
        offerCatgRepository.deactivateOfferCatg(offerCatgId);

        offerCategoryMemRepository.deleteChildOfferCatgMem(childOfferCatgId);

        offerCatgApplyChannelRepository.deleteOfferCatgApplyChannel(offerCatgId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    public ResponseEntity<CustomeResponse> deleteRelatedProduct(Integer offerCatgId, Integer specId,Integer packageId, Integer childOfferCatgId) {
        offerCategoryMemRepository.countActiveMembersInCatg(offerCatgId);

        dependProdSpecRepository.countDependProdRelations(offerCatgId, packageId);

        offerCatgRepository.deactivateOfferCatg(offerCatgId);

        offerCategoryMemRepository.deleteChildOfferCatgMem(childOfferCatgId);

        offerCatgApplyChannelRepository.deleteOfferCatgApplyChannel(offerCatgId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    public ResponseEntity<CustomeResponse> deleteRelatedProductMaster(Integer offerCatgId, Integer specId,Integer packageId, Integer childOfferCatgId) {
        offerCategoryMemRepository.countActiveMembersInCatg(offerCatgId);

        offerCatgRepository.deactivateOfferCatg(offerCatgId);

        offerCategoryMemRepository.deleteChildOfferCatgMem(childOfferCatgId);

        offerCatgApplyChannelRepository.deleteOfferCatgApplyChannel(offerCatgId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

}
