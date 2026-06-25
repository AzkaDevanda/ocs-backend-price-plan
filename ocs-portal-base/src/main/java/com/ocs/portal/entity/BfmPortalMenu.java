package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import com.ocs.portal.entity.embeddable.BfmPortalMenuId;

@Entity
@Table(name = "BFM_PORTAL_MENU", schema = "POT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BfmPortalMenu {
    @EmbeddedId
    private BfmPortalMenuId id;

    @Column(name = "TYPE", length = 1)
    private String type;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "STATE", length = 1, nullable = false)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "SP_ID")
    private Long spId;
}
