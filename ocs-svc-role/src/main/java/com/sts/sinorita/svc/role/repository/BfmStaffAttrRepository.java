package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmStaffAttr;
import com.sts.sinorita.svc.role.dto.request.BfmStaffAttrDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BfmStaffAttrRepository extends JpaRepository<BfmStaffAttr, Long> {

    @Modifying
    @Query(value = "UPDATE POT.BFM_STAFF_ATTR SET ATTR_VALUE = :attrValue WHERE STAFF_ID = :staffId AND ATTR_CODE = :attrCode", nativeQuery = true)
    void updateAttrValue(@Param("attrValue") String attrValue,
                                             @Param("staffId") Long staffId,
                                             @Param("attrCode") String attrCode);

}
