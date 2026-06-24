package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class Partner {
  private String partyType;

  private String partyCode;

  // public Boolean checkPartyTypeAndPartyCode() {
  //   if (this.partyType == null || this.partyType.isEmpty() || "E".equals(this.partyType))
  //     return Boolean.valueOf(true); 
  //   if (this.partyCode.isEmpty() || this.partyCode == null)
  //     return Boolean.valueOf(false); 
  //   return checkPartCodeMatch();
  // }

  // private Boolean checkPartCodeMatch() {
  //   Boolean ret = Boolean.valueOf(false);
  //   String partyTypeProjectization = ConfigItemCache.instance().getString("CUSTOMER_CARE.CC_PUBLIC.PARTY_TYPE_PROJECTIZATION");
  //   IPartnerDAO partnerDAO = (IPartnerDAO)DAOFactory.createModuleDAO("Partner", "bizcommon.abe.partner", 
  //       JdbcUtil4BC.getDbBackService());
  //   if (StringUtils.isNotEmpty(partyTypeProjectization) && partyTypeProjectization.contains(this.partyType)) {
  //     DynamicDict dict = new DynamicDict();
  //     dict.setServiceName("CheckPartyCodeIsMatch");
  //     dict.set("PARTY_CODE", this.partyCode);
  //     dict.set("PARTY_TYPE", this.partyType);
  //     ServiceFlow.callService(dict);
  //     String retFlag = dict.getString("RET_FLAG");
  //     if (StringUtils.isNotEmpty(retFlag))
  //       ret = Boolean.valueOf(retFlag); 
  //   } else if ("A".equals(this.partyType)) {
  //     Long staffJobId = Long.valueOf(this.partyCode);
  //     ret = partnerDAO.isStaffJobExist(staffJobId);
  //   } else if ("B".equals(this.partyType)) {
  //     Long bankId = Long.valueOf(this.partyCode);
  //     ret = partnerDAO.isBankExist(bankId);
  //   } else if ("C".equals(this.partyType)) {
  //     Long agentId = Long.valueOf(this.partyCode);
  //     ret = partnerDAO.isAgentExist(agentId);
  //   } else if ("D".equals(this.partyType)) {
  //     Long custId = Long.valueOf(this.partyCode);
  //     if (!DbRoutingCfg.isDistributedDB()) {
  //       ret = partnerDAO.isCustExist(custId);
  //     } else {
  //       partnerDAO = (IPartnerDAO)DAOFactory.createModuleDAOAllRoute("Partner", "bizcommon.abe.partner", 
  //           JdbcUtil4BC.getDbBackService());
  //       ret = partnerDAO.isCustExistForDistribution(custId);
  //     } 
  //   } else if ("F".equals(this.partyType)) {
  //     Long staffId = Long.valueOf(this.partyCode);
  //     ret = partnerDAO.isStaffExist(staffId);
  //   } else if ("O".equals(this.partyType)) {
  //     Long orgId = Long.valueOf(this.partyCode);
  //     ret = partnerDAO.isOrgnizationExist(orgId);
  //   } else if ("Z".equals(this.partyType)) {
  //     Long userId = Long.valueOf(this.partyCode);
  //     ret = partnerDAO.isUserExist(userId);
  //   } 
  //   return ret;
  // }
}
