package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.mapper.attr.AttrMapper;
import com.sts.sinorita.mapper.balanceAdjustment.ProdAttrValueMapper;
import com.sts.sinorita.mapper.balanceAdjustment.ProdMapper;
import com.sts.sinorita.mapper.offer.OfferAttrMapper;
import com.sts.sinorita.repository.AttrRepository;
import com.sts.sinorita.repository.ProdAttrValueRepository;
import com.sts.sinorita.repository.ProdRepository;
import com.sts.sinorita.repository.offer.OfferAttrRepository;
import com.sts.sinorita.util.StringParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubsQueryService {
  private final ProductService productService;
  private final ProdRepository prodRepository;
  private final ProdAttrValueRepository prodAttrValueRepository;
  private final OfferAttrRepository offerAttrRepository;
  private final AttrRepository attrRepository;
  private final ProdMapper prodMapper;
  private final AttrMapper attrMapper;
  private final ProdAttrValueMapper prodAttrValueMapper;
  private final OfferAttrMapper offerAttrMapper;
  private final Logger logger = LoggerFactory.getLogger(SubsQueryService.class);

  public String qryProdAttrValueByAttrCode (Long prodId, String attrCode) {
    logger.info("qryProdAttrValue start");
//    logger.debug("dict=[{}]", paramDynamicDict);
    Long long_ = prodId;
    String str1 = attrCode;
    String str2 = null;
    AttrDto attrDto = attrRepository.getAttrByCode(str1)
            .map(attrMapper::getAttrByCodeToAttrDto)
            .orElse(null);
    if (null != attrDto) {
      if (!"N".equals(attrDto.getInstantiatable())) {
        ProdAttrValueResponseDto prodAttrValue = prodAttrValueRepository.qryProdAttrValueByAttrCode(long_, str1)
                .map(prodAttrValueMapper::qryProdAttrValueByAttrCodeToProdAttrValue)
                .orElse(null);
        if (null == prodAttrValue) {
          str2 = qryAttrValue4Standard(long_, str1);
        } else {
          str2 = prodAttrValue.getValue();
        }
      } else {
        str2 = qryAttrValue4Standard(long_, str1);
      }
      logger.debug("attrValue=[{}]", str2);
//      paramDynamicDict.set("ATTR_VALUE", str2);
    } else {
      logger.debug("attrCode =[{}] is null", str2);
    }
    logger.info("qryProdAttrValue end.");
    return str2;
  }

  private String qryAttrValue4Standard (Long paramLong, String paramString) {
    String str = null;
    ProdResponseDto prod = prodRepository.qryProdById(paramLong)
            .map(prodMapper::qryProdByIdToProdDto)
            .orElse(null);
    if (prod == null)
      return null;
    Long long_1 = prod.getOfferId();
    Long long_2 = null;
    if (null == prod.getSubsPlanId()) {
      prod = prodRepository.qryProdById(prod.getIndepProdId())
              .map(prodMapper::qryProdByIdToProdDto)
              .orElse(null);
      long_2 = prod.getSubsPlanId();
    } else {
      long_2 = prod.getSubsPlanId();
    }
    logger.debug("subsPlanId=[{}], offerId=[{}]", long_2, long_1);
    SubsPlanOfferAttrResponseDto subsPlanOfferAttr = productService.qrySubsPlanOfferAttrByAttrCode(long_2, long_1, paramString);
    if (null == subsPlanOfferAttr || subsPlanOfferAttr.getDefaultValue().isEmpty()) {
      List<String> attrCodes = StringParser.adjustValueStrToSqlParam(paramString, ",");
      List<OfferAttrDto> list = offerAttrRepository.selectOfferAttrListByAttrCode(long_1, attrCodes)
              .stream()
              .map(offerAttrMapper::selectOfferAttrListByAttrCodeToListOfferAttrDto)
              .toList();
      if (!list.isEmpty())
        str = list.get(0).getDefaultValue();
    } else {
      str = subsPlanOfferAttr.getDefaultValue();
    }
    return str;
  }

}
