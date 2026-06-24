//package com.ocs.microservice.entity;
//
//import com.ztesoft.zsmart.core.jdbc.AbstractDto;
//import com.ztesoft.zsmart.core.utils.StringUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RefValueFormula extends AbstractDto {
//   private List<Element> elementList = new ArrayList<>();
//
//   public List<Element> getElementList() {
//      return this.elementList;
//   }
//
//   public void setElementList(List<Element> elementList) {
//      this.elementList = elementList;
//   }
//
//   public List<RefValue> getFirstLevelRefValueListInFormula() {
//      List<RefValue> refValueList = new ArrayList<>();
//
//      for (Element ele : this.elementList) {
//         if ("2".equals(ele.getElementType())) {
//            refValueList.add(ele.getRefValue());
//         }
//      }
//
//      return refValueList;
//   }
//
//   public List<RefValue> getAllLevelRefValueInFormula() {
//      List<RefValue> refValueList = new ArrayList<>();
//      RefValue refVal = null;
//
//      for (Element ele : this.elementList) {
//         if ("2".equals(ele.getElementType())) {
//            refVal = ele.getRefValue();
//            if ("4".equals(refVal.getRefValueType()) && refVal.getFormula() != null) {
//               refValueList.addAll(refVal.getFormula().getAllLevelRefValueInFormula());
//            }
//
//            refValueList.add(ele.getRefValue());
//         }
//      }
//
//      return refValueList;
//   }
//
//   public List<RefValue> getAtomicRefValueInFormula() {
//      List<RefValue> refValueList = new ArrayList<>();
//      RefValue refVal = null;
//
//      for (Element ele : this.elementList) {
//         if ("2".equals(ele.getElementType())) {
//            refVal = ele.getRefValue();
//            if ("4".equals(refVal.getRefValueType()) && refVal.getFormula() != null) {
//               refValueList.addAll(refVal.getFormula().getAtomicRefValueInFormula());
//            } else {
//               refValueList.add(ele.getRefValue());
//            }
//         }
//      }
//
//      return refValueList;
//   }
//
//   public String getRefValueFormulaStr() {
//      StringBuilder sb = new StringBuilder();
//      RefValue refVal = null;
//      RefValueFormula formula = null;
//
//      for (Element ele : this.elementList) {
//         if ("1".equals(ele.getElementType())) {
//            sb.append(ele.getOperator());
//         } else if ("2".equals(ele.getElementType())) {
//            refVal = ele.getRefValue();
//            if ("4".equals(refVal.getRefValueType())) {
//               if (formula != null) {
//                  formula = refVal.getFormula();
//                  sb.append(formula.getRefValueFormulaStr());
//               }
//            } else {
//               sb.append(refVal.getRefValueId());
//            }
//         }
//      }
//
//      return sb.toString();
//   }
//
//   public void addOperand(RefValue refValue) {
//      if (refValue != null) {
//         Element ele = new Element();
//         ele.setElementType("2");
//         ele.setRefValue(refValue);
//         this.elementList.add(ele);
//      }
//   }
//
//   public void addOperator(String operator) {
//      if (this.checkOperator(operator)) {
//         Element ele = new Element();
//         ele.setElementType("1");
//         ele.setOperator(operator);
//         this.elementList.add(ele);
//      }
//   }
//
//   private boolean checkOperator(String operator) {
//      boolean passed = false;
//      if (StringUtil.isEmpty(operator)) {
//         return passed;
//      } else {
//         if ("+".equals(operator) || "-".equals(operator) || "*".equals(operator) || "/".equals(operator) || "(".equals(operator) || ")".equals(operator)) {
//            passed = true;
//         }
//
//         return passed;
//      }
//   }
//}
