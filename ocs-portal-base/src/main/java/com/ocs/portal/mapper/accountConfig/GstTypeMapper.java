package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.request.accountConfig.GstTypeDto;
import com.ocs.portal.projection.acct.GstTypeProject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GstTypeMapper {
    GstTypeDto gstTypeDto(GstTypeProject gstTypeProject);
}
