package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.SubsUppInstDto;
import com.sts.sinorita.entity.Prod;
import com.sts.sinorita.entity.Subs;
import com.sts.sinorita.repository.SubsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubsService {

  private final SubsRepository subsRepository;

  public Subs[] selectAllSubsListByAcctId (Long acctId) {
    List<Object[]> rows = subsRepository.findSubsWithProdByAcctId(acctId);
    List<Subs> subsList = new ArrayList<>();

    for (Object[] row : rows) {
      Subs subs = new Subs();
      int i = 0;

      subs.setSubsId(((BigDecimal) row[i++]).longValue());
      subs.setPrefix((String) row[i++]);
      subs.setAccNbr((String) row[i++]);
      subs.setCustId(((BigDecimal) row[i++]).longValue());
      subs.setUserId(((BigDecimal) row[i++]).longValue());
      subs.setAcctId(((BigDecimal) row[i++]).longValue());
      subs.setAgentId(((BigDecimal) row[i++]).longValue());
      subs.setAreaId(((BigDecimal) row[i++]).longValue());
      subs.setUpdateDate(((Timestamp) row[i++]).toLocalDateTime());
      subs.setRoutingId(((BigDecimal) row[i++]).longValue());
      subs.setDefLangId(((BigDecimal) row[i++]).longValue());
      subs.setPpsPwd((String) row[i++]);
      subs.setSpId(((BigDecimal) row[i++]).longValue());

      Prod prod = new Prod();
      prod.setCreatedDate(((LocalDate) row[i++]));
      prod.setCompletedDate(((LocalDate) row[i++]));
      prod.setProdState((Character) row[i++]);
      prod.setStateDate(((LocalDate) row[i++]));
      prod.setUpdateDate(((LocalDate) row[i++]));
      prod.setProdStateDate(((LocalDate) row[i++]));
      prod.setBlockReason((String) row[i++]);
      prod.setProdExpDate(((LocalDate) row[i++]));
      prod.setIndepProdId(((BigDecimal) row[i++]).longValue());
      prod.setOfferId(((Integer) row[i++]));

      subs.setProd(prod);
      subsList.add(subs);
    }

    return subsList.toArray(new Subs[0]);
  }

  public SubsUppInstDto selectSubsDefaultPricePlan (Long subsId) {
    List<Object[]> result = subsRepository.selectSubsDefaultPricePlan(subsId);
    if (result.isEmpty()) {
      return null;
    }

    Object[] row = result.get(0);
    return new SubsUppInstDto(
            ((Number) row[0]).longValue(),
            (Date) row[1],
            (Date) row[2]
    );
  }

  public Long qryDefaultPricePlanId (Long subsId) {
//    ISubsExDAO subsExDao = (ISubsExDAO)DAOFactory.createModuleDAO("SubsEx", "billing.abe.subs",
//            JdbcUtil4BL.getDbIdentifier());
    SubsUppInstDto subsUppInstDto = selectSubsDefaultPricePlan(subsId);
    Long defaultPricePlanId = null;
    if (subsUppInstDto != null)
      defaultPricePlanId = subsUppInstDto.getPricePlanId();
    return defaultPricePlanId;
  }
}
