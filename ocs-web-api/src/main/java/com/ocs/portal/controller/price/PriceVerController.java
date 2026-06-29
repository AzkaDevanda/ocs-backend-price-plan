package com.ocs.portal.controller.price;

import com.ocs.portal.pricever.PricePlanVerService;
import com.ocs.portal.dto.request.UpdatePriceVerDto;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/price-version")
@Tag(name = "PriceVersion")
public class PriceVerController {
    @Autowired
    private PricePlanVerService pricePlanVerService;
    @Autowired
    private PriceServices priceService;


    @GetMapping({"/{ratePlanId}"})
    public ResponseEntity<BaseResponseDto> getPricePlanVer(@PathVariable Integer ratePlanId) {
//        log.info("::Request createPricePlan :: {} ",new Gson().toJson(pricePlanRequestDto));
        return ResponseEntity.ok(pricePlanVerService.listPriceVerByRatePlan(ratePlanId));
    }

    @PutMapping("/update/{priceVerId}")
    public ResponseEntity<CustomeResponse> updatePricePlanVer(@Schema(description = "ex : priceVerId (11516)") @PathVariable Integer priceVerId, @RequestBody UpdatePriceVerDto dto) {
        return pricePlanVerService.updatePriceVer(priceVerId, dto);
    }

    @GetMapping("re-attr/list")
    public ResponseEntity<CustomeResponse> listReAttr( @Parameter(description = """
            - For Usage Price ReType = 1\n
            - For Reurring Price ReType = 2\n
            - For Subscription Price Type = 3""") @RequestParam(required = false) Character reType, @RequestParam(required = false) String reAttrName, @RequestParam(required = false) Integer spId) {
        return pricePlanVerService.listReAttrMapping(reType,reAttrName, spId);
    }

    @GetMapping(value = "reattr-price/list")
    public ResponseEntity<CustomeResponse> listReAttrForPrice() {
        return pricePlanVerService.listReAttrForPrice();
    }

    @DeleteMapping("/delete/{priceVerId}")
    public ResponseEntity<BaseResponseDto> deletePriceVer(@PathVariable Integer priceVerId){
        return ResponseEntity.ok(pricePlanVerService.deletePriceVer(priceVerId));
    }

    @DeleteMapping("/delete/acm/{priceId}/{priceVerId}")
    public ResponseEntity<CustomeResponse> deletePriceVerAcm(@PathVariable Integer priceId, @PathVariable Integer priceVerId){
        return priceService.deleteAcm(priceId,priceVerId);
    }


//    @GetMapping(value = "listForUpdate/{id}")
//    public ResponseEntity<BaseResponseDto> listForUpdate(@PathVariable Long id) {
//        return ResponseEntity.ok(pricePlanVerService.getPriceDetailById(id));
//    }


//    @DeleteMapping({"/delete/{priceId}"})
//    public ResponseEntity<BaseResponseDto> deletePricePlanVer(@PathVariable Long priceId) {
////        log.info("::Request deletePricePlanVer :: {} ",new Gson().toJson(priceVerId));
//        BaseResponseDto response = pricePlanVerService.deletePrice(priceId);
    ////        log.info("::Response deletePricePlanVer :: {} ",new Gson().toJson(response));
//        return ResponseEntity.ok(response);
//    }

    // TODO : service masih error
//    @PostMapping("/share")
//    public ResponseEntity<BaseResponseDto> sharePricePlan(@RequestBody SharePricePlanRequestDTO sharedPricePlanVerId) {
//        log.info("::Request sharePricePlanVer :: {} ",new Gson().toJson(sharedPricePlanVerId));
//        return ResponseEntity.ok(pricePlanVerService.sharePricePlanVer(sharedPricePlanVerId));
//    }


//    @PostMapping("/expression-price/create")
//    public ResponseEntity<BaseResponseDto> addExpressionPrice(@Validated @RequestBody ExpressionPriceRequest dto){
//        return ResponseEntity.ok(pricePlanVerService.addExpressionPrice(dto));
//    }

}