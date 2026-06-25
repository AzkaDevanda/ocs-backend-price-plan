package com.ocs.portal.repository;

import com.ocs.portal.entity.OfferCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OfferCategoryRepository extends JpaRepository<OfferCategory, Integer> {

    @Query(value = "SELECT oc.OFFER_CATG_ID  ,oc.OFFER_CATG_NAME, COUNT(ocm.OFFER_ID) AS offer_count\n" +
            "FROM OFFER_CATG_MEM ocm\n" +
            "JOIN OFFER_CATG oc ON ocm.OFFER_CATG_ID = oc.OFFER_CATG_ID\n" +
            "WHERE oc.OFFER_CATG_TYPE = :offerType AND oc.STATE = 'A'\n" +
            "GROUP BY oc.OFFER_CATG_NAME, oc.OFFER_CATG_ID", nativeQuery = true)
    List<Object[]> listOfferCategory(@Param("offerType") Character offerType);


    @Modifying
    @Query("DELETE FROM OfferCatGMem ocm WHERE ocm.childOfferCatgId = :offerCatgId")
    void deleteOfferCatgMemByChildCatgId(@Param("offerCatgId") Integer offerCatgId);

    @Modifying
    @Transactional
    @Query("UPDATE OfferCategory o SET o.state = :state, o.stateDate = :current WHERE o.offerCatgId = :id")
    int updateStateById(@Param("state") Character state, @Param("id") Integer id, @Param("current")LocalDate current);

//    @Modifying
//    @Query("DELETE FROM OfferCatgApplyChannel ocac WHERE ocac.offerCatgId = :offerCatgId")
//    void deleteOfferCatgApplyChannelByOfferCatgId(@Param("offerCatgId") Long offerCatgId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE OFFER_CATG " +
            "SET STATE = 'X', STATE_DATE = SYSDATE " +
            "WHERE OFFER_CATG_ID = :offerCatgId",
            nativeQuery = true
    )
    int updateOfferCatgState(@Param("offerCatgId") Integer offerCatgId);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM OFFER_CATG_MEM " +
                    "WHERE CHILD_OFFER_CATG_ID = :offerCatgId",
            nativeQuery = true
    )
    int deleteOfferCatgMemByChildId(@Param("offerCatgId") Integer offerCatgId);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM OFFER_CATG_APPLY_CHANNEL " +
                    "WHERE OFFER_CATG_ID = :offerCatgId",
            nativeQuery = true
    )
    int deleteOfferCatgApplyChannelByOfferCatgId(@Param("offerCatgId") Integer offerCatgId);
}
