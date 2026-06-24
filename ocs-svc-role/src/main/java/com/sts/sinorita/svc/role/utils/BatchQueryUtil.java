package com.sts.sinorita.svc.role.utils;

import com.sts.sinorita.svc.role.utils.interfaces.InConditionQueryFunc;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class BatchQueryUtil {

    public static <T, K> List<T> queryWithInCondition(List<K> queryCondition, InConditionQueryFunc<T, K> inConditionQueryFunc) {
        return queryWithInCondition(queryCondition, 500, inConditionQueryFunc);
    }

    public static <T, K> List<T> queryWithInCondition(List<K> queryCondition, int batchPolicy, InConditionQueryFunc<T, K> inConditionQueryFunc) {
        List<List<K>> idConditions = splitInCondition(queryCondition, batchPolicy);
        List<T> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(idConditions))
            return result;
        for (List<K> idCondition : idConditions) {
            List<T> oneBatch = inConditionQueryFunc.queryInListCondition(idCondition);
            result.addAll(oneBatch);
        }
        return result;
    }

    public static <T> List<List<T>> splitInCondition(List<T> originalList, int batchPolicy) {
        if (originalList == null || originalList.isEmpty())
            return new ArrayList<>();
        if (batchPolicy <= 0) {
            List<List<T>> list = new ArrayList<>();
            list.add(originalList);
            return list;
        }
        int originalListLen = originalList.size();
        int totalBatch = (originalListLen + batchPolicy - 1) / batchPolicy;
        List<List<T>> lists = new ArrayList<>(totalBatch);
        for (int i = 0; i < totalBatch; i++) {
            List<T> tmp = originalList.subList(i * batchPolicy, Math.min((i + 1) * batchPolicy, originalListLen));
            lists.add(tmp);
        }
        return lists;
    }

}
