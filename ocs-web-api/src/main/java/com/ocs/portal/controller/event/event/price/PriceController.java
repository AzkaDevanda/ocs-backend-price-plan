package com.ocs.portal.controller.event.event.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ocs.portal.controller.priceplan.PricePlanController;
import com.ocs.portal.dto.request.BenefitRequest;
import com.ocs.portal.dto.request.InsertAccumulationPriceDto;
import com.ocs.portal.dto.request.ModPricePriorityRequest;
import com.ocs.portal.dto.request.PricePlanVerRequestDto;
import com.ocs.portal.dto.request.PricePlanVerUpdateDto;
import com.ocs.portal.dto.request.UpdateBenefitRequestDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.priceplan.PricePlanVerService;
import com.ocs.portal.priceplan.PriceService;
import com.ocs.portal.utils.GsonCustom;
import com.ocs.portal.utils.LoggerPortal;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Price")
@RestController
@RequestMapping("api/price")
public class PriceController {
  private static final Logger log = LoggerFactory.getLogger(PriceController.class);

  @Autowired
  private PriceService priceService;

  @Autowired
  private PricePlanVerService pricePlanVerService;

  @Autowired
  private LoggerPortal loggerPortal;

  @Autowired
  private GsonCustom gsonCustom;

  @PutMapping("/priority/update")
  public ResponseEntity<CustomeResponse> modPricePriority(@Validated @RequestBody ModPricePriorityRequest request) {
    return ResponseEntity.ok(priceService.modifyPricePriority(request));
  }

  @PutMapping("/update/{priceId}")
  public ResponseEntity<CustomeResponse> updatePricePlanVer(@PathVariable Long priceId, @RequestParam(required = false) Character reType, @RequestBody PricePlanVerUpdateDto dto) {
    loggerPortal.loggerInfo(PricePlanController.class, gsonCustom.doJsonString(dto));
    return pricePlanVerService.updatePrice(priceId, reType, dto);
  }

  @PostMapping("/create")
  public ResponseEntity<CustomeResponse> addPricePlanVer(@RequestParam(required = false) Character reType, @Validated @RequestBody PricePlanVerRequestDto pricePlanRequestDto) {
    loggerPortal.loggerInfo(PricePlanController.class, gsonCustom.doJsonString(pricePlanRequestDto));
    return pricePlanVerService.addPricePlanVer(reType, pricePlanRequestDto);
  }

  @PostMapping("accumulation/create")
  public ResponseEntity<CustomeResponse> addAccumulationPrice(@Validated @RequestBody InsertAccumulationPriceDto request) {
    return pricePlanVerService.addAccumulationPrice(request);
  }

  @PutMapping("accumulation/update")
  public ResponseEntity<CustomeResponse> updateAccumulationPrice(@Validated @RequestBody InsertAccumulationPriceDto request) {
    return pricePlanVerService.updateAccumulationPrice(request);
  }

  @GetMapping("accumulation/list")
  public ResponseEntity<CustomeResponse> getListAccumulation(@RequestParam(required = false) Integer ratePlanId, @RequestParam(required = false) Long priceId, @RequestParam(required = false) Integer mappingId) {
    return pricePlanVerService.doGetAccumulationList(ratePlanId, priceId, mappingId);
  }

  @GetMapping("accumulation-type/list")
  public ResponseEntity<CustomeResponse> getAccumulationTypeList() {
    return pricePlanVerService.getAccumulationTypeList();
  }

  @GetMapping("/benefit/list")
  public ResponseEntity<CustomeResponse> getBenefitList(@RequestParam(required = false) Integer ratePlanId, @RequestParam(required = false) Integer priceId, @RequestParam(required = false) Integer priceVerId, @RequestParam(required = false) Integer mappingId, @RequestParam(required = false) Integer spId) {
    return pricePlanVerService.qryEventBenefit(priceId, ratePlanId, priceVerId, mappingId, spId);
  }

  @PutMapping("/benefit/priority/update")
  public ResponseEntity<CustomeResponse> updatePriority(@Validated @RequestBody ModPricePriorityRequest request) {
    return ResponseEntity.ok(priceService.modifyBenefitPriority(request));
  }

  @PostMapping("/benefit/create")
  public ResponseEntity<CustomeResponse> addBenefit(@Validated @RequestBody BenefitRequest dto) {
    return pricePlanVerService.addBenefit(dto);
  }

  @PutMapping("/benefit/update/{priceId}")
  public ResponseEntity<CustomeResponse> updateBenefit(@PathVariable Integer priceId, @Validated @RequestBody UpdateBenefitRequestDto dto) {
    return pricePlanVerService.updateBenefitPrice(priceId, dto);
  }

  @GetMapping("/detail/{priceId}")
  public ResponseEntity<CustomeResponse> getBenefitDetail(@Parameter(description = "FOR RATING USAGE PRICE, SUBSCRIPTION PRICE AND BENEFIT USAGE PRICE, SUBSCRIPTION PRICE, RECURRING PRICE") @PathVariable Integer priceId) {
    return pricePlanVerService.getDetailPriceBenefit(priceId);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<CustomeResponse> deletePricePlan(@RequestParam Integer priceId, @RequestParam Integer priceVerId, @RequestParam Character reType) {
    return priceService.deletePrice(priceId, priceVerId, reType);
  }

  @DeleteMapping("/benefit/delete")
  public ResponseEntity<CustomeResponse> deleteBenefit(@RequestParam Integer priceId, @RequestParam Integer priceVerId, @RequestParam Integer subBalTypeId) {
    return priceService.deleteBenefit(priceId, priceVerId, subBalTypeId);
  }

  @GetMapping("rating/list")
  public ResponseEntity<CustomeResponse> getPriceRating(@RequestParam Integer ratePlanId, @RequestParam(required = false) Integer mappingId, @RequestParam(required = false) Integer priceVerId, @RequestParam(required = false) Long priceId, @RequestParam(required = false) Long priceIdSelf, @RequestParam(defaultValue = "0") Integer spId, @RequestParam(required = false) Integer parentPriceId, @RequestParam(required = false) Integer srcPriceId, @RequestParam(required = false) Character shareFlag) {
    return priceService.getPriceRating(ratePlanId, mappingId, priceVerId, priceId, priceIdSelf, spId, parentPriceId, srcPriceId, shareFlag);
  }
}