package com.sts.sinorita.svc.role.dto.request;

import com.sts.sinorita.svc.role.dto.response.StaffDto;
import lombok.Data;

import java.util.List;

@Data
public class StaffExDto extends StaffDto {
    private static final long serialVersionUID = -2536569976912398596L;

    private Long spId;

    private Long srcId;

    private List<Long> orgIds;

}
