package com.ocs.portal.dto.request.offer.commonoffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactChanelDto {
    private Integer contactChannelId;
    private String contactChannelName;
    private String spId;
    private String comments;
    private String channelType;
    private String channelTypeName;
}