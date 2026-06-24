package com.sts.sinorita.helper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BillingSeqHelper {
  @PersistenceContext
  private EntityManager em;

  @Transactional(readOnly = true)
  public Long getBillingSeq(String sequenceName) {
    String sql = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
    return ((Number) em.createNativeQuery(sql).getSingleResult()).longValue();
  }

  public Long getNextVal(String seqName) {
    String sql = "SELECT " + seqName + ".NEXTVAL FROM DUAL";
    return ((Number) em
        .createNativeQuery(sql)
        .getSingleResult())
        .longValue();
  }
}
