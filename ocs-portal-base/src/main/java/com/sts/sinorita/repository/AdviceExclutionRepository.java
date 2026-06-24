// package com.sts.sinorita.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import com.sts.sinorita.entity.AdviceExclution;

// import jakarta.transaction.Transactional;

// @Repository
// public interface AdviceExclutionRepository extends JpaRepository<AdviceExclution, Long> {

//   @Query(value = "DELETE FROM ADVICE_EXCLUSION A WHERE A.ADVICE_TYPE= :adviceType")
//   @Modifying
//   @Transactional
//   void deleteAdviceTypeExclusion(@Param("adviceType") Long adviceType);

//   @Query(value = "INSERT INTO ADVICE_EXCLUSION (ADVICE_TYPE) VALUES (:adviceType)")
//   @Modifying
//   @Transactional
//   void insertAdviceTypeExclusion(@Param("adviceType") Long adviceType);

// }
