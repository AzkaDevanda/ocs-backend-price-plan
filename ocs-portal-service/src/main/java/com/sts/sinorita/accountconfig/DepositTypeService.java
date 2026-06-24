package com.sts.sinorita.accountconfig;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.accountConfig.DepositTypeDto;
import com.sts.sinorita.dto.request.common.PagingRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.DepositType;
import com.sts.sinorita.mapper.accountConfig.DepositTypeMapper;
import com.sts.sinorita.repository.DepositItemRepository;
import com.sts.sinorita.repository.DepositTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class DepositTypeService {
    // ==========> REPOSITORY <==========
    @Autowired
    private DepositTypeRepository depositTypeRepository;
    @Autowired
    private DepositItemRepository depositItemRepository;

    @Autowired
    private MessageService messageService;
    @Autowired
    private DepositTypeMapper depositTypeMapper;

    public ResponseEntity<CustomeResponse> qryDepositType(PagingRequestDto pagingRequest,Integer spId){
        int page = Math.max(pagingRequest.getPage() - 1, 0);
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
        Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

        var data = depositTypeRepository.qryDepositType(pageable,spId);
        var result = data.getContent().stream().map(depositTypeMapper::depositType);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
    }

    public ResponseEntity<CustomeResponse> addDepositType(DepositTypeDto depositTypeDto) {
        if(depositTypeDto.getName() == null || depositTypeDto.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deposit name can not be null!");
        }
        if(depositTypeRepository.existsByNameAndSpId(depositTypeDto.getName(), depositTypeDto.getSpId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00603"));
        }

        if(depositTypeRepository.existsByDepositTypeCodeAndSpId(depositTypeDto.getDepositTypeCode(), depositTypeDto.getSpId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00604"));
        }

        Integer newId = depositTypeRepository.getNextDepositTypeId();
        DepositType depositType = new DepositType();
        depositType.setId(newId);
        depositType.setName(depositTypeDto.getName());
        depositType.setComments(depositTypeDto.getComments());
        depositType.setCharge(depositTypeDto.getCharge());
        depositType.setSpId(depositTypeDto.getSpId());
        depositType.setDepositTypeCode(depositTypeDto.getDepositTypeCode());
        depositType.setRefundable(depositTypeDto.getRefundable());
        depositType.setTransCredit(depositTypeDto.getTransCredit());
        depositType.setCheckDuration(depositTypeDto.getCheckDuration());
        depositTypeRepository.save(depositType);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));

    }

    public ResponseEntity<CustomeResponse> delDepositType(Integer id){
       if(!depositTypeRepository.findById(id).isPresent()){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DEPOSIT_TYPE_ID not found");
       }
//       if(!depositItemRepository.findById(id.longValue()).isPresent()){
//           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DEPOSIT_ITEM_ID not found");
//       }

       depositTypeRepository.deleteById(id);
       return ResponseEntity.status(HttpStatus.OK)
               .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }

    public ResponseEntity<CustomeResponse> modDepositType(Integer depositTypeId, DepositTypeDto depositTypeDto){
        if(depositTypeId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DEPOSIT_TYPE_ID not found!");
        }
        if(depositTypeDto.getName() == null || depositTypeDto.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deposit name can not be null!");
        }
        int sameName = depositTypeRepository.countByNameAndSpIdExcludingId(depositTypeDto.getName(),depositTypeDto.getSpId(),depositTypeId);
        if(sameName > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00603"));
        }
        if(depositTypeDto.getDepositTypeCode() != null){
            int sameCode = depositTypeRepository.countByCodeAndSpIdExcludingId(depositTypeDto.getDepositTypeCode(),depositTypeDto.getSpId(),depositTypeId);
            if(sameCode > 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00604"));
            }
        }
        DepositType depositType = depositTypeRepository.findById(depositTypeId).orElse(null);

        depositType.setName(depositTypeDto.getName());
        depositType.setComments(depositTypeDto.getComments());
        depositType.setCharge(depositTypeDto.getCharge());
        depositType.setSpId(depositTypeDto.getSpId());
        depositType.setDepositTypeCode(depositTypeDto.getDepositTypeCode());
        depositType.setRefundable(depositTypeDto.getRefundable());
        depositType.setTransCredit(depositTypeDto.getTransCredit());
        depositType.setCheckDuration(depositTypeDto.getCheckDuration());

        depositTypeRepository.save(depositType);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }
}
