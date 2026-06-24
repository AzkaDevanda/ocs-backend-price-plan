package com.sts.sinorita.dto.request.priceplan.discount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sts.sinorita.dto.request.DisctObjRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRequestDto {
    @JsonIgnore
    private Integer dpId;
    @JsonIgnore
    private Integer calcDisctObjId;
    @JsonIgnore
    private Integer refDisctObjId;
    @JsonIgnore
    private Integer applyDisctObjId;
    @JsonIgnore
    private Integer tabDpCondGroupId;


    private Integer offerVerId;
    private String discountName;
    @Schema(description = "DiscountType: E (Expression), T (Tabular)")
    private Character discountType;
    @Schema(description = "Promotion: Yes (4) No (1)")
    private Character promotion;
    private Integer resultAccountItemType;
    private Character distributeMethod;
    @Schema(description = "kalau DiscountType T, negativeFlag N, kalau DiscountType E, negativeFlag null")
    private Character negativeResult;
    @Schema(description = "yang bagian tabular ada tambahan field discount type")
    private Character tabDiscountType;
    private String remarks;

    private List<TabDpDtRequestDto> discountDetail;

    private List<TabDpCondGrpDtListRequestDto> InsertDiscountConditionGroup;

    private DpRuleRequestDto dpRule;

    private DisctObjRequestDto referenceObject;

    private DisctObjRequestDto calculationObject;

    private DisctObjRequestDto applyingObject;

    private Character dpRefCondType;
    private String tabDpCondGrpName;

}
