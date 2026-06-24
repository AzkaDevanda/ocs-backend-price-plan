package com.sts.sinorita.accountconfig;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.*;
import com.sts.sinorita.dto.request.common.PagingRequestDto;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.AcctItemTaxApply;
import com.sts.sinorita.entity.AcctItemType;
import com.sts.sinorita.entity.AcctItemTypeBind;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.mapper.accountConfig.GstTypeMapper;
import com.sts.sinorita.mapper.accountConfig.QryBalTypeMapper;
import com.sts.sinorita.mapper.acct.AcctItemTypeMapper;
import com.sts.sinorita.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcctItemTypeService {
  // ==========> REPOSITORY <==========
  private final AcctItemTypeRepository acctItemTypeRepository;
  private final AcctResRepository acctResRepository;
  private final AcctItemTypeBindRepository acctItemTypeBindRepository;
  private final AcctItemTaxApplyRepository acctItemTaxApplyRepository;
  private final BalTypeRepository balTypeRepository;
  private final DpRuleRepository dpRuleRepository;
  private final RefDpRepository refDpRepository;
  private final RefAcctItemTypeRepository refAcctItemTypeRepository;
  private final PriceRepository priceRepository;
  private final AdjustPrepareItemRepository adjustPrepareItemRepository;
  private final AcctItemRepository acctItemRepository;
  private final OverduePlanItemRepository overduePlanItemRepository;
  private final InstalmentTypeItemRepository instalmentTypeItemRepository;
  private final GlAcctItemTypeRepository glAcctItemTypeRepository;
  private final GstTypeRepository gstTypeRepository;

  // ==========> MAPPER <==========
  private final AcctItemTypeMapper acctItemTypeMapper;
  private final QryBalTypeMapper qryBalTypeMapper;
  private final GstTypeMapper gstTypeMapper;

  private final MessageService messageService;

  public ResponseEntity<CustomeResponse> getAllAcctItemType(PagingRequestDto pagingRequest, String acctItemTypeName, Long acctResId, String balType, Long spId) {
    int page = Math.max(pagingRequest.getPage() - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
    Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

    var data = acctItemTypeRepository.qryBalanceType(acctItemTypeName, acctResId, balType, null, spId, pageable);
    var result = data.getContent().stream().map(acctItemTypeMapper::toQryBalanceTypeResponseDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> addAcctItemType(AcctItemTypeRequestDto dto) {
    if (dto.getAcctItemTypeName() == null || dto.getAcctResId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ACCT_ITEM_TYPE_NAME and ACCT_RES_ID are required");
    }

    boolean nameExists = acctItemTypeRepository.existsByAcctItemTypeNameIgnoreCase(dto.getAcctItemTypeName());
    if (nameExists) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00609") + dto.getAcctItemTypeName());
    }

    AcctItemType acctItemType = new AcctItemType();
    acctItemType.setAcctResId(dto.getAcctResId());
    acctItemType.setParentId(dto.getParentId());
    acctItemType.setExchangeItemTypeId(dto.getExchangeItemTypeId());
    acctItemType.setAcctItemTypeName(dto.getAcctItemTypeName());
    acctItemType.setComments(dto.getComments());
    acctItemType.setAcctItemTypeCode(dto.getAcctItemTypeCode());
    acctItemType.setUsageType(dto.getUsageType());
    acctItemType.setSpId(dto.getSpId());
    acctItemType.setGstType(dto.getGstType());
    acctItemType.setFeeType(dto.getFeeType());
    acctItemType.setZeroFeePrintFlag(dto.getZeroFeePrintFlag());
    acctItemType.setDefaultTaxItemTypeId(dto.getDefaultTaxItemTypeId());
    acctItemType.setFeeClass(dto.getFeeClass());
    acctItemType.setBillPriority(dto.getBillPriority());
    acctItemType.setAcctItemGroupId(dto.getAcctItemGroupId());
    acctItemType.setBillItemType(dto.getBillItemType());

    AcctItemType saved = acctItemTypeRepository.save(acctItemType);

    if (dto.getTaxAcctItemTypeId() != null) {
      AcctItemTypeBind bind = new AcctItemTypeBind();
      bind.setAcctItemTypeId(saved.getId());
      bind.setAcctItemTypeBindType("T");
      bind.setBindAcctItemTypeId(dto.getTaxAcctItemTypeId());
      bind.setSpId(dto.getSpId());
      acctItemTypeBindRepository.save(bind);
    }

    if (dto.getDiscountAcctItemTypeId() != null) {
      AcctItemTypeBind bind = new AcctItemTypeBind();
      bind.setAcctItemTypeId(acctItemType.getId());
      bind.setAcctItemTypeBindType("D");
      bind.setBindAcctItemTypeId(dto.getDiscountAcctItemTypeId());
      bind.setSpId(dto.getSpId());
      acctItemTypeBindRepository.save(bind);
    }

    if (dto.getTaxApplyId() != null) {
      AcctItemTaxApply taxApply = new AcctItemTaxApply();
      taxApply.setAcctItemTypeId(saved.getId());
      taxApply.setTaxApplyId(dto.getTaxApplyId());
      acctItemTaxApplyRepository.save(taxApply);
    }

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "Success", Map.of("acctItemTypeId", saved.getId())));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> updateAcctItemType(AcctItemTypeRequestDto dto, Integer acctItemTypeId) {

    if (dto.getAcctItemTypeName() == null || dto.getAcctResId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ACCT_ITEM_TYPE_NAME, and ACCT_RES_ID are required");
    }

    AcctItemType existing = acctItemTypeRepository.findById(acctItemTypeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ACCT_ITEM_TYPE_ID not found"));

    // Validasi duplicate name (kecuali dirinya sendiri)
    boolean nameExists = acctItemTypeRepository.existsByAcctItemTypeNameIgnoreCaseAndIdNot(dto.getAcctItemTypeName(), acctItemTypeId);
    if (nameExists) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00609"));
    }

    existing.setAcctResId(dto.getAcctResId());
    existing.setParentId(dto.getParentId());
    existing.setExchangeItemTypeId(dto.getExchangeItemTypeId());
    existing.setAcctItemTypeName(dto.getAcctItemTypeName());
    existing.setComments(dto.getComments());
    existing.setAcctItemTypeCode(dto.getAcctItemTypeCode());
    existing.setUsageType(dto.getUsageType());
    existing.setSpId(dto.getSpId());
    existing.setGstType(dto.getGstType());
    existing.setFeeType(dto.getFeeType());
    existing.setZeroFeePrintFlag(dto.getZeroFeePrintFlag());
    existing.setDefaultTaxItemTypeId(dto.getDefaultTaxItemTypeId());
    existing.setFeeClass(dto.getFeeClass());
    existing.setBillPriority(dto.getBillPriority());
    existing.setAcctItemGroupId(dto.getAcctItemGroupId());
    existing.setBillItemType(dto.getBillItemType());

    acctItemTypeRepository.save(existing);

    // Hapus dulu semua (lama)
    acctItemTypeBindRepository.deleteByAcctItemTypeId(acctItemTypeId);

    // Insert ulang bind baru
    if (dto.getTaxAcctItemTypeId() != null) {
      AcctItemTypeBind bind = new AcctItemTypeBind();
      bind.setAcctItemTypeId(acctItemTypeId);
      bind.setAcctItemTypeBindType("T");
      bind.setBindAcctItemTypeId(dto.getTaxAcctItemTypeId());
      bind.setSpId(dto.getSpId());
      acctItemTypeBindRepository.save(bind);
    }

    if (dto.getDiscountAcctItemTypeId() != null) {
      AcctItemTypeBind bind = new AcctItemTypeBind();
      bind.setAcctItemTypeId(acctItemTypeId);
      bind.setAcctItemTypeBindType("D");
      bind.setBindAcctItemTypeId(dto.getDiscountAcctItemTypeId());
      bind.setSpId(dto.getSpId());
      acctItemTypeBindRepository.save(bind);
    }

    // Hapus dulu tax apply lama
    acctItemTaxApplyRepository.deleteByAcctItemTypeId(acctItemTypeId);

    // Insert ulang
    if (dto.getTaxApplyId() != null) {
      AcctItemTaxApply taxApply = new AcctItemTaxApply();
      taxApply.setAcctItemTypeId(acctItemTypeId);
      taxApply.setTaxApplyId(dto.getTaxApplyId());
      acctItemTaxApplyRepository.save(taxApply);
    }

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "Update Success", Map.of("acctItemTypeId", acctItemTypeId)));
  }

  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<CustomeResponse> deleteAcctItemType(Integer id) {
    if (id == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ACCT_ITEM_TYPE_ID is required");
    }

    // Validasi jika data tidak ditemukan
    AcctItemType acctItemType = acctItemTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ACCT_ITEM_TYPE_ID not found: " + id));

    // Cek referensi dari tabel-tabel lain
    if (acctItemTypeRepository.existsByParentId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00778"));
    }
    if (acctItemTypeBindRepository.existsByBindAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00779"));
    }

    if (acctItemTypeRepository.existsByExchangeItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00780"));
    }

    if (dpRuleRepository.existsByAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00781"));
    }

    if (refDpRepository.existsByResultAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00782"));
    }

    if (refAcctItemTypeRepository.existsByIdAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00783"));
    }

    if (priceRepository.existsByAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00784"));
    }

    if (adjustPrepareItemRepository.existsByAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00785"));
    }

    if (acctItemRepository.existsByAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00786"));
    }

    if (overduePlanItemRepository.existsByAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00787"));
    }

    if (instalmentTypeItemRepository.existsByIdAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00788"));
    }

    // if (ibTapItemRepository.existsByAcctItemTypeId(id)) {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
    // messageService.getMessage("S-ACT-00789"));
    // }
    //
    // if (ibItemTypeRepository.existsByAcctItemTypeId(id)) {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
    // messageService.getMessage("S-ACT-00790"));
    // }
    //
    // if (acctItemLimitRepository.existsByAcctItemTypeId(id)) {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
    // messageService.getMessage("S-ACT-00791"));
    // }

    if (glAcctItemTypeRepository.existsByIdAcctItemTypeId(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00792"));
    }

    // Delete child binding data
    acctItemTypeBindRepository.deleteByAcctItemTypeId(id);
    acctItemTaxApplyRepository.deleteByAcctItemTypeId(id);

    acctItemTypeRepository.deleteById(id);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "Deleted successfully", Map.of("acctItemTypeId", id)));
  }

  public BaseResponseDto listAcctResName() {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<AcctResNameDto> acctResList = acctResRepository.findAcctResByAcctResId();

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setData(acctResList);
    return baseResponseDto;
  }

  public BaseResponseDto listAllAcctRes() {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<Object[]> resultList = acctItemTypeRepository.findAcctItemTypeList();
    List<AcctItemTypeListDto> dto = new ArrayList<>();
    for (Object[] row : resultList) {
      String acctItemTypeName = (String) row[0];
      String acctResName = (String) row[1];
      String balTypeName = (String) row[2];
      dto.add(new AcctItemTypeListDto(acctItemTypeName, acctResName, balTypeName));
    }

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setData(dto);
    return baseResponseDto;
  }

  public ResponseEntity<CustomeResponse> getAcctItemType(PagingRequestDto pagingRequest, Integer spId,
      Integer acctResId) {
    int page = Math.max(pagingRequest.getPage() - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
    Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

    var data = acctItemTypeRepository.getAcctItemTypeQueryList(spId, acctResId, pageable);
    var result = data.getContent().stream().map(acctItemTypeMapper::dto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(),
            data.getTotalPages()));

  }

  public ResponseEntity<CustomeResponse> getBalType() {
    var data = balTypeRepository.findBalType()
        .stream()
        .map(qryBalTypeMapper::toDto)
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

  public ResponseEntity<CustomeResponse> getBalTypeAcctRes(PagingRequestDto pagingRequest, Integer spId) {

    int page = Math.max(pagingRequest.getPage() - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
    Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

    var data = balTypeRepository.findBalTypeChild(spId, pageable);
    var result = data.getContent().stream().map(qryBalTypeMapper::toSimpleDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(),
            data.getTotalPages()));
  }

  public ResponseEntity<CustomeResponse> getAcctItemTypeForAdd(PagingRequestDto pagingRequest, Integer acctItemTypeId,
      Integer acctResId, Integer nullFlag, Integer parentId, Integer priceVerId, Integer priceVerIdExists,
      Integer priceVerIdEx,
      Integer curPriceId, Integer spId, String usageType, Integer defaultAcctResId, String acctItemTypeName) {
    int page = Math.max(pagingRequest.getPage() - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
    Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

    var data = acctItemTypeRepository.getAcctItemTypeDetailsForAdd(acctItemTypeId, acctResId, nullFlag, parentId,
        priceVerId, priceVerIdExists, priceVerIdEx, curPriceId, spId, usageType, defaultAcctResId,
        acctItemTypeName, pageable);
    var result = data.getContent().stream().map(acctItemTypeMapper::acctItemTypeDetailDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(),
            data.getTotalPages()));
  }

  public ResponseEntity<CustomeResponse> getAcctItemTypeForUpdate(PagingRequestDto pagingRequest,
      Integer acctItemTypeId, Integer acctResId, Integer nullFlag, Integer parentId, Integer priceVerId,
      Integer priceVerIdExists, Integer priceVerIdEx,
      Integer curPriceId, Integer spId, String usageType, Integer defaultAcctResId, String acctItemTypeName) {
    int page = Math.max(pagingRequest.getPage() - 1, 0);
    Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
    Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

    var data = acctItemTypeRepository.getAcctItemTypeDetailsForAdd(acctItemTypeId, acctResId, nullFlag, parentId,
        priceVerId, priceVerIdExists, priceVerIdEx, curPriceId, spId, usageType, defaultAcctResId,
        acctItemTypeName, pageable);
    var result = data.getContent().stream().map(acctItemTypeMapper::acctItemTypeUpdateDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(),
            data.getTotalPages()));
  }

  public ResponseEntity<CustomeResponse> getAllGstType() {
    var data = gstTypeRepository.findAllGstType()
        .stream()
        .map(gstTypeMapper::gstTypeDto)
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
  }

}
