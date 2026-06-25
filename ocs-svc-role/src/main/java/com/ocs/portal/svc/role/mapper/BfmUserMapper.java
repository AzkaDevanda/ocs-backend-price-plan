package com.ocs.portal.svc.role.mapper;

import com.ocs.portal.entity.BfmUser;
import com.ocs.portal.entity.BfmUserPriv;
import com.ocs.portal.entity.embeddable.BfmUserPrivId;
import com.ocs.portal.svc.role.dto.response.ProdUserPrivDto;
import com.ocs.portal.svc.role.dto.response.UserDto;
import com.ocs.portal.svc.role.dto.response.UserPrivDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BfmUserMapper {

    @Mapping(target = "userId", ignore = true)
    void updateEntityFromDto(UserDto dto, @MappingTarget BfmUser entity);


    @Mapping(target = "id", source = ".", qualifiedByName = "toEmbeddedId")
    BfmUserPriv toBfmUserPriv(ProdUserPrivDto dto);

    List<BfmUserPriv> toBfmUserPrivList(List<ProdUserPrivDto> list);

    @Named("toEmbeddedId")
    default BfmUserPrivId toEmbeddedId(ProdUserPrivDto dto) {
        BfmUserPrivId id = new BfmUserPrivId();
        id.setUserId(dto.getUserId());
        id.setPrivId(dto.getPrivId());
        return id;
    }

    @Mapping(target = "id", source = ".", qualifiedByName = "toEmbeddedIds")
    BfmUserPriv toBfmUserPrivs(UserPrivDto dto);
    List<BfmUserPriv> toBfmUserPrivsList(List<UserPrivDto> list);
    @Named("toEmbeddedIds")
    default BfmUserPrivId toEmbeddedIds(UserPrivDto dto) {
        BfmUserPrivId id = new BfmUserPrivId();
        id.setUserId(dto.getUserId());
        id.setPrivId(dto.getPrivId());
        return id;
    }
}
