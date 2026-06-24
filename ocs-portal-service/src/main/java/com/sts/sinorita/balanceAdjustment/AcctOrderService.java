package com.sts.sinorita.balanceAdjustment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.ProdDto;
import com.sts.sinorita.dto.request.balanceAdjustment.StateChangeDict;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.mapper.balanceAdjustment.ProdMapper;
import com.sts.sinorita.repository.ProdRepository;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcctOrderService {

  private final ProdRepository prodRepository;

  private final ProdMapper prodMapper;

  // public int acctModProdState(StateChangeDict paramDynamicDict) {
  //   log.debug("acctModProdState start. dict = [{}]", new Object[] { paramDynamicDict });
  //   AssertUtil.isNotNull(paramDynamicDict);
  //   if ("Y".equals(paramDynamicDict.getString("ASYN_CALL_FLAG"))) {
  //     paramDynamicDict.serviceName = "AddAsyncall";
  //     ServiceFlow.callService(paramDynamicDict);
  //   } else {
  //     List list = paramDynamicDict.getSubsIdList();
  //     String str = paramDynamicDict.getString("OBJ_STATE");
  //     Long long_1 = paramDynamicDict.getSubsEventId();
  //     if (null == long_1) {
  //       AssertUtil.isNotEmpty(str, "objState");
  //       if (!"A".equalsIgnoreCase(str) &&
  //           !"D".equalsIgnoreCase(str) &&
  //           !"E".equalsIgnoreCase(str) &&
  //           !"B".equalsIgnoreCase(str))
  //         // ExceptionHandler.publish("CC-S-SALES-00401", 0);
  //         throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, MessageService.getMessage("CC-S-SALES-00401"));
  //     }
  //     if (CommonUtil.isEmpty(list)) {
  //       log.debug("needn't change any subsriber's status");
  //       return 0;
  //     }
  //     Long long_2 = paramDynamicDict.getSubsId();
  //     Long long_3 = null;
  //     Long long_4 = null;
  //     Long long_5 = null;
  //     for (Object object : list) {
  //       try {
  //         long_5 = Long.valueOf(Long.parseLong(object.toString()));
  //         acctModProdStateEach(long_5, paramDynamicDict);
  //         if (long_5.equals(long_2)) {
  //           log.debug("Begin to calulate fee for subs {}", new Object[] { long_2 });
  //           long_3 = paramDynamicDict.getLong("REC_SUBS_EVENT_ID");
  //           long_4 = getOnceFee(paramDynamicDict);
  //         }
  //       } catch (Exception exception) {
  //         recCcAsynCall(long_5, paramDynamicDict, exception);
  //         // ExceptionHandler.publish("CC-S-SALES-02312", "", 0, exception, "Modify
  //         // prodstate");
  //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("CC-S-SALES-02312"));
  //       }
  //     }
  //     paramDynamicDict.set("SUBS_EVENT_ID", long_3);
  //     paramDynamicDict.set("ONCE_FEE", long_4);
  //     paramDynamicDict.remove("CUST_ORDER");
  //     log.debug("acctModProdState end. dict = [{}]", new Object[] { paramDynamicDict });
  //   }
  //   return 0;
  // }

  // private void acctModProdStateEach(Long paramLong, StateChangeDict paramDynamicDict) {
  //   String str = paramDynamicDict.getString("OBJ_STATE");
  //   Long long_ = paramDynamicDict.getLong("SUBS_EVENT_ID");
  //   ProdDto prod = prodRepository.qryProdById(paramLong).map(prodMapper::qryProdIdToProdDto).orElse(null);
  //   if (long_ == null)
  //     long_ = judgeSubsEventByProdAndObjState(prod, str);
  //   if (long_ == null)
  //     return;
  //   paramDynamicDict.set("REC_SUBS_EVENT_ID", long_);
  //   if (existApplicableLifeCycle(prod.getOfferId())) {
  //     log.debug("begin TriggerLifecycle.");
  //     paramDynamicDict.set("SUBS_ID", paramLong);
  //     paramDynamicDict.set("SUBS_EVENT_ID", long_);
  //     paramDynamicDict.set("OFFER_ID", prod.getOfferId());
  //     paramDynamicDict.set("SRC_PROD_STATE", prod.getProdState());
  //     paramDynamicDict.set("OBJ_PROD_STATE", str);
  //     if (paramDynamicDict.getBoolean("IS_CHECK_OWE_CHARGE") != null) {
  //       DynamicDict dynamicDict = new DynamicDict();
  //       dynamicDict.set("IS_CHECK_OWE_CHARGE", paramDynamicDict.getBoolean("IS_CHECK_OWE_CHARGE"));
  //       paramDynamicDict.set("EXT_PARAM", dynamicDict);
  //     }
  //     paramDynamicDict.serviceName = "TriggerLifecycle";
  //     ServiceFlow.callService(paramDynamicDict);
  //     log.debug("end TriggerLifecycle.");
  //   } else {
  //     log.debug("begin dispatch order.");
  //     DynamicDict dynamicDict1 = new DynamicDict();
  //     dynamicDict1.set("PARTY_TYPE", paramDynamicDict.getString("PARTY_TYPE"));
  //     dynamicDict1.set("PARTY_CODE", paramDynamicDict.getString("PARTY_CODE"));
  //     SubsDto subs = ProfileRepository.qrySubsById(paramLong);
  //     OrderData orderData = OrderHelper.createOrderData(dynamicDict1, subs, long_);
  //     orderData.setChannelType(ChannelTypeDef.ZSMART_SYSTEM_AUTOMATIC_CHANNEL_TYPE);
  //     if (paramDynamicDict.getBoolean("IS_CHECK_OWE_CHARGE") != null)
  //       orderData.setIsCheckOweCharge(paramDynamicDict.getBoolean("IS_CHECK_OWE_CHARGE"));
  //     DynamicDict dynamicDict2 = OrderHelper.dispatchOrder(orderData, false);
  //     paramDynamicDict.set("CUST_ORDER", dynamicDict2.get("CUST_ORDER"));
  //     log.debug("end dispatch order.");
  //   }
  // }

  // private Long judgeSubsEventByProdAndObjState(Prod paramProd, String paramString) throws BaseAppException {
  //   log.debug("judgeSubsEventByProdAndObjState start.");
  //   String str1 = paramProd.getBlockReason();
  //   if (StringUtil.isEmpty(str1) || str1.length() < 3)
  //     str1 = ProdStateHelper.getInitBlockReason();
  //   String str2 = str1.substring(2, 3);
  //   String str3 = str1.substring(5, 6);
  //   String str4 = ProfileRepository.qryProdStdState(paramProd.getProdId());
  //   Long long_ = null;
  //   log.debug("targetState = [{}], curProdState = [{}], blockReason = [{}]",
  //       new Object[] { paramString, str4, str1 });
  //   if ("A".equals(paramString)) {
  //     long_ = getSubsEventIdForActive(str4, str2, str3);
  //   } else if ("D".equals(paramString)) {
  //     long_ = getSubsEventIdForOneWayBlock(paramString, str2, paramProd);
  //   } else if ("E".equals(paramString)) {
  //     long_ = getSubsEventIdForTwoWayBlock(paramString, str2, paramProd);
  //   } else if ("B".equals(paramString)) {
  //     long_ = Long.valueOf(46L);
  //   }
  //   log.debug("judgeSubsEventByProdAndObjState end.   subsEventId = [{}]", new Object[] { long_ });
  //   return long_;
  // }

}
