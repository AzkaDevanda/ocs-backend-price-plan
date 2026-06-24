package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.mapper.balanceAdjustment.OriNoteMapper;
import com.sts.sinorita.repository.OriNoteRepository;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class BillingTransactionForNotePayment implements IBillingTransactionRecord.IBillingTransactionForFee {
  NotePaymentData[] notePaymentList;

  boolean hasFee = false;

  IBillingTransactionRecord.IGlCodeMatcher matcher;
  Long subsEventId = 285L;
  private OriNoteRepository oriNoteRepository;
  private OriNoteMapper oriNoteMapper;

  public BillingTransactionForNotePayment (ReCcInstData firstReCcInstData, NotePaymentData[] notePaymentList) {
    if (firstReCcInstData.getQryFeeParam() != null)
      this.subsEventId = firstReCcInstData.getQryFeeParam().getSubsEventId();
    this.notePaymentList = notePaymentList;
    this.hasFee = CommonUtil.isNotEmpty((Object[]) notePaymentList);
    this.matcher = AbsGlCodeMatcher.getPaymentMatcher();
  }

  public boolean hasFee () {
    return this.hasFee;
  }

  public List<BillingTransactionDto> prepairFeeItem () {
    AssertUtil.isTrue(this.hasFee);
    List<BillingTransactionDto> ret = new ArrayList<>();
//    IBillingTransactionStoreDAO dao = (IBillingTransactionStoreDAO) DAOFactory.createModuleDAO("BillingTransactionStore", "billing.fc.common.billingtransactionstore",
//      JdbcUtil4BL.getDbIdentifier());
    BillingTransactionDto dict = null;
    GlCodeCfgDto notePaymentGlCode = null;
    for (NotePaymentData notePaymentData : this.notePaymentList) {
      OriNoteDto oriNote = oriNoteRepository.qryOriNote(notePaymentData.getNoteId())
        .map(oriNoteMapper::toOriNoteDto)
        .orElse(null);
      AssertUtil.isNotNull(oriNote, "Cannot find ori_note by id:" + notePaymentData.getNoteId());
      Long paymentMethodId = oriNote.getPaymentMethodId();
      if (paymentMethodId != null)
        notePaymentGlCode = this.matcher.matcherBusiInfo(this.subsEventId, paymentMethodId.toString());
      dict = new BillingTransactionDto();
      if (notePaymentGlCode != null) {
//        dict.set("GL_CODE1", notePaymentGlCode.getGlCode());
        dict.setGlCode1(notePaymentGlCode.getGlCode());
//        dict.set("GL_DIRECTION1", notePaymentGlCode.getGlDirection());
        dict.setGlDirection1(notePaymentGlCode.getGlDirection());
//        dict.set("CHARGE1", notePaymentData.getCharge().longValue() * notePaymentGlCode.getGlCoefficient().longValue());
        dict.setCharge1(notePaymentData.getCharge() * notePaymentGlCode.getGlCoefficient());
      } else {
//        dict.set("CHARGE1", notePaymentData.getCharge());
        dict.setCharge1(notePaymentData.getCharge());
      }
//      dict.set("EVENT_PAYMENT_ID", notePaymentData.getEventPaymentId());
      dict.setEventPaymentId(notePaymentData.getEventPaymentId());
//      dict.set("SUBS_EVENT_ID", this.subsEventId);
      dict.setSubsEventId(this.subsEventId);
//      dict.set("PARTNER_DATE", oriNote.getReceivedDate());
      dict.setPartnerDate(oriNote.getReceivedDate());
      ret.add(dict);
    }
    return ret;
  }
}
