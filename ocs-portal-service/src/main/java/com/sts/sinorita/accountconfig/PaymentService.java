package com.sts.sinorita.accountconfig;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.constant.SortDirection;
import com.sts.sinorita.dto.PaymentMethodDto;
import com.sts.sinorita.dto.request.accountConfig.AddDDParamDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.DDParam;
import com.sts.sinorita.entity.PaymentMethod;
import com.sts.sinorita.mapper.accountConfig.DDParamMapper;
import com.sts.sinorita.mapper.accountConfig.PaymentMethodMapper;
import com.sts.sinorita.repository.DDParamRepository;
import com.sts.sinorita.repository.PaymentMethodRepository;
import com.sts.sinorita.repository.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    // ==========> REPOSITORY <==========
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private DDParamRepository ddParamRepository;

    // ==========> MAPPER <==========
    @Autowired
    private PaymentMethodMapper paymentMethodMapper;
    @Autowired
    private DDParamMapper ddParamMapper;
    @Autowired
    private MessageService messageService;

    public ResponseEntity<CustomeResponse> getAllPaymentMethodList(Integer paymentMethodId, Character paymentType, Integer spId, int page, int size, String orderField, SortDirection orderDirection) {
        int paging = Math.max(page - 1, 0);
        Sort.Direction direction = Sort.Direction.valueOf(orderDirection.name());
        Sort sort = Sort.by(direction, orderField);
        Pageable pageable = PageRequest.of(paging, size, sort);

        var data = paymentMethodRepository.findPaymentMethods(paymentMethodId, paymentType, spId, pageable);
        var result = data.getContent().stream().map(paymentMethodMapper::dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
    }

    public ResponseEntity<CustomeResponse> getAllPaymentTypeList(){
        var data = paymentTypeRepository.findByPaymentTypeNative()
                .stream()
                .map(paymentMethodMapper::dto2)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> insertPaymentMethod(PaymentMethodDto paymentMethodDto) {
        if (paymentMethodDto.getPaymentMethodName() == null || paymentMethodDto.getPaymentType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PAYMENT_METHOD_NAME and PAYMENT_TYPE are required");
        }

        boolean isExist = checkPaymentMethodNameExist(paymentMethodDto);
        if (isExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-0000866"));
        }

        Integer paymentMethodId = paymentMethodDto.getPaymentMethodId() != null
                ? paymentMethodDto.getPaymentMethodId()
                : paymentMethodRepository.getNextPaymentMethodId();

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setPaymentMethodId(paymentMethodId);
        paymentMethod.setPaymentMethodName(paymentMethodDto.getPaymentMethodName());
        paymentMethod.setPaymentType(paymentMethodDto.getPaymentType());
        paymentMethod.setComments(paymentMethodDto.getComments());
        paymentMethod.setSystemReserved(paymentMethodDto.getSystemReserved());
        paymentMethod.setSpId(paymentMethodDto.getSpId());
        paymentMethod.setPaymentMethodCode(paymentMethodDto.getPaymentMethodCode());
        paymentMethod.setGroupType(paymentMethodDto.getGroupType());
        paymentMethodRepository.save(paymentMethod);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null ));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modPaymentMethod(Integer paymentMethodId, PaymentMethodDto paymentMethodDto) {
        if(paymentMethodDto == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }
        if(paymentMethodId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PAYMENT_METHOD_ID is missing");
        }
        if(paymentMethodDto.getPaymentMethodName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PAYMENT_METHOD_NAME is missing");
        }
        if(paymentMethodDto.getPaymentType() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PAYMENT_TYPE is missing");
        }

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PAYMENT_METHOD_ID NOT_FOUND"));

        if (paymentMethodRepository.checksSamePaymentMethodName(paymentMethodDto.getPaymentMethodName(),paymentMethodId)>0 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-0000867"));
        }

        if(paymentMethod.getPaymentType() == 'A' && paymentMethodDto.getPaymentType() == 'B'){
            ddParamRepository.deleteById(paymentMethodId);
        }

        paymentMethod.setPaymentMethodName(paymentMethodDto.getPaymentMethodName());
        paymentMethod.setPaymentType(paymentMethodDto.getPaymentType());
        paymentMethod.setComments(paymentMethodDto.getComments());
        paymentMethod.setSystemReserved(paymentMethodDto.getSystemReserved());
        paymentMethod.setSpId(paymentMethodDto.getSpId());
        paymentMethod.setGroupType(paymentMethodDto.getGroupType());
        paymentMethod.setPaymentMethodCode(paymentMethodDto.getPaymentMethodCode());
        paymentMethod.setGroupType(paymentMethodDto.getGroupType());
        paymentMethodRepository.save(paymentMethod);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,paymentMethod));

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> delPaymentMethod(Integer paymentMethodId) {
        if(paymentMethodId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PAYMENT_METHOD_ID is missing");
        }

        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(paymentMethodId);
        if (!paymentMethod.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PAYMENT_METHOD_ID NOT_FOUND");
        }
        ddParamRepository.deleteById(paymentMethodId);
        paymentMethodRepository.deleteById(paymentMethodId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> addDDParam(AddDDParamDto addParamDto){
        DDParam ddParam = new DDParam();
        if(addParamDto.getPaymentMethodId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentMethodId is missing");
        }
        if(addParamDto.getSpIban() == null || addParamDto.getSpIban().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "spIban can not be null!");
        }
        ddParam.setPaymentMethodId(addParamDto.getPaymentMethodId());
        ddParam.setDaysBefExtra(addParamDto.getDaysBefExtra());
        ddParam.setSpIban(addParamDto.getSpIban());
        ddParam.setReissueDelay(addParamDto.getReIssueDelay());
        ddParam.setCloseMandateLimit(addParamDto.getCloseMandateLimit());
        ddParamRepository.save(ddParam);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null ));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modDDParam(Integer paymentMethodId,AddDDParamDto addParamDto){
        Optional<DDParam> ddParam = ddParamRepository.findById(paymentMethodId);
        if(ddParam.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentMethodId is missing");
        }

        if(addParamDto.getSpIban() == null || addParamDto.getSpIban().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "spIban can not be null!");
        }
        DDParam ddParam1 = ddParam.get();
        ddParam1.setDaysBefExtra(addParamDto.getDaysBefExtra());
        ddParam1.setSpIban(addParamDto.getSpIban());
        ddParam1.setReissueDelay(addParamDto.getReIssueDelay());
        ddParam1.setCloseMandateLimit(addParamDto.getCloseMandateLimit());
        ddParamRepository.save(ddParam1);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null ));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> delDDParam(Integer paymentMethodId){
        Optional<DDParam> ddParam = ddParamRepository.findById(paymentMethodId);
        if(ddParam.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentMethodId is missing");
        }
        ddParamRepository.delete(ddParam.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null ));
    }

    public ResponseEntity<CustomeResponse> listDDParam(Integer paymentMethodId){
        var data = ddParamRepository.listDDParam(paymentMethodId)
                .stream()
                .map(ddParamMapper::DDParamDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public Boolean checkPaymentMethodNameExist(PaymentMethodDto dto){
        if (dto.getPaymentMethodName() == null || dto.getPaymentMethodName().isBlank()) {
            throw new IllegalArgumentException("Payment Method Name cannot be empty");
        }

        // Jika sedang update, maka kita cek apakah ada nama yang sama selain dirinya sendiri
        if (dto.getPaymentMethodId() != null) {
            return paymentMethodRepository.existsByPaymentMethodNameAndSpIdAndPaymentMethodIdNot(dto.getPaymentMethodName(), dto.getPaymentMethodId(), dto.getSpId());
        }
        else{
            // Jika sedang create baru
            return paymentMethodRepository.existsByPaymentMethodNameAndSpId(dto.getPaymentMethodName(), dto.getSpId());
        }
    }
}
