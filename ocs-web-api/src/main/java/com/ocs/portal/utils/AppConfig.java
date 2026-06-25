package com.ocs.portal.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class AppConfig {

    @Value("${spring.messages.basename:/apps}")
    private String basePath;

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages",
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
                "classpath:portal/Jscript");
//        messageSource.setBasenames(
//                "file:" + basePath + "/resources/account/AccountRes",
//
//                "file:" + basePath + "/resources/billingcare/BcApplicationRes",
//                "file:" + basePath + "/resources/billingcare/BcCommonRes",
//                "file:" + basePath + "/resources/billingcare/BcContactRes",
//                "file:" + basePath + "/resources/billingcare/BcDomainRes",
//
//                "file:" + basePath + "/resources/bizCommon/bizCommonRes",
//
//                "file:" + basePath + "/resources/bmc/BmcRes",
//
//                "file:" + basePath + "/resources/bportal/BPortalRes",
//
//                "file:" + basePath + "/resources/cc/CCRes",
//
//                "file:" + basePath + "/resources/comResource/account/AccountRes",
//                "file:" + basePath + "/resources/comResource/bfm/BfmRes",
//                "file:" + basePath + "/resources/comResource/billingcare/BcApplicationRes",
//                "file:" + basePath + "/resources/comResource/billingcare/BcCommonRes",
//                "file:" + basePath + "/resources/comResource/billingcare/BcContactRes",
//                "file:" + basePath + "/resources/comResource/billingcare/BcDomainRes",
//                "file:" + basePath + "/resources/comResource/bizCommon/bizCommonRes",
//                "file:" + basePath + "/resources/comResource/bmc/BmcRes",
//                "file:" + basePath + "/resources/comResource/bportal/BPortalRes",
//                "file:" + basePath + "/resources/comResource/busiorder/BusiorderRes",
//                "file:" + basePath + "/resources/comResource/cc/CCRes",
//                "file:" + basePath + "/resources/comResource/contact/ContactRes",
//                "file:" + basePath + "/resources/comResource/doc/docRes",
//                "file:" + basePath + "/resources/comResource/domainframe/DomainFrameRes",
//                "file:" + basePath + "/resources/comResource/evoucher/EvoucherRes",
//                "file:" + basePath + "/resources/comResource/jobservermgr/JobServerManager",
//                "file:" + basePath + "/resources/comResource/journal/JournalRes",
//                "file:" + basePath + "/resources/comResource/mdr/MdrRes",
//                "file:" + basePath + "/resources/comResource/ms/MsRes",
//                "file:" + basePath + "/resources/comResource/msgbt/MsgBtRes",
//                "file:" + basePath + "/resources/comResource/orange/OrangeBillingRes",
//                "file:" + basePath + "/resources/comResource/orange/OrangeCCRes",
//                "file:" + basePath + "/resources/comResource/prov/ProvRes",
//                "file:" + basePath + "/resources/comResource/publicrule/PublicRuleRes",
//                "file:" + basePath + "/resources/comResource/stafforg/StaffOrgRes",
//                "file:" + basePath + "/resources/comResource/sysmgt/SysmgrRes",
//                "file:" + basePath + "/resources/comResource/uip/UipRes",
//                "file:" + basePath + "/resources/comResource/userrole/UserRoleRes",
//                "file:" + basePath + "/resources/comResource/CommonRes",
//
//                "file:" + basePath + "/resources/comResourceCus/account/AccountRes",
//                "file:" + basePath + "/resources/comResourceCus/bizcommon/bizCommonRes",
//                "file:" + basePath + "/resources/comResourceCus/bizcommon/BusinessCommon",
//                "file:" + basePath + "/resources/comResourceCus/cc/CCRes",
//
//                "file:" + basePath + "/resources/contact/ContactRes",
//
//                "file:" + basePath + "/resources/doc/docRes",
//
//                "file:" + basePath + "/resources/domainframe/DomainFrameRes",
//
//                "file:" + basePath + "/resources/evoucher/EvoucherRes",
//
//                "file:" + basePath + "/resources/flexResource/bfmbusiness/BusiOrder",
//                "file:" + basePath + "/resources/flexResource/bfmbusiness/MsgBt",
//                "file:" + basePath + "/resources/flexResource/bfmbusiness/PublicRule",
//                "file:" + basePath + "/resources/flexResource/bfmcommon/Config",
//                "file:" + basePath + "/resources/flexResource/bfmcommon/Memo",
//                "file:" + basePath + "/resources/flexResource/bfmcommon/Config",
//                "file:" + basePath + "/resources/flexResource/bfmportal/Busifrm",
//                "file:" + basePath + "/resources/flexResource/billing/Billing",
//                "file:" + basePath + "/resources/flexResource/bizcommon/BusinessCommon",
//                "file:" + basePath + "/resources/flexResource/bportal/BPortal",
//                "file:" + basePath + "/resources/flexResource/contact/CustContact",
//                "file:" + basePath + "/resources/flexResource/corelib/corelib",
//                "file:" + basePath + "/resources/flexResource/customercare/CustomerCare",
//                "file:" + basePath + "/resources/flexResource/customreport/Report",
//                "file:" + basePath + "/resources/flexResource/default/Common",
//                "file:" + basePath + "/resources/flexResource/etopup/etopup",
//                "file:" + basePath + "/resources/flexResource/jobservermgr/JobServerMgr",
//                "file:" + basePath + "/resources/flexResource/journal/journal",
//                "file:" + basePath + "/resources/flexResource/mvno/MVNO",
//                "file:" + basePath + "/resources/flexResource/RichEditor/RichEditor",
//                "file:" + basePath + "/resources/flexResource/stafforg/StaffOrg",
//                "file:" + basePath + "/resources/flexResource/userrole/UserRole",
//
//                "file:" + basePath + "/resources/flexResourceCus/billing/Billing",
//                "file:" + basePath + "/resources/flexResourceCus/BusinessCommon/BusinessCommon",
//                "file:" + basePath + "/resources/flexResourceCus/customercare/CustomerCare",
//                "file:" + basePath + "/resources/flexResourceCus/provisioning/Prov_Project",
//
//                "file:" + basePath + "/resources/gloResource/contact/Global",
//                "file:" + basePath + "/resources/gloResource/portal/Global",
//
//                "file:" + basePath + "/resources/journal/JournalRes",
//                "file:" + basePath + "/resources/orange/OrangeBillingRes",
//                "file:" + basePath + "/resources/portal/Jscript");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
