package com.sts.sinorita.helper;

import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.request.priceplan.treshold.SubsEventDto;
import com.sts.sinorita.mapper.balanceAdjustment.SubsEventMapper;
import com.sts.sinorita.repository.SubsEventRepository;
import com.sts.sinorita.util.StringUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdStateHelper {

  private final SubsEventRepository subsEventRepository;

  private final SubsEventMapper subsEventMapper;

  public static final char SKIP_STATE_SET = '9';

  public static final char STATE_NORMAL = '0';

  public static final char STATE_ONE_WAY_BLOCK = '1';

  public static final char STATE_TWO_WAY_BLOCK = '2';

  public String getInitBlockReason() {
    String blockReasonStr = "00000000000000";
    SubsEventDto subsEventDto = subsEventRepository.selectSubsEvent(Long.valueOf(1L))
        .map(subsEventMapper::toSubsEventDtoFromSelectSubEvent).orElse(null);
    int boockReadonLengthInt = subsEventDto.getStateSet().length();
    return StringUtil.padRight(blockReasonStr, boockReadonLengthInt, '0');
  }

}
