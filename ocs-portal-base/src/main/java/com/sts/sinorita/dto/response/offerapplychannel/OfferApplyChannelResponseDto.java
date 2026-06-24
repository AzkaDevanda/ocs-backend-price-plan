package com.sts.sinorita.dto.response.offerapplychannel;

import lombok.Data;

@Data
public class OfferApplyChannelResponseDto {
    private Integer offerId;

    private Integer ContactChannelId;

    private Integer spId;

    private Character excludeFlag;

    private Integer channelType;

    private String contactChannelName;

    private String conditionName;

    private String comments;
}
