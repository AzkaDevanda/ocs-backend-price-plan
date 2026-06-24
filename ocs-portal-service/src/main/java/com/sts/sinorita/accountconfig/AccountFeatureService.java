package com.sts.sinorita.accountconfig;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.accountConfig.*;
import com.sts.sinorita.dto.request.common.PagingRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.accountconfig.*;
import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.entity.AcctAttr;
import com.sts.sinorita.mapper.accountConfig.AccountFeatureMapper;
import com.sts.sinorita.projection.accountConfig.QryAcctAttrIncludeChannelNewProjection;
import com.sts.sinorita.projection.accountConfig.QryAttrValueProjection;
import com.sts.sinorita.projection.accountConfig.QryBaseAttrListByPKProjection;
import com.sts.sinorita.projection.attrvalue.AttrValueProjection;
import com.sts.sinorita.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountFeatureService {
    @Autowired
    private BaseAttrRepository baseAttrRepository;
    @Autowired
    private AttrValueRepository attrValueRepository;
    @Autowired
    private AcctAttrRepository acctAttrRepository;
    @Autowired
    private AttrRepository attrRepository;


    @Autowired
    private AccountFeatureMapper accountFeatureMapper;
    @Autowired
    private MessageService messageService;

    public ResponseEntity<CustomeResponse> qryAttrListByCatg(PagingRequestDto pagingRequest,String attrName){
        int page = Math.max(pagingRequest.getPage() - 1, 0);
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
        Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);


        var data = attrRepository.QryAttrListByCatg(pageable,attrName);
        var result = data.getContent().stream().map(accountFeatureMapper::qryAttrListByCatgDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,result, data.getTotalElements(), data.getTotalPages()));
    }

    public ResponseEntity<CustomeResponse> qryAttrDetail(Integer baseAttrId, Integer spId){
        var data = baseAttrRepository.qryAttrDetail(baseAttrId,spId)
                .stream()
                .map(accountFeatureMapper::qryAttrDetailDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,data));
    }

    public ResponseEntity<CustomeResponse> qryAttrValue(Integer baseAttrId){
        var data = attrValueRepository.findAttrValueListByBaseAttrIds(baseAttrId)
                .stream()
                .map(accountFeatureMapper::qryAttrValueDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,data));
    }

    public List<QryAcctAttrIncludeChannelNewProjection> getAcctAttrIncludeChannel(Integer spId){
        return acctAttrRepository.qryAcctAttrIncludeChannelNew(spId);
    }

    public List<QryBaseAttrListByPKProjection> getBaseAttrListByPK(List<Integer> baseAttrId){
        return baseAttrRepository.qryBaseAttrListByPK(baseAttrId);
    }

    public List<QryAttrValueProjection> qryAttrValue(Integer baseAttrId, Integer spId){
        return attrValueRepository.QryAttrValue(baseAttrId,spId);
    }

    public ResponseEntity<CustomeResponse> getAttrValueListt(Integer spId) {
        // Step 1: ambil attribute list
        List<QryAcctAttrIncludeChannelNewProjection> acctAttrList = getAcctAttrIncludeChannel(spId);
        if (acctAttrList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found");
        }

        // Step 2: ambil baseAttrIds untuk query berikutnya
        List<Integer> baseAttrIds = acctAttrList.stream()
                .map(QryAcctAttrIncludeChannelNewProjection::getAttrId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // Step 3: ambil base attribute detail
        List<QryBaseAttrListByPKProjection> baseAttrList = getBaseAttrListByPK(baseAttrIds);
        Map<Integer, QryBaseAttrListByPKProjection> baseAttrMap = baseAttrList.stream()
                .collect(Collectors.toMap(QryBaseAttrListByPKProjection::getAttrId, b -> b));

        // Step 4: ambil semua attrValue berdasarkan baseAttrId dan spId
        List<QryAttrValueProjection> allAttrValues = baseAttrIds.stream()
                .flatMap(id -> qryAttrValue(id, spId).stream())
                .collect(Collectors.toList());

        // Group by baseAttrId
        Map<Integer, List<QryAttrValueProjection>> attrValueMap = allAttrValues.stream()
                .collect(Collectors.groupingBy(QryAttrValueProjection::getBaseAttrId));

        // Step 5: merge semua data ke DTO final
        List<AccountFeatureDto> result = new ArrayList<>();

            for (QryAcctAttrIncludeChannelNewProjection acctAttr : acctAttrList) {
                AccountFeatureDto dto = new AccountFeatureDto();

                // isi dari QryAcctAttrIncludeChannelNew
                dto.setAttrId(acctAttr.getAttrId());
                dto.setAttrCode(acctAttr.getAttrCode());
                dto.setAttrName(acctAttr.getAttrName());
                dto.setAttrType(acctAttr.getAttrType());
                dto.setCsrVisible(acctAttr.getCsrVisible());
                dto.setDispOrder(acctAttr.getDispOrder());
                dto.setSpId(acctAttr.getSpId());
                dto.setAttrValue(acctAttr.getAttrValue());

                // isi tambahan dari QryBaseAttrListByPK
                QryBaseAttrListByPKProjection baseAttr = baseAttrMap.get(acctAttr.getAttrId());
                if (baseAttr != null) {
                    dto.setInputType(baseAttr.getInputType());
                    dto.setNullable(baseAttr.getNullable());
                    dto.setComments(baseAttr.getComments());
                    dto.setDefaultValue(baseAttr.getDefaultValue());
                }
//                if (baseAttr != null) {
//                    System.out.println("✅ BASE ATTR FOUND for ID " + acctAttr.getAttrId() + " : " + baseAttr.getAttrName());
//                } else {
//                    System.out.println("❌ BASE ATTR MISSING for ID " + acctAttr.getAttrId());
//                }


                List<QryAttrValueProjection> values = attrValueMap.get(acctAttr.getAttrId());
            if (values != null && !values.isEmpty()) {
                List<AcctValuesListDto> valueDtos = values.stream().map(v -> {
                    AcctValuesListDto valDto = new AcctValuesListDto();
                    valDto.setAttrValueId(v.getAttrValueId());
                    valDto.setBaseAttrId(v.getBaseAttrId());
                    valDto.setValueMark(v.getValueMark());
                    valDto.setValue(v.getValue());
                    valDto.setAttrName(v.getAttrName());
                    return valDto;
                }).collect(Collectors.toList());
                dto.setAcctValuesListDto(valueDtos);
            }
            result.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,result));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modAcctAttrNew(Integer spId, List<AcctAttrItemDto> acctAttr, List<AcctAttrItemDto> srcAcctAttr){
        if(srcAcctAttr != null && !srcAcctAttr.isEmpty()){
            for (AcctAttrItemDto dto : srcAcctAttr) {
                acctAttrRepository.deleteBySpIdAndAttrId(spId, dto.getAttrId());
            }
        }
        List<AcctAttr> existingAcctAttr = acctAttrRepository.findAllBySpId(spId);
        acctAttrRepository.deleteBySpId(spId);
        List<AcctAttr> merged = new ArrayList<>(existingAcctAttr);

        if(acctAttr != null && !acctAttr.isEmpty()){
            for (AcctAttrItemDto dto : acctAttr) {
                AcctAttr newAcctAttr = new AcctAttr();
                newAcctAttr.setAttrId(dto.getAttrId());
                newAcctAttr.setAttrValue(dto.getAttrValue());
                newAcctAttr.setSpId(spId);
                merged.add(newAcctAttr);
            }
        }
        Integer dispOrder = 0;
        for(AcctAttr acctAttr1 : merged){
            acctAttr1.setDispOrder(dispOrder++);
            acctAttrRepository.save(acctAttr1);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modAcctAttrValue(Integer attrId, AcctAttrValueRequestDto dto){
        if(dto == null || attrId == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ATTR_ID can not be null");
        }
        acctAttrRepository.deleteById(attrId);

        AcctAttr acctAttr = new AcctAttr();
        acctAttr.setAttrId(attrId);
        acctAttr.setAttrValue(dto.getAttrValue());
        acctAttr.setSpId(0);
        acctAttr.setDispOrder(dto.getDispOrder());
        acctAttrRepository.save(acctAttr);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> delAcctAttr(Integer acctId){
        if(acctId == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ATTR_ID can not be null");
        }
        acctAttrRepository.deleteById(acctId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modAcctAttrDispOrder(AcctAttrDispOrderDto dto){
        if(dto.getAttrId() == null || dto.getDispOrder() == null ||
                dto.getToAttrId() == null || dto.getToDispOrder() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ATTR_ID, DISP_ORDER, TO_ATTR_ID, and TO_DISP_ORDER cannot be null.");
        }
        acctAttrRepository.updateDispOrder(dto.getAttrId(), dto.getToDispOrder());
        acctAttrRepository.updateDispOrder(dto.getToAttrId(), dto.getDispOrder());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }
}
