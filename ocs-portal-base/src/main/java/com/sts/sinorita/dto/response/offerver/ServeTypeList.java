package com.sts.sinorita.dto.response.offerver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServeTypeList {
    private Integer servType;
    private String servTypeName;
    private Character networkType;
    private Character catgType;
    private Character paidFlag;
    private String stdCode;
    private String networkTypeName;

    public ServeTypeList(Integer servType, String servTypeName, Character networkType, Character catgType,
            Character paidFlag, String stdCode, String networkTypeName) {
        this.servType = servType;
        this.servTypeName = servTypeName;
        this.networkType = networkType;
        this.catgType = catgType;
        this.paidFlag = paidFlag;
        this.stdCode = stdCode;
        this.networkTypeName = networkTypeName;
    }
}
