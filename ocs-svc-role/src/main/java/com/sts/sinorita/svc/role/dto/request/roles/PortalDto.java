package com.sts.sinorita.svc.role.dto.request.roles;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PortalDto {
    private static final long serialVersionUID = -5815823987150942160L;

    private Long portalId;

    @NotNull(message = "portalName not null")
    @Size(max = 255, message = "portalName max 255 ")
    private String portalName;

    private String iconUrl;

    private String state;

    private LocalDate stateDate;

    private Long contactChannelId;

    private String channelType;

    @Size(max = 255, message = "url max 255 ")
    private String url;

    private String indexName;

    private String comments;

    private Long layoutId;

    private Long type = Long.valueOf(0L);

    private String partyName;

    private Long keyId;

    private Long partyId;

    private String extraUrl;

    private Long spId;
}
