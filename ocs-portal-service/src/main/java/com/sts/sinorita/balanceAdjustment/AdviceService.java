package com.sts.sinorita.balanceAdjustment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Service;

import com.sts.sinorita.util.DateUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Service
@Slf4j
public class AdviceService {

  // private static BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>();
  
  // private static boolean isThreadStarted = false;
  
  // private static final Long SMPP_FLUX_CODE = Long.valueOf(-5L);
  
  // private static final String INGORE_ACC_NBR = "IngoreAccNbr";
  
  // AdviceExclusionManager adviceExclusionManager = new AdviceExclusionManager();

  // public int perform(DynamicDict dict) {
  //   DynamicDict dictTmp = new DynamicDict();
  //   dictTmp.valueMap.putAll(dict.valueMap);
  //   log.info("AdviceService params={} \n,dictTmp={}", new Object[] { dict, dictTmp });
  //   boolean isInnerrout = false;
  //   try {
  //     Date sendTime = dict.getDate("SEND_TIME");
  //     AdviceTypeDto adviceTypeDto = getTemplate(dictTmp);
  //     log.info(">>>>>>adviceTypeDto={}", new Object[] { adviceTypeDto });
  //     isInnerrout = AdviceRoutUtil.setDbRoutingContext(dict);
  //     Date nowTime = DateUtil.GetDBDateTime();
  //     dictTmp.set("SYSDATE", nowTime);
  //     if (null == adviceTypeDto) {
  //       log.warn("Template Failure.");
  //       return 1;
  //     }
  //     BusiProxy.getSendParamByChannel(dictTmp);
  //     String adviceChannel = dictTmp.getString("ADVICE_CHANNEL", true);
  //     if (!isThreadStarted && "1".equals(adviceChannel)) {
  //       String isNeedGenerateFile = AdviceConfig.getInstance().getParam("isNeedCreateSMSCdrFile", "0");
  //       if ("1".equals(isNeedGenerateFile)) {
  //         Thread thread = new Thread((Runnable) new SmsGenerateFileThread(msgQueue, "directSendThread"),
  //             "directSendThread");
  //         thread.setDaemon(true);
  //         thread.start();
  //         isThreadStarted = true;
  //       }
  //     }
  //     Long adviceId = AdviceServiceUtil.getAdviceId(dictTmp);
  //     AdviceBusiness.formatSenderParam(dictTmp, adviceTypeDto);
  //     dictTmp.set("MSG_PARAM", AdviceBusiness.getMsgParam(dictTmp, adviceTypeDto));
  //     dictTmp.set("ADVICE_STATE", "A");
  //     AdviceDto adviceDto = createAdvice(dictTmp, adviceId, adviceTypeDto);
  //     adviceDto.setSrcNbr(adviceTypeDto.getSrcNbr());
  //     boolean directSend = false;
  //     directSend = checkDirectSend(dictTmp, sendTime, nowTime, adviceTypeDto);
  //     if (directSend) {
  //       directSend = checkDirectSend(adviceChannel);
  //       log.debug("get direct send [{}] by advice channel [{}].",
  //           new Object[] { Boolean.valueOf(directSend), adviceChannel });
  //     }
  //     if ("27".equals(adviceChannel)) {
  //       directSend = true;
  //       log.info("AdviceTelkomcelRestService adviceChannel=27, set directSend=true");
  //     }
  //     if (checkBlackList(adviceDto, adviceChannel, adviceTypeDto.getAdviceCatg())) {
  //       log.debug("The accNbr is in blacklist, donsend");
  //       adviceDto.setComments("in black list, donsend");
  //       adviceDto.setState("E");
  //       getAdviceHisDAO().insertAdviceHis(adviceDto);
  //       dict.set("SendResult", "0");
  //       dict.set("MSG", adviceDto.getMsg());
  //       dict.set("ADVICE_ID", adviceId);
  //       return 0;
  //     }
  //     if (checkOutboundList(adviceDto, adviceChannel, adviceTypeDto.getAdviceCatg())) {
  //       if (directSend) {
  //         try {
  //           if (("3".equals(adviceChannel) || "1".equals(adviceChannel)) &&
  //               "Y".equals(adviceTypeDto.getIsRule())) {
  //             List<DynamicDict> ruleDictList = getAdviceDAO().selectRuleByAdviceType(adviceTypeDto.getAdviceType());
  //             Long adviceTypeInRule = null;
  //             for (DynamicDict ruleDict : ruleDictList) {
  //               adviceTypeInRule = AdviceTypeUtil.getAdviceTypeByRule(adviceTypeDto, adviceDto, ruleDict);
  //               if (adviceTypeInRule != null
  //                   && adviceTypeInRule.longValue() != adviceTypeDto.getAdviceType().longValue()) {
  //                 dictTmp.set("ADVICE_TYPE", adviceTypeInRule);
  //                 dictTmp.set("STD_CODE", ruleDict.getString("STD_CODE"));
  //                 dictTmp.set("SP_ID", ruleDict.getString("SP_ID"));
  //                 adviceTypeDto = getTemplate(dictTmp);
  //                 adviceDto.setAdviceType(adviceTypeDto.getAdviceType());
  //                 adviceDto.setStdCode(adviceTypeDto.getStdCode());
  //                 break;
  //               }
  //             }
  //           }
  //           if ("3".equals(adviceChannel) || "9".equals(adviceChannel)) {
  //             directSendAdvice(adviceDto, adviceTypeDto, dictTmp);
  //           } else {
  //             directSendAdviceByTemplate(adviceDto, adviceTypeDto, dictTmp);
  //           }
  //         } catch (BaseAppException e) {
  //           handleDirectSendException(e, adviceDto, dict, dictTmp);
  //         }
  //         dict.set("SendResult", dictTmp.getString("SendResult"));
  //         dict.set("ErrorMessage", dictTmp.getString("ErrorMessage"));
  //       } else {
  //         Integer times = adviceTypeDto.getTimes();
  //         Long timeInterval = adviceTypeDto.getTimeInterval();
  //         if (null != times && null != timeInterval) {
  //           adviceDto.setTimes(times);
  //           if (times.intValue() > 1) {
  //             adviceDto.setTimeInterval(timeInterval);
  //             if (adviceTypeDto.getDelayTime() != null) {
  //               adviceDto
  //                   .setDelayTime(Long.valueOf(adviceTypeDto.getDelayTime().longValue() + timeInterval.longValue()));
  //             } else {
  //               adviceDto.setDelayTime(timeInterval);
  //             }
  //           }
  //         }
  //         setNetworkType(adviceTypeDto, adviceDto);
  //         getAdviceDAO().insertAdvice(adviceDto);
  //         dict.set("SendResult", "0");
  //       }
  //     } else {
  //       adviceDto.setComments("Not in outbound list");
  //       adviceDto.setState("C");
  //       getAdviceHisDAO().insertAdviceHis(adviceDto);
  //       dict.set("SendResult", "0");
  //     }
  //     dict.set("MSG", adviceDto.getMsg());
  //     dict.set("ADVICE_ID", adviceId);
  //   } catch (BaseAppException e) {
  //     e.setLocaleMessage(StringUtil
  //         .stringFormat("Add advice[std_code={0}] for Subscribers[prefix={1},acc_nbr={2}] error!reason:{3}", dictTmp
  //             .get("STD_CODE"), dictTmp.get("PREFIX"), dictTmp.get("ACC_NBR"), e.getLocaleMessage()));
  //     throw e;
  //   } finally {
  //     if (isInnerrout)
  //       DbRoutingContext.endCurrentDbRoutingEnv();
  //   }
  //   return 0;
  // }
}
