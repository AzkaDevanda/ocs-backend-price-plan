package com.ocs.portal.mapper.offer;

import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;

import com.ocs.portal.dto.request.common.AttrApplyCatgDto;
import com.ocs.portal.dto.request.offer.commonoffer.AttrApplyChannelDto;
import com.ocs.portal.dto.request.offer.commonoffer.AttrCatgDto;
import com.ocs.portal.dto.request.offer.commonoffer.ContactChanelDto;
import com.ocs.portal.dto.request.offer.commonoffer.PricePlanByIdDto;
import com.ocs.portal.dto.response.commonoffer.LifeCycleTypeResponseDto;
import com.ocs.portal.dto.response.commonoffer.Offer4ReConfResponseDto;
import com.ocs.portal.dto.response.offercatgmem.IndepOfferListByCatgIdResponseDto;
import com.ocs.portal.projection.offer.commonoffer.*;
import com.ocs.portal.projection.offer.offercatgmem.IndepOfferListByCatgIdProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;



@Mapper(componentModel = "spring")
public interface CommonOfferMapper {
  // ObjectMapper objectMapper = new ObjectMapper();

  Offer4ReConfResponseDto toDto(Offer4ReConfProjection projection);

  @Mapping(source = "extAttr", target = "extAttr", qualifiedByName = "clobToString")
  LifeCycleTypeResponseDto toLifeCycleTypeResponseDto(LifeCycleTypeProjection projection);

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

  IndepOfferListByCatgIdResponseDto toIndepOfferListByCatgIdResponseDto(IndepOfferListByCatgIdProjection projection);


  // @Named("clobToJsonNode")
  // static JsonNode clobToJsonNode(Clob clob) {
  //   if (clob == null) return null;

  //   try (Reader reader = clob.getCharacterStream()) {
  //     StringBuilder sb = new StringBuilder();
  //     char[] buffer = new char[2048];
  //     int len;
  //     while ((len = reader.read(buffer)) != -1) {
  //       sb.append(buffer, 0, len);
  //     }
  //     return objectMapper.readTree(sb.toString());
  //   } catch (Exception e) {
  //     throw new RuntimeException("Failed to convert CLOB to JsonNode", e);
  //   }
  // }

  PricePlanByIdDto dto(PricePlanByIdProjection projection);
  AttrCatgDto dto2(AttrCatgProjection projection2);
  ContactChanelDto dto3(ContactChanelProjection projection3);
  AttrApplyCatgDto dto4(AttrApplyCatgProjection projection4);
  AttrApplyChannelDto dto5(AttrApplyChannelProjection projection5);
}
