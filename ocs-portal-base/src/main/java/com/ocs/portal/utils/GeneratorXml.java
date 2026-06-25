package com.ocs.portal.utils;

import com.ocs.portal.dto.response.trigger.ScriptParamValueDto;
import com.ocs.portal.entity.ScriptTemplet;
import com.ocs.portal.repository.RefValueRepository;
import com.ocs.portal.repository.ScriptTempletRepository;
import com.ocs.portal.validation.ValidationHandler;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Component
public class GeneratorXml {

    @Autowired
    ScriptTempletRepository scriptTempletRepository;
    @Autowired
    LoggerPortal logger;

    @Autowired
    RefValueRepository refValueRepository;

    public byte[] getXmlScriptPage(Integer scriptTemplateId, String jsonStr) throws DocumentException {
        logger.loggerInfo(GeneratorXml.class, "getXmlScriptPage");
        JSONArray groupDicts = new JSONArray(jsonStr); //(dict.getString("JSON_SCRIPT_PAGE"));

        Optional<ScriptTemplet> scriptTemplet = scriptTempletRepository.findScriptTempletByScriptTempletId(scriptTemplateId);
        if (scriptTemplet.isEmpty()) {

            throw new ValidationHandler("Script templete not found");
        }
        byte[] templateContent = scriptTemplet.get().getTempletContent();

        String templateContentStr = new String(templateContent);

        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        Document doc = reader.read(new StringReader(templateContentStr));

        Element root = (Element) doc.getRootElement().selectSingleNode("//Properties");
        Element valueElement = root.addElement("value");

        Element groupElement = null;

        for (int i = 0; i < groupDicts.length(); i++) {

            JSONObject jsonObject = groupDicts.getJSONObject(i);
            String key = (String) jsonObject.keys().next();

            groupElement = valueElement.addElement("group").addAttribute("name", key);

            JSONObject itemDict = jsonObject.getJSONObject(key);
            Iterator<String> itemNames = itemDict.keys();


            while (itemNames.hasNext()) {

                String itemName = itemNames.next();

                Element metaElement = (Element) root.selectSingleNode("//Property[@id='" + itemName + "']");

                if (metaElement != null) {

                    Element itemElement = groupElement.addElement("item");

                    itemElement.addAttribute("id", itemName);
                    itemElement.addAttribute("type", metaElement.attributeValue("type"));
                    itemElement.addAttribute("dataType", metaElement.attributeValue("dataType"));
                    itemElement.addAttribute("value", itemDict.getString(itemName));
                    if (StringUtils.isEmpty(itemDict.getString(itemName))) {
                        itemElement.addAttribute("value", metaElement.attributeValue("defaultValue"));
                    }

                }
            }
        }
        return root.asXML().getBytes();
    }


    public String getNewScriptRule(String ruleScript , byte[] scriptPage) {

        try {
            String xmlSource = new String(scriptPage, "UTF-8");
            Document doc = XMLUtils.createDocument(xmlSource, false, "UTF-8");
            Element root = doc.getRootElement();
            Element value = XMLUtils.child(root, "value");
            List<Element> groups = XMLUtils.children(value, "group");
            List<Element> items = null;
            List<ScriptParamValueDto> paramList = new ArrayList<>();

            for (Element group : groups) {
                for (Element item : XMLUtils.children(group, "item")) {
                    ScriptParamValueDto paramValue = new ScriptParamValueDto();
                    paramValue.setParamId(item.attributeValue("id"));
                    paramValue.setType(item.attributeValue("type"));
                    paramValue.setDataType(item.attributeValue("dataType"));
                    paramValue.setParamValue(item.attributeValue("value"));
                    paramList.add(paramValue);
                }
            }

            Map<String, String> valueMap = paramPreHandle(paramList);

            for (Map.Entry<String, String> entry : valueMap.entrySet()) {
                String key = entry.getKey().toString();
                String val = entry.getValue().toString();
                ruleScript = ruleScript.replace("&" + key + "&", val);
            }
        } catch (UnsupportedEncodingException var16) {
            logger.loggerInfo(GeneratorXml.class, "error :"+var16.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ruleScript;
    }

    private static Map<String, String> paramPreHandle(List<ScriptParamValueDto> paramList) {
        Map<String, String> valueMap = new HashMap<>();
        String valueStr = "";

        for (ScriptParamValueDto param : paramList) {
            if ("DateTimePicker".equals(param.getType())) {
                valueStr = param.getParamValue().replace("-", "");
                valueStr = valueStr.replace(":", "");
                valueStr = valueStr.replace(" ", "");
                valueStr = "'" + valueStr + "'";
            } else if ("TextBox".equals(param.getType())) {
                if ("C".equals(param.getDataType())) {
                    valueStr = "'" + param.getParamValue() + "'";
                } else {
                    valueStr = param.getParamValue();
                }
            } else if ("TextArea".equals(param.getType())) {
                valueStr = "'" + param.getParamValue() + "'";
            } else if ("Reference".equals(param.getType())) {
                valueStr = "r.event.GetValueByRefID(" + param.getParamValue() + ")";
            } else {
                valueStr = param.getParamValue();
            }

            saveAndMergeParam(param, valueStr, valueMap);
        }

        return valueMap;
    }
    private static void saveAndMergeParam(ScriptParamValueDto param, String valueStr, Map<String, String> valueMap) {
        if (null == valueMap.get(param.getParamId())) {
            valueMap.put(param.getParamId(), valueStr);
        } else {
            String temp = valueMap.get(param.getParamId());
            if (!StringUtils.isNotEmpty(valueStr)) {
                valueStr = temp;
            } else if (!"DateTimePicker".equals(param.getType())
                    && !"TextArea".equals(param.getType())
                    && (!"TextBox".equals(param.getType()) || !"C".equals(param.getDataType()))) {
                valueStr = temp + "," + valueStr;
            } else {
                valueStr = temp.substring(0, temp.length() - 1) + "," + valueStr.substring(1, valueStr.length());
            }

            valueMap.put(param.getParamId(), valueStr);
        }
    }
}

 /* tidak dipakai
    public String addRefValueInRuleScript(String funPrefixParm, String ruleScriptParm,String scriptPageParam, String inputRefValueBoField,Integer offerVerId, Long priceId, Long ratePlanId) {

        String funPrefix = "";
        if (StringUtils.isEmpty(funPrefixParm)) {
            funPrefix = "r.event.";
        }

        String ruleScript = ruleScriptParm;// ("RULE_SCRIPT");
        String scriptPage = scriptPageParam; // dict.getString("SCRIPT_PAGE");
        if (!StringUtils.isEmpty(ruleScript)) {

            List<RefValue> refValueList = new ArrayList<>();
            refValueList = refValueRepository.findByOfferIdAndValueString(offerVerId,);
            if (refValueList != null && !refValueList.isEmpty()) {
//                RefValueManager refValueMgr = new RefValueManager();

                for (RefValue refValueBo : refValueList) {
//                    refValueMgr.addRefValueBo(refValueBo, priceId, ratePlanId);
                    ruleScript = replaceNewRefValueFun(ruleScript, "r.event.GetProrMonthFee(", ")", String.valueOf(refValueBo.getId()));
                    ruleScript = ruleScript.replace(
                            "&" + refValueBo.getValueString() + "&", "r.event.GetValueByRefID(" + String.valueOf(refValueBo.getId() + ")"
                            ));
                    if (StringUtils.isNotEmpty(scriptPage)) {
                        scriptPage = scriptPage.replace("#" + refValueBo.getValueString() + "#", String.valueOf(refValueBo.getId()));
                    }
                }

//                dict.set("RULE_SCRIPT", ruleScript);
//                dict.set("SCRIPT_PAGE", scriptPage);
            }

            if (StringUtils.isNotEmpty(ruleScript)) {
                //checkRuleScript(funPrefix, ruleScript);
            }

        }
        return ruleScript;
    }

        private static String replaceNewRefValueFun(String ruleScript, String startStr, String endStr, String targetStr)
        {
            if (!StringUtils.isEmpty(ruleScript) && ruleScript.indexOf(startStr) >= 0) {
                StringBuffer sb = new StringBuffer();
                int startIndex = ruleScript.indexOf(startStr) + startStr.length();
                String subFirstStr = ruleScript.substring(ruleScript.indexOf(startStr) + startStr.length(), ruleScript.length());
                String subSecondStr = subFirstStr.substring(0, subFirstStr.indexOf(endStr));
                int endIndex = startIndex + subSecondStr.length();
                if (endIndex < ruleScript.length()) {
                    sb.append(ruleScript.substring(0, startIndex)).append(targetStr).append(ruleScript.substring(endIndex, ruleScript.length()));
                    return sb.toString();
                } else {
                    return ruleScript;
                }
            } else {
                return ruleScript;
            }
        }


//        public static void checkRuleScript(String funPrefix, String ruleScript){
//
//            List<String> arrAttrFunc = getRuleScriptFunc();
//            if (!arrAttrFunc.isEmpty()) {
//                String lineScript = "";
//                String funName = "";
//                String[] arrScript = null;
//                if (ruleScript.indexOf("\r") != -1) {
//                    arrScript = ruleScript.split("\r");
//                } else if (ruleScript.indexOf("\n") != -1) {
//                    arrScript = ruleScript.split("\n");
//                }
//
//                for (int i = 0; i < arrScript.length; i++) {
//                    lineScript = arrScript[i];
//
//                    for (int j = 0; j < arrAttrFunc.size(); j++) {
//                        funName = arrAttrFunc.get(j);
//                        int funIndex = lineScript.indexOf(funName);
//                        if (funIndex != -1
//                                && "(".equals(lineScript.substring(funIndex + funName.length(), funIndex + funName.length() + 1))
//                                && !funPrefix.equals(lineScript.substring(funIndex - funPrefix.length(), funIndex))) {
//                            throw new ValidationHandler("funName");
//                            //ExceptionHandler.publish("S-PRD-50963", 0, funName, funPrefix);
//                        }
//                    }
//                }
//            }
//        }

//        private static List<String> getRuleScriptFunc(){
//            List<String> arrAttrFunc = new ArrayList<>();
//            DynamicDict pythonDict = new DynamicDict();
//            pythonDict.serviceName = "QryPythonSelect";
//            ServiceFlow.callService(pythonDict);
//            if (pythonDict != null && pythonDict.getBO("z_d_r") != null) {
//                List<DynamicDict> pythodSelectTable = pythonDict.getList("z_d_r");
//                if (pythodSelectTable != null && !pythodSelectTable.isEmpty()) {
//                    for (int i = 0; i < pythodSelectTable.size(); i++) {
//                        DynamicDict tableDict = pythodSelectTable.get(i);
//                        if (tableDict.getLong("SELECT_TYPE") != null && 1 == tableDict.getLong("SELECT_TYPE").intValue()) {
//                            arrAttrFunc.add(tableDict.getString("SELECT_NAME"));
//                        }
//                    }
//                }
//            }
//
//            arrAttrFunc.add("GetValueByRefID");
//            return arrAttrFunc;
//        }
    }
    /*
     public static void addRefValueInRuleScript(AdvanceTriggerRuleDto advanceTriggerRuleDto, String inputRefValueBoField, Long priceId, Long ratePlanId) {

        String funPrefix = "";
        if (StringUtils.isEmpty(advanceTriggerRuleDto.getFunPrefix())) {
            funPrefix = "r.event.";
        }

        String ruleScript = advanceTriggerRuleDto.getRuleScript();// ("RULE_SCRIPT");
        String scriptPage = advanceTriggerRuleDto.getScriptPage(); // dict.getString("SCRIPT_PAGE");
        if (!StringUtil.isEmpty(ruleScript)) {
            List<DynamicDict> refValueList = dict.getList(inputRefValueBoField);
            if (refValueList != null && !refValueList.isEmpty()) {
                RefValueManager refValueMgr = new RefValueManager();

                for (DynamicDict refValueBo : refValueList) {
                    refValueMgr.addRefValueBo(refValueBo, priceId, ratePlanId);
                    ruleScript = replaceNewRefValueFun(ruleScript, "r.event.GetProrMonthFee(", ")", refValueBo.getString("REF_VALUE_ID"));
                    ruleScript = ruleScript.replace(
                            "&" + refValueBo.getString("PYTHON_PARAM_NAME") + "&", "r.event.GetValueByRefID(" + refValueBo.getLong("REF_VALUE_ID") + ")"
                    );
                    if (StringUtil.isNotEmpty(scriptPage)) {
                        scriptPage = scriptPage.replace("#" + refValueBo.getString("PYTHON_PARAM_NAME") + "#", refValueBo.getString("REF_VALUE_ID"));
                    }
                }

                dict.set("RULE_SCRIPT", ruleScript);
                dict.set("SCRIPT_PAGE", scriptPage);
            }

            if (StringUtil.isNotEmpty(dict.getString("RULE_SCRIPT"))) {
                checkRuleScript(funPrefix, dict.getString("RULE_SCRIPT"));
            }
        }
    }
     */

//    }
