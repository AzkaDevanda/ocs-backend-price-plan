package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.request.accountConfig.GstTypeDto;
import com.sts.sinorita.projection.acct.GstTypeProject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GstTypeMapper {
    GstTypeDto gstTypeDto(GstTypeProject gstTypeProject);
}
