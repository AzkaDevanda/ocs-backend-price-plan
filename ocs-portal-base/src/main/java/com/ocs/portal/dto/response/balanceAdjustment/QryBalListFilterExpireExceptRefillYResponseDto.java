package com.ocs.portal.dto.response.balanceAdjustment;

import com.ocs.portal.dto.response.acct.QryAcctInfoResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class QryBalListFilterExpireExceptRefillYResponseDto {
  QryAcctInfoResponseDto acctInfo;
  List<SelectBalListFilterExpireExceptRefillYResponseDto> balList;
}
