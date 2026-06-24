package com.sts.sinorita.dto.response.trigger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyParamsIdDto {
    private Integer notifyParamsId;
    private String notifyName;
    private Character notifyType;
    private Integer notifyId;
    private String comments;
    private LocalDate effDate;
    private LocalDate expDate;
    private Integer spId;
}
