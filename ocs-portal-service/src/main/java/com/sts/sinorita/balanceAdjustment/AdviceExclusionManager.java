// package com.sts.sinorita.balanceAdjustment;

// import java.time.LocalDateTime;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.ConcurrentMap;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.support.CronExpression;

// import com.sts.sinorita.dto.response.balanceAdjustment.AdviceTypeCronDto;
// import com.sts.sinorita.repository.AdviceExclutionRepository;

// import lombok.Getter;
// import lombok.Setter;
// import lombok.extern.slf4j.Slf4j;

// @Getter
// @Setter
// @Slf4j
// public class AdviceExclusionManager {
//   private final ConcurrentMap<String, CronExpression> cronMap = new ConcurrentHashMap<>();

//   // @Autowired
//   // private final AdviceExclutionRepository adviceExclutionRepository;

//   // public void refreshAdviceExclusion(String adviceChannel) {
//   //   LocalDateTime date = LocalDateTime.now();
//   //   boolean exclusionEnable = ConfigurationMgr.instance().getBoolean("contact.AdviceType.AdviceExclusionEnable", false);
//   //   if (exclusionEnable) {
//   //     Map<Long, List<AdviceTypeCronDto>> adviceTypeCronChannelMap = AdviceConfig.getInstance()
//   //         .getAdviceTypeCronChannelMap(adviceChannel);
//   //     if (adviceTypeCronChannelMap == null)
//   //       return;
//   //     Set<Long> notSend = new HashSet<>();
//   //     for (Map.Entry<Long, List<AdviceTypeCronDto>> entrySet : adviceTypeCronChannelMap.entrySet()) {
//   //       Long adviceType = entrySet.getKey();
//   //       List<AdviceTypeCronDto> list = entrySet.getValue();
//   //       boolean checkSend = checkSend(list, date);
//   //       if (!checkSend)
//   //         notSend.add(adviceType);
//   //     }
//   //     List<Long> oldExclusionList = getIAdviceTypeExclusionDao().selectAdviceTypeExclusionByChannel(adviceChannel);
//   //     Set<Long> oldExclusionSet = new HashSet<>(oldExclusionList);
//   //     handleAdviceExclusion(oldExclusionSet, notSend);
//   //   }
//   // }

//   // private void handleAdviceExclusion(Set<Long> oldExclusionSet, Set<Long> notSend) {
//   //   // Session ses = SessionContext.currentSession();
//   //   // try {
//   //   // ses.beginTrans();
//   //   for (Long adviceType : oldExclusionSet) {
//   //     if (!notSend.contains(adviceType))
//   //       adviceExclutionRepository.deleteAdviceTypeExclusion(adviceType);
//   //   }
//   //   for (Long adviceType : notSend) {
//   //     if (!oldExclusionSet.contains(adviceType))
//   //       adviceExclutionRepository.insertAdviceTypeExclusion(adviceType);
//   //   }
//     // ses.commitTrans();
//     // } catch (Exception e) {
// //     // ses.releaseTrans();
// //     // } finally {
// //     // ses.releaseTrans();
// //   }

// //   }

// //   public boolean checkSend(boolean directSend, AdviceTypeDto adviceTypeDto, LocalDateTime date) {
// //     boolean exclusionEnable = ConfigurationMgr.instance().getBoolean("contact.AdviceType.AdviceExclusionEnable", false);
// //     if (directSend && exclusionEnable) {
// //       String adviceChannel = adviceTypeDto.getAdviceChannel();
// //       Long adviceType = adviceTypeDto.getAdviceType();
// //       List<AdviceTypeCronDto> list = AdviceConfig.getInstance().getAdviceTypeCronList(adviceChannel, adviceType);
// //       boolean checkSend = checkSend(list, date);
// //       directSend = (directSend && checkSend);
// //     }
// //     return directSend;
// //   }

// //   private boolean checkSend(List<AdviceTypeCronDto> list, LocalDateTime date) {
// //     if (list == null)
// //       return true;
// //     boolean isSend = true;
// //     for (AdviceTypeCronDto adviceTypeCronDto : list) {
// //       String cronKey = adviceTypeCronDto.getCronExpression();
// //       CronExpression cronExpression = getCronExpression(cronKey);
// //       if (cronExpression instanceof InvalidCronExpression) {
// //         log.error("[{}] is not valid cron expression, this rule will be ignored.", new Object[] { cronKey });
// //         continue;
// //       }
// //       boolean effective = cronExpression.isSatisfiedBy(date);
// //       if (!effective)
// //         continue;
// //       String sendFlag = adviceTypeCronDto.getSendFlag();
// //       isSend = "Y".equals(sendFlag);
// //     }
// //     return isSend;
// //   }

// //   private CronExpression getCronExpression(String key) throws BaseAppException {
// //     CronExpression cronExpression;
// //     if (this.cronMap.containsKey(key)) {
// //       cronExpression = this.cronMap.get(key);
// //     } else {
// //       try {
// //         cronExpression = new CronExpression(key);
// //       } catch (ParseException e) {
// //         log.error("[{}]failed to parse cron expression.", new Object[] { key, e });
// //         cronExpression = createInvalidCronExpression();
// //       }
// //       CronExpression oldExpression = this.cronMap.putIfAbsent(key, cronExpression);
// //       if (oldExpression != null)
// //         cronExpression = oldExpression;
// //     }
// //     return cronExpression;
// //   }
// }
