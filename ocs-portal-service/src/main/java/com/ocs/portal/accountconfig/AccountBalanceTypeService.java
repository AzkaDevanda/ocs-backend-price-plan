package com.ocs.portal.accountconfig;

import com.ocs.portal.common.MessageService;
import com.ocs.portal.constant.HttpStatusConstant;
import com.ocs.portal.constant.SortDirection;
import com.ocs.portal.dto.request.accountConfig.AccountBalanceTypeRequestDto;
import com.ocs.portal.dto.request.common.PagingRequestDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.AcctRes;
import com.ocs.portal.entity.AcctResFree;
import com.ocs.portal.entity.TransAcctResCfg;
import com.ocs.portal.mapper.accountConfig.AccountBalanceMapper;
import com.ocs.portal.mapper.accountConfig.QryAcctResListMapper;
import com.ocs.portal.mapper.accountConfig.QryBalTypeMapper;
import com.ocs.portal.mapper.accountConfig.QryBalanceTypeWithMVNOMapper;
import com.ocs.portal.mapper.acct.AcctResMapper;
import com.ocs.portal.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class AccountBalanceTypeService {

    // =========> REPOSITORY <============
    @Autowired
    private AcctResRepository acctResRepository;
    @Autowired
    private BalTypeRepository balTypeRepository;
    @Autowired
    private AcctResFreeRepository acctResFreeRepository;
    @Autowired
    private TransAcctResCfgRepository transAcctResCfgRepository;
//    @Autowired
//    private AcctItemTypeRepository acctItemTypeRepository;
    @Autowired
    private SubBalTypeRepository subBalTypeRepository;
    @Autowired
    private BalTriggerRepository balTriggerRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private BsTypePtlRepository bsTypePtlRepository;
    @Autowired
    private CardProfileBonusRepository cardProfileBonusRepository;
    @Autowired
    private CardProfileRuleRepository cardProfileRuleRepository;

    // =========> MAPPER <============
    @Autowired
    private AcctResMapper acctResMapper;
    @Autowired
    private QryAcctResListMapper qryAcctResListMapper;
    @Autowired
    private QryBalTypeMapper qryBalTypeMapper;
    @Autowired
    private QryBalanceTypeWithMVNOMapper qryBalanceTypeWithMVNOMapper;
    @Autowired
    private AccountBalanceMapper accountBalanceMapper;

    @NotNull
    private static TransAcctResCfg getTransAcctResCfg(AccountBalanceTypeRequestDto dto, AcctRes acctRes) {
        TransAcctResCfg transAcctResCfg = new TransAcctResCfg();
        transAcctResCfg.setId(acctRes.getAcctResId());
        transAcctResCfg.setDayThreshold(dto.getTransAcctResCfg().getDayThreshold());
        transAcctResCfg.setWeekThreshold(dto.getTransAcctResCfg().getWeekThreshold());
        transAcctResCfg.setMonthThreshold(dto.getTransAcctResCfg().getMonthThreshold());
        transAcctResCfg.setDayCount(dto.getTransAcctResCfg().getDayCount());
        transAcctResCfg.setWeekCount(dto.getTransAcctResCfg().getWeekCount());
        transAcctResCfg.setMonthCount(dto.getTransAcctResCfg().getMonthCount());
        transAcctResCfg.setMinResidualBal(dto.getTransAcctResCfg().getMinResidualBal());
        transAcctResCfg.setMaxAllowed(dto.getTransAcctResCfg().getMaxAllowed());
        transAcctResCfg.setMinAllowed(dto.getTransAcctResCfg().getMinAllowed());
        transAcctResCfg.setTransferFactor(dto.getTransAcctResCfg().getTransferFactor());
        return transAcctResCfg;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> addAcctRes(AccountBalanceTypeRequestDto dto) {

        if (acctResRepository.checksSameAcctResName(dto.getAcctResName()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00793"));
        }
        if (acctResRepository.checksSameStandardCode(dto.getStdCode()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00794"));
        }
        AcctRes acctRes = accountBalanceMapper.toEntityAcctRes(dto);
        acctResRepository.save(acctRes);

        addAcctResFree(dto, acctRes);

        // if (7L == acctResDto.getBalType().longValue() &&
        // ValidateUtil.validateNotEmpty(dict.getList("VIR_ACCT_RES_LIST"))) {
        // ArrayList<DynamicDict> virAcctResList =
        // (ArrayList<DynamicDict>)dict.getList("VIR_ACCT_RES_LIST");
        // for (int i = 0; i < virAcctResList.size(); i++)
        // getAcctResDAO().insertVirAcctRes(Long.valueOf(maxAcctResId.longValue()),
        // ((DynamicDict)virAcctResList
        // .get(i)).getLong("ACCT_RES_ID"));
        // }

        if (dto.getTransAcctResCfg() != null) {
            TransAcctResCfg transAcctResCfg = getTransAcctResCfg(dto, acctRes);
            transAcctResCfgRepository.save(transAcctResCfg);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modAcctRes(Long acctResId, AccountBalanceTypeRequestDto dto) {
        if (acctResRepository.checksSameAcctResName(dto.getAcctResName(), acctResId) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00793"));
        }
        if (acctResRepository.checksSameStandardCode(dto.getStdCode(), acctResId) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00794"));
        }

        AcctRes acctRes = acctResRepository.findById(acctResId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatusConstant.NOT_FOUND_MESSAGE));
        accountBalanceMapper.modAcctRes(dto, acctRes);
        acctResRepository.save(acctRes);

        acctResFreeRepository.findById(acctResId).ifPresent(acctResFree -> acctResFreeRepository.delete(acctResFree));
        addAcctResFree(dto, acctRes);

        TransAcctResCfg transAcctResCfg1 = transAcctResCfgRepository.selectTransAcctResCfgByAcctResId(acctResId);
        if (dto.getTransAcctResCfg() == null) {
            transAcctResCfgRepository.findById(acctResId)
                    .ifPresent(transAcctResCfg -> transAcctResCfgRepository.delete(transAcctResCfg));
        } else if (transAcctResCfg1 == null) {
            TransAcctResCfg transAcctResCfg = getTransAcctResCfg(dto, acctRes);
            transAcctResCfgRepository.save(transAcctResCfg);
        } else {
            transAcctResCfgRepository.save(getTransAcctResCfg(dto, acctRes));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    private void addAcctResFree(AccountBalanceTypeRequestDto dto, AcctRes acctRes) {
        if (dto.getAcctResFree() != null) {
            AcctResFree acctResFree = new AcctResFree();
            acctResFree.setId(acctRes.getAcctResId());
            acctResFree.setValue(dto.getAcctResFree().getValue());
            acctResFree.setRum(Math.toIntExact(dto.getAcctResFree().getRum()));
            acctResFree.setSpId(0);
            acctResFreeRepository.save(acctResFree);
        }
    }

    public ResponseEntity<CustomeResponse> qryBalanceTypeWithMVNO(Integer acctResId, String acctResName,
                                                                  Integer balType, String exceptBalTypes, String refillable, Integer spId, int page, int size,
                                                                  String order_filed, SortDirection sortDirection) {

        int paging = Math.max(page - 1, 0);
        Sort.Direction direction = Sort.Direction.valueOf(sortDirection.name());
        Sort sort = Sort.by(direction, order_filed);
        Pageable pageable = PageRequest.of(paging, size, sort);

        var data = acctResRepository.qryBalanceTypeWithMVNO(acctResId, acctResName, balType, exceptBalTypes, refillable,
                spId, pageable);
        var result = data.getContent().stream().map(qryBalanceTypeWithMVNOMapper::toDto).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,
                result, data.getTotalElements(), data.getTotalPages()));
    }

    public ResponseEntity<CustomeResponse> delAcctRes(Long acctResId) {
        // Long sysParam =
        // SystemParamCache.getLongSystemParamByMask("DEFAULT_ACCT_RES_ID", null);
        // if (sysParam != null && acctResId.equals(Long.valueOf(sysParam.longValue())))
        // ExceptionHandler.publish("S-ACT-00810", 0);

        if (exchangeRateRepository.isReferencedInSrcAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00797"));
        }

        if (exchangeRateRepository.isReferencedInObjAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00798"));
        }

//        if (acctItemTypeRepository.isReferencedInAcctRes(acctResId).isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00799"));
//        }

        if (subBalTypeRepository.isReferencedInAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00800"));
        }

        if (balTriggerRepository.isReferencedInAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00801"));
        }

        if (acctResRepository.isReferencedInAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00802"));
        }

        if (bsTypePtlRepository.isReferencedInAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00803"));
        }

        if (acctResFreeRepository.isReferencedInAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00805"));
        }

        if (cardProfileBonusRepository.isReferencedInAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00806"));
        }

        if (cardProfileBonusRepository.isReferencedInAcctRes(acctResId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00807"));
        }

        acctResFreeRepository.findById(acctResId).ifPresent(acctResFree -> acctResFreeRepository.delete(acctResFree));
        // getAcctResDAO().deleteVirAcctTaxRes(acctResId);
        // getAcctResDAO().deleteVirAcctRes(acctResId);
        acctResRepository.findById(acctResId).ifPresent(acctRes -> acctResRepository.delete(acctRes));
        transAcctResCfgRepository.findById(acctResId)
                .ifPresent(transAcctResCfg -> transAcctResCfgRepository.delete(transAcctResCfg));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));

    }

    // =======> DROPDOWN LIST <=======
    public ResponseEntity<CustomeResponse> qryAcctResList(String acctResName, Character isCurrency,
                                                          Character refillable, Integer balType, String stdCode, Integer acctResIds, Integer spId) {

        var data = acctResRepository
                .qryAcctResList(acctResName, isCurrency, refillable, balType, stdCode, acctResIds, spId)
                .stream()
                .map(qryAcctResListMapper::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));

    }

    public ResponseEntity<CustomeResponse> qryBalType(Integer balType, Integer balTypeFlag, Integer spId) {

        var data = balTypeRepository.qryBalType(balType, balTypeFlag, spId)
                .stream()
                .map(qryBalTypeMapper::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));

    }

    public ResponseEntity<CustomeResponse> getBalanceTypeList(PagingRequestDto pagingRequest, String acctResName) {
        int page = Math.max(pagingRequest.getPage() - 1, 0);
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
        Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

        var data = acctResRepository.findAllBalType(acctResName, pageable);
        var result = data.getContent()
                .stream()
                .map(accountBalanceMapper::dto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(
                        200,
                        HttpStatusConstant.SUCCESS_MESSAGE,
                        result,
                        data.getTotalElements(),
                        data.getTotalPages()));
    }

}
