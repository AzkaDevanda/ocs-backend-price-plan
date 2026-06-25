package com.ocs.portal.repository;

import com.ocs.portal.entity.SortOperator;
import com.ocs.portal.projection.pricePlan.discount.QrySortOperatorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SortOperatorRepository extends JpaRepository<SortOperator, String> {

    @Query(value = "select o from SortOperator o where o.sortOperator = :sortOperator")
    public Optional<SortOperator> findBySortOperator(@Param("sortOperator") String sortOperator);

    @Query(value = """
           SELECT A.SORT_OPERATOR as sortOperator, A.SORT_OPERATOR_NAME as sortOperatorName FROM SORT_OPERATOR A WHERE 1 = 1 AND (:SORT_OPERATOR IS NULL OR A.SORT_OPERATOR = :SORT_OPERATOR)
           """,nativeQuery = true)
    List<QrySortOperatorProjection> qrySortOperator(@Param("SORT_OPERATOR") String sortOperator);
}
