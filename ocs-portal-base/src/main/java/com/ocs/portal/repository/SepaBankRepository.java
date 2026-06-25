package com.ocs.portal.repository;

import com.ocs.portal.entity.SepaBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SepaBankRepository extends JpaRepository<SepaBank, Integer> {
    @Modifying
    @Query(value = "UPDATE SEPA_BANK SET SEPA_STATE = 'C' WHERE BANK_ID = :bankId", nativeQuery = true)
    void updateSepaBankStateC(@Param("bankId") Integer bankId);
}
