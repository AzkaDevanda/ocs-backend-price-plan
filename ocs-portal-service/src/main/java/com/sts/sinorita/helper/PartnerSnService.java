package com.sts.sinorita.helper;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AcctBookReconcileRepository;
import com.sts.sinorita.repository.ConfigItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerSnService {

  private final ConfigItemRepository configItemRepository;

  private final AcctBookReconcileRepository acctBookReconcileRepository;

  public boolean checkPartnerSn(Long channelId, String sn) {
    log.debug("checkPartnerSn channelId is {} sn is {}", new Object[] { channelId, sn });
    boolean isExist = false;
    // String routingFlag =
    // ConfigItemCache.instance().getString("ACCT.ACCOUNT_PUBLIC.CHECK_PARTNER_SN_IS_USE_CURRENT_ROUTING",
    // "N");
    Optional<ConfigItemParamProjection> findRoutingFlag = configItemRepository.findConfigItem("ACCT", "ACCOUNT_PUBLIC",
        "CHECK_PARTNER_SN_IS_USE_CURRENT_ROUTING");
    String routingFlag = findRoutingFlag.map(ConfigItemParamProjection::getDefaultValue).orElse("N");
    boolean isCurrentRouting = false;
    if ("Y".equals(routingFlag))
      isCurrentRouting = true;
    Long acctBookId = qryAcctBookIdByPartnerSn(sn, channelId, isCurrentRouting);
    if (acctBookId != null)
      isExist = true;
    return isExist;
  }

  public Long qryAcctBookIdByPartnerSn(String partnerSn, Long channelId, boolean isCurrentRouting) {
    Long acctBookId = null;
    // IPartnerSnHelperDAO partnerQueryDAO = null;
    // if (isCurrentRouting) {
    // partnerQueryDAO =
    // (IPartnerSnHelperDAO)DAOFactory.createModuleDAO("PartnerSnHelper",
    // "billing.coreapi",
    // JdbcUtil4BL.getDbIdentifier());
    // } else {
    // partnerQueryDAO =
    // (IPartnerSnHelperDAO)DAOFactory.createModuleDAOAllRoute("PartnerSnHelper",
    // "billing.coreapi",
    // JdbcUtil4BL.getDbIdentifier());
    // }
    acctBookId = acctBookReconcileRepository.selectAcctBookIdByPartnerSn(partnerSn, channelId);
    return acctBookId;
  }

}
