package com.ocs.portal.svc.role.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PortalMenuDto implements Serializable {

        private static final long serialVersionUID = -1131914874288523402L;

        private Long seq;

        private Long portalId;

        private Long partyId;

        private Long parentId;

        private String type;

        private String state;

        private LocalDate stateDate;

        private Long spId;

        private String partyName;

        private String url;

        private String comments;
        private Long privId;
        private String privName;
}
