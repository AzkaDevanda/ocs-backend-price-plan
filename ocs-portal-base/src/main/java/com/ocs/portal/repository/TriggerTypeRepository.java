package com.ocs.portal.repository;

import com.ocs.portal.dto.response.trigger.GetTriggerTypeProjection;
import com.ocs.portal.entity.TriggerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriggerTypeRepository extends JpaRepository<TriggerType, Integer> {

    @Query(value = "SELECT \n" +
            "    A.TRIGGER_TYPE as triggerType, \n" +
            "    A.TRIGGER_TYPE_NAME as triggerTypeName, \n" +
            "    A.COMMENTS as comments\n" +
            "FROM \n" +
            "    TRIGGER_TYPE A\n" +
            "WHERE \n" +
            "    1 = 1\n", nativeQuery = true)
    List<GetTriggerTypeProjection> getTriggerTypeList();

}
