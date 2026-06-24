package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.request.priceplan.trigger.BwfActionDto;
import com.sts.sinorita.dto.request.priceplan.trigger.BwfCondDto;
import com.sts.sinorita.dto.request.priceplan.trigger.BwfSysActionDto;
import com.sts.sinorita.dto.response.BwfStepResponse;
import com.sts.sinorita.entity.BwfAction;
import com.sts.sinorita.entity.BwfStep;
import com.sts.sinorita.entity.BwfSysAction;
import com.sts.sinorita.projection.trigger.FindBwfCondGroupByStepIdProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BwfMapper {

    default Integer parseInt(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    BwfStepResponse toBwfStepResponse(BwfStep entity);

    @Mapping(target = "reAttr", expression = "java(parseInt(projection.getReAttr()))")
//    @Mapping(target = "rReAttr", expression = "java(parseInt(projection.getRReAttr()))")
    List<BwfCondDto> toBwfCondDto(List<FindBwfCondGroupByStepIdProjection> list);

    List<BwfActionDto> toBwfActionDtoList(List<BwfAction> list);

    @Mapping(source = "scriptPage", target = "scriptPage")
    BwfSysActionDto toBwfSysAction(BwfSysAction list);

    // tambahkan konversi byte[] → String
    default String map(byte[] value) {
        return value != null ? new String(value, java.nio.charset.StandardCharsets.UTF_8) : null;
    }

    // kalau perlu balikannya juga (String → byte[])
    default byte[] map(String value) {
        return value != null ? value.getBytes(java.nio.charset.StandardCharsets.UTF_8) : null;
    }
}
