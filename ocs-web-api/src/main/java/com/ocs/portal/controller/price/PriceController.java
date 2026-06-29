package com.ocs.portal.controller.price;


import com.ocs.portal.controller.priceplan.PricePlanController;
import com.ocs.portal.dto.request.*;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.pricever.PricePlanVerService;
import com.ocs.portal.rateplan.PriceServices;
import com.ocs.portal.utils.GsonCustom;
import com.ocs.portal.utils.LoggerPortal;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Price")
@RestController
@RequestMapping("api/price")
public class PriceController {
    private static final Logger log = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private PriceServices priceService;

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
    public ResponseEntity<CustomeResponse> deletePrice(@RequestParam Integer priceId, @RequestParam Integer priceVerId, @RequestParam Character reType) {
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