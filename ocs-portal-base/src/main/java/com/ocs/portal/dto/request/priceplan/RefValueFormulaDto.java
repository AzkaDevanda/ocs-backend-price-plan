package com.ocs.portal.dto.request.priceplan;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RefValueFormulaDto {
    private List<Element> elementList = new ArrayList<>();

    public List<Element> getElementList() {
        return this.elementList;
    }

    public void setElementList(List<Element> elementList) {
        this.elementList = elementList;
    }

    public List<RefValueExtendDto> getFirstLevelRefValueListInFormula() {
        List<RefValueExtendDto> refValueList = new ArrayList<>();
        for (Element ele : this.elementList) {
            if ("2".equals(ele.getElementType()))
                refValueList.add(ele.getRefValueExtendDto());
        }
        return refValueList;
    }

    public List<RefValueExtendDto> getAllLevelRefValueInFormula() {
        List<RefValueExtendDto> refValueList = new ArrayList<>();
        RefValueExtendDto refVal = null;
        for (Element ele : this.elementList) {
            if ("2".equals(ele.getElementType())) {
                refVal = ele.getRefValueExtendDto();
                if ("4".equals(refVal.getRefValueType()) && refVal.getFormula() != null)
                    refValueList.addAll(refVal.getFormula().getAllLevelRefValueInFormula());
                refValueList.add(ele.getRefValueExtendDto());
            }
        }
        return refValueList;
    }

    public List<RefValueExtendDto> getAtomicRefValueInFormula() {
        List<RefValueExtendDto> refValueList = new ArrayList<>();
        RefValueExtendDto refVal = null;
        for (Element ele : this.elementList) {
            if ("2".equals(ele.getElementType())) {
                refVal = ele.getRefValueExtendDto();
                if ("4".equals(refVal.getRefValueType()) && refVal.getFormula() != null) {
                    refValueList.addAll(refVal.getFormula().getAtomicRefValueInFormula());
                    continue;
                }
                refValueList.add(ele.getRefValueExtendDto());
            }
        }
        return refValueList;
    }

    public String getRefValueFormulaStr() {
        StringBuilder sb = new StringBuilder();
        RefValueExtendDto refVal = null;
        RefValueFormulaDto formula = null;
        for (Element ele : this.elementList) {
            if ("1".equals(ele.getElementType())) {
                sb.append(ele.getOperator());
                continue;
            }
            if ("2".equals(ele.getElementType())) {
                refVal = ele.getRefValueExtendDto();
                if ("4".equals(refVal.getRefValueType())) {
                    if (formula != null) {
                        formula = refVal.getFormula();
                        sb.append(formula.getRefValueFormulaStr());
                    }
                    continue;
                }
                sb.append(refVal.getRefValueId());
            }
        }
        return sb.toString();
    }

    public void addOperand(RefValueExtendDto refValue) {
        if (refValue == null)
            return;
        Element ele = new Element();
        ele.setElementType("2");
        ele.setRefValueExtendDto(refValue);
        this.elementList.add(ele);
    }

    public void addOperator(String operator) {
        if (!checkOperator(operator))
            return;
        Element ele = new Element();
        ele.setElementType("1");
        ele.setOperator(operator);
        this.elementList.add(ele);
    }

    private boolean checkOperator(String operator) {
        boolean passed = false;
        if (operator.isEmpty())
            return passed;
        if ("+".equals(operator) || "-".equals(operator) || "*".equals(operator) || "/".equals(operator) || "(".equals(operator) || ")"
                .equals(operator))
            passed = true;
        return passed;
    }
}
