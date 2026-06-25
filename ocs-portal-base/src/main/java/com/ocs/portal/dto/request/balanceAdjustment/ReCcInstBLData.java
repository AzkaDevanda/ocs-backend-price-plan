package com.ocs.portal.dto.request.balanceAdjustment;

import com.ocs.portal.entity.Bal;
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
