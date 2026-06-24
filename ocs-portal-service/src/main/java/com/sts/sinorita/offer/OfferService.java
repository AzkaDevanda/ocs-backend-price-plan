package com.sts.sinorita.offer;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.DuplicateFlagOptionDto;
import com.sts.sinorita.dto.request.common.PagingRequestDto;
import com.sts.sinorita.dto.request.offer.*;
import com.sts.sinorita.dto.request.offer.offerCatg.RootCatgRequestDto;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.offer.GetRelaTypeProjection;
import com.sts.sinorita.dto.response.offer.GetSalesRuleScript;
import com.sts.sinorita.dto.response.offer.PricePlanOfferResponseDto;
import com.sts.sinorita.dto.response.offerver.ServeTypeList;
import com.sts.sinorita.entity.*;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.mapper.attrvalue.AttrValueMapper;
import com.sts.sinorita.mapper.baseattr.BaseAttrMapper;
import com.sts.sinorita.mapper.offer.CommonOfferMapper;
import com.sts.sinorita.mapper.offer.IndepProdSpecByNameMapper;
import com.sts.sinorita.mapper.offer.OfferMapper;
import com.sts.sinorita.mapper.offer.OfferRelaMapper;
import com.sts.sinorita.mapper.offer.OfferVerMapper;
import com.sts.sinorita.mapper.offer.SubsPlanByIndepProdMapper;
import com.sts.sinorita.mapper.pricePlan.IndepProdSpecMapper;
import com.sts.sinorita.projection.offer.SubsPlanByIndepProdProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.repository.depend.DependProdPackageRepository;
import com.sts.sinorita.repository.offer.OfferCategoryMemRepository;
import com.sts.sinorita.repository.offer.OfferCatgRepository;
import com.sts.sinorita.repository.offer.OfferRelaRepository;
import com.sts.sinorita.utils.Hellper;
import com.sts.sinorita.validation.ValidationHandler;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfferService {
	// ====> Repository <====
	@Autowired
	LifeCycleTypeRepository lifeCycleTypeRepository;

	@Autowired
	AllServeTypeRepository allServeTypeRepository;

	@Autowired
	OfferCategoryMemRepository offerCategoryMemRepository;

	@Autowired
	PricePlanRepository pricePlanRepository;

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	SubsPricePlanRepository subsPricePlanRepository;

	@Autowired
	AcctPricePlanRepository acctPricePlanRepository;

	@Autowired
	IndepProdSpecRepository indepProdSpecRepository;

	@Autowired
	OfferRelaRepository offerRelaRepository;

	@Autowired
	OfferCatgRepository offerCatgRepository;

	@Autowired
	OfferVerRepository offerVerRepository;

	@Autowired
	BaseAttrRepository baseAttrRepository;

	@Autowired
	AttrValueRepository attrValueRepository;

	@Autowired
	DependProdPackageRepository dependProdPackageRepository;

	@Autowired
	RecurringReTypeRepository recurringReTypeRepository;

	// ====> Mapper <====
	@Autowired
	CommonOfferMapper commonOfferMapper;

	@Autowired
	IndepProdSpecByNameMapper indepProdSpecByNameMapper;

	@Autowired
	IndepProdSpecMapper indepProdSpecMapper;

	@Autowired
	OfferRelaMapper offerRelaMapper;

	@Autowired
	SubsPlanByIndepProdMapper subsPlanByIndepProdMapper;

	@Autowired
	OfferVerMapper offerVerMapper;
	
	@Autowired
	BaseAttrMapper baseAttrMapper;

	@Autowired
	AttrValueMapper attrValueMapper;

	@Autowired
	OfferMapper offerMapper;

	@Autowired
	Hellper hellper;

	public ResponseEntity<CustomeResponse> getRootCatg(RootCatgRequestDto rootCatgRequest) {
		var data = offerCatgRepository.findRootCatg(rootCatgRequest.getOfferCatgType(), rootCatgRequest.getOfferCatgClass(),
				rootCatgRequest.getOfferCatgId(), rootCatgRequest.getOfferCatgName(), rootCatgRequest.getSpId(),
				rootCatgRequest.getPolicyFlag());
		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getIndepOfferListByCatgId(PagingRequestDto pagingRequest, Integer offerCatgId,
			Integer spId) {
		// Untuk membuat pagination with dynamic sort
		int page = Math.max(pagingRequest.getPage() - 1, 0);
		Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
		Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

		var data = offerCategoryMemRepository.findIndepOfferListByCatgId(offerCatgId, spId, pageable);
		var result = data.getContent().stream().map(commonOfferMapper::toIndepOfferListByCatgIdResponseDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(),
						data.getTotalPages()));
	}

	@Transactional
	public BaseResponseDto addPricePlanOffer(CreatePricePlanOfferDto dto) {
		BaseResponseDto response = new BaseResponseDto();

		Offer offer = createOffer(dto);
		PricePlan pricePlan = createPricePlan(dto, offer.getId());
		Object pricePlanDetail = createPricePlanDetail(dto, pricePlan);
		OfferCatGMem offerCatGMem = createOfferCatgMem(dto, offer.getId());

		PricePlanOfferResponseDto data = new PricePlanOfferResponseDto(pricePlan, pricePlanDetail, offer, offerCatGMem);

		response.setData(data);
		response.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
		response.setMessage(EnumRC.SUCCESS.getMessage());

		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<CustomeResponse> modPricePlanOffer(PricePlanOfferRequestDto pricePlanRequest) {
		boolean isNameDuplicate = offerRepository.existsByOfferName(pricePlanRequest.getOffer().getOfferName());
		if (isNameDuplicate)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.OFFER_NAME_ALREADY_EXISTS);
				
		boolean isNameWithStateDuplicate = offerRepository.existsByNameTypeAndState(pricePlanRequest.getOffer().getOfferName(), pricePlanRequest.getOffer().getOfferType(), pricePlanRequest.getOffer().getSpId());
		if (isNameWithStateDuplicate)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.OFFER_ALREADY_EXISTS);

		Offer offerUpdate = offerRepository.findById(pricePlanRequest.getOffer().getOfferId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatusConstant.NOT_FOUND_MESSAGE));
		offerMapper.updateEntityFromDto(pricePlanRequest.getOffer(), offerUpdate);
		offerRepository.save(offerUpdate);

		if ("Y".equals(pricePlanRequest.getIsPackage().toString())  && "B".equals(pricePlanRequest.getGroupType().toString())) {
			checkDefaultFlagExistCount(pricePlanRequest.getPricePlanId());
		}

		PricePlan pricePlanUpdate = pricePlanRepository.findById(pricePlanRequest.getPricePlanId()).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatusConstant.NOT_FOUND_MESSAGE));
		pricePlanUpdate.setApplyLevel(pricePlanRequest.getApplyLevel());
		pricePlanUpdate.setServType(pricePlanRequest.getServType());
		pricePlanUpdate.setPriority(pricePlanRequest.getPriority());
		pricePlanUpdate.setIsPackage(pricePlanRequest.getIsPackage());
		pricePlanUpdate.setSpId(pricePlanRequest.getSpId());
		pricePlanUpdate.setGroupType(pricePlanRequest.getGroupType());
		pricePlanUpdate.setUpperLimit(pricePlanRequest.getUpperLimit());
		pricePlanUpdate.setLowerLimit(pricePlanRequest.getLowerLimit());
		pricePlanRepository.save(pricePlanUpdate);

		if("S".equals(pricePlanRequest.getApplyLevel().toString())) {
			SubsPricePlan subsPricePlanUpdate = subsPricePlanRepository.findById(pricePlanRequest.getPricePlanId()).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatusConstant.NOT_FOUND_MESSAGE));
			subsPricePlanUpdate.setPricePlanType(pricePlanRequest.getPricePlanType());
			subsPricePlanUpdate.setSpId(pricePlanRequest.getSpId());
			subsPricePlanRepository.save(subsPricePlanUpdate);
		} else if("A".equals(pricePlanRequest.getApplyLevel().toString())) {
			AcctPricePlan acctPricePlanUpdate = acctPricePlanRepository.findById(pricePlanRequest.getPricePlanId()).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatusConstant.NOT_FOUND_MESSAGE));
			acctPricePlanUpdate.setPricePlanType(pricePlanRequest.getPricePlanType());
			acctPricePlanUpdate.setSpId(pricePlanRequest.getSpId());
			acctPricePlanRepository.save(acctPricePlanUpdate);
		}

		// Update recurring re type
		recurringReTypeRepository.updateReName(offerUpdate.getOfferName(), pricePlanRequest.getPricePlanId());

		return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
	}

	// public BaseResponseDto updatePricePlanOffer(CreatePricePlanOfferDto dto,
	// Integer id){
	// BaseResponseDto responseDto = new BaseResponseDto();
	//
	// PricePlan existingPricePlan = pricePlanRepository.findById(id).orElseThrow(()
	// _> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()))
	//
	// }

	private PricePlan createPricePlan(CreatePricePlanOfferDto dto, Integer id) {
		PricePlan pricePlan = new PricePlan();
		pricePlan.setId(id);
		pricePlan.setApplyLevel(dto.getApplyLevel());
		pricePlan.setServType(dto.getServiceType());
		pricePlan.setIsPackage(dto.getIsPackage());
		pricePlan.setSpId(0);
		pricePlan.setGroupType(dto.getPackageMode());
		pricePlan.setUpperLimit(dto.getUpperLimit());
		pricePlan.setLowerLimit(dto.getLowerLimit());

		return pricePlanRepository.save(pricePlan);
	}

	private Object createPricePlanDetail(CreatePricePlanOfferDto dto, PricePlan pricePlan) {
		if (dto.getApplyLevel().equals('S')) {
			SubsPricePlan subs = new SubsPricePlan();
			subs.setId(pricePlan.getId());
			subs.setPricePlanType(dto.getPricePlanType());

			return subsPricePlanRepository.save(subs);
		} else {
			AcctPricePlan acct = new AcctPricePlan();
			acct.setId(pricePlan.getId());
			acct.setPricePlanType(dto.getPricePlanType());

			return acctPricePlanRepository.save(acct);
		}
	}

	private Offer createOffer(CreatePricePlanOfferDto dto) {
		Offer offer = new Offer();
		offer.setOfferType(dto.getOfferType());
		offer.setOfferName(dto.getPricePlanName());
		offer.setComments(dto.getRemarks());
		offer.setOfferCode(dto.getPricePlanCode());
		offer.setSaleListPrice(dto.getSalePrice());
		offer.setRentListPrice(dto.getRentPrice());
		offer.setEffDate(dto.getEffDate());
		offer.setExpDate(dto.getExpDate());
		offer.setCreatedDate(LocalDate.now());
		offer.setState('A');
		offer.setStateDate(LocalDate.now());
		offer.setEffType(dto.getEffType());
		offer.setExpOff(dto.getAgreementPeriodL());
		offer.setTimeUnit(dto.getCycleTimeLimitR());
		offer.setAutoContinueFlag(dto.getAutomaticRenewal());
		offer.setCycleQuantity(dto.getCycleTimeLimitL());
		offer.setDuplicateFlag(dto.getDuplicateOrder());
		offer.setSpId(0);
		offer.setExpTimeUnit(dto.getAgreementPeriodR());
		offer.setAgreementEffType(dto.getAgreementEffType());

		return offerRepository.save(offer);
	}

	private OfferCatGMem createOfferCatgMem(CreatePricePlanOfferDto dto, Integer offerId) {
		if (dto.getOfferCatgId() != null && dto.getOfferCatgId() != 1L) {
			OfferCatGMem mem = new OfferCatGMem();
			mem.setOfferId(offerId);
			mem.setOfferCatgId(dto.getOfferCatgId());
			mem.setSpId(0);

			return offerCategoryMemRepository.save(mem);
		}
		return null;
	}

	public BaseResponseDto getOfferByOfferCatg(Integer offerCatgId, Integer servType, Character isPackage, Integer spId,
			Character policyFlag, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Object[]> resultPage = offerRepository.findOfferByCatg(offerCatgId, servType, isPackage, spId, policyFlag,
				pageable);

		Page<OfferCatgMemDto> dtoPage = resultPage.map(objArr -> {
			OfferCatgMemDto dto = new OfferCatgMemDto();
			dto.setSeq(hellper.toInt(objArr[0]));
			dto.setOfferCatgMemId(hellper.toInt(objArr[1]));
			dto.setOfferId(hellper.toInt(objArr[2]));
			dto.setOfferType((Character) objArr[3]);
			dto.setOfferName((String) objArr[4]);
			dto.setOfferCode((String) objArr[5]);
			dto.setEffDate(objArr[6] == null ? null : ((java.sql.Timestamp) objArr[6]).toLocalDateTime().toLocalDate());
			dto.setExpDate(objArr[7] == null ? null : ((java.sql.Timestamp) objArr[7]).toLocalDateTime().toLocalDate());
			dto.setDuplicateFlag((Character) objArr[8]);
			dto.setExpOff(hellper.toInt(objArr[9]));
			dto.setExpTimeUnit((Character) objArr[10]);
			dto.setIsPackage((Character) objArr[11]);
			dto.setApplyLevel((Character) objArr[12]);
			dto.setPolicyFlag((Character) objArr[13]);
			dto.setWarnLevel((Character) objArr[14]);
			dto.setPricePlanType((Character) objArr[15]);
			return dto;
		});

		BaseResponseDto response = new BaseResponseDto();
		response.setData(dtoPage);
		response.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
		response.setMessage(EnumRC.SUCCESS.getMessage());

		return response;
	}

    public BaseResponseDto getServeTypeListResponse(Character catgType) {
        List<ServeTypeList> all = allServeTypeRepository.getServeTypeList(catgType);
        if (all.isEmpty()) {
            throw new ValidationHandler("Serve Type empty");
        }

		// Modify names directly using stream
		List<ServeTypeList> responseList = all.stream()
				.peek(serve -> serve
						.setServTypeName(String.format("%s[%s]", serve.getServTypeName(), serve.getNetworkTypeName())))
				.collect(Collectors.toList());

		return new BaseResponseDto(EnumRC.SUCCESS.getMessage(), EnumRC.SUCCESS.getRESPONSE_CODE().toString(), responseList,
				200);
	}

	public BaseResponseDto getOfferName(String offerName, String offerCode, String policyFlagY, String policyFlagN,
			String servType, String isPackage, Long spId, List<String> pricePlanType, boolean pricePlanTypeEmpty) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();

		List<OfferNameDtoRequest> getOfferNameDto = offerRepository.findOfferFiltered(
				offerName, offerCode, policyFlagY, policyFlagN, servType, isPackage, spId, pricePlanType,
				pricePlanTypeEmpty).stream().map(
						row -> new OfferNameDtoRequest(
								((BigDecimal) row[0]).intValue(),
								(String) row[1],
								(String) row[2],
								(Character) row[3],
								row[4] != null ? ((BigDecimal) row[4]).longValue() : null))
				.collect(Collectors.toList());

		baseResponseDto.setData(getOfferNameDto);
		baseResponseDto.setMessage("Success");
		baseResponseDto.setCode("200");
		return baseResponseDto;
	}

	public BaseResponseDto offerDetail(Integer pricePlanId, Integer spId) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();

		List<OfferDetailDto> offerDetailDtoList = offerRepository.findOfferDetail(pricePlanId, spId)
				.stream().map(row -> {
					OfferDetailDto dto = new OfferDetailDto();

					dto.setOfferId(row[0] != null ? ((BigDecimal) row[0]).longValue() : null);
					dto.setOfferType(row[1] != null ? (Character) row[1] : null);
					dto.setOfferName(row[2] != null ? (String) row[2] : null);
					dto.setOfferCode(row[3] != null ? (String) row[3] : null);
					dto.setComments(row[4] != null ? (String) row[4] : null);
					dto.setSaleListPrice(row[5] != null ? ((BigDecimal) row[5]).longValue() : null);
					dto.setRentListPrice(row[6] != null ? ((BigDecimal) row[6]).longValue() : null);
					dto.setEffDate(row[7] != null ? ((Timestamp) row[7]).toLocalDateTime().toLocalDate() : null);
					dto.setExpDate(row[8] != null ? ((Timestamp) row[8]).toLocalDateTime().toLocalDate() : null);
					dto.setAutoContinueFlag(row[9] != null ? (Character) row[9] : null);
					dto.setCycleQuantity(row[10] != null ? ((BigDecimal) row[10]).intValue() : null);
					dto.setTimeUnit(row[11] != null ? (Character) row[11] : null);
					dto.setExpOff(row[12] != null ? ((BigDecimal) row[12]).intValue() : null);
					dto.setExpTimeUnit(row[13] != null ? (Character) row[13] : null);
					dto.setDuplicateFlag(row[14] != null ? (Character) row[14] : null);
					dto.setEffType(row[15] != null ? (String) row[15] : null);
					dto.setPricePlanId(row[16] != null ? ((BigDecimal) row[16]).intValue() : null);
					dto.setApplyLevel(row[17] != null ? (Character) row[17] : null);
					dto.setServType(row[18] != null ? ((BigDecimal) row[18]).intValue() : null);
					dto.setPriority(row[19] != null ? ((BigDecimal) row[19]).intValue() : null);
					dto.setIsPackage(row[20] != null ? (Character) row[20] : null);
					dto.setPricePlanType(row[21] != null ? (Character) row[21] : null);
					dto.setCreatedDate(row[22] != null ? ((Timestamp) row[22]).toLocalDateTime().toLocalDate() : null);
					dto.setAgreementEffType(row[23] != null ? (Character) row[23] : null);
					dto.setSalePriceGstType(row[24] != null ? (Character) row[24] : null);
					dto.setRentPriceGstType(row[25] != null ? (Character) row[25] : null);
					dto.setGroupType(row[26] != null ? (Character) row[26] : null);
					dto.setUpperLimit(row[27] != null ? ((BigDecimal) row[27]).intValue() : null);
					dto.setLowerLimit(row[28] != null ? ((BigDecimal) row[28]).intValue() : null);

					return dto;
				})
				.collect(Collectors.toList());

		baseResponseDto.setData(offerDetailDtoList);
		baseResponseDto.setMessage("Success");
		baseResponseDto.setCode("200");
		return baseResponseDto;
	}

	private List<DuplicateFlagOptionDto> getDomainlist() {
		List<DuplicateFlagOptionDto> option = new ArrayList<>();

		option.add(new DuplicateFlagOptionDto("E", "Extend Effective period of original instance from ExpDate"));
		option.add(new DuplicateFlagOptionDto("F", "Add Offer Instance, New Instance EffDate equal Old ExpDate"));
		option.add(new DuplicateFlagOptionDto("A", "Don't Allow to Duplicate Order"));
		option.add(new DuplicateFlagOptionDto("C", "Add Offer Instance, Don't Change Old Instance"));
		option.add(new DuplicateFlagOptionDto("D", "Add Offer Instance, Cancel Old Instance"));
		option.add(new DuplicateFlagOptionDto("B", "Extend Effective period of original instance from sysdate"));

		return option;
	}

	private List<DuplicateFlagOptionDto> getUnitOfTimeList() {
		List<DuplicateFlagOptionDto> option = new ArrayList<>();

		option.add(new DuplicateFlagOptionDto("Y", "Year"));
		option.add(new DuplicateFlagOptionDto("M", "Month"));
		option.add(new DuplicateFlagOptionDto("W", "Week"));
		option.add(new DuplicateFlagOptionDto("D", "Day"));
		option.add(new DuplicateFlagOptionDto("H", "Hour"));
		option.add(new DuplicateFlagOptionDto("C", "Billing Cycle"));
		option.add(new DuplicateFlagOptionDto("T", "Exact Time"));

		return option;
	}

	private List<DuplicateFlagOptionDto> getAgreementEffTypeList() {
		List<DuplicateFlagOptionDto> option = new ArrayList<>();

		option.add(new DuplicateFlagOptionDto("", "Next Day"));
		option.add(new DuplicateFlagOptionDto("", "Next Month"));
		option.add(new DuplicateFlagOptionDto("", "Next Billing Cycle"));
		option.add(new DuplicateFlagOptionDto("", "Today 0:00"));

		return option;
	}

	private List<DuplicateFlagOptionDto> getListSpecialDay() {
		List<DuplicateFlagOptionDto> option = new ArrayList<>();

		option.add(new DuplicateFlagOptionDto("A", "Special Day"));
		option.add(new DuplicateFlagOptionDto("B", "Instant"));
		option.add(new DuplicateFlagOptionDto("C", "Next Day"));
		option.add(new DuplicateFlagOptionDto("D", "Next Week"));
		option.add(new DuplicateFlagOptionDto("E", "Next Month"));
		option.add(new DuplicateFlagOptionDto("F", "Next Billing Cycle"));
		option.add(new DuplicateFlagOptionDto("G", "The Cycle After Next Cycle"));
		option.add(new DuplicateFlagOptionDto("H", "Special Date"));

		return option;

	}

	public BaseResponseDto getAgreementEffType() {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setData(getAgreementEffTypeList());
		baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
		baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());

		return baseResponseDto;
	}

	public BaseResponseDto getDomain() {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setData(getDomainlist());
		baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
		baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());

		return baseResponseDto;
	}

	public BaseResponseDto getUnitOfTime() {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setData(getUnitOfTimeList());
		baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
		baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());

		return baseResponseDto;
	}

	public ResponseEntity<CustomeResponse> getSpecialDay() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, getListSpecialDay()));
	}

	public BaseResponseDto qrySalesRuleScriptByOfferId(Integer offerId) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();

		Optional<GetSalesRuleScript> optionalResult = offerRepository.qrySalesRuleScriptByOfferId(offerId);
		optionalResult.ifPresentOrElse(result -> {
			baseResponseDto.setData(result);
			baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
			baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
		}, () -> {
			baseResponseDto.setData(null);
			baseResponseDto.setMessage("Data not found");
			baseResponseDto.setCode("404");
		});

		return baseResponseDto;
	}

	public BaseResponseDto qryRelaType() {
		BaseResponseDto baseResponseDto = new BaseResponseDto();

		List<GetRelaTypeProjection> list = offerRepository.qryRelaType();

		baseResponseDto.setData(list);
		baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
		baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

		return baseResponseDto;
	}

	public ResponseEntity<CustomeResponse> getLifecycleType(Integer lifecycleType, Integer spId) {
		var data = lifeCycleTypeRepository.findLifecycleType(lifecycleType, spId)
				.map(commonOfferMapper::toLifeCycleTypeResponseDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getOfferRelaAsOri(Integer offerId) {
		var data = offerRelaRepository.findOfferRelaAsOri(offerId).stream()
				.map(offerRelaMapper::toOfferRelaAsOriResponseDto).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getOfferRelaAsDest(Integer offerId) {
		var data = offerRelaRepository.findOfferRelaAsOri(offerId).stream()
				.map(offerRelaMapper::toOfferRelaAsOriResponseDto).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getIndepProdSpec(Long indepProdSpecId, Integer spId) {
		var data = indepProdSpecRepository.findProductDetails(indepProdSpecId, spId)
				.stream()
				.map(indepProdSpecMapper::toDto)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getSubsPlanByIndepProd(Long indepProdId) {
		List<SubsPlanByIndepProdProjection> rawData = indepProdSpecRepository.findSubsPlanByIndepProdId(indepProdId);

		List<SubsPlanByIndepProdDto> result = rawData.stream().map(plan -> {
			SubsPlanByIndepProdDto dto = new SubsPlanByIndepProdDto();
			BeanUtils.copyProperties(plan, dto);

			List<OfferVerForSubsPlanDto> offerVers = indepProdSpecRepository.findOfferVerByOfferId(plan.getOfferId());
			dto.setOfferVerList(offerVers);

			return dto;
		}).collect(Collectors.toList());

		Map<String, Object> dataWrapper = new HashMap<>();
		dataWrapper.put("SubsPlanList", result);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, dataWrapper));
	}

	public ResponseEntity<CustomeResponse> getOfferEffectiveVerByOfferId(Integer offerId){
		var data = offerVerRepository.findOfferEffectiveVerByOfferId(offerId).map(offerVerMapper::toOfferEffectiveVerByOfferIdResponseDto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getQrySubsPlanVer(Integer offerId, Integer onlyValid, Integer spId) {
		var data = offerVerRepository.findSubsPlanVer(offerId, onlyValid, spId).map(offerVerMapper::toOfferEffectiveVerByOfferIdResponseDto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}

	public ResponseEntity<CustomeResponse> getAttrDetail(Integer baseAttrId, Integer spId) {
		var data = baseAttrRepository.findAttrDetail(baseAttrId, spId).map(baseAttrMapper::toAttrDetailResponseDto);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}
	
	public ResponseEntity<CustomeResponse> getAttrValue(Integer baseAttrId, String attrCode, Integer attrValueId, Integer value, String valueMark, Integer oldCreditLimit1, Integer oldCreditLimit2, Integer spId) {
		var data = attrValueRepository.findAttrValue(baseAttrId, attrCode, attrValueId, value, valueMark, oldCreditLimit1, oldCreditLimit2, spId).map(attrValueMapper::toAttrValueResponseDto);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
	}


	// ====> Utils
	public Long checkDefaultFlagExistCount(Integer pricePlanId) {
		Long count = dependProdPackageRepository.getDefaultOfferMemCnt(pricePlanId);
		if(count > 1)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed checking");

		return count;
	}
}
