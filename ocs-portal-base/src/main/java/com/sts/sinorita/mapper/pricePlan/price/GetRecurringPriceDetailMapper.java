package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.GetDetailBenefitPriceProjection;
import com.sts.sinorita.dto.response.priceVer.GetRecurringPriceDetailResponseDto;
import com.sts.sinorita.projection.pricePlan.price.GetRecurringPriceDetailProjection;
import com.sts.sinorita.utils.LobMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {LobMapper.class})
public abstract class GetRecurringPriceDetailMapper {

    public GetRecurringPriceDetailResponseDto toDtoManual(GetRecurringPriceDetailProjection projection) {
        if (projection == null) return null;

        GetRecurringPriceDetailResponseDto dto = GetRecurringPriceDetailResponseDto.builder()
                .priceId(projection.getPriceId())
                .priceName(projection.getPriceName())
                .effDate(projection.getEffDate())
                .expDate(projection.getExpDate())
                .valueString(projection.getValueString())
                .acctItemTypeId(projection.getAcctItemTypeId())
                .acctItemTypeName(projection.getAcctItemTypeName())
                .calculateUnit(projection.getCalculateUnit())
                .comments(projection.getComments())
                .ratePrecision(projection.getRatePrecision())
                .calcPrecision(projection.getCalcPrecision())
                .creditLimit(projection.getCreditLimit() != null ? projection.getCreditLimit().longValue() : null)
                .priority(projection.getPriority())
                .payIndicator(projection.getPayIndicator())
                .scriptTempletId(projection.getScriptTempletId())
                .ruleScript(projection.getRuleScript())
                .ruleComments(projection.getRuleComments())
                .param(projection.getParam())
                .scriptPage(convertBytesToString(projection.getScriptPage()))
                .configType(projection.getConfigType())

                .build();

        // panggil manual mapping param
        mapParamFields(projection, dto);

        return dto;
    }

    private void mapParamFields(GetRecurringPriceDetailProjection projection,
                                GetRecurringPriceDetailResponseDto dto) {
        String param = projection.getParam();
        if (param == null || param.isEmpty()) {
            return;
        }

        Map<String, String> paramMap = Arrays.stream(param.split(";"))
                .map(entry -> entry.split("=", 2))
                .filter(parts -> parts.length == 2 && !parts[0].isBlank())
                .collect(Collectors.toMap(
                        parts -> parts[0].trim().toUpperCase(),
                        parts -> parts[1].replace("'", "").trim()
                ));
        dto.setNewConnection(paramMap.getOrDefault("NEWCONNECTION", ""));
        dto.setTermination(paramMap.getOrDefault("TERMINATION", ""));
        dto.setNormal(paramMap.getOrDefault("NORMAL", ""));
        dto.setInAdvance(paramMap.getOrDefault("INADVANCE", ""));
        dto.setPriceByDay(paramMap.getOrDefault("PRICEBYDAY", ""));
        dto.setPriceByCycle(paramMap.getOrDefault("PRICEBYCYCLE", ""));
        dto.setAmount(paramMap.getOrDefault("AMOUNT", ""));
        dto.setRoundMode(Integer.valueOf(paramMap.getOrDefault("ROUNDMODE", "")));
        // set rpPriceUnit berdasarkan PriceByDay
        String priceByDay = dto.getPriceByDay();
        if ("-1".equals(priceByDay)) {
            dto.setRpPriceUnit('C');
        } else {
            dto.setRpPriceUnit('D');
        }
    }

    private String convertBytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        return new String(bytes, StandardCharsets.UTF_8); // ganti charset kalau bukan UTF-8
    }

}
