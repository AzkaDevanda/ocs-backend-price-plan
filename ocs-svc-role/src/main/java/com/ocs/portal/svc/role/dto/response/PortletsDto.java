package com.ocs.portal.svc.role.dto.response;

import com.ocs.portal.svc.role.dto.request.roles.PrivDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PortletsDto extends PrivDto {
    private static final long serialVersionUID = -2547809054615751980L;

    @NotNull(message = "portletName not null")
    @Size(max = 60, message = "portletName max 60 ")
    private String portletName;

    private Long portalId;

    private Long menuId;

    private String refreshable;

    private String showHeader;

    private String collapsable;

    private String settable;

    private Long portletId;

    @NotNull
    private Long typeId;

    private Long type = Long.valueOf(0L);

    @NotNull(message = "defaultTitle not null")
    @Size(max = 255, message = "defaultTitle max 255 ")
    private String defaultTitle;

    @NotNull(message = "isPublic not null")
    private String isPublic;

    private String maxable;

    private String closable;

    @Size(max = 255, message = "icon max 255 ")
    private String icon;

    @Size(max = 1024, message = "defaultParam max 1024 ")
    private String defaultParam;

    private String defaultWidth;

    private String defaultHeight;

    private String viewType;

    private String isDrawable;
}
