package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.entity.AcctBook;
import com.sts.sinorita.repository.AcctBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AcctBookService {
  private final AcctBookRepository acctBookRepository;

  public AcctBook saveAcctBook (AcctBookData data) {
    AcctBook entity = toEntity(data);
    return acctBookRepository.save(entity);
  }

  private AcctBook toEntity (AcctBookData data) {
    return AcctBook.builder()
//            .acctBookId(data.getAcctBookId())
            .acctId(data.getAcctId())
            .acctResId(data.getAcctResId())
            .acctBookType(data.getAcctBookType())
            .createdDate(data.getCreatedDate())
            .preBalance(BigDecimal.valueOf(
                    data.getPreBalance() == null ? 0 : data.getPreBalance()
            ))
            .preExpDate(data.getPreExpDate())
            .charge(BigDecimal.valueOf(
                    data.getCharge() == null ? 0 : data.getCharge()
            ))
            .billId(data.getBillId())
            .partyType(data.getPartyType())
            .partyCode(data.getPartyCode())
            .preSuttleBal(BigDecimal.valueOf(
                    data.getPreSuttleBal() == null ? 0 : data.getPreSuttleBal()
            ))
            .seconds(data.getSeconds())
            .balId(data.getBalId())
            .contactChannelId(
                    data.getContactChannelId() == null ? null : data.getContactChannelId().intValue()
            )
            .eventPaymentId(data.getEventPaymentId())
            .glCode(data.getGlCode())
            .preEffDate(data.getPreEffDate())
            .effSeconds(data.getEffSeconds())
            .spId(data.getSpId() == null ? null : data.getSpId().intValue())
            .partId(data.getPartId() == null ? null : data.getPartId().shortValue())
            .refAttr(data.getRefAttr())
            .orgId(data.getOrgId() == null ? null : data.getOrgId().intValue())
            .build();
  }

}
