package com.ocs.portal.repository.offer;

import com.ocs.portal.entity.LifecyleApply;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeCycleApplyRepository extends JpaRepository<LifecyleApply, Integer> {
  Optional<LifecyleApply> findByOfferId(Integer offerId);

  void deleteByOfferId(Integer offerId);
}
