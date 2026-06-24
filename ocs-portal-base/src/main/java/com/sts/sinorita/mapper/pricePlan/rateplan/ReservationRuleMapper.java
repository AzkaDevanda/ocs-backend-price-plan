package com.sts.sinorita.mapper.pricePlan.rateplan;

import com.sts.sinorita.dto.request.ModRePricePlanDto;
import com.sts.sinorita.dto.request.RefValueToOfferVerDto;
import com.sts.sinorita.dto.request.priceplan.*;
import com.sts.sinorita.dto.response.rateplan.QryRePricePlanByReIdAndOfferVerIdDto;
import com.sts.sinorita.dto.response.rateplan.QryScriptTemplateDto;
import com.sts.sinorita.entity.*;
import com.sts.sinorita.mapper.CommonMapperHelper;
import com.sts.sinorita.projection.pricePlan.rateplan.QryRePricePlanByReIdAndOfferVerIdProjection;
import com.sts.sinorita.projection.pricePlan.rateplan.QryScriptTemplateProjection;
import org.mapstruct.*;
import org.mapstruct.Mapping;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

@Mapper(componentModel = "spring")
public interface ReservationRuleMapper {
    QryScriptTemplateDto qryScriptTemplatedto(QryScriptTemplateProjection qryScriptTemplateProjection);

    @Mapping(source = "scriptPage", target = "scriptPage", qualifiedByName = "blobToString")
    @Mapping(source = "ruleScript", target = "ruleScript", qualifiedByName = "clobToString")
    QryRePricePlanByReIdAndOfferVerIdDto qryRePricePlanByReIdAndOfferVerIddto(QryRePricePlanByReIdAndOfferVerIdProjection qryRePricePlanByReIdAndOfferVerIdProjection);

    RefValueExtendDto refValueExtendDto(RefValueDto refValueDto);

    SimpleParamDefineDto toDto(SimpleParamDefine entity);

    TableParamDefineDto toDto(TableParamDefine entity);
//    RefValueExtendDto refValue (ModRePricePlanDto modRePricePlanDto);


    @Mapping(target = "id", ignore = true)
    RefValue toEntity(RefValueDto refValueDto);

    @Mapping(target = "priceParamId", ignore = true)
    PriceParam toEntityPriceParam(PriceParamDto priceParamDto);

    @Mapping(target = "ratePlanParamId", ignore = true)
    RatePlanParam toEntityRatePlanParam(RatePlanParamDto ratePlanParamDto);

    @Mapping(target = "id", ignore = true)
    PricePlanParam toEntityPricePlanParam(PricePlanParamDto pricePlanParamDto);

    @Mapping(target = "id", ignore = true)
    RefValueToOfferVer toEntityRefValueOfferVer(RefValueToOfferVerDto refValueToOfferVerDto);

    @Mapping(target = "id.reId", ignore = true)
    @Mapping(target = "id.offerVerId", ignore = true)
    @Mapping(target = "scriptPage", source = "scriptPage", qualifiedByName = "stringToBytes")
    RePricePlan toEntityRePricePlan(ModRePricePlanDto modRePricePlanDto);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id.reId", ignore = true)
    @Mapping(target = "id.offerVerId", ignore = true)
    @Mapping(target = "scriptPage", source = "scriptPage", qualifiedByName = "stringToBytes")
    void updateEntityRePricePlan(ModRePricePlanDto modRePricePlanDto, @MappingTarget RePricePlan rePricePlan);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(PricePlanParamDto pricePlanParamDto, @MappingTarget PricePlanParam entity);

    //   Helper
    @Named("clobToString")
    static String clobToString(Clob clob) {
        if (clob == null) return null;

        try (Reader reader = clob.getCharacterStream(); StringWriter writer = new StringWriter()) {
            char[] buffer = new char[2048];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, len);
            }
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert CLOB to String", e);
        }
    }

    @Named("stringToClob")
    static Clob stringToClob(String value) {
        if (value == null) return null;

        try {
            return new SerialClob(value.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert String to CLOB", e);
        }
    }

    // ========================
    // 🧱 String ↔ byte[] (BLOB)
    // ========================
    @Named("stringToBytes")
    public static byte[] stringToBytes(String value) {
        return value != null ? value.getBytes(StandardCharsets.UTF_8) : null;
    }

    @Named("bytesToString")
    public static String bytesToString(byte[] value) {
        return value != null ? new String(value, StandardCharsets.UTF_8) : null;
    }


    @Named("blobToString")
    public static String blobToString(Blob blob) {
        if (blob == null) return null;

        try (
                InputStream is = blob.getBinaryStream();
                Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                StringWriter writer = new StringWriter()
        ) {
            char[] buffer = new char[2048];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, len);
            }
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert BLOB to String", e);
        }
    }

    @Named("stringToBlob")
    public static Blob stringToBlob(String value) {
        if (value == null) return null;

        try {
            return new SerialBlob(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert String to BLOB", e);
        }
    }
}
