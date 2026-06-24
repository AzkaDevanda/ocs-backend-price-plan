package com.sts.sinorita.balanceAdjustment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.AsynCallDto;
import com.sts.sinorita.dto.request.balanceAdjustment.StateChangeDict;
import com.sts.sinorita.helper.BillingSeqHelper;
import com.sts.sinorita.mapper.balanceAdjustment.AsynCallMapper;
import com.sts.sinorita.repository.AsynCallRepository;
import com.sts.sinorita.util.CollectionUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddAsyncallService {

  private final AsynCallRepository asynCallRepository;

  private final AsynCallMapper asynCallMapper;

  private final BillingSeqHelper billingSeqHelper;

  public int perform(StateChangeDict dict) {
    AsynCallDto asynCallDto = new AsynCallDto();
    asynCallDto.setEventId(dict.getSubsEventId());
    asynCallDto.setState("A");
    // asynCallDto.setAvp(dict.getString("AVP"));
    // asynCallDto.setComments(dict.getString("COMMENTS"));
    // IAsynCallDAO asynCallDAO = (IAsynCallDAO) DAOFactory.create("AsynCall",
    // JdbcUtil4BL.getDbCache());
    List subsIdList = dict.getSubsIdList();
    if (CollectionUtil.isEmpty(subsIdList)) {
      asynCallDto.setAsynCalId(billingSeqHelper.getBillingSeq("ASYN_CALL_ID_SEQ"));
      log.debug("subsIdList is empty, record by subs_id:{}", new Object[] { dict.getSubsId() });
      asynCallDto.setSubsId(dict.getSubsId());
      asynCallRepository.save(asynCallMapper.toEntity(asynCallDto));
      log.debug(" insertAsynCall success. ASYN_CAL_ID={}", new Object[] { asynCallDto.getAsynCalId() });
    } else {
      log.debug("record by subsIdList");
      for (Object subsIdObj : subsIdList) {
        asynCallDto.setAsynCalId(billingSeqHelper.getBillingSeq("ASYN_CALL_ID_SEQ"));
        asynCallDto.setSubsId(Long.valueOf(Long.parseLong(subsIdObj.toString())));
        asynCallRepository.save(asynCallMapper.toEntity(asynCallDto));
        log.debug(" insertAsynCall success. ASYN_CAL_ID={}", new Object[] { asynCallDto.getAsynCalId() });
      }
    }
    return 0;
  }
}
