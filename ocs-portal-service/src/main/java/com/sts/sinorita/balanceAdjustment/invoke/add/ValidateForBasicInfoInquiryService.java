package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.balanceAdjustment.invoke.adjust.BalAdjustDataPrepareService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BillDto;
import com.sts.sinorita.dto.request.balanceAdjustment.CustDto;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.repository.orderentry.CustomerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class ValidateForBasicInfoInquiryService {
  private final Logger log = LoggerFactory.getLogger(ValidateForBasicInfoInquiryService.class);

  private final AddAdjustBalDataPrepareService addAdjustBalDataPrepareService;

  private final BalAdjustDataPrepareService balAdjustDataPrepareService;

  private final CustomerRepository customerRepository;

  // private AddAdjustBalApplicationRequestDto dto;

  private String prefix;
  private String accNbr;
  private Long subsId;
  // private Long acctId = dto.getAcctId();
  private Long acctId;
  // private String acctNbr= dto.getAcctNbr();
  private String acctNbr;
  private Long billId;
  private String billNbr;
  // private Long acctResId = dto.getAcctResId();
  private Long acctResId;
  private String acctResCode;
  private AcctDto acct;
  private SubsDto subs;
  private BillDto bill;
  private AcctResDto acctResDto;
  private String subsCheckMode;
  private String certNbr;
  private Character flag;

  public void invoke(AddBalanceAccountRequestDto requestDto) {
    this.acctId = requestDto.getAcctId();
    this.accNbr = requestDto.getAcctNbr();
    this.acctResId = requestDto.getAcctResId();
    log.info("Basic info inquiry validation passed.");
    if (acct != null)
      validateAcctInfo();
    if (subs != null)
      validateSubsInfo();
    if (bill != null)
      validateBillInfo();
    if (acctResDto != null)
      validateAcctResInfo();
    if (flag != null && flag.equals('A')) {
    balAdjustDataPrepareService.invoke();
}
    log.info("addAdjustBalDataPrepareService invoked.");
    addAdjustBalDataPrepareService.invoke(requestDto);
  }

  private void validateAcctInfo() {
    if (acctId != null && !acct.getAcctId().equals(acctId))
      throwBadRequest("BL-S-ACT-00075");

    if (hasText(acctNbr) && !acctNbr.equals(acct.getAcctNbr())) {
      log.debug("Account number mismatch: expected={}, input={}", acct.getAcctNbr(), acctNbr);
      throwBadRequest("BL-S-ACT-00159");
    }

    if (hasText(certNbr) && acct.getCustId() != null)
      validateCertNbrInfo(certNbr, acct.getCustId(), acct.getSpId());
  }

  private void validateSubsInfo() {
    if (subsId != null && !subs.getSubsId().equals(subsId))
      throwBadRequest("BL-S-ACT-00076");

    if (hasText(prefix) && !prefix.equals(subs.getPrefix())) {
      log.warn("Prefix mismatch: expected={}, input={}", subs.getPrefix(), prefix);
      throwBadRequest("BL-S-ACT-00117");
    }

    if (hasText(accNbr) && !accNbr.equals(subs.getAccNbr())) {
      log.warn("Service number mismatch: expected={}, input={}", subs.getAccNbr(), accNbr);
      throwBadRequest("BL-S-ACT-00118");
    }

    if ("1".equalsIgnoreCase(subsCheckMode)
        && subs.getProd() != null
        && ("B".equals(subs.getProd().getProdState()) || "F".equals(subs.getProd().getProdState()))) {
      log.warn("Invalid subs state: prefix={}, accNbr={}", prefix, accNbr);
      throwBadRequest("BL-S-ACT-00170");
    }

    if (hasText(certNbr) && subs.getCustId() != null)
      validateCertNbrInfo(certNbr, subs.getCustId(), subs.getSpId());
  }

  private void validateBillInfo() {
    if (billId != null && !bill.getBillId().equals(billId))
      throwBadRequest("BL-S-ACT-00077");

    if (hasText(billNbr) && !billNbr.equals(bill.getBillNbr())) {
      log.warn("Bill number mismatch: expected={}, input={}", bill.getBillNbr(), billNbr);
      throwBadRequest("BL-S-ACT-00119");
    }
  }

  private void validateAcctResInfo() {
    if (acctResId != null && !acctResDto.getAcctResId().equals(acctResId))
      throwBadRequest("BL-S-ACT-00150");

    if (hasText(acctResCode) && !acctResCode.equals(acctResDto.getStdCode()))
      throwBadRequest("BL-S-ACT-00150");

    if (acctResDto != null && Long.valueOf(6).equals(acctResDto.getBalType()))
      throwBadRequest("BL-S-ACT-00260");
  }

  private void validateCertNbrInfo(String certNbr, Long custId, Long spId) {
    log.debug("validateCertNbrInfo called for certNbr={}, custId={}", certNbr, custId);
    CustDto cust = selectCustByCustIdAndCertNbr(custId, certNbr)
        .orElseThrow(() -> {
          log.debug("Invalid certNbr={}", certNbr);
          return new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00348"));
        });
    log.debug("validateCertNbrInfo success for cust={}", cust.getCustId());
  }

  private Optional<CustDto> selectCustByCustIdAndCertNbr(Long custId, String certNbr) {
    List<Object[]> results = customerRepository.findCustByCustIdAndCertNbr(custId, certNbr);
    if (results.isEmpty())
      return Optional.empty();

    Object[] row = results.get(0);
    CustDto dto = new CustDto();
    dto.setCustId(((Number) row[0]).longValue());
    dto.setCustCode((String) row[1]);
    dto.setCustName((String) row[2]);
    dto.setCustType((String) row[3]);
    dto.setCertId(((Number) row[4]).longValue());
    dto.setParentId(((Number) row[5]).longValue());
    dto.setAreaId(((Number) row[6]).longValue());
    dto.setImpGradeId(((Number) row[7]).longValue());
    dto.setBirthdayDay((Date) row[8]);
    return Optional.of(dto);
  }

  private boolean hasText(String str) {
    return StringUtils.hasText(str);
  }

  private void throwBadRequest(String messageCode) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage(messageCode));
  }
}
