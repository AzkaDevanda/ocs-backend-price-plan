package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.offer.IndepProdSpecRequestDto;
import com.sts.sinorita.dto.request.offer.product.CreateProductRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.IndepProdSpec;
import com.sts.sinorita.entity.LifecyleApply;
import com.sts.sinorita.entity.Offer;
import com.sts.sinorita.entity.OfferCatGMem;
import com.sts.sinorita.repository.IndepProdSpecRepository;
import com.sts.sinorita.repository.OfferRepository;
import com.sts.sinorita.repository.offer.LifeCycleApplyRepository;
import com.sts.sinorita.repository.offer.OfferCategoryMemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OfferMainProductService {
    @Autowired
    private IndepProdSpecRepository indepProdSpecRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private LifeCycleApplyRepository lifeCycleApplyRepository;
    @Autowired
    private OfferCategoryMemRepository offerCategoryMemRepository;

    public ResponseEntity<CustomeResponse> addIndepProdSpec(CreateProductRequestDto dto) {
        Integer spId = dto.getSpId();

        // save offer
        Offer offer = new Offer();
        offer.setOfferName(dto.getProductName());
        offer.setOfferType(dto.getOfferType());
        offer.setEffDate(dto.getEffDate());
        offer.setExpDate(dto.getExpDate());
        offer.setComments(dto.getRemarks());
        offer.setEffType(dto.getEffType());
        offer.setCreatedDate(LocalDate.now());
        offer.setOfferCode(dto.getCode());
        offer.setState('A');
        offer.setSpId(spId);
        offer.setStateDate(LocalDate.now());

        if (dto.getProductName() != null) {
            boolean isNameDuplicate = offerRepository.existsByNameTypeAndState(dto.getProductName(), dto.getOfferType(),
                    spId);
            if (isNameDuplicate) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new CustomeResponse(400, HttpStatusConstant.OFFER_ALREADY_EXISTS, null));
            }
        }

        String code = dto.getCode();
        if (code != null && !code.isEmpty()) {
            boolean isCodeDuplicate = offerRepository.existsByCodeTypeAndState(code, dto.getOfferType(), spId);
            if (isCodeDuplicate) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new CustomeResponse(400, HttpStatusConstant.OFFER_CODE_ALREADY_EXISTS, null));
            }
        }

        offerRepository.save(offer);

        // save indepProdSpec
        IndepProdSpec indepProdSpec = new IndepProdSpec();
        indepProdSpec.setIndepProdSpecId(Long.valueOf(offer.getId()));
        indepProdSpec.setServType(dto.getServiceType());
        indepProdSpec.setPaidFlag(dto.getPaidFlag());

        // save lifecycleType if not null
        if (dto.getLifecycleType() != null) {
            LifecyleApply lifecyleApply = new LifecyleApply();
            lifecyleApply.setOfferId(offer.getId());
            lifecyleApply.setLifeCycleType(dto.getLifecycleType());
            lifecyleApply.setSpId(spId);
            lifeCycleApplyRepository.save(lifecyleApply);
        }

        // save offer category
        OfferCatGMem offerCatGMem = new OfferCatGMem();
        offerCatGMem.setOfferId(offer.getId());
        offerCatGMem.setOfferCatgId(dto.getOfferCatgId());
        offerCatGMem.setSpId(spId);
        offerCategoryMemRepository.save(offerCatGMem);

        indepProdSpecRepository.save(indepProdSpec);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modIndepProdSpec(IndepProdSpecRequestDto IndepProdSpecRequest) {
        if (IndepProdSpecRequest.getOfferName() != null) {
            boolean isNameDuplicate = offerRepository.existsByNameTypeAndState(IndepProdSpecRequest.getOfferName(), IndepProdSpecRequest.getOfferType(), IndepProdSpecRequest.getSpId());
            if (isNameDuplicate)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.OFFER_ALREADY_EXISTS);
        }

        String code = IndepProdSpecRequest.getOfferCode();
        if (code != null && !code.isEmpty()) {
            boolean isCodeDuplicate = offerRepository.existsByCodeTypeAndState(code, IndepProdSpecRequest.getOfferType(), IndepProdSpecRequest.getSpId());
            if (isCodeDuplicate)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.OFFER_CODE_ALREADY_EXISTS);
        }

        // Update offer
        Offer offer = offerRepository.findByOfferCode(IndepProdSpecRequest.getOfferCode());
        offer.setOfferType(IndepProdSpecRequest.getOfferType());
        offer.setOfferCode(IndepProdSpecRequest.getOfferCode());
        offer.setOfferName(IndepProdSpecRequest.getOfferName());
        offerRepository.save(offer);

        // Update IndepProdSpec
        IndepProdSpec indepProdSpec = indepProdSpecRepository.findById(IndepProdSpecRequest.getIndepProdSpecId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Indep Prod Spec Not Found"));
        indepProdSpec.setServType(IndepProdSpecRequest.getServType());
        indepProdSpec.setIndepProdSpecId(IndepProdSpecRequest.getIndepProdSpecId());
        indepProdSpec.setPaidFlag(IndepProdSpecRequest.getPaidFlag());
        indepProdSpec.setBrandPricePlanId(IndepProdSpecRequest.getBrandPricePlanId());
        indepProdSpec.setSpId(IndepProdSpecRequest.getSpId());
        indepProdSpecRepository.save(indepProdSpec);

        if (IndepProdSpecRequest.getLifecycleType() != null) {
            // add LifecycleTypeApply
            Optional<LifecyleApply> lifecyleApply = lifeCycleApplyRepository.findByOfferId(IndepProdSpecRequest.getOfferId());
            if (lifecyleApply.isPresent()){
                LifecyleApply entityLifecyleApply = lifecyleApply.get();
                entityLifecyleApply.setOfferId(IndepProdSpecRequest.getOfferId());
                entityLifecyleApply.setLifeCycleType(IndepProdSpecRequest.getLifecycleType());
                lifeCycleApplyRepository.save(entityLifecyleApply);
            } else {
                LifecyleApply newEntityLifecyleApply = new LifecyleApply();
                newEntityLifecyleApply.setOfferId(IndepProdSpecRequest.getOfferId());
                newEntityLifecyleApply.setLifeCycleType(IndepProdSpecRequest.getLifecycleType());
                newEntityLifecyleApply.setSpId(IndepProdSpecRequest.getSpId());
                lifeCycleApplyRepository.save(newEntityLifecyleApply);
            }
        } else { 
            // delete LifecycleTypeApply
            lifeCycleApplyRepository.deleteByOfferId(IndepProdSpecRequest.getOfferId());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }
}
