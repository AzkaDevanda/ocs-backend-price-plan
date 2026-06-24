package com.sts.sinorita.repository.customRepository;

import java.util.List;

import com.sts.sinorita.entity.mdbtt.BalEntity;

public interface BalEntityRepositoryCustom {
  List<BalEntity> selectBalListFilterExpireExceptRefillY(
      Long acctId, Long acctResId);

}
