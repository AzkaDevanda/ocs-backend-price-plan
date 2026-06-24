package com.sts.sinorita.svc.role.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class StaffParamDto extends StaffExDto {
    private static final long serialVersionUID = -1436152289645113556L;

    private List<AttrDto> attrDataList;

    private String lockedState;

    private List<LinkedHashMap> attrList;

    private String ipLimit;

    private LocalDate userExpDate;

    private boolean nullAble;
}
