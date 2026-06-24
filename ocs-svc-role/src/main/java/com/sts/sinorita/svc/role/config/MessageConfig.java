package com.sts.sinorita.svc.role.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import com.sts.sinorita.svc.role.utils.ThreadLocalMap;
@Configuration
public class MessageConfig {
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
    source.setBasenames("classpath:messages",
        "classpath:account/AccountRes",
        "classpath:billingcare/BcApplicationRes",
        "classpath:billingcare/BcCommonRes",
        "classpath:billingcare/BcContactRes",
        "classpath:billingcare/BcDomainRes",
        "classpath:bizCommon/bizCommonRes",
        "classpath:bmc/BmcRes",
        "classpath:bportal/BPortalRes",
        "classpath:cc/CCRes",
        "classpath:comResource/account/AccountRes",
        "classpath:comResource/bfm/BfmRes",
        "classpath:comResource/billingcare/BcApplicationRes",
        "classpath:comResource/billingcare/BcCommonRes",
        "classpath:comResource/billingcare/BcContactRes",
        "classpath:comResource/billingcare/BcDomainRes",
        "classpath:comResource/bizCommon/bizCommonRes",
        "classpath:comResource/bmc/BmcRes",
        "classpath:comResource/bportal/BPortalRes",
        "classpath:comResource/busiorder/BusiorderRes",
        "classpath:comResource/cc/CCRes",
        "classpath:comResource/contact/ContactRes",
        "classpath:comResource/doc/docRes",
        "classpath:comResource/domainframe/DomainFrameRes",
        "classpath:comResource/evoucher/EvoucherRes",
        "classpath:comResource/jobservermgr/JobServerManager",
        "classpath:comResource/journal/JournalRes",
        "classpath:comResource/mdr/MdrRes",
        "classpath:comResource/ms/MsRes",
        "classpath:comResource/msgbt/MsgBtRes",
        "classpath:comResource/orange/OrangeBillingRes",
        "classpath:comResource/orange/OrangeCCRes",
        "classpath:comResource/prov/ProvRes",
        "classpath:comResource/publicrule/PublicRuleRes",
        "classpath:comResource/stafforg/StaffOrgRes",
        "classpath:comResource/sysmgt/SysmgrRes",
        "classpath:comResource/uip/UipRes",
        "classpath:comResource/userrole/UserRoleRes",
        "classpath:comResource/CommonRes",
        "classpath:comResourceCus/account/AccountRes",
        "classpath:comResourceCus/bizcommon/bizCommonRes",
        "classpath:comResourceCus/bizcommon/BusinessCommon",
        "classpath:comResourceCus/cc/CCRes",
        "classpath:contact/ContactRes",
        "classpath:doc/docRes",
        "classpath:domainframe/DomainFrameRes",
        "classpath:evoucher/EvoucherRes",
        "classpath:flexResource/bfmbusiness/BusiOrder",
        "classpath:flexResource/bfmbusiness/MsgBt",
        "classpath:flexResource/bfmbusiness/PublicRule",
        "classpath:flexResource/bfmcommon/Config",
        "classpath:flexResource/bfmcommon/Memo",
        "classpath:flexResource/bfmcommon/Config",
        "classpath:flexResource/bfmportal/Busifrm",
        "classpath:flexResource/billing/Billing",
        "classpath:flexResource/bizcommon/BusinessCommon",
        "classpath:flexResource/bportal/BPortal",
        "classpath:flexResource/contact/CustContact",
        "classpath:flexResource/corelib/corelib",
        "classpath:flexResource/customercare/CustomerCare",
        "classpath:flexResource/customreport/Report",
        "classpath:flexResource/default/Common",
        "classpath:flexResource/etopup/etopup",
        "classpath:flexResource/jobservermgr/JobServerMgr",
        "classpath:flexResource/journal/journal",
        "classpath:flexResource/mvno/MVNO",
        "classpath:flexResource/RichEditor/RichEditor",
        "classpath:flexResource/stafforg/StaffOrg",
        "classpath:flexResource/userrole/UserRole",
        "classpath:flexResourceCus/billing/Billing",
        "classpath:flexResourceCus/BusinessCommon/BusinessCommon",
        "classpath:flexResourceCus/customercare/CustomerCare",
        "classpath:flexResourceCus/provisioning/Prov_Project",
        "classpath:gloResource/contact/Global",
        "classpath:gloResource/portal/Global",
        "classpath:journal/JournalRes",
        "classpath:orange/OrangeBillingRes",
        "classpath:portal/Jscript","classpath:portal/portalzcm");
    source.setDefaultEncoding("UTF-8");
//    source.setFallbackToSystemLocale(false);
    return source;
  }


  @Autowired
  private ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
    ThreadLocalMap.setObjectMapper(objectMapper);
  }
}