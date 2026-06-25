package com.ocs.portal.repository;

import com.ocs.portal.entity.InstalmentItem;
import com.ocs.portal.entity.InstalmentItemId;
import com.ocs.portal.projection.accountConfig.QryInstalmentItemByTypeIDProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstalmentItemRepository extends JpaRepository<InstalmentItem, InstalmentItemId> {
        @Modifying
        @Query("UPDATE InstalmentItem i SET i.itemPercent = :itemPercent, i.repeatTime = :repeatTime, i.feePercent = :feePercent "
                        +
                        "WHERE i.id.instalmentTypeId = :instalmentTypeId AND i.id.seq = :seq")
        void updateInstalmentItem(
                        @Param("itemPercent") Integer itemPercent,
                        @Param("repeatTime") Integer repeatTime,
                        @Param("feePercent") Integer feePercent,
                        @Param("instalmentTypeId") Long instalmentTypeId,
                        @Param("seq") Integer seq);

        @Modifying
        @Query("DELETE FROM InstalmentItem i WHERE i.id.instalmentTypeId = :instalmentTypeId AND (:seq IS NULL OR i.id.seq = :seq)")
        void deleteByInstalmentTypeIdAndSeq(
                        @Param("instalmentTypeId") Long instalmentTypeId,
                        @Param("seq") Integer seq);

        @Query(value = "SELECT i.INSTALMENT_TYPE_ID AS instalmentTypeId, i.SEQ AS seq, i.ITEM_PERCENT AS itemPercent, i.REPEAT_TIME AS repeatTime, 'X' AS actionType, i.FEE_PERCENT AS feePercent FROM INSTALMENT_ITEM i WHERE i.INSTALMENT_TYPE_ID = :instalmentTypeId ORDER BY i.seq", nativeQuery = true)
        List<QryInstalmentItemByTypeIDProjection> QryInstalmentItemByTypeID(
                        @Param("instalmentTypeId") Long instalmentTypeId);

}
