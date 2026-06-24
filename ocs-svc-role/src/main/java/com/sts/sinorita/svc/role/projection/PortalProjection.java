package com.sts.sinorita.svc.role.projection;

public interface PortalProjection {

    Long getPortalId();

    String getPortalName();

    String getIconUrl();

    Character getState();

    java.time.LocalDate getStateDate();

    Long getContactChannelId();

    Character getChannelType();

    String getUrl();

    String getIndexName();

    String getComments();

    Long getLayoutId();

    Long getType();

    String getPartyName();

    Long getKeyId();

    Long getPartyId();

    String getExtraUrl();
}
