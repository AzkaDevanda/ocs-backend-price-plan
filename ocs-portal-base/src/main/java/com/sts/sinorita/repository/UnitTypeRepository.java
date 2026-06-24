package com.sts.sinorita.repository;

import com.sts.sinorita.entity.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, Integer> {

    @Query(value = "SELECT ut.UNIT_TYPE_ID , ut.UNIT_TYPE_NAME " +
            "FROM UNIT_TYPE ut ORDER BY UNIT_TYPE_NAME ASC", nativeQuery = true)
    List<Object[]> getUnitType();

}
