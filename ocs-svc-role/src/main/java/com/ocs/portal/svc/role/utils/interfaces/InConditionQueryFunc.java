package com.ocs.portal.svc.role.utils.interfaces;

import java.util.List;

@FunctionalInterface
public interface InConditionQueryFunc <T, K>{
    List<T> queryInListCondition(List<K> paramList);
}
