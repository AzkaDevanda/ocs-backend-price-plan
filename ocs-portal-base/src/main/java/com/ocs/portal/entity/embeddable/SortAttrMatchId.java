package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class SortAttrMatchId {

    @Column(name = "SORT_ATTR_GROUP_ID", nullable = false)
    private Long sortAttrGroupId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;
}
