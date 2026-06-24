package com.sts.sinorita.svc.role.repository.custom;

import com.sts.sinorita.svc.role.dto.response.StaffDto;

import java.util.List;
import java.util.Map;

public interface BfmStaffCustomRepository {
    Map<String, Object> getCurrentValueInAttrTable(String tableName, String keyName, Object keyValue, String attrCode);
    void updateColValue(String tableName, String colName, Object colValue, String keyName, Object keyValue);
    void updateStaff(StaffDto dto);
    void deleteAttrExtValue(String tableName, String keyName, Object keyValue, String attrCode);
    void insertAttrExtValue(String tableName, String keyName, Object keyValue,
                       String attrCode, String attrName, String attrValue);
    void updateAttrExtValue(String tableName, String keyName, Object keyValue,
                            String attrCode, String attrValue);

    List<Map<String, Object>> getCurrentValueInAttrTableList(String tableName, String keyName, Object keyValue, String attrCode);

}
