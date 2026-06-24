package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AttrDto;
import com.sts.sinorita.dto.request.balanceAdjustment.OfferAttrDto;
import com.sts.sinorita.dto.request.balanceAdjustment.ProdAttrValueResponseDto;
import com.sts.sinorita.entity.OfferAttr;
import com.sts.sinorita.entity.Prod;
import com.sts.sinorita.entity.ProdAttrValue;
import com.sts.sinorita.entity.SubsPlanOfferAttr;
import com.sts.sinorita.mapper.attr.AttrMapper;
import com.sts.sinorita.mapper.balanceAdjustment.ProdAttrValueMapper;
import com.sts.sinorita.mapper.offer.OfferAttrMapper;
import com.sts.sinorita.repository.AttrRepository;
import com.sts.sinorita.repository.ProdAttrValueRepository;
import com.sts.sinorita.repository.ProdRepository;
import com.sts.sinorita.repository.offer.OfferAttrRepository;
import com.sts.sinorita.util.StringParser;
import com.sts.sinorita.util.StringUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdService {

  private final SubsQueryService subsQueryService;

  private final ProdRepository prodRepository;

  private final OfferAttrRepository offerAttrRepository;

  private final OfferAttrMapper offerAttrMapper;

  private final AttrRepository attrRepository;

  private final AttrMapper attrMapper;

  private final ProdAttrValueRepository prodAttrValueRepository;

  private final ProdAttrValueMapper prodAttrValueMapper;

  public Prod qryProdBySubsId(Long subsId) {
    Prod prod = prodRepository.selectProdBySubsId(subsId);
    if (prod != null) {
      // ProdStateDto prodState = ProdCache.getProdState(prod.getProdState());
      // if (prodState != null)
      // prod.setProdStdState(prodState.getProdStdState());
    }
    return prod;
  }

  public OfferAttrDto getOfferAttrByOfferIdAndAttrCode(long offerId, String attrCode) {
    AttrDto attrDto = attrRepository.selectAttrByCode(attrCode)
        .map(attrMapper::selectAttrByCodeToAttrDto)
        .orElse(null);
    if (attrDto == null)
      return null;
    List<OfferAttrDto> offerAttrDtoList = offerAttrRepository.selectOfferAttrByOfferId(offerId).stream()
        .map(offerAttrMapper::tOfferAttrDtoFromSelectOfferAttrByOfferId).toList();
    if (offerAttrDtoList != null && !offerAttrDtoList.isEmpty())
      for (OfferAttrDto offerAttrDto : offerAttrDtoList) {
        if (offerAttrDto.getAttrId().longValue() == attrDto.getAttrId().longValue())
          return offerAttrDto;
      }
    return null;
  }
}
