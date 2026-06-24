package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.OfferDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalAdjustDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.BalanceShareHisData;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingBaseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.LoanDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.LoanRepayParam;
import com.sts.sinorita.dto.request.balanceAdjustment.LoanRequest;
import com.sts.sinorita.dto.request.balanceAdjustment.ReCcInstBLData;
import com.sts.sinorita.dto.request.balanceAdjustment.RechargeDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.RechargeRequest;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.entity.Bal;
import com.sts.sinorita.entity.Subs;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingCache;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class SendSmsService {
  // // @DataMapping(mapping = "acct")
  // private Acct acct;

  // // @DataMapping(mapping = "this", required = true)
  // private BillingBaseDataBus dataBus;

  // public Acct getAcct() {
  //   return this.acct;
  // }

  // public void setAcct(Acct acct) {
  //   this.acct = acct;
  // }

  // public BillingBaseDataBus getDataBus() {
  //   return this.dataBus;
  // }

  // public void setDataBus(BillingBaseDataBus dataBus) {
  //   this.dataBus = dataBus;
  // }

  // public void invoke() throws BaseAppException {
  //   try {
  //     if (this.dataBus.getRequest().getIsSendAdvice() != null
  //         && this.dataBus.getRequest().getIsSendAdvice().booleanValue()) {
  //       sendSmsForBalShareHis();
  //       if (this.dataBus.getAcctBookData().getAcctBookType().equals("P")) {
  //         if (this.dataBus instanceof RechargeDataBus)
  //           sendSmsForRecharge();
  //       } else if (this.dataBus.getAcctBookData().getAcctBookType().equals("H")) {
  //         sendSmsForBalAdjust();
  //       } else if ("J".equals(this.dataBus.getAcctBookData().getAcctBookType())) {
  //         sendSmsForLoan();
  //       } else if ("N".equals(this.dataBus.getAcctBookData()
  //           .getAcctBookType())) {
  //         sendSmsForClearLoan();
  //       }
  //       if (this.dataBus.getAcctBookData().getAcctBookType().equals("M"))
  //         sendSmsForDelayPeriod();
  //     }
  //   } catch (BaseAppException bax) {
  //     logger.warn("Fail to send advice.", (Throwable) bax);
  //     ExceptionHandler.publish("BL-S-ACT-00109", 0);
  //   } catch (Exception e) {
  //     logger.warn("Fail to send advice.", e);
  //     ExceptionHandler.publish("BL-S-ACT-00109", 0);
  //   }
  // }

  // private void sendSmsForBalShareHis() throws BaseAppException {
  //   AcctBookData acctBookData = this.dataBus.getAcctBookData();
  //   if (acctBookData == null)
  //     return;
  //   Long thresHold = ConfigItemCache.instance().getLong("ACCT.FEE.THRESHOLD_4_BAL_SHARE_USAGE_NOTIFY");
  //   if (thresHold == null)
  //     return;
  //   if (thresHold.longValue() > 100L || thresHold.longValue() < 0L) {
  //     logger.debug("THRESHOLD_4_BAL_SHARE_USAGE_NOTIFY must between 0 and 100");
  //     return;
  //   }
  //   List<ReCcInstBLData> reCcInstBLDataList = acctBookData.getReCcInstDataList();
  //   if (CommonUtil.isEmpty(reCcInstBLDataList))
  //     return;
  //   for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
  //     OnceFeeInstData[] onceFeeInstDataList = reCcInstBLData.getOnceFeeInstDataList();
  //     if (CommonUtil.isEmpty((Object[]) onceFeeInstDataList))
  //       continue;
  //     for (OnceFeeInstData onceFeeInstData : onceFeeInstDataList)
  //       dealOnceFeeInstData(onceFeeInstData, thresHold);
  //   }
  // }

  // private void dealOnceFeeInstData(OnceFeeInstData onceFeeInstData, Long thresHold) throws BaseAppException {
  //   BalanceShareHisData[] balanceShareHisDataList = onceFeeInstData.getBalanceShareHisDataList();
  //   if (CommonUtil.isEmpty((Object[]) balanceShareHisDataList))
  //     return;
  //   Subs subs = null;
  //   Acct tmpAcct = getAccountByAcctId(onceFeeInstData.getAcctId());
  //   if (tmpAcct.getAllSubsList() != null && (tmpAcct.getAllSubsList()).length > 0) {
  //     subs = tmpAcct.getAllSubsList()[0];
  //   } else {
  //     return;
  //   }
  //   Long routingId = null;
  //   if (BillingHelper.isQryBalShareHisByAcctRoutingId().booleanValue()) {
  //     routingId = RoutingHelper.getRoutingIdByAcctId(onceFeeInstData.getAcctId());
  //   } else {
  //     routingId = RoutingHelper.getRoutingIdBySubsId(onceFeeInstData.getSubsId());
  //   }
  //   ISendSmsDAO sendSmsDAOTimesten = getSendSmsDAOTimesten(routingId);
  //   for (BalanceShareHisData balanceShareHisData : balanceShareHisDataList) {
  //     Long balShareId = balanceShareHisData.getBalShareId();
  //     DynamicDict result = qryBalShare(balShareId);
  //     if (result != null) {
  //       Long allConsume = null;
  //       Long limit = null;
  //       Long charge = null;
  //       if (balanceShareHisData.getBillBal() != null) {
  //         allConsume = sendSmsDAOTimesten.selectBalShareHis(balShareId, balanceShareHisData.getDateStamp(), null);
  //         limit = result.getLong("CEIL_LIMIT");
  //         charge = balanceShareHisData.getBillBal();
  //       } else if (balanceShareHisData.getDailyBal() != null) {
  //         allConsume = sendSmsDAOTimesten.selectBalShareHis(balShareId, null, balanceShareHisData.getDateStamp());
  //         limit = result.getLong("DAILY_CEIL_LIMIT");
  //         charge = balanceShareHisData.getDailyBal();
  //       }
  //       if (limit != null) {
  //         long flag = limit.longValue() * thresHold.longValue() / 100L;
  //         if (flag <= allConsume.longValue()) {
  //           try {
  //             DynamicDict dict = new DynamicDict();
  //             dict.set("STD_CODE", "BAL_SHARE_LIMIT_NOTIFY");
  //             dict.set("LIMIT", limit);
  //             dict.set("BAL_SHARE_HIS_BAL", allConsume);
  //             if (subs != null) {
  //               dict.set("ACC_NBR", subs.getAccNbr());
  //               dict.set("PREFIX", subs.getPrefix());
  //             }
  //             dict.set("CHARGE", charge);
  //             dict.serviceName = "AdviceService";
  //             ServiceFlow.callService(dict);
  //           } catch (BaseAppException e) {
  //             logger.warn("Send fee advice failed.", (Throwable) e);
  //           }
  //           break;
  //         }
  //       }
  //     }
  //   }
  // }

  // private Acct getAccountByAcctId(Long acctId) throws BaseAppException {
  //   Acct tmpAcct = null;
  //   if (this.acct.getAcctId().equals(acctId)) {
  //     tmpAcct = this.acct;
  //   } else {
  //     tmpAcct = AccountRepository.qryDistributedAcctByAcctId(acctId);
  //   }
  //   return tmpAcct;
  // }

  // private DynamicDict qryBalShare(Long balShareId) throws BaseAppException {
  //   DynamicDict result = null;
  //   DynamicDict qryBalShare = new DynamicDict();
  //   qryBalShare.set("BAL_SHARE_ID", balShareId);
  //   qryBalShare.serviceName = "QryBalShareByBalShareId";
  //   DbRoutingContext.beginMasterDbRoutingEvn();
  //   try {
  //     ServiceFlow.callService(qryBalShare);
  //   } finally {
  //     DbRoutingContext.endCurrentDbRoutingEnv();
  //   }
  //   List<DynamicDict> list = qryBalShare.getList("z_d_r");
  //   if (CommonUtil.isNotEmpty(list))
  //     result = list.get(0);
  //   return result;
  // }

  // private ISendSmsDAO getSendSmsDAOTimesten(Long routingId) throws BaseAppException {
  //   return (ISendSmsDAO) DAOFactory.createModuleDAO("SendSms", "billing.fc.common.sendsms",
  //       JdbcUtil4BC.getDbCache(routingId), routingId);
  // }

  // private void sendSmsForDelayPeriod() throws BaseAppException {
  //   DelayPeriodDataBus delayPeriodDataBus = (DelayPeriodDataBus) this.dataBus;
  //   DynamicDict dict = new DynamicDict();
  //   dict.set("STD_CODE", "EXCHANGE_BALANCE_VALUE_AND_VALIDITY_PERIOD");
  //   dict.set("ContactChannelId", delayPeriodDataBus.getRequest().getContactChannelId());
  //   if (this.acct.getAllSubsList() != null && (this.acct.getAllSubsList()).length > 0) {
  //     Subs subs = this.acct.getAllSubsList()[0];
  //     dict.set("ACC_NBR", subs.getAccNbr());
  //     dict.set("PREFIX", subs.getPrefix());
  //   } else {
  //     return;
  //   }
  //   dict.set("AMOUNT", delayPeriodDataBus.getAcctBookData().getCharge());
  //   List<ReCcInstBLData> reCcInstDataList = delayPeriodDataBus.getAcctBookData().getReCcInstDataList();
  //   if (reCcInstDataList != null && !reCcInstDataList.isEmpty()) {
  //     AcctBookDto[] benefitAcctBookDto = ((ReCcInstBLData) reCcInstDataList.get(0)).getAcctBookDtoList();
  //     if (benefitAcctBookDto != null && benefitAcctBookDto.length > 0) {
  //       dict.set("ADD_DAYS", benefitAcctBookDto[0].getSeconds().longValue() / 86400L);
  //       Date expiryDate = DateUtil.offsetSecond(delayPeriodDataBus.getAcctBookData().getPreExpDate(),
  //           benefitAcctBookDto[0].getSeconds().longValue());
  //       dict.set("EXPIRY_DATE", expiryDate);
  //     }
  //   }
  //   dict.set("SP_ID", this.dataBus.getRequest().getSpId());
  //   dict.serviceName = "AdviceService";
  //   ServiceFlow.callService(dict);
  // }

  // public Information getInformation() {
  //   return null;
  // }

  // public void sendSmsForRecharge() throws BaseAppException {
  //   if (!validateRechargeSmsFlag().booleanValue())
  //     return;
  //   RechargeDataBus rechargeDataBus = (RechargeDataBus) this.dataBus;
  //   DynamicDict dict = new DynamicDict();
  //   initSubsInfo(dict, rechargeDataBus.getSubs(), this.acct);
  //   String accNbr = dict.getString("ACC_NBR");
  //   if (StringUtil.isEmpty(accNbr))
  //     return;
  //   dict.set("RechargeNbr", accNbr);
  //   dict.set("ContactChannelId", rechargeDataBus.getRequest().getContactChannelId());
  //   dict.set("IsOwnerFlag", "Y");
  //   dict.set("ChargeAmount", rechargeDataBus.getRechargeAmount());
  //   RechargeRequest request = (RechargeRequest) rechargeDataBus.getRequest();
  //   dict.set("RechargeTime", request.getRechargeTime());
  //   Bal defaultBal = BalHelper.getDefaultBal(this.dataBus.getAcct().getNewBalList());
  //   if (defaultBal != null) {
  //     dict.set("CurrentDefBalance", defaultBal.getRealBal());
  //     dict.set("DefBalEffDate", defaultBal.getEffDate());
  //     dict.set("DefBalExpDate", defaultBal.getExpDate());
  //   }
  //   Bal preDefaultBal = BalHelper.getDefaultBal(this.dataBus.getAcct().getOldBalList());
  //   if (preDefaultBal != null)
  //     dict.set("PreviousDefBalance", preDefaultBal.getRealBal());
  //   Bal operBal = BalHelper.getBalFromListByBalId(this.dataBus.getAcct().getNewBalList(),
  //       this.dataBus.getAcctBookData().getBalId());
  //   if (operBal != null) {
  //     dict.set("CurrentBalance", operBal.getRealBal());
  //     dict.set("BalEffDate", operBal.getEffDate());
  //     dict.set("BalExpDate", operBal.getExpDate());
  //     dict.set("BalAcctResId", operBal.getAcctResId());
  //   }
  //   Bal preOperBal = BalHelper.getBalFromListByBalId(this.dataBus.getAcct().getOldBalList(),
  //       this.dataBus.getAcctBookData().getBalId());
  //   if (preOperBal != null)
  //     dict.set("PreviousBalance", preOperBal.getRealBal());
  //   dict.set("SP_ID", this.dataBus.getRequest().getSpId());
  //   List<DynamicDict> presentDict = getInfoFromPresentInfo();
  //   if (!presentDict.isEmpty()) {
  //     dict.set("BenefitFlag", "Y");
  //     dict.set("BenefitList", presentDict);
  //   } else {
  //     dict.set("BenefitFlag", "N");
  //   }
  //   Map<String, Object> extMap = request.getExtMap();
  //   if (extMap != null && extMap.get("CALLING_NUMBER") != null) {
  //     String callingNumber = (String) extMap.get("CALLING_NUMBER");
  //     dict.set("Callingnumber", callingNumber);
  //   }
  //   String stdCode = "RECHARGE_NOTIFY";
  //   if (extMap != null && extMap.get("STD_CODE") != null)
  //     stdCode = (String) extMap.get("STD_CODE");
  //   dict.set("STD_CODE", stdCode);
  //   dict.serviceName = "AdviceService";
  //   ServiceFlow.callService(dict);
  // }

  // public Boolean validateRechargeSmsFlag() throws BaseAppException {
  //   String acctType, sendFlag = ConfigItemCache.instance().getString("ACCT.PAYMENT.IS_SEND_SMS_4_PAYMENT", "11");
  //   if ("11".equals(sendFlag))
  //     return Boolean.valueOf(true);
  //   String flag = "1";
  //   if ("Y".equals(this.acct.getPostpaid())) {
  //     flag = sendFlag.substring(sendFlag.length() - 1);
  //     acctType = "postpaid";
  //   } else {
  //     flag = sendFlag.substring(sendFlag.length() - 2, sendFlag.length() - 1);
  //     acctType = "prepaid";
  //   }
  //   if ("0".equals(flag)) {
  //     logger.debug("No need to send sms for {} account.", new Object[] { acctType });
  //     return Boolean.valueOf(false);
  //   }
  //   return Boolean.valueOf(true);
  // }

  // public void sendSmsForBalTransfer() throws BaseAppException {
  // }

  // public void sendSmsForBalAdjust() throws BaseAppException {
  //   BalAdjustDataBus balAdjustDataBus = (BalAdjustDataBus) this.dataBus;
  //   DynamicDict dict = new DynamicDict();
  //   dict.set("STD_CODE", "ADJUST_NOTIFY");
  //   dict.set("AcctBookType", balAdjustDataBus.getAcctBookData().getAcctBookType());
  //   dict.set("AcctResName", balAdjustDataBus.getAcctResDto().getAcctResName());
  //   dict.set("ContactChannelId", balAdjustDataBus.getRequest().getContactChannelId());
  //   if (balAdjustDataBus.getAcctBookData().getModBal() != null && balAdjustDataBus
  //       .getAcctBookData().getModBal().getPreBalance() != null && balAdjustDataBus
  //           .getAcctBookData().getModBal().getCharge() != null) {
  //     Long realBal = Long
  //         .valueOf(balAdjustDataBus.getAcctBookData().getModBal().getPreBalance().longValue() + balAdjustDataBus
  //             .getAcctBookData().getModBal().getCharge().longValue());
  //     dict.set("RealBal", realBal);
  //   }
  //   dict.set("IsCurrency", balAdjustDataBus.getAcctResDto().getIsCurrency());
  //   dict.set("EffDate", balAdjustDataBus.getBalAdjustEffDate());
  //   dict.set("ExpDate", balAdjustDataBus.getBalAdjustExpDate());
  //   if (this.acct.getAllSubsList() != null && (this.acct.getAllSubsList()).length > 0) {
  //     Subs subs = new Subs();
  //     subs = this.acct.getAllSubsList()[0];
  //     dict.set("ACC_NBR", subs.getAccNbr());
  //     dict.set("PREFIX", subs.getPrefix());
  //   } else {
  //     return;
  //   }
  //   if (balAdjustDataBus.getBalAdjustAmount().longValue() > 0L) {
  //     dict.set("Action", "D");
  //   } else {
  //     dict.set("Action", "I");
  //   }
  //   dict.set("Amount", balAdjustDataBus.getBalAdjustAmount());
  //   dict.set("SP_ID", this.dataBus.getRequest().getSpId());
  //   dict.serviceName = "AdviceService";
  //   ServiceFlow.callService(dict);
  // }

  // private List<DynamicDict> getInfoFromPresentInfo() throws BaseAppException {
  //   List<ReCcInstBLData> reCcInstDataBLList = this.dataBus.getAcctBookData().getReCcInstDataList();
  //   List<DynamicDict> presentList = new ArrayList<>();
  //   if (reCcInstDataBLList != null && !reCcInstDataBLList.isEmpty()) {
  //     ReCcInstBLData reCcInstBLData = null;
  //     AcctBookDto[] acctBookDtoList = null;
  //     Iterator<ReCcInstBLData> iterator = reCcInstDataBLList.iterator();
  //     while (iterator.hasNext()) {
  //       reCcInstBLData = iterator.next();
  //       acctBookDtoList = reCcInstBLData.getAcctBookDtoList();
  //       if (acctBookDtoList != null && acctBookDtoList.length > 0)
  //         for (AcctBookDto acctBookDto : acctBookDtoList) {
  //           DynamicDict singlePresent = new DynamicDict();
  //           AcctResDto acctResDtoTemp = BillingCache.getAcctResById(acctBookDto.getAcctResId());
  //           String acctResCode = acctResDtoTemp.getStdCode();
  //           Bal benefitBal = BalHelper.getBalFromListByBalId(this.acct.getNewBalList(), acctBookDto.getBalId());
  //           if (benefitBal != null) {
  //             singlePresent.set("BENEFIT_EFF_DATE", benefitBal.getEffDate());
  //             singlePresent.set("BENEFIT_EXP_DATE", benefitBal.getExpDate());
  //             singlePresent.set("REAL_AMOUNT", benefitBal.getRealBal());
  //           }
  //           singlePresent.set("ACCT_RES_CODE", acctResCode);
  //           singlePresent.set("ACCT_RES_NAME", acctResDtoTemp.getAcctResName());
  //           singlePresent.set("IS_CURRENCY", acctResDtoTemp.getIsCurrency());
  //           singlePresent.set("BENEFIT_BALANCE", acctBookDto.getCharge());
  //           presentList.add(singlePresent);
  //         }
  //     }
  //   }
  //   return presentList;
  // }

  // private void sendSmsForLoan() throws BaseAppException {
  //   LoanDataBus loanDataBus = (LoanDataBus) this.dataBus;
  //   LoanRequest request = (LoanRequest) loanDataBus.getRequest();
  //   DynamicDict dict = new DynamicDict();
  //   dict.set("LoanAmount", loanDataBus.getLoanAmount());
  //   dict.set("LoanType", request.getDebitLoanType());
  //   Bal bal = BalHelper.getBalFromListByBalId(loanDataBus.getAcct().getNewBalList(), loanDataBus.getAcctBookData()
  //       .getBalId());
  //   if (bal != null)
  //     dict.set("BalExpDate", bal.getExpDate());
  //   initSubsInfo(dict, loanDataBus.getSubs(), loanDataBus.getAcct());
  //   String illimixPricePlanCode = request.getIllimixPricePlanCode();
  //   if (StringUtil.isNotEmpty(illimixPricePlanCode)) {
  //     OfferDto pricePlanBO = BcCache.getOfferByOfferCode(illimixPricePlanCode, null, loanDataBus.getSubs()
  //         .getSpId());
  //     if (pricePlanBO != null) {
  //       dict.set("PRICEPLAN_NAME", pricePlanBO.getOfferName());
  //       dict.set("PRICEPLAN_CODE", pricePlanBO.getOfferCode());
  //     }
  //   }
  //   dict.set("CommissionAmount", 0L);
  //   dict.set("STD_CODE", "LOAN_PROMOTION");
  //   dict.set("SP_ID", loanDataBus.getRequest().getSpId());
  //   dict.serviceName = "AdviceService";
  //   ServiceFlow.callService(dict);
  // }

  // private void sendSmsForClearLoan() throws BaseAppException {
  //   LoanDataBus loanDataBus = (LoanDataBus) this.dataBus;
  //   DynamicDict dict = new DynamicDict();
  //   LoanRequest request = (LoanRequest) loanDataBus.getRequest();
  //   dict.set("RechargeAmount", request.getReceiveAmount());
  //   Bal bal = BalHelper.getBalFromListByBalId(loanDataBus.getAcct().getNewBalList(),
  //       loanDataBus.getAcctBookData().getBalId());
  //   if (bal != null) {
  //     dict.set("CurrentBalance", bal.getRealBal());
  //     dict.set("BalExpDate", bal.getExpDate());
  //   }
  //   LoanRepayParam loanRepayParam = loanDataBus.getLoanRepayParam();
  //   dict.set("LoanType", loanRepayParam.getDebitItemDto().getLoanType());
  //   dict.set("ReturnAmount", loanRepayParam.getLoanRepayAmount());
  //   Long commCharge = loanRepayParam.getCommRepayAmount();
  //   if (commCharge == null)
  //     commCharge = Long.valueOf(0L);
  //   dict.set("CommissionAmount", commCharge);
  //   initSubsInfo(dict, loanDataBus.getSubs(), loanDataBus.getAcct());
  //   logger.debug("LoanAmountLeft=[{}],LastCommCharge=[{}],LastCommRetCharge=[{}],CommRepayAmount=[{}]",
  //       new Object[] { loanRepayParam

  //           .getLoanAmountLeft(), loanRepayParam.getLastCommCharge(),
  //           loanRepayParam
  //               .getLastCommRetCharge(),
  //           loanRepayParam.getCommRepayAmount() });
  //   dict.set("OWE_AMOUNT", loanRepayParam.getLoanAmountLeft().longValue() + loanRepayParam
  //       .getLastCommCharge().longValue() + loanRepayParam.getLastCommRetCharge().longValue()
  //       - loanRepayParam.getCommRepayAmount().longValue());
  //   dict.set("ALL_RETURN", (loanRepayParam.getIsLoanAmountCleared().booleanValue()
  //       && loanRepayParam.getIsCommissionCleared().booleanValue()) ? "Y" : "N");
  //   dict.set("LoanDate", loanRepayParam.getLoanDate());
  //   dict.set("STD_CODE", "RETURN_LOAN_NOTIFY");
  //   dict.set("SP_ID", loanDataBus.getRequest().getSpId());
  //   dict.serviceName = "AdviceService";
  //   ServiceFlow.callService(dict);
  //   String isOrange = ConfigItemCache.instance().getString("ACCT.ACCOUNT_PUBLIC.IS_ORANGE_PRODUCT");
  //   if ("Y".equals(isOrange)) {
  //     DynamicDict qryDict = new DynamicDict();
  //     qryDict.serviceName = "QryDebitBalByAcctId";
  //     qryDict.set("ACCT_ID", loanDataBus.getAcct().getAcctId());
  //     qryDict.set("SUBS_ID", loanDataBus.getSubs().getSubsId());
  //     qryDict.set("LOAN_TYPE", request.getDebitLoanType());
  //     ServiceFlow.callService(qryDict);
  //     if (request.getDebitLoanType().longValue() == 3L) {
  //       DynamicDict sendDict = new DynamicDict();
  //       sendDict.set("MSISDN", dict.getString("PREFIX") + dict.getString("ACC_NBR"));
  //       sendDict.set("PreLoanBal", qryDict.getLong("LAST_DEBIT_CHARGE"));
  //       sendDict.set("Repayment", loanRepayParam.getLoanRepayAmount());
  //       sendDict.set("Datetime", DateUtil.date2String(qryDict.getDate("LAST_DEBIT_DATE"), "yyyyMMddHHmmss"));
  //       sendDict.set("LoanId", loanDataBus.getDebitBalDto().getLastButNDebitInfo());
  //       sendDict.set("RequestId", loanDataBus.getAcctBookData().getAcctBookId());
  //       logger.debug("CommRepayAmount {} and LoanRepayAmount {}",
  //           new Object[] { loanRepayParam.getCommRepayAmount(), loanRepayParam.getLoanRepayAmount() });
  //       if (loanRepayParam.getCommRepayAmount().longValue() != 0L && loanRepayParam
  //           .getLoanRepayAmount().longValue() != 0L) {
  //         logger.debug("send all...");
  //         sendDict.set("Type", "SR");
  //         sendDict.set("Repayment", loanRepayParam.getCommRepayAmount());
  //         sendDict.set("RequestId", loanDataBus.getAcctBookData().getAcctBookId() + "SR");
  //         sendMessToAgora(sendDict);
  //         sendDict.set("Type", "PR");
  //         sendDict.set("Repayment", loanRepayParam.getLoanRepayAmount());
  //         sendDict.set("RequestId", loanDataBus.getAcctBookData().getAcctBookId() + "PR");
  //         sendMessToAgora(sendDict);
  //       } else if (loanRepayParam.getCommRepayAmount().longValue() != 0L) {
  //         logger.debug("send sr...");
  //         sendDict.set("Repayment", loanRepayParam.getCommRepayAmount());
  //         sendDict.set("Type", "SR");
  //         sendMessToAgora(sendDict);
  //       } else if (loanRepayParam.getLoanRepayAmount().longValue() != 0L) {
  //         logger.debug("send pr...");
  //         sendDict.set("Type", "PR");
  //         sendMessToAgora(sendDict);
  //       }
  //     }
  //   }
  // }

  // private void sendMessToAgora(DynamicDict sendDict) {
  //   HttpURLConnection con = null;
  //   try {
  //     logger.debug(">>>>>>>>>send to the agora Http Server.....");
  //     String agWsUrl = ConfigItemCache.instance().getString("ACCT.ACCOUNT_PUBLIC.AGORA_WS_URL_PRODUCT");
  //     if (StringUtil.isEmpty(agWsUrl)) {
  //       logger.debug("agWsUrl is null");
  //       return;
  //     }
  //     URL url = new URL(agWsUrl);
  //     logger.debug("url:" + url);
  //     String soapXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n  <soapenv:Body>\n    <acs xmlns=\"http://example.com/sample.wsdl\">\n      <Data>\n        <body>\n           <Msisdn>{0}</Msisdn>\n           <PreLoanBal>{1}</PreLoanBal>\n           <Repayment>{2}</Repayment>\n           <Datetime>{3}</Datetime>\n           <Type>{4}</Type>\n           <LoanId>{5}</LoanId>\n           <RequestId>{6}</RequestId>\n        </body>\n      </Data>\n    </acs>\n  </soapenv:Body>\n</soapenv:Envelope>\n";
  //     soapXml = StringUtil.stringFormat(soapXml, (Object[]) new String[] { sendDict
  //         .getString("MSISDN"),
  //         sendDict
  //             .getString("PreLoanBal"),
  //         sendDict
  //             .getString("Repayment"),
  //         sendDict
  //             .getString("Datetime"),
  //         sendDict
  //             .getString("Type"),
  //         sendDict
  //             .getString("LoanId"),
  //         sendDict
  //             .getString("RequestId") });
  //     logger.debug("HTTP request:" + soapXml);
  //     byte[] data = soapXml.getBytes("UTF-8");
  //     System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(5000));
  //     System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(5000));
  //     con = (HttpURLConnection) url.openConnection();
  //     con.setRequestMethod("POST");
  //     con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
  //     con.setUseCaches(false);
  //     con.setDoOutput(true);
  //     con.setDoInput(true);
  //     con.setAllowUserInteraction(true);
  //     con.connect();
  //     OutputStream output = con.getOutputStream();
  //     output.write(data);
  //     output.flush();
  //     output.close();
  //     con.getResponseCode();
  //     InputStream input = con.getErrorStream();
  //     if (input == null)
  //       input = con.getInputStream();
  //     byte[] soapResp = readData(input);
  //     logger.info("HTTP response:" + new String(soapResp, "UTF-8"));
  //   } catch (Exception ex) {
  //     logger.error("The Http error,", ex);
  //   } finally {
  //     if (con != null) {
  //       con.disconnect();
  //       con = null;
  //     }
  //   }
  // }

  // protected byte[] readData(InputStream input) {
  //   byte[] res = null;
  //   try {
  //     if (input != null) {
  //       int len = 0;
  //       int length = 10240;
  //       int offset = 0;
  //       byte[] inputBuffer = new byte[length];
  //       while ((len = input.read(inputBuffer, offset, length - offset)) >= 0) {
  //         logger.info("read length: [{}]", new Object[] { Integer.valueOf(len) });
  //         offset += len;
  //       }
  //       if (len < 0)
  //         logger.info("The http has closed.....");
  //       if (offset > 0) {
  //         logger.info("read total length: [{}]", new Object[] { Integer.valueOf(offset) });
  //         res = new byte[offset];
  //         System.arraycopy(inputBuffer, 0, res, 0, offset);
  //         if (input != null)
  //           input.close();
  //       }
  //     }
  //   } catch (Exception ex) {
  //     logger.warn("Message:[{}]", ex);
  //   }
  //   return res;
  // }

  // private void initSubsInfo(DynamicDict dict, Subs subs, Acct acct) throws BaseAppException {
  //   if (subs != null) {
  //     dict.set("PREFIX", subs.getPrefix());
  //     dict.set("ACC_NBR", subs.getAccNbr());
  //     dict.set("SUBS_ID", subs.getSubsId());
  //   } else if (CommonUtil.isNotEmpty((Object[]) acct.getAllSubsList())) {
  //     Subs subs2 = acct.getAllSubsList()[0];
  //     dict.set("PREFIX", subs2.getPrefix());
  //     dict.set("ACC_NBR", subs2.getAccNbr());
  //     dict.set("SUBS_ID", subs2.getSubsId());
  //   }
  // }
}
