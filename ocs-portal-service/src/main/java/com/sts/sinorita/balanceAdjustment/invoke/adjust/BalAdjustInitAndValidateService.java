package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.Partner;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.AdjustBalanceRequest;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.helper.PartnerSnService;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.ValidateUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class BalAdjustInitAndValidateService {

    private final BalAdjustRepeatInfoInquiryService balAdjustRepeatInfoInquiryService;

    private final PartnerSnService partnerSnService;

    private Long acctId;

    private String accNbr;

    private String acctNbr;

    private String partyType;

    private String partyCode;

    private Long contactChannelId;

    private String partnerSn;

    private Long rptFlag;

    private Long balanceCalcFlag;

    private Boolean canCreateBal;

    private Long expDateExtendFlag;

    private Boolean isLimitMaxCharge;

    private Boolean isLimitMaxDays;

    private String acctBookType;

    private Boolean isRepeat;

    private Boolean acctLockFlag;

    private Partner partner;

    private LocalDateTime dateNow;

    public Information getInformation() {
        return null;
    }

    public void invoke(AdjustBalanceRequest request) {
        log.info("BalAdjustInitAndValidateService invoked");


        log.info("partnerSn={}", this.partnerSn);
        log.info("contactChannelId={}", this.contactChannelId);
        log.info("rptFlag={}", this.rptFlag);
        log.info("isRepeat={}", this.isRepeat);

        log.info("BalAdjustInitAndValidateService invoked");
        this.setAcctId(request.getAcctId());
        this.setAccNbr(request.getAccNbr());
        this.setAcctNbr(request.getAcctNbr());
        this.setPartyCode(request.getPartyCode());
        this.setPartyType(request.getPartyType());
        this.setContactChannelId(request.getContactChannelId());
        ValidateUtil.isTrue((this.acctId != null || CommonUtil.eitherEmpty(this.accNbr, this.acctNbr)),
                "acctId and accNbr and acctNbr can not be null at the same time. ");
        validatePartner();
        validateAndInitPartnerSn();
        if (this.acctLockFlag == null)
            this.acctLockFlag = Boolean.valueOf(!this.isRepeat.booleanValue());
        this.acctBookType = "H";
        if (this.dateNow == null)
            this.dateNow = DateUtil.GetDBDateTime();
        if (this.canCreateBal == null)
            this.canCreateBal = BillingHelper.getBooleanFromConfig("ACCT.PAYMENT.CAN_CREATE_BAL", "Y");
        this.balanceCalcFlag = Long.valueOf((this.balanceCalcFlag == null) ? 1L : this.balanceCalcFlag.longValue());
        this.expDateExtendFlag = Long.valueOf((this.expDateExtendFlag == null) ? 1L : this.expDateExtendFlag.longValue());
        this.isLimitMaxCharge = (this.isLimitMaxCharge == null) ? Boolean.TRUE : this.isLimitMaxCharge;
        this.isLimitMaxDays = (this.isLimitMaxDays == null) ? Boolean.TRUE : this.isLimitMaxDays;
        log.info("BalAdjustInitAndValidateService passed");
        balAdjustRepeatInfoInquiryService.setIsRepeat(this.isRepeat);
        balAdjustRepeatInfoInquiryService.setPartnerSn(this.partnerSn);
        balAdjustRepeatInfoInquiryService.setContactChannelId(this.contactChannelId);
        log.info("AFTER validateAndInitPartnerSn()");
        log.info("partnerSn={}", this.partnerSn);
        log.info("contactChannelId={}", this.contactChannelId);
        log.info("rptFlag={}", this.rptFlag);
        log.info("isRepeat={}", this.isRepeat);

        log.info("=== CALLING BalAdjustRepeatInfoInquiryService.invoke() ===");
        balAdjustRepeatInfoInquiryService.invoke(request);
        log.info("=== AFTER BalAdjustRepeatInfoInquiryService.invoke() ===");
    }

    private void validatePartner() {
        this.partner = new Partner();
        this.partner.setPartyCode(this.partyCode);
        this.partner.setPartyType(this.partyType);
        // boolean isPartnerMatch = this.partner.checkPartyTypeAndPartyCode().booleanValue();
        // if (!isPartnerMatch) {
        //   String exceptionMsg = StringUtil.stringFormat("CheckParty(PARTY_TYPE=[{0}],PARTY_CODE=[{1}]) error",
        //       this.partyType, this.partyCode);
        //   throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        //       MessageService.getMessage("BL-S-ACT-00062"));
        //   // ExceptionHandler.publish("BL-S-ACT-00062", exceptionMsg, 0);
        // }
    }

    private void validateAndInitPartnerSn() {
        log.info("rptFlag={}", this.rptFlag);
        if (this.rptFlag == null)
            this.rptFlag = Long.valueOf(1L);
        this.isRepeat = Boolean.FALSE;
        log.info("partnerSn={}", this.partnerSn);
        if (this.partnerSn == null || this.partnerSn.isEmpty())
            return;
        boolean isExist = partnerSnService.checkPartnerSn(this.contactChannelId, this.partnerSn);
        log.info("isExist={}", isExist);
        if (!isExist)
            return;
        log.info("rptFlag={}", this.rptFlag);
        if (this.rptFlag.longValue() == 1L) {
            this.isRepeat = Boolean.TRUE;
        } else if (this.rptFlag.longValue() == 0L) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00041"));
            // ExceptionHandler.publish("BL-S-ACT-00041", 0);
        }
    }

}
