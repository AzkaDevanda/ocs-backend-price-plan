package com.sts.sinorita.offer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.sts.sinorita.repository.AttrApplyCatgRepository;
import com.sts.sinorita.repository.AttrApplyChannelRepository;
import com.sts.sinorita.repository.AttrRepository;
import com.sts.sinorita.repository.ContactChannelRepository;
import com.sts.sinorita.repository.DataTypeRepository;
import com.sts.sinorita.repository.DependProdSpecRepository;
import com.sts.sinorita.repository.InputTypeRepository;
import com.sts.sinorita.repository.offer.AttrCatgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.constant.SortDirection;
import com.sts.sinorita.dto.request.common.PagingRequestDto;
import com.sts.sinorita.dto.request.offer.commonoffer.Offer4ReConfRequestDto;
import com.sts.sinorita.dto.request.offer.commonoffer.OfferGroupCountRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.attr.AttrMapper;
import com.sts.sinorita.mapper.depend.dependprodpackage.DependProdPackageMapper;
import com.sts.sinorita.mapper.depend.dependprodspec.DependProdSpecMapper;
import com.sts.sinorita.mapper.offer.CommonOfferMapper;
import com.sts.sinorita.mapper.offer.IndepProdSpecByNameMapper;
import com.sts.sinorita.mapper.offer.OfferCatgMemMapper;
import com.sts.sinorita.mapper.offer.OfferGroupMapper;
import com.sts.sinorita.mapper.offer.OfferMapper;
import com.sts.sinorita.mapper.offer.OfferRelaMapper;
import com.sts.sinorita.repository.OfferRepository;
import com.sts.sinorita.repository.depend.DependProdPackageRepository;
import com.sts.sinorita.repository.offer.OfferCategoryMemRepository;
import com.sts.sinorita.repository.offer.OfferGroupRepository;
import com.sts.sinorita.repository.offer.OfferRelaRepository;
import com.sts.sinorita.repository.offer.OfferTypeRepository;

@Service
public class CommonOfferService {
	// ====> Repository <====
	@Autowired
	OfferTypeRepository offerTypeRepository;

	@Autowired
	OfferCategoryMemRepository offerCategoryMemRepository;

	@Autowired
	OfferGroupRepository offerGroupRepository;

	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	DependProdPackageRepository dependProdPackageRepository;

	@Autowired
	AttrCatgRepository attrCatgRepository;

	@Autowired
	ContactChannelRepository contactChannelRepository;

	@Autowired
	AttrApplyCatgRepository attrApplyCatgRepository;

	@Autowired
	AttrApplyChannelRepository attrApplyChannelRepository;

	@Autowired
	AttrRepository attrRepository;

	@Autowired
	InputTypeRepository inputTypeRepository;

	@Autowired
	DataTypeRepository dataTypeRepository;

	@Autowired
	DependProdSpecRepository dependProdSpecRepository;

	@Autowired
	OfferRelaRepository offerRelaRepository;

	// ====> Mapper <====
	@Autowired
	OfferMapper offerMapper;

	@Autowired
	CommonOfferMapper commonOfferMapper;

	@Autowired
	IndepProdSpecByNameMapper indepProdSpecByNameMapper;
	
	@Autowired
	OfferCatgMemMapper offerCatgMemMapper;

	@Autowired
	DependProdPackageMapper dependProdPackageMapper;

	@Autowired
	OfferGroupMapper offerGroupMapper;

	@Autowired
	AttrMapper attrMapper;

	@Autowired
	DependProdSpecMapper dependProdSpecMapper;

	@Autowired
	OfferRelaMapper offerRelaMapper;

	public ResponseEntity<CustomeResponse> getOfferByName(String offerName, String offerType, Integer spId, Character isBundleFlag){
		var data = offerRepository.findOfferByName(offerName, offerType, spId, isBundleFlag);
		var result = data.stream().map(offerMapper::toOfferByNameResponseDto).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result));
	}

	public ResponseEntity<CustomeResponse> getIndepProdSpecByName(String offerName, Integer spId, String order_field, SortDirection order_direction, Integer paging, Integer size) {
		int page = Math.max(paging - 1, 0);
		Sort sort = Sort.by(Sort.Direction.fromString(order_direction.toString()), order_field);
		Pageable pageable = PageRequest.of(page, size, sort);

		var data = offerRepository.qryIndepProdSpecByName(offerName, spId, pageable);
		var result = data.getContent().stream().map(indepProdSpecByNameMapper::toDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
	}

	public ResponseEntity<CustomeResponse> getPriceOfferListByCatgId(PagingRequestDto pagingRequest, Integer offerCatgId, Integer spId) {
		// Untuk membuat pagination with dynamic sort
		int page = Math.max(pagingRequest.getPage() - 1, 0);
		Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
		Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

		var data = offerCategoryMemRepository.qryPriceOfferListByCatgId(offerCatgId, spId, pageable);
		var result = data.getContent().stream().map(offerCatgMemMapper::toPriceOfferListByCatgIdResponseDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
	}

	public ResponseEntity<CustomeResponse> getOffer4ReConf(Offer4ReConfRequestDto offer4ReConfRequest) {
		// Untuk membuat pagination with dynamic sort
		int page = Math.max(offer4ReConfRequest.getPage() - 1, 0);
		Sort sort = Sort.by(Sort.Direction.fromString(offer4ReConfRequest.getSortDirection()), offer4ReConfRequest.getSortBy());
		Pageable pageable = PageRequest.of(page, offer4ReConfRequest.getSize(), sort);

		var data = offerTypeRepository.findoffer4ReConf(offer4ReConfRequest.getProdSpecName(),
				offer4ReConfRequest.getStdCode(),
				offer4ReConfRequest.getOfferType(),
				offer4ReConfRequest.getState(),
				offer4ReConfRequest.getSpId(), pageable);

		var result = data.getContent().stream().map(commonOfferMapper::toDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
	}

	public ResponseEntity<CustomeResponse> getOfferAndGroupByNameOrCode(Offer4ReConfRequestDto OfferAndGroupByNameOrCodeRequest){
		// Untuk membuat pagination with dynamic sort
		int page = Math.max(OfferAndGroupByNameOrCodeRequest.getPage() - 1, 0);
		Sort sort = Sort.by(Sort.Direction.fromString(OfferAndGroupByNameOrCodeRequest.getSortDirection()), OfferAndGroupByNameOrCodeRequest.getSortBy());
		Pageable pageable = PageRequest.of(page, OfferAndGroupByNameOrCodeRequest.getSize(), sort);

		var data = offerTypeRepository.findoffer4ReConf(OfferAndGroupByNameOrCodeRequest.getProdSpecName(),
				OfferAndGroupByNameOrCodeRequest.getStdCode(),
				OfferAndGroupByNameOrCodeRequest.getOfferType(),
				OfferAndGroupByNameOrCodeRequest.getState(),
				OfferAndGroupByNameOrCodeRequest.getSpId(), pageable);

		var result = data.getContent().stream().map(commonOfferMapper::toDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
	}

	public ResponseEntity<CustomeResponse> getOfferGroupCount(OfferGroupCountRequestDto offerGroupCountRequest){
		Long data = offerGroupRepository.findOfferGroupCount(offerGroupCountRequest.getOfferGroupType(), offerGroupCountRequest.getShareFlag(), offerGroupCountRequest.getIndepProdSpecId(), offerGroupCountRequest.getOfferVerId(), offerGroupCountRequest.getOfferGroupId(), offerGroupCountRequest.getSpId());

		Map<String, Object> response = new HashMap<>();
    response.put("count", data);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, response));
	}

	public ResponseEntity<CustomeResponse> getDependProdJoinPackage(Integer dependProdSpecId, Integer spId){
		var data = dependProdPackageRepository.findDependProdJoinPackage(dependProdSpecId, spId)
				.stream().map(dependProdPackageMapper::toDependProdJoinPackageResponseDto).toList();

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getPricePlanById(Integer pricePlanId, Integer spId){
		var data = offerRepository.findPricePlanById(pricePlanId, spId)
				.stream()
				.map(commonOfferMapper::dto)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getOfferGroupAndMember(PagingRequestDto pagingRequest, Integer offerGroupType, Integer shareFlag, Integer indepProdSpecId, Integer spId){
		// Untuk membuat pagination with dynamic sort
		int page = Math.max(pagingRequest.getPage() - 1, 0);
		Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
		Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

		var data = offerGroupRepository.findOfferGroupAndMember(offerGroupType, shareFlag, indepProdSpecId, spId, pageable);
		var result = data.getContent().stream().map(offerGroupMapper::toOfferGroupAndMemberResponseDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
	}

	public ResponseEntity<CustomeResponse> getAttrCatg(){
		var data = attrCatgRepository.findAllAttrCatg()
				.stream()
				.map(commonOfferMapper::dto2)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getContactChannel(String channelType, Integer spId){
		var data = contactChannelRepository.findContactChannels(channelType, spId)
				.stream()
				.map(commonOfferMapper::dto3)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getAttrApplyCatg(Integer attrApplyCatgId){
		var data = attrApplyCatgRepository.findAttrApplyCatg(attrApplyCatgId)
				.stream()
				.map(commonOfferMapper::dto4)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getAttrApplyChannel(Integer attrId){
		var data = attrApplyChannelRepository.findByAttrApplyChannel(attrId)
				.stream()
				.map(commonOfferMapper::dto5)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getAttrListByCatg(@ModelAttribute PagingRequestDto pagingRequest, @RequestParam Integer attrCatg){
		// Untuk membuat pagination with dynamic sort
		int page = Math.max(pagingRequest.getPage() - 1, 0);
		Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
		Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

		var data = attrRepository.findAttrListByCatg(attrCatg, pageable);
		var result = data.getContent().stream().map(attrMapper::toAttrListByCatgResponseDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
	}

  public ResponseEntity<CustomeResponse> getInputTypeList(){
		var data = inputTypeRepository.findAll();

    return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }
	
  public ResponseEntity<CustomeResponse> getDataType(){
		var data = dataTypeRepository.findAll();

    return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> getContactChannelList(Integer contactChannelId, Integer spId){
		var data = contactChannelRepository.findContactChannelList(contactChannelId, spId)
				.stream().map(commonOfferMapper::dto3)
				.collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

	public ResponseEntity<CustomeResponse> getDependProdWithNetworkType(Integer spId){
    var data = dependProdSpecRepository.findDependProdWithNetworkType(spId)
				.stream().map(dependProdSpecMapper::toDependProdWithNetworkTypeResponseDto)
				.collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

	public ResponseEntity<CustomeResponse> getOfferRela(PagingRequestDto pagingRequest){
		// Untuk membuat pagination with dynamic sort
		int page = Math.max(pagingRequest.getPage() - 1, 0);
		Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
		Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

		var data = offerRelaRepository.findOfferRela(pageable);

		var result = data.getContent().stream().map(offerRelaMapper::toOfferRelaResponseDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }

	public ResponseEntity<CustomeResponse> delIndepProdSpec(Integer offerId){
		// Delete offer
		offerRepository.deactivateOfferById('X', LocalDate.now(), offerId);

		// Delete OfferCatgMemByOfferId
		offerRepository.deleteOfferCatgMemByOfferId(offerId);

	 	//  Delete OfferGroupByIndepProdSpecId
		offerRepository.deactivateOfferGroupByIndepProdSpecId('X', LocalDate.now(), offerId);

    //  Delete SubsPlanByIndepProdSpecId
		offerRepository.deactivateOfferBySubsPlanIndepProdSpecId('X', LocalDate.now(), offerId);

    return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }
}
