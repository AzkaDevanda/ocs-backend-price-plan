package com.sts.sinorita.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sts.sinorita.dto.request.priceplan.discount.TabDpCondGrpDtListRequestDto;
import com.sts.sinorita.dto.request.priceplan.discount.TabDpCondGrpRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisctObjRequestDto {
    @JsonIgnore
    private Integer disctObjId;
    @JsonIgnore
    private Integer tabDpCondGrpId;
    @JsonIgnore
    private Integer offerVerId;

    private Integer objIdentityId;
    private Integer gstSeq;
    private String objectName;
    private Character objectType;
    private String memberAlias;
    private List<MappingAccountItemType> mappingAccountItemTypes;
    private List<TabDpCondGrpDtListRequestDto> InsertDiscountConditionGroup;
    private Character dpRefCondType;
    private String tabDpCondGrpName;
}

