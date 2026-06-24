package com.sts.sinorita.projection.offer.offerapplychannel;

public interface OfferApplyChannelProjection {
    Integer getOfferId();

    Integer getContactChannelId();

    Integer getSpId();

    Character getExcludeFlag();

    Integer getChannelType();

    String getContactChannelName();

    String getConditionName();

    String getComments();
}
