package com.sts.sinorita.dto.response.balanceAdjustment;

import com.sts.sinorita.dto.response.acct.QryAcctInfoResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class QryBalListFilterExpireExceptRefillYResponseDto {
  QryAcctInfoResponseDto acctInfo;
  List<SelectBalListFilterExpireExceptRefillYResponseDto> balList;
}
