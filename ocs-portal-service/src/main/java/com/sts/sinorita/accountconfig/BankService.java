package com.sts.sinorita.accountconfig;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.common.RefrenceChecker;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.accountConfig.AcctBankInfo;
import com.sts.sinorita.dto.request.accountConfig.BankInsertDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.Bank;
import com.sts.sinorita.entity.SepaBank;
import com.sts.sinorita.mapper.accountConfig.BankMapper;
import com.sts.sinorita.projection.accountConfig.AcctBankInfoProjection;
import com.sts.sinorita.repository.AcctRepository;
import com.sts.sinorita.repository.BankRepository;
import com.sts.sinorita.repository.SepaBankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BankService {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private SepaBankRepository sepaBankRepository;
    @Autowired
    private AcctRepository acctRepository;
    @Autowired
    private RefrenceChecker refrenceChecker;

    @Autowired
    private MessageService messageService;
    @Autowired
    private BankMapper bankMapper;

    public ResponseEntity<CustomeResponse> qryBank(Integer bankId, String onlyParent, String bankCode, Integer parentId, Integer spId, String bankName, String directFlag, String parentFlag, String countryCode){
        var data = bankRepository.qryBank(bankId,onlyParent,bankCode,parentId,spId,bankName,directFlag,parentFlag,countryCode).
                stream()
                .map(bankMapper::bankDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,data));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> addBank(BankInsertDto bankDto) {
        if(bankDto.getBankName() == null || bankDto.getBankName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank name is required");
        }
        if(bankRepository.existsByBankNameAndSpId(bankDto.getBankName(), bankDto.getSpId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-00151"));
        }
        if(bankRepository.existsByBankCodeAndSpId(bankDto.getBankCode(), bankDto.getSpId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("SS-CFG-00509"));
        }

        Integer bankId = bankDto.getBankId() != null
                ? bankDto.getBankId()
                : bankRepository.getNextBankId();
        Bank bank = new Bank();
        bank.setBankId(bankId);
        bank.setBankName(bankDto.getBankName());
        bank.setComments(bankDto.getComments());
        bank.setParentId(bankDto.getParentId());
        bank.setBankCode(bankDto.getBankCode());
        bank.setStateDate(LocalDate.now());
        bank.setState('A');
        bank.setDirectDebitFlag(bankDto.getDirectDebitFlag());
        bank.setCountryCode(bankDto.getCountryCode());
        bank.setSpId(0);
        bank.setCountryCode(bankDto.getCountryCode() != null ? bankDto.getCountryCode() : "0");
        bankRepository.save(bank);

        if(bankDto.getSepaAction().equals("new")){
            SepaBank sepaBank = new SepaBank();
            sepaBank.setBankId(bankId);
            sepaBank.setBic(bankDto.getBic());
            sepaBank.setIbanFormat(bankDto.getIbanFormat());
            sepaBank.setSepaState('A');
            sepaBankRepository.save(sepaBank);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modBank(Integer bankId, BankInsertDto bankDto) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BANK_ID not found"));

        if(bankId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BANK_ID can not be null");
        }
        if(bankDto.getBankName() == null || bankDto.getBankName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BANK_NAME can not be null");
        }

        if(bankRepository.existsByBankNameAndSpIdAndBankIdNot(bankDto.getBankName(), bankDto.getSpId(), bankId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-00151"));
        }
        if(bankRepository.existsByBankCodeAndSpIdAndBankIdNot(bankDto.getBankCode(), bankDto.getSpId(), bankId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("SS-CFG-00509"));
        }

        bank.setParentId(bankDto.getParentId());
        bank.setBankName(bankDto.getBankName());
        bank.setBankCode(bankDto.getBankCode());
        bank.setStateDate(LocalDate.now());
        bank.setComments(bankDto.getComments());
        bankRepository.save(bank);

        if(bankDto.getSepaAction() != null){
            if(sepaBankRepository.findById(bankId).isEmpty() && bankDto.getBic() != null && bankDto.getIbanFormat() != null){
                if(bankDto.getSepaAction().equals("new")){
                    SepaBank sepaBank = new SepaBank();
                    sepaBank.setBankId(bankId);
                    sepaBank.setBic(bankDto.getBic());
                    sepaBank.setIbanFormat(bankDto.getIbanFormat());
                    sepaBank.setSepaState('A');
                    sepaBankRepository.save(sepaBank);
                }
            }
            if(sepaBankRepository.findById(bankId).isPresent() && bankDto.getBic() != null && bankDto.getIbanFormat() != null){
                if(bankDto.getSepaAction().equals("mod")){
                    SepaBank sepaBank = sepaBankRepository.findById(bankId).get();
                    sepaBank.setBic(bankDto.getBic());
                    sepaBank.setIbanFormat(bankDto.getIbanFormat());
                    sepaBankRepository.save(sepaBank);
                }
            }
            if(sepaBankRepository.findById(bankId).isPresent() && bankDto.getBic() == null && bankDto.getIbanFormat() == null){
                if(bankDto.getSepaAction().equals("del")){
                    sepaBankRepository.deleteById(bankId);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> deleteBank(Integer bankId, String sepaAction){
        if(bankId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BANK_ID can not be null");
        }
        refrenceChecker.checkFieldNotReferenced("INSTANT_PAYMENT", "BANK_ID", bankId, "S-CFG-00174");
        refrenceChecker.checkFieldNotReferenced("IN_CHARGING", "BANK_ID", bankId, "S-CFG-00175");
        refrenceChecker.checkFieldNotReferenced("DEPOSIT_ITEM", "BANK_ID", bankId, "S-CFG-00176");
        refrenceChecker.checkFieldNotReferenced("PAYMENT", "BANK_ID", bankId, "S-CFG-00179");
        refrenceChecker.checkFieldNotReferenced("DIRECT_DEBIT_INFO", "BANK_ID", bankId, "S-CFG-00181");
        refrenceChecker.checkFieldNotReferenced("BANK_CARD_TYPE_INST", "BANK_ID", bankId, "S-CFG-00182");
        refrenceChecker.checkFieldNotReferenced("ORI_NOTE", "BANK_ID", bankId, "S-CFG-00183");
        refrenceChecker.checkFieldNotReferenced("PART_SP_BANK", "BANK_ID", bankId, "S-CFG-00184");
        Integer childCount = bankRepository.countChildBank(bankId,'A');
        if(childCount != null && childCount > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-00171"));
        }
        else{
            AcctBankInfo acctBankInfo = qryAcctInfoByBankId(bankId);
            if(!acctBankInfo.isAcct){
                deleteBankWithoutAcct(bankId,sepaAction);
            }
            else{
                deleteBankWithAcct(bankId,sepaAction);
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,null));
    }

    private void deleteBankWithoutAcct(Integer bankId, String sepaAction) {
    //    Integer childCount = bankRepository.hasAllXChildBank(bankId);
    //     boolean hasChild = (childCount != null && childCount > 0);
    //    if(hasChild){
    //       if(sepaAction.equals("del")){
    //           sepaBankRepository.findById(bankId).ifPresent(sepaBank -> {
    //               sepaBank.setSepaState('X');
    //               sepaBankRepository.save(sepaBank);
    //           });
    //       }
    //        bankRepository.findById(bankId).ifPresent(bank -> {
    //            bank.setState('X');
    //            bank.setStateDate(LocalDate.now());
    //            bankRepository.save(bank);
    //        });
    //    }
    //    else{
           if(sepaAction.equals("del")){
               sepaBankRepository.deleteById(bankId);
           }
           bankRepository.deleteById(bankId);
    }
    
    private AcctBankInfo qryAcctInfoByBankId(Integer bankId){
        List<Character> states = acctRepository.findStatesByBankId(bankId);

        AcctBankInfo acctBankInfo = new AcctBankInfo();
        acctBankInfo.setAcct(false);
        acctBankInfo.setAAcct(false);
        acctBankInfo.setXAcct(false);

        if(states == null || states.isEmpty()){
            return acctBankInfo;
        }

        for (Character state : states) {
            if(state.equals('A')){
                acctBankInfo.setAAcct(true);
            }
            else if(state.equals('X')){
                acctBankInfo.setXAcct(true);
            }
        }
        acctBankInfo.setAcct(true);
        return acctBankInfo;
    }


    private void deleteBankWithAcct(Integer bankId, String sepaAction) {
        AcctBankInfo acctBankInfo = new AcctBankInfo();
        if(acctBankInfo.isAAcct && !acctBankInfo.isXAcct){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-00172"));
        }
        if(!acctBankInfo.isAAcct && acctBankInfo.isXAcct){
            bankRepository.updateBankState(bankId,'X',LocalDate.now());
            if(sepaAction.equals("del")){
                sepaBankRepository.updateSepaBankStateC(bankId);
            }
        }
        if(acctBankInfo.isAAcct() && acctBankInfo.isXAcct){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-00172"));
        }
        if(!acctBankInfo.isAAcct && !acctBankInfo.isXAcct){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-CFG-00173"));
        }

    }
}
