package com.sts.sinorita.dto.request.balanceAdjustment;

import com.sts.sinorita.entity.Bal;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReCcInstBLData extends ReCcInstData {
  public OverdueItemDto overdueItemDto;
  AbEventBenefitDto[] abEventBenefitDtoList;
  AcctBookDto[] acctBookDtoList;
  Bal[] updateBalList;
  ReOverDueInstDto reOverdueInstDto;

  List<BenefitDto> benefitList;

  List<OnceFeeDto> onceFeeList;
}
