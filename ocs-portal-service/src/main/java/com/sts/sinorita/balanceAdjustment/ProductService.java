package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AttrDto;
import com.sts.sinorita.dto.request.balanceAdjustment.OfferAttrDto;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsPlanOfferAttrResponseDto;
import com.sts.sinorita.mapper.attr.AttrMapper;
import com.sts.sinorita.mapper.balanceAdjustment.SubsPlanOfferAttrMapper;
import com.sts.sinorita.mapper.offer.OfferAttrMapper;
import com.sts.sinorita.repository.AttrRepository;
import com.sts.sinorita.repository.SubsPlanOfferAttrRepository;
import com.sts.sinorita.repository.offer.OfferAttrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final SubsPlanOfferAttrRepository subsPlanOfferAttrRepository;
  private final AttrRepository attrRepository;
  private final OfferAttrRepository offerAttrRepository;
  private final SubsPlanOfferAttrMapper subsPlanOfferAttrMapper;
  private final AttrMapper attrMapper;
  private final OfferAttrMapper offerAttrMapper;

  public SubsPlanOfferAttrResponseDto qrySubsPlanOfferAttrByAttrCode (Long paramLong1, Long paramLong2, String paramString) {
    SubsPlanOfferAttrResponseDto subsPlanOfferAttr = selectSubsPlanOfferAttrByAttrCode(paramLong1, paramLong2, paramString);
    if (subsPlanOfferAttr == null) {
      OfferAttrDto offerAttr = offerAttrRepository.selectOfferAttrByAttrCode(paramLong2, paramString)
              .map(offerAttrMapper::selectOfferAttrByAttrCodeToOfferAttrDto)
              .orElse(null);
      if (offerAttr != null) {
        subsPlanOfferAttr = new SubsPlanOfferAttrResponseDto();
        subsPlanOfferAttr.setOfferId(offerAttr.getAttrId());
        subsPlanOfferAttr.setDefaultValue(offerAttr.getDefaultValue());
      }
    }
    return subsPlanOfferAttr;
  }

  public SubsPlanOfferAttrResponseDto selectSubsPlanOfferAttrByAttrCode (Long paramLong1, Long paramLong2, String paramString) {
    SubsPlanOfferAttrResponseDto subsPlanOfferAttr;
    AttrDto attrDto = attrRepository.getAttrByCode(paramString)
            .map(attrMapper::getAttrByCodeToAttrDto)
            .orElse(null);
    if (attrDto == null)
      return null;
    subsPlanOfferAttr = subsPlanOfferAttrRepository.selectSubsPlanOfferAttrByAttrCode(paramLong1, paramLong2, attrDto.getAttrId())
            .map(subsPlanOfferAttrMapper::toSubsPlanOfferAttrResponseDto)
            .orElse(null);
    return subsPlanOfferAttr;
  }
}
