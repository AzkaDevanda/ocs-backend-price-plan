package com.sts.sinorita.dto.request.priceplan.trigger;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BwfSysActionDto {

    @Schema(description = "System action name", example = "GetB2BCallType")
    private String sysActionName;

    @Schema(description = "Comments or remarks", example = "sys action remarks")
    private String comments;

    @Schema(description = "Service provider ID", example = "0")
    private Integer spId;

    @Schema(description = "Template script ID", example = "302")
    private Integer scriptTempletId;

    @Schema(description = "Script page content (XML)",
            example = "<Properties><Property id=\\\"fee\\\" type=\\\"TextBox\\\" name=\\\"fee\\\" displayName=\\\"fee\\\" groupDesc=\\\"\\\" nullable=\\\"false\\\" comments=\\\"\\\" defaultValue=\\\"\\\" dataType=\\\"F\\\" minValue=\\\"\\\" maxValue=\\\"\\\" minLength=\\\"\\\" maxLength=\\\"\\\" dateType=\\\"\\\" initialParams=\\\"\\\"/><value><group name=\\\"\\\"><item id=\\\"fee\\\" type=\\\"TextBox\\\" dataType=\\\"F\\\" value=\\\"666\\\"/></group></value></Properties>")
    private String scriptPage;

    @Schema(description = "Extended script content (usually Groovy or Python)",
            example = "def main(r):\\n    fee = 666\\n    fee = fee * 100000\\n    r.event.SetAttrList(42,fee)\\n    r.event.SetAttrList(916,'DEDUCT_ACCT_RES:97-83-40-100-1')\\n    r.SetResult(0)")
    private String extScript;

}
