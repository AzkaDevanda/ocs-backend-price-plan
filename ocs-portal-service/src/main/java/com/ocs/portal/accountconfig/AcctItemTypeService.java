package com.ocs.portal.accountconfig;

import com.ocs.portal.common.MessageService;
import com.ocs.portal.constant.HttpStatusConstant;
import com.ocs.portal.dto.request.AcctItemTypeListDto;
import com.ocs.portal.dto.request.AcctItemTypeRequestDto;
import com.ocs.portal.dto.request.AcctResNameDto;
import com.ocs.portal.repository.*;
import com.ocs.portal.dto.request.common.PagingRequestDto;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.AcctItemTaxApply;
import com.ocs.portal.entity.AcctItemType;
import com.ocs.portal.entity.AcctItemTypeBind;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.mapper.accountConfig.GstTypeMapper;
import com.ocs.portal.mapper.accountConfig.QryBalTypeMapper;
import com.ocs.portal.mapper.acct.AcctItemTypeMapper;

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
//  private final AcctItemTypeBindRepository acctItemTypeBindRepository;
//  private final AcctItemTaxApplyRepository acctItemTaxApplyRepository;
  private final BalTypeRepository balTypeRepository;
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
