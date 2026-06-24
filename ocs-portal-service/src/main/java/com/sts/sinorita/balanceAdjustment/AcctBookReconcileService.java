package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookReconcileDto;
import com.sts.sinorita.entity.AcctBookReconcile;
import com.sts.sinorita.repository.AcctBookReconcileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcctBookReconcileService {
  private final AcctBookReconcileRepository acctBookReconcileRepository;

  public AcctBookReconcile saveReconcile (AcctBookReconcileDto dto) {
    AcctBookReconcile entity = toEntity(dto);
    return acctBookReconcileRepository.save(entity);
  }

  private AcctBookReconcile toEntity (AcctBookReconcileDto dto) {
    // gunakan mapper di atas
    AcctBookReconcile entity = new AcctBookReconcile();
    entity.setAcctBookId(dto.getAcctBookId());
    entity.setReconcileState("A".equalsIgnoreCase(dto.getReconcileState()));
    entity.setLogId(dto.getLogId() == null ? null : dto.getLogId().intValue());
    entity.setContactChannelId(dto.getContactChannelId() == null ? null : dto.getContactChannelId().shortValue());
    entity.setSpId(dto.getSpId() == null ? null : dto.getSpId().intValue());
    entity.setPartId(dto.getPartId() == null ? null : dto.getPartId().shortValue());
    if (dto.getAcctBookType() != null)
      entity.setAcctBookType("C".equalsIgnoreCase(dto.getAcctBookType()));
    entity.setCharge(dto.getCharge());
    entity.setPartnerSn(dto.getPartnerSn());
    entity.setPartnerDetail(dto.getPartnerDetail());
    entity.setTransactionType(dto.getTransactionType());
    if (dto.getCreatedDate() != null)
      entity.setCreatedDate(dto.getCreatedDate().toLocalDate());
    if (dto.getPaymentDate() != null)
      entity.setPaymentDate(dto.getPaymentDate().toLocalDate());
    entity.setProcessState(false);
    return entity;
  }
}
