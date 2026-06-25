package com.ocs.portal.projection.accountConfig;

import java.time.LocalDate;
import java.util.Date;

public interface QryBankProjection {
    Integer getBankId();
    Integer getParentId();
    String getBankName();
    String getComments();
    String getBankCode();
    String getState();
    LocalDate getStateDate();
    Integer getSpId();
    String getDirectDebitFlag();
    String getBic();
    String getIbanFormat();
    Integer getChild();
}
