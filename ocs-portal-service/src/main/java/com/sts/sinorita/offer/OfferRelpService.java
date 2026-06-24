package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.DependProdSpecRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.DependProdSpec;
import com.sts.sinorita.entity.LifecyleApply;
import com.sts.sinorita.entity.Offer;
import com.sts.sinorita.entity.OfferCatGMem;
import com.sts.sinorita.mapper.offer.OfferMapper;
import com.sts.sinorita.mapper.offer.OfferRelaMapper;
import com.sts.sinorita.repository.DependProdSpecRepository;
import com.sts.sinorita.repository.OfferRepository;
import com.sts.sinorita.repository.depend.DependProdPackageRepository;
import com.sts.sinorita.repository.offer.LifeCycleApplyRepository;
import com.sts.sinorita.repository.offer.OfferCategoryMemRepository;
import com.sts.sinorita.repository.offer.OfferRelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferRelpService {
	// ===> Repository
	@Autowired
	private OfferRelaRepository offerRelaRepository;

	@Autowired
	private DependProdSpecRepository dependProdSpecRepository;

	@Autowired
	private DependProdPackageRepository dependProdPackageRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private LifeCycleApplyRepository lifeCycleApplyRepository;

	@Autowired
	private OfferCategoryMemRepository offerCategoryMemRepository;

	// ===> Mapper
	@Autowired
	OfferRelaMapper offerRelaMapper;

	@Autowired
	OfferMapper offerMapper;

	public ResponseEntity<CustomeResponse> getDependProdSpecByName(String offerName, String offerCode,
			Integer servType, String isPackage, Integer spId) {
		var data = offerRelaRepository.findProdSpecByName(offerName, offerCode, servType, isPackage, spId)
				.stream()
				.map(offerRelaMapper::dto)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getDependOfferListbyCatgId(Integer offerCatgid, Integer servType,
			String isPackage, Integer spId) {
		var data = offerRelaRepository.dependOfferListByCatgId(offerCatgid, servType, isPackage, spId)
				.stream()
				.map(offerRelaMapper::dto2)
				.collect(Collectors.toList());

		System.out.println(data);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<CustomeResponse> addDependProdSpec(DependProdSpecRequestDto dependProdSpecRequest) {
		boolean isNameDuplicate = offerRepository.existsByOfferName(dependProdSpecRequest.getOffer().getOfferName());
		if (isNameDuplicate)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.OFFER_NAME_ALREADY_EXISTS);
				
		boolean isNameWithStateDuplicate = offerRepository.existsByNameTypeAndState(dependProdSpecRequest.getOffer().getOfferName(), dependProdSpecRequest.getOffer().getOfferType(), dependProdSpecRequest.getOffer().getSpId());
		if (isNameWithStateDuplicate)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.OFFER_ALREADY_EXISTS);

		// Adding offer
		Offer offerEntity = offerMapper.toEntity(dependProdSpecRequest.getOffer());
		dependProdSpecRequest.getOffer().setState('A');
		dependProdSpecRequest.getOffer().setStateDate(LocalDate.now());
		dependProdSpecRequest.getOffer().setCreatedDate(LocalDate.now());
		offerRepository.save(offerEntity);

		// Adding depend prod spec
		DependProdSpec dependProdSpec = new DependProdSpec();
		dependProdSpec.setDependProdSpecId((long) offerEntity.getId());
		dependProdSpec.setServType(dependProdSpecRequest.getServType());
		dependProdSpec.setIsPackage(dependProdSpecRequest.getIsPackage());
		dependProdSpec.setSpId(dependProdSpecRequest.getSpId());
		dependProdSpec.setNetworkType(dependProdSpecRequest.getNetworkType());
		dependProdSpec.setGroupType(dependProdSpecRequest.getGroupType());
		dependProdSpec.setUpperLimit(dependProdSpecRequest.getUpperLimit());
		dependProdSpec.setLowerLimit(dependProdSpecRequest.getLowerLimit());
		dependProdSpecRepository.save(dependProdSpec);

		// Check LifeCycleType is not null then add to LifeCycleType
		if(dependProdSpecRequest.getLifecycleType() != null) {
			LifecyleApply lifecyleApply = new LifecyleApply();
			lifecyleApply.setOfferId(offerEntity.getId());
			lifecyleApply.setLifeCycleType(dependProdSpecRequest.getLifecycleType());
			lifecyleApply.setSpId(dependProdSpecRequest.getSpId());

			lifeCycleApplyRepository.save(lifecyleApply);
		}

		// Add offer category mem
		OfferCatGMem offerCatGMem = new OfferCatGMem();
		offerCatGMem.setOfferCatgId((long) dependProdSpecRequest.getOfferCatgId());
    offerCatGMem.setOfferId(dependProdSpec.getDependProdSpecId().intValue());
    offerCatGMem.setSpId(dependProdSpecRequest.getSpId());
		offerCategoryMemRepository.save(offerCatGMem);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<CustomeResponse> modDependProdSpec(DependProdSpecRequestDto dependProdSpecRequest) {
		boolean isNameDuplicate = offerRepository.existsByOfferName(dependProdSpecRequest.getOffer().getOfferName());
		if (isNameDuplicate)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.OFFER_NAME_ALREADY_EXISTS);
				
		boolean isNameWithStateDuplicate = offerRepository.existsByNameTypeAndState(dependProdSpecRequest.getOffer().getOfferName(), dependProdSpecRequest.getOffer().getOfferType(), dependProdSpecRequest.getOffer().getSpId());
		if (isNameWithStateDuplicate)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.OFFER_ALREADY_EXISTS);

		// Update mapper
		Offer offer = offerRepository.findById(dependProdSpecRequest.getOffer().getOfferId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatusConstant.NOT_FOUND_MESSAGE));
		offerMapper.updateEntityFromDto(dependProdSpecRequest.getOffer(), offer);
		offerRepository.save(offer);

		// Update depend prod spec
		DependProdSpec dependProdSpec = dependProdSpecRepository.findById((long) dependProdSpecRequest.getDependProdSpecId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatusConstant.NOT_FOUND_MESSAGE));
		dependProdSpec.setServType(dependProdSpecRequest.getServType());
		dependProdSpec.setIsPackage(dependProdSpecRequest.getIsPackage());
		dependProdSpec.setNetworkType(dependProdSpecRequest.getNetworkType());
		dependProdSpec.setSpId(dependProdSpecRequest.getSpId());
		dependProdSpec.setGroupType(dependProdSpecRequest.getGroupType());
		dependProdSpec.setUpperLimit(dependProdSpecRequest.getUpperLimit());
		dependProdSpec.setLowerLimit(dependProdSpecRequest.getLowerLimit());

		if ("Y".equals(dependProdSpecRequest.getIsPackage())  && "B".equals(dependProdSpecRequest.getGroupType())) {
			checkDefaultFlagExistCount(dependProdSpecRequest.getDependProdSpecId());
		}

		dependProdSpecRepository.save(dependProdSpec);

		// Check LifecycleType if exist update or add, if null delete
		if (dependProdSpecRequest.getLifecycleType() != null) {
			// add LifecycleTypeApply
			Optional<LifecyleApply> lifecyleApply = lifeCycleApplyRepository.findByOfferId(dependProdSpecRequest.getDependProdSpecId());
			if (lifecyleApply.isPresent()){
					LifecyleApply entityLifecyleApply = lifecyleApply.get();
					entityLifecyleApply.setOfferId(dependProdSpecRequest.getDependProdSpecId());
					entityLifecyleApply.setLifeCycleType(dependProdSpecRequest.getLifecycleType());
					lifeCycleApplyRepository.save(entityLifecyleApply);
			} else {
					LifecyleApply newEntityLifecyleApply = new LifecyleApply();
					newEntityLifecyleApply.setOfferId(dependProdSpecRequest.getDependProdSpecId());
					newEntityLifecyleApply.setLifeCycleType(dependProdSpecRequest.getLifecycleType());
					newEntityLifecyleApply.setSpId(dependProdSpecRequest.getSpId());
					lifeCycleApplyRepository.save(newEntityLifecyleApply);
			}
		} else { 
				// delete LifecycleTypeApply
				lifeCycleApplyRepository.deleteByOfferId(dependProdSpecRequest.getDependProdSpecId());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, dependProdSpecRequest));
	}

	public ResponseEntity<CustomeResponse> getDependProdDetailByOfferId(Integer depenProdSpecId, Integer spId){
		var data = offerRelaRepository.getDependProdDetailByOfferId(depenProdSpecId,spId)
				.stream()
				.map(offerRelaMapper::dto3)
				.collect(Collectors.toList());

		return  ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public Long checkDefaultFlagExistCount(Integer dependProdSpecId) {
		Long count = dependProdPackageRepository.getDefaultOfferMemCnt(dependProdSpecId);
		if(count > 1)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed checking");

		return count;
	}

	public ResponseEntity<CustomeResponse> getIndepOfferForRela(Integer servType,String offerName,Integer spId){
		var data = offerRelaRepository.findIndepOfferForRela(servType,offerName,spId)
				.stream()
				.map(offerRelaMapper::dto4)
				.collect(Collectors.toList());

		return  ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getPricePlanForRela(String isPackage,String pricePlanType,Character networkType,Integer servType, Integer spId,String offerName){
		var data = offerRelaRepository.findPricePlanForRela(isPackage,pricePlanType,networkType,servType,spId,offerName)
				.stream()
				.map(offerRelaMapper::dto5)
				.collect(Collectors.toList());

		return  ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getSubsPlanOfferForRela(Integer servType, String offerName, Integer spId, Integer paging, Integer size) {
		int page = Math.max(paging - 1, 0);
		Pageable pageable = PageRequest.of(page, size);

		var data = offerRelaRepository.findSubsPlanOfferForRela(servType, offerName, spId, pageable);
		var result = data.getContent().stream().map(offerRelaMapper::dto6);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,result, data.getTotalElements(), data.getTotalPages()));
	}

	public ResponseEntity<CustomeResponse> getBundleOfferForRela(Integer servType, String offerName, Integer spId) {
		var data = offerRelaRepository.findBundleOfferForRela(servType, offerName, spId)
				.stream()
				.map(offerRelaMapper::dto7)
				.collect(Collectors.toList());

		return  ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getOfferGroupForRela(Integer servType, String offerName, Integer spId) {
		var data = offerRelaRepository.findOfferGroupForRela(servType, offerName, spId)
				.stream()
				.map(offerRelaMapper::dto8)
				.collect(Collectors.toList());

		return  ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getGoodsOfferForRela(String offerName, Integer spId) {
		var data = offerRelaRepository.findGoodsOfferForRela(offerName, spId)
				.stream()
				.map(offerRelaMapper::dto9)
				.collect(Collectors.toList());

		return  ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getDependOfferForRela(String isPackage, Integer servType,Character networkType, String offerName, Integer spId) {
		var data = offerRelaRepository.findDependOfferForRela(isPackage,servType,networkType,offerName,spId)
				.stream()
				.map(offerRelaMapper::dto10)
				.collect(Collectors.toList());

		return  ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

}
