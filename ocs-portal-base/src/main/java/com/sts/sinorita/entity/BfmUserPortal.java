package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.BfmUserPortalId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BFM_USER_PORTAL", schema = "POT")
public class BfmUserPortal {

    @EmbeddedId
    private BfmUserPortalId id;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "CSS_PATH", length = 256)
    private String cssPath;

}
