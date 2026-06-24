package com.sts.sinorita.accountconfig;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.constant.SortDirection;
import com.sts.sinorita.dto.request.accountConfig.BillingCycleTypeInsertDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.accountconfig.BillingCycleDto;
import com.sts.sinorita.dto.response.accountconfig.BillingCycleTypesDto;
import com.sts.sinorita.entity.BillingCycle;
import com.sts.sinorita.entity.BillingCycleType;
import com.sts.sinorita.mapper.accountConfig.BillingCycleMapper;
import com.sts.sinorita.projection.accountConfig.BillingCycleLastProjection;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.utils.Hellper;
import jakarta.persistence.EntityManager;
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

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class BillingCycleService {
    // ==========> REPOSITORY <==========
    @Autowired
    private BillingCycleRepository billingCycleRepository;
    @Autowired
    private BillingCycleTypeRepository billingCycleTypeRepository;
    @Autowired
    private ConfigItemRepository configItemRepository;
    @Autowired
    private AcctCatgCycleTypeRepository acctCatgCycleTypeRepository;


    // ==========> MAPPER <==========
    @Autowired
    private CustTypeRepository custTypeRepository;
    @Autowired
    private BillingCycleMapper billingCycleMapper;

    @Autowired
    private MessageService messageService;
    @Autowired
    private EntityManager entityManager;

    public ResponseEntity<CustomeResponse> getBillingCycleList(Character state, Integer billingCycleTypeId, Integer spId, int page, int size){
        int paging = Math.max(page - 1, 0);
//        Sort.Direction direction = Sort.Direction.valueOf(orderDirection.name());
//        Sort sort = Sort.by(direction, orderField);
        Pageable pageable = PageRequest.of(paging, size);

        var data = billingCycleRepository.findBillingCyclelist(state,billingCycleTypeId,spId, pageable);
        var result = data.getContent().stream().map(billingCycleMapper::dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
    }

    public ResponseEntity<CustomeResponse> getBillingCycleType(Integer billingCycleTypeId, Integer spId,  int page, int size){
        int paging = Math.max(page - 1, 0);
//        Sort.Direction direction = Sort.Direction.valueOf(orderDirection.name());
//        Sort sort = Sort.by(direction, orderField);
        Pageable pageable = PageRequest.of(paging, size);

        var data = billingCycleTypeRepository.findBillingCycleType(billingCycleTypeId, spId, pageable);
        var result = data.getContent().stream().map(billingCycleMapper::dto2);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, result, data.getTotalElements(), data.getTotalPages()));
    }

    public ResponseEntity<CustomeResponse> getCustType(Character state){
        var data = custTypeRepository.findCusType(state)
                .stream()
                .map(billingCycleMapper::dto3)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> insertBillingCycleType(BillingCycleTypeInsertDto dto){
        boolean isNameDuplicate = billingCycleTypeRepository.existsByBillingCycleTypeNameAndSpId(dto.getBillingCycleTypeName(), dto.getSpId());
        if (isNameDuplicate) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00602"));
        }
        if (dto.getBillingCycleTypeCode() != null && !dto.getBillingCycleTypeCode().isEmpty()) {
            boolean isCodeDuplicate = billingCycleTypeRepository.existsByBillingCycleTypeCodeAndSpId(dto.getBillingCycleTypeCode(), dto.getSpId());
            if (isCodeDuplicate) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-RES-61099"));
            }
        }

        Integer billingCycleTypeId = billingCycleTypeRepository.getNextBillingCycleTypeId();

        BillingCycleType billingCycleType = new BillingCycleType();
        billingCycleType.setBillingCycleTypeId(billingCycleTypeId);
        billingCycleType.setTimeUnit(dto.getTimeUnit());
        billingCycleType.setBillingCycleTypeName(dto.getBillingCycleTypeName());
        billingCycleType.setComments(dto.getComments());
        billingCycleType.setQuantity((int) dto.getQuantity());
        billingCycleType.setBeginDate(dto.getBeginDate());
        billingCycleType.setDebtDate(dto.getDebtDate());
        billingCycleType.setSpId((int) dto.getSpId());
        billingCycleType.setOperator(dto.getOperator());
        billingCycleType.setBillingCycleTypeCode(dto.getBillingCycleTypeCode());
        billingCycleType.setRunDate(dto.getRunDate());
        if(dto.getPostPaid() == null){
            billingCycleType.setPostpaid('Y');
        }else{
            billingCycleType.setPostpaid(dto.getPostPaid());
        }
        billingCycleType.setCustType(dto.getCustType());
        billingCycleType.setProdType(dto.getProdType());

        billingCycleTypeRepository.save(billingCycleType);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, billingCycleType));
    }

    public ResponseEntity<CustomeResponse> delBillingCycleType (Integer billingCycleTypeId){
        try{
            if(!billingCycleTypeRepository.findById(billingCycleTypeId).isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BillingCycle Id not found");
            }
            boolean billingCycleTypesId = acctCatgCycleTypeRepository.existsByBillingCycleTypeId(billingCycleTypeId);
            if(billingCycleTypesId){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00601"));
            }
            billingCycleTypeRepository.deleteById(billingCycleTypeId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,messageService.getMessage("S-ACT-00304"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    public ResponseEntity<CustomeResponse> modBillingCycleType(Integer billingCycleTypeId, BillingCycleTypeInsertDto dto){
        try{
            boolean nameExist = billingCycleTypeRepository.existsByNameAndSpIdAndNotId(dto.getBillingCycleTypeName(),dto.getSpId(),billingCycleTypeId);
            if(nameExist){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00602"));
            }
            if(dto.getBillingCycleTypeCode() != null && !dto.getBillingCycleTypeCode().isEmpty()){
                boolean codeExist = billingCycleTypeRepository.existsByCodeAndSpIdAndNotId(dto.getBillingCycleTypeCode(),dto.getSpId(),billingCycleTypeId);
                if(codeExist){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-RES-61099"));
                }
            }
            BillingCycleType billingCycleType = billingCycleTypeRepository.findById(billingCycleTypeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageService.getMessage("S-ACT-00306")));

            billingCycleType.setBillingCycleTypeName(dto.getBillingCycleTypeName());
            billingCycleType.setBillingCycleTypeCode(dto.getBillingCycleTypeCode());
            billingCycleType.setComments(dto.getComments());
            billingCycleType.setTimeUnit(dto.getTimeUnit());
            billingCycleType.setQuantity(dto.getQuantity());
            billingCycleType.setBeginDate(dto.getBeginDate());
            billingCycleType.setDebtDate(dto.getDebtDate());
            billingCycleType.setRunDate(dto.getRunDate());
            billingCycleType.setOperator(dto.getOperator());
            billingCycleType.setSpId(dto.getSpId());
            billingCycleType.setPostpaid(dto.getPostPaid() == null ? 'Y' : dto.getPostPaid());
            billingCycleType.setCustType(dto.getCustType());
            billingCycleType.setProdType(dto.getProdType());
            billingCycleTypeRepository.save(billingCycleType);
        } catch (ResponseStatusException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,messageService.getMessage("S-ACT-00305"), ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> addBillingCycle(BillingCycleTypesDto billingCycleTypesDto){
        Optional<BillingCycleLastProjection> lastBillingCycle = billingCycleRepository.findLastByBillingCycleTypeId(billingCycleTypesDto.getBillingCycleTypeId());


        List<BillingCycleDto> listBillingCycleDto = null;
        Date cycleBeginDate = null;
        Date debtDate = null;
        try{
            if(lastBillingCycle.isPresent() && lastBillingCycle.get().getCycleEndDate() != null){
                cycleBeginDate = lastBillingCycle.get().getCycleEndDate();
                debtDate = lastBillingCycle.get().getDebtDate();

                if('Y' == billingCycleTypesDto.getTimeUnit()){
                    debtDate = Hellper.offsetYear(debtDate, billingCycleTypesDto.getQuantity());
                }
                else if('M' == billingCycleTypesDto.getTimeUnit()){
                    debtDate = Hellper.offsetMonth(debtDate, billingCycleTypesDto.getQuantity());
                }
                else if('W' == billingCycleTypesDto.getTimeUnit()){
                    debtDate = Hellper.offsetWeek(debtDate, billingCycleTypesDto.getQuantity());
                }
                else if('D' == billingCycleTypesDto.getTimeUnit()){
                    debtDate = Hellper.offsetDay(debtDate, billingCycleTypesDto.getQuantity());
                }
                else if('S' == billingCycleTypesDto.getTimeUnit()){
                    debtDate = Hellper.offsetSecond(debtDate, billingCycleTypesDto.getQuantity());
                }
                else{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00307"));
                }
            }
            else{
                cycleBeginDate = billingCycleTypesDto.getBeginDate();
                debtDate = billingCycleTypesDto.getBeginDate();
            }
            listBillingCycleDto = generateBillingCycleList(cycleBeginDate,debtDate,billingCycleTypesDto);
            billingCycleRepository.saveAll(listBillingCycleDto.stream().map(BillingCycleMapper::toEntity).toList());

        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00308"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, listBillingCycleDto));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity <CustomeResponse> delBillingCycle (Integer billingCycleTypeId){
        int cnt = billingCycleRepository.countActiveBillingCycles(billingCycleTypeId);
        int delCnt = billingCycleRepository.deleteFutureBillingCycles(billingCycleTypeId);
        if(cnt > delCnt){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-87030"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> delSingleBillingCycle(Integer billingCycleId){

        int chgBillingCnt = billingCycleRepository.deleteByBillingCycleIdIfFuture(billingCycleId);
        if(chgBillingCnt == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-87031"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    private BillingCycleDto convertToDto(BillingCycle entity) {
        BillingCycleDto dto = new BillingCycleDto();
        dto.setBillingCycleId(entity.getBillingCycleId());
        dto.setBillingCycleTypeId(entity.getBillingCycleTypeId());
        dto.setCycleBeginDate(entity.getCycleBeginDate());
        dto.setCycleEndDate(entity.getCycleEndDate());
        dto.setState(entity.getState());
        dto.setDebtDate(entity.getDebtDate());
        dto.setSpId(entity.getSpId());
        dto.setRunDate(entity.getRunDate());
//        dto.setDocumentDate(entity.getDocumentDate());
//        dto.setPostingDate(entity.getPostingDate());
//        dto.setInvoiceDate(entity.getInvoiceDate());
        dto.setOriginDate(entity.getOriginDate());
        dto.setNotificationDate(entity.getNotificationDate());
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> editSingleBillingCycle(Integer billingCycleId, BillingCycleDto billingCycleDto){
        BillingCycle original = billingCycleRepository.findById(billingCycleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00316")));

        if(billingCycleDto.getCycleBeginDate().after(billingCycleDto.getCycleEndDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00310"));
        }
        if(billingCycleDto.getCycleBeginDate().after(billingCycleDto.getDebtDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00913"));
        }
        if(!billingCycleDto.getBillingCycleId().equals(billingCycleId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00311"));
        }
        BillingCycleDto originalDto = convertToDto(original);
        editBillingCycleBeginDate(billingCycleDto, originalDto);
        editBillingCycleEndDate(billingCycleDto, originalDto);

//        original.setCycleBeginDate(billingCycleDto.getCycleBeginDate());
//        original.setCycleEndDate(billingCycleDto.getCycleEndDate());
        original.setDebtDate(billingCycleDto.getDebtDate());
        original.setRunDate(billingCycleDto.getRunDate());
        original.setDocumentDate(billingCycleDto.getDocumentDate());
        original.setPostingDate(billingCycleDto.getPostingDate());
        original.setInvoiceDate(billingCycleDto.getInvoiceDate());
        original.setNotificationDate(billingCycleDto.getNotificationDate());
        original.setState(billingCycleDto.getState());
        original.setSpId(billingCycleDto.getSpId());

        if(billingCycleDto.getOriginDate() == null){
            original.setOriginDate(original.getDocumentDate());
        }
        else{
            original.setOriginDate(billingCycleDto.getOriginDate());
        }
        original.setNotificationDate(billingCycleDto.getNotificationDate());
        billingCycleRepository.save(original);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    private void editBillingCycleBeginDate(BillingCycleDto billingCycleDto, BillingCycleDto originalBillingCycleDto){
        if(!billingCycleDto.getCycleBeginDate().equals(originalBillingCycleDto.getCycleBeginDate())){
           BillingCycle previousBillingCycle = billingCycleRepository.findPreviousBillingCycle(billingCycleDto.getBillingCycleId(),billingCycleDto.getBillingCycleTypeId());
            if(previousBillingCycle != null && previousBillingCycle.getBillingCycleId() != null){
                if(billingCycleDto.getCycleBeginDate().compareTo(previousBillingCycle.getCycleBeginDate()) < 0){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00312"));
                }
                if(previousBillingCycle.getState() != 'A'){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00313"));
                }
                previousBillingCycle.setCycleEndDate(billingCycleDto.getCycleBeginDate());

                billingCycleRepository.save(previousBillingCycle);
            }
        }
    }

    private void editBillingCycleEndDate(BillingCycleDto billingCycleDto, BillingCycleDto originalBillingCycleDto){
        if(!billingCycleDto.getCycleEndDate().equals(originalBillingCycleDto.getCycleEndDate())){
            BillingCycle nextBillingCycle = billingCycleRepository.findNextBillingCycle(billingCycleDto.getBillingCycleId(),billingCycleDto.getBillingCycleTypeId());
            if(nextBillingCycle != null && nextBillingCycle.getBillingCycleId() != null){
                if(billingCycleDto.getCycleEndDate().compareTo(nextBillingCycle.getCycleEndDate()) > 0){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00314"));
                }
                if(nextBillingCycle.getState() != 'A'){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00315"));
                }
                nextBillingCycle.setCycleBeginDate(billingCycleDto.getCycleEndDate());

                billingCycleRepository.save(nextBillingCycle);
            }
        }
    }

    private List<BillingCycleDto> generateBillingCycleList(Date cycleBeginDate, Date debtDate, BillingCycleTypesDto billingCycleTypesDto){
        ArrayList<BillingCycleDto> listBillingCycleDto = new ArrayList<>();
        Integer spId = billingCycleTypesDto.getSpId();
        Date runDate = billingCycleTypesDto.getRunDate();
        Date type_begin_date = billingCycleTypesDto.getBeginDate();
        Character timeUnit = billingCycleTypesDto.getTimeUnit();
        Long diffSeconds = 0L;
        if(runDate != null){
            diffSeconds = Hellper.differDateInDays(runDate, type_begin_date, 0);
        }
        if('Y' == timeUnit){
            generatedBillingCycleByYear(cycleBeginDate, debtDate, billingCycleTypesDto, spId, runDate, diffSeconds, listBillingCycleDto);
        }
        else if('M' == timeUnit){
            generatedBillingCycleByMonth(cycleBeginDate, debtDate, billingCycleTypesDto, spId, runDate, diffSeconds, listBillingCycleDto);
        }
        else if('W' == timeUnit){
            generatedBillingCycleByWeek(cycleBeginDate, debtDate, billingCycleTypesDto, spId, runDate, diffSeconds, listBillingCycleDto);
        }
        else if('D' == timeUnit){
            generatedBillingCycleByDay(cycleBeginDate, debtDate, billingCycleTypesDto, spId, runDate, diffSeconds, listBillingCycleDto);
        }
        else if('S' == timeUnit){
            generatedBillingCycleSpecifiedDay(cycleBeginDate, debtDate, billingCycleTypesDto, spId, runDate, diffSeconds, listBillingCycleDto);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00307"));
        }
        return listBillingCycleDto;
    }


    private void generatedBillingCycleByYear(Date cycleBeginDate, Date debtDate, BillingCycleTypesDto billingCycleTypesDto, Integer spId, Date runDate, Long diffSeconds, List<BillingCycleDto> listBillingCycleDto){
        BillingCycleDto billingCycleDto = new BillingCycleDto();
        billingCycleDto.setBillingCycleTypeId(billingCycleTypesDto.getBillingCycleTypeId());
        billingCycleDto.setBillingCycleId(getBillingCycleId(billingCycleTypesDto.getBillingCycleTypeId()));
        billingCycleDto.setState('A');
        billingCycleDto.setCycleBeginDate(cycleBeginDate);
        billingCycleDto.setCycleEndDate(Hellper.offsetYear(cycleBeginDate, billingCycleTypesDto.getQuantity()));
        billingCycleDto.setDebtDate(debtDate);
        billingCycleDto.setSpId(spId);
        if(runDate == null){
            billingCycleDto.setRunDate(null);
        }
        else{
            billingCycleDto.setRunDate(Hellper.offsetSecond(billingCycleDto.getCycleEndDate(),diffSeconds));
        }
        listBillingCycleDto.add(billingCycleDto);
    }

    private void generatedBillingCycleByMonth(Date cycleBeginDate, Date debtDate, BillingCycleTypesDto billingCycleTypesDto, Integer spId, Date runDate, long diffSeconds, List<BillingCycleDto> listBillingCycleDto){
        Integer quantity = billingCycleTypesDto.getQuantity();
        Integer count = (int) Math.ceil(12.0 / quantity);
        for(int i=0; i < count; i++){
            BillingCycleDto billingCycleDto = new BillingCycleDto();
            billingCycleDto.setBillingCycleTypeId(billingCycleTypesDto.getBillingCycleTypeId());
            billingCycleDto.setBillingCycleId(getBillingCycleId(billingCycleTypesDto.getBillingCycleTypeId()));
            billingCycleDto.setState('A');
            billingCycleDto.setCycleBeginDate(Hellper.offsetMonth(cycleBeginDate, quantity * i));
            billingCycleDto.setCycleEndDate(Hellper.offsetMonth(cycleBeginDate, quantity * (i + 1)));
            billingCycleDto.setDebtDate(Hellper.offsetMonth(debtDate,quantity * i));
            billingCycleDto.setSpId(spId);
            if(runDate == null){
                billingCycleDto.setRunDate(null);
            }
            else{
                billingCycleDto.setRunDate(Hellper.offsetSecond(billingCycleDto.getCycleEndDate(),diffSeconds));
            }
            listBillingCycleDto.add(billingCycleDto);
        }
    }

    private void generatedBillingCycleByWeek(Date cycleBeginDate, Date debtDate, BillingCycleTypesDto billingCycleTypesDto, Integer spId, Date runDate, long diffSeconds, List<BillingCycleDto> listBillingCycleDto){
        Integer quantity = billingCycleTypesDto.getQuantity();
        Integer count = (int) Math.ceil(53.0 / quantity);
        for(int i = 0; i < count; i++){
            BillingCycleDto billingCycleDto = new BillingCycleDto();
            billingCycleDto.setBillingCycleTypeId(billingCycleTypesDto.getBillingCycleTypeId());
            billingCycleDto.setBillingCycleId(getBillingCycleId(billingCycleTypesDto.getBillingCycleTypeId()));
            billingCycleDto.setState('A');
            billingCycleDto.setCycleBeginDate(Hellper.offsetWeek(cycleBeginDate, quantity * i));
            billingCycleDto.setCycleEndDate(Hellper.offsetWeek(cycleBeginDate, quantity * (i + 1)));
            billingCycleDto.setDebtDate(Hellper.offsetWeek(debtDate,quantity * i));
            billingCycleDto.setSpId(spId);
            if(runDate == null){
                billingCycleDto.setRunDate(null);
            }
            else{
                billingCycleDto.setRunDate(Hellper.offsetSecond(billingCycleDto.getCycleEndDate(),diffSeconds));
            }
            listBillingCycleDto.add(billingCycleDto);
        }
    }

    private void generatedBillingCycleByDay(Date cycleBeginDate, Date debtDate, BillingCycleTypesDto billingCycleTypesDto, Integer spId, Date runDate, long diffSeconds, List<BillingCycleDto> listBillingCycleDto){
        Integer quantity = billingCycleTypesDto.getQuantity();
        Integer count = (int) Math.ceil(365.0 / quantity);
        for(int i = 0; i < count; i++){
            BillingCycleDto billingCycleDto = new BillingCycleDto();
            billingCycleDto.setBillingCycleTypeId(billingCycleTypesDto.getBillingCycleTypeId());
            billingCycleDto.setBillingCycleId(getBillingCycleId(billingCycleTypesDto.getBillingCycleTypeId()));
            billingCycleDto.setState('A');
            billingCycleDto.setCycleBeginDate(Hellper.offsetDay(cycleBeginDate, quantity * i));
            billingCycleDto.setCycleEndDate(Hellper.offsetDay(cycleBeginDate, quantity * (i + 1)));
            billingCycleDto.setDebtDate(Hellper.offsetDay(debtDate,quantity * i));
            billingCycleDto.setSpId(spId);
            if(runDate == null){
                billingCycleDto.setRunDate(null);
            }
            else{
                billingCycleDto.setRunDate(Hellper.offsetSecond(billingCycleDto.getCycleEndDate(),diffSeconds));
            }
            listBillingCycleDto.add(billingCycleDto);
        }
    }

    private void generatedBillingCycleSpecifiedDay(Date cycleBeginDate, Date debtDate, BillingCycleTypesDto billingCycleTypesDto, Integer spId, Date runDate, long diffSeconds, List<BillingCycleDto> listBillingCycleDto){
        BillingCycleDto billingCycleDto = new BillingCycleDto();
        billingCycleDto.setBillingCycleTypeId(billingCycleTypesDto.getBillingCycleTypeId());
        billingCycleDto.setBillingCycleId(getBillingCycleId(billingCycleTypesDto.getBillingCycleTypeId()));
        billingCycleDto.setState('A');
        billingCycleDto.setCycleBeginDate(cycleBeginDate);
        billingCycleDto.setCycleEndDate(debtDate);
        billingCycleDto.setDebtDate(Hellper.offsetDay(debtDate, 1));
        billingCycleDto.setSpId(spId);
        if(runDate == null){
            billingCycleDto.setRunDate(null);
        }
        else{
            billingCycleDto.setRunDate(Hellper.offsetSecond(billingCycleDto.getCycleEndDate(),diffSeconds));
        }
        listBillingCycleDto.add(billingCycleDto);
    }

    public Integer getBillingCycleId(Integer billingCycleTypeId){
        Integer billingCycleId = billingCycleRepository.getNextBillingCycleId();
        String prefixes = configItemRepository.findConfigItem("ACCT","ACCT_BILLING","PREFIX_4_BILLING_CYCLE_ID").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
        log.info("prefixes = " + prefixes);
        String prefix = getValFromKeyValuesStr(prefixes, billingCycleTypeId + "", ";", "=");
        if (prefix != null)
            billingCycleId = Integer.parseInt(prefix + billingCycleId);
        return billingCycleId;
    }

    public String getValFromKeyValuesStr(String inputStr, String key, String spliter, String keyValueSpliter){
        String isRemoveSpace = configItemRepository.findConfigItem("ACCT","FEE","IS_REMOVE_SPACE_4_EXT_CAL_FEE_PARAM_916").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
        return getValFromKeyValues(inputStr, key, spliter, keyValueSpliter, !"N".equals(isRemoveSpace));
    }

    public String getValFromKeyValues(String inputStr, String key, String spliter, String keyValueSpliter, boolean isRemoveSpace){
        String value = null;
        if (inputStr == null || inputStr.isEmpty() || key == null || key.isEmpty())
            return value;
        if (keyValueSpliter.isEmpty())
            keyValueSpliter = "=";
        if (spliter.isEmpty())
            spliter = ";";
        if (isRemoveSpace) {
            inputStr = inputStr.replaceAll(" ", "");
        } else {
            inputStr = inputStr.trim();
        }
        String longKey = spliter + key + keyValueSpliter;
        String mediumKey = key + keyValueSpliter;
        int pos = getKeyPosition(inputStr, longKey, mediumKey);
        if (pos < 0) {
            longKey = spliter + '\'' + key + '\'' + keyValueSpliter;
            mediumKey = '\'' + key + '\'' + keyValueSpliter;
            pos = getKeyPosition(inputStr, longKey, mediumKey);
            if (pos < 0)
                return value;
        }
        int endIndex = inputStr.indexOf(spliter, pos);
        if (endIndex < 0) {
            value = inputStr.substring(pos);
        } else {
            value = inputStr.substring(pos, endIndex);
        }
        value = value.trim();
        log.info("value = " + value);
        if (spliter.equals(",") && keyValueSpliter
                .equals(":"))
            value = unEscapeCalFeeStr(value);
        return value.replaceFirst("^'([^']+)'$", "$1");
    }

    private Integer getKeyPosition(String inputStr, String longKey, String mediumKey){
        int pos = inputStr.indexOf(longKey);
        if (pos < 0) {
            pos = inputStr.indexOf(mediumKey);
            if (pos != 0) {
                String mediumKey916 = "916=" + mediumKey;
                pos = inputStr.indexOf(mediumKey916);
                if (pos < 0)
                    return -1;
                pos += mediumKey916.length();
                return pos;
            }
            pos += mediumKey.length();
        } else {
            pos += longKey.length();
        }
        return pos;
    }

    public String unEscapeCalFeeStr (String inputString){
        String isNeedEscape = configItemRepository.findConfigItem("ACCT","FEE","IS_NEED_ESCAPE").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
        if (!"Y".equals(isNeedEscape))
            return inputString;
        if (inputString.isEmpty() || inputString == null)
            return inputString;
        inputString = inputString.replaceAll("&\\{2c}", ",");
        inputString = inputString.replaceAll("&\\{20}", " ");
        inputString = inputString.replaceAll("&\\{7c}", "\\|");
        inputString = inputString.replaceAll("&\\{3b}", ";");
        inputString = inputString.replaceAll("&\\{3a}", ":");
        inputString = inputString.replaceAll("&\\{3d}", "=");
        return inputString;
    }
}
