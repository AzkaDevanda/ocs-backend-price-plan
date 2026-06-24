package com.sts.sinorita.repository.offer;

import com.sts.sinorita.entity.LifecyleApply;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeCycleApplyRepository extends JpaRepository<LifecyleApply, Integer> {
  Optional<LifecyleApply> findByOfferId(Integer offerId);

  void deleteByOfferId(Integer offerId);
}
