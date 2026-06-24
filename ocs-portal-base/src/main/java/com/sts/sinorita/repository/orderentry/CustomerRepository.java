package com.sts.sinorita.repository.orderentry;

import com.sts.sinorita.entity.CustEntity;
import com.sts.sinorita.projection.orderentry.CustomerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustEntity, Integer> {

  @Query("""
              SELECT 
                  A.custId AS custId,
                  A.custCode AS custCode,
                  A.custName AS custName,
                  A.firstName AS firstName,
                  A.secondName AS secondName,
                  A.thirdName AS thirdName,
                  A.fourName AS fourName,
                  A.custType AS custType,
                  A.email AS email,
                  F.custTypeName AS custTypeName,
                  A.certId AS certId,
                  A.custTitleId AS custTitleId,
                  G.certNbr AS certNbr,
                  G.certTypeId AS certTypeId,
                  G.issueOrg AS certIssueOrg,
                  G.issueDate AS certIssueDate,
                  G.effDate AS certEffDate,
                  G.expDate AS certExpDate,
                  G.certAddress AS certAddress,
                  A.zipcode AS zipcode,
                  A.address AS address,
                  A.createdDate AS createdDate,
                  A.updateDate AS updateDate,
                  A.state AS state,
                  A.stateDate AS stateDate,
                  A.areaId AS areaId,
                  A.stdAddrId AS stdAddrId,
                  A.custCreditGradeId AS custCreditGradeId,
                  A.religionId AS religionId,
                  A.industryId AS industryId,
                  A.occupationId AS occupationId,
                  A.gender AS gender,
                  A.phoneNumber AS phoneNumber,
                  A.comments AS comments,
                  H.certTypeName AS certTypeName,
                  A.impGradeId AS impGradeId,
                  I.impGradeName AS impGradeName,
                  A.birthdayDay AS birthdayDay,
                  A.routingId AS routingId
              FROM 
                  CustEntity A
              LEFT JOIN CertEntity G ON A.certId = G.certId
              LEFT JOIN CertTypeEntity H ON G.certTypeId = H.certTypeId
              LEFT JOIN CustTypeEntity F ON A.custType = F.custType
              LEFT JOIN ImpGradeEntity I ON A.impGradeId = I.impGradeId
              WHERE A.state = 'A'
                AND COALESCE(A.spId, 0) = :spId
                AND (:email IS NULL OR A.email = :email)
                AND (:industryId IS NULL OR A.industryId = :industryId)
                AND (:custCode IS NULL OR A.custCode = :custCode)
                AND (:custType IS NULL OR A.custType = :custType)
                AND (:certTypeId IS NULL OR G.certTypeId = :certTypeId)
                AND (:certNbr IS NULL OR G.certNbr = :certNbr)
                AND (:custId IS NULL OR A.custId = :custId)
          """)
  List<CustomerProjection> searchCustomers (
          @Param("spId") Long spId,
          @Param("email") String email,
          @Param("industryId") Long industryId,
          @Param("custCode") String custCode,
          @Param("custType") String custType,
          @Param("certTypeId") Long certTypeId,
          @Param("certNbr") String certNbr,
          @Param("custId") Long custId);

  @Query(value = """
          SELECT 
              B.CUST_ID, 
              B.CUST_CODE, 
              B.CUST_NAME, 
              B.CUST_TYPE, 
              B.CERT_ID, 
              B.PARENT_ID, 
              B.AREA_ID, 
              B.IMP_GRADE_ID, 
              B.BIRTHDAY_DAY
          FROM CERT A
          JOIN CUST B ON A.CERT_ID = B.CERT_ID
          WHERE B.CUST_ID = :custId 
            AND A.CERT_NBR = :certNbr
          """, nativeQuery = true)
  List<Object[]> findCustByCustIdAndCertNbr (
          @Param("custId") Long custId,
          @Param("certNbr") String certNbr);

  @Query(value = "SELECT CUST_TYPE FROM CUST WHERE CUST_ID = :custId", nativeQuery = true)
  String selectCustType(@Param("custId") Long custId);
}
