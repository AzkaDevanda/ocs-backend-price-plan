package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class BcMemberHisId implements Serializable {

    @Column(name = "BC_MEMBER_ID", nullable = false)
    private Long bcMemberId;

    @Column(name = "SEQ", nullable = false)
    private Long seq;
}
