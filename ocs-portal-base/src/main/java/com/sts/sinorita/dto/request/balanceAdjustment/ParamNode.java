package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ParamNode {
  protected String name = "";

  protected String type = "S";

  protected Map attrMap = new HashMap();

  public static ParamNode parseParamExp (String paramExp) {
    ParamNode paramNode = new ParamNode();
    if (paramExp.indexOf(":") >= 0) {
      paramNode.name = paramExp.substring(0, paramExp.indexOf(":"));
      paramNode.type = paramExp.substring(paramExp.indexOf(":") + 1, paramExp.indexOf(":") + 2);
    } else {
      paramNode.name = paramExp;
    }
    while (paramExp != null && paramExp.indexOf(",") >= 0) {
      paramExp = paramExp.substring(paramExp.indexOf(",") + 1);
      String strOper = "";
      if (paramExp.indexOf(",") >= 0) {
        strOper = paramExp.substring(0, paramExp.indexOf(","));
      } else {
        strOper = paramExp;
      }
      String key = strOper.substring(0, strOper.indexOf("="));
      String value = strOper.substring(strOper.indexOf("=") + 1);
      paramNode.attrMap.put(key, value);
    }
    return paramNode;
  }

}
