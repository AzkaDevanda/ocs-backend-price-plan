package com.ocs.portal.controller.priceplan;

import com.ocs.portal.dto.request.PricePlanPriorityRequest;
import com.ocs.portal.dto.request.PricePlanRequestDto;
import com.ocs.portal.dto.request.PricePlanUpdateDto;
import com.ocs.portal.dto.request.priceplan.VersionDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.constant.SortDirection;
import com.ocs.portal.priceplan.PricePlanTypeService;
import com.ocs.portal.priceplan.PricePlanService;
import com.ocs.portal.priceplan.ServTypeService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/priceplan")
@Tag(name = "PricePlan")
@RequiredArgsConstructor
public class PricePlanController {
  private final PricePlanTypeService pricePlanTypeService;
  private final PricePlanService pricePlanService;
  private final ServTypeService servTypeService;

    @GetMapping(value = "all-type/list")
    public ResponseEntity<BaseResponseDto> getAllPricePlanType() {
        // log.info("::Request getAllType :: {}", new Gson().toJson(new PricePlanTypeDto()));
        return ResponseEntity.ok(pricePlanTypeService.getAllPricePlanType());
    }

    @GetMapping(value = "menu/list")
    public ResponseEntity<BaseResponseDto> getMenuList() {
        return ResponseEntity.ok(pricePlanTypeService.menuList());
    }

    @GetMapping(value = "serv-type/list")
    public ResponseEntity<CustomeResponse> getAllServiceType(@RequestParam(required = false) @Schema(description = "By ServType Name") String filter, @RequestParam int page, @RequestParam int size) {
        // log.info("::Request getAllType :: {}", new Gson().toJson(new PricePlanTypeDto()));
        return servTypeService.getServType(filter, page, size);
    }

    @PostMapping(value = "create", consumes = "application/json; charset=UTF-8", produces = "application/json")
    public ResponseEntity<BaseResponseDto> createPricePlan(@Valid @RequestBody PricePlanRequestDto pricePlanRequestDto) {
        // log.info("::Request createPricePlan :: {} ", localDateAdapter.gsonConfig().toJson(pricePlanRequestDto));
        return ResponseEntity.ok(pricePlanService.createPricePlan(pricePlanRequestDto));
    }

    @GetMapping("detail")
    public ResponseEntity<CustomeResponse> getPricePlanByOfferId(@RequestParam Integer offerId, @RequestParam Character applyLevel) {
        return pricePlanService.getPricePlanByOfferId(offerId, applyLevel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomeResponse> deletePricePlan(@PathVariable Integer id) {
        return pricePlanService.deletePricePlan(id);
    }

    @GetMapping("list")
    public ResponseEntity<BaseResponseDto> getPricePlanByType(@Schema(description = "filter") @RequestParam(required = false) String filter, @RequestParam Character applyLevel, @RequestParam(required = false) Character pricePlanTypeId, @RequestParam int page, @RequestParam int size, @RequestParam String order_field, @RequestParam(required = true) SortDirection order_direction) {
        BaseResponseDto response = pricePlanService.getPricePlanByType(applyLevel, page, size, pricePlanTypeId, filter, order_field, order_direction);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomeResponse> updatePricePlan(@PathVariable Long id, @RequestBody PricePlanUpdateDto pricePlanUpdateDto) {
        return pricePlanService.updatePricePlan(id, pricePlanUpdateDto);
    }

    @PutMapping("/priority/update")
    public ResponseEntity<BaseResponseDto> updatePriority(@RequestBody PricePlanPriorityRequest pricePlanPriorityRequest) {
        return ResponseEntity.ok(pricePlanService.updatePricePlanByPriority(pricePlanPriorityRequest));
    }

    @PutMapping("priority/{pricePlanId}/update")
    public ResponseEntity<BaseResponseDto> updatePriorityById(@PathVariable Integer pricePlanId, @RequestBody PricePlanPriorityRequest pricePlanPriorityRequest) {
        return ResponseEntity.ok(pricePlanService.updatePricePlanPriorityById(pricePlanId, pricePlanPriorityRequest));
    }

    // @PostMapping("createDiscount")
    // public ResponseEntity<BaseResponseDto> doCreateDiscount() {
    //   return ResponseEntity.ok(new BaseResponseDto());
    // }

    @GetMapping("SubsPricePlan/list")
    public ResponseEntity<CustomeResponse> getSubsPricePlan(@RequestParam(required = false) String pricePlanName, @RequestParam(required = false) String pricePlanCode, @RequestParam(required = false) Character pricePlanType, @RequestParam(required = false) Integer pricePlanId, @RequestParam(required = false) Integer spId, @RequestParam Integer page, @RequestParam Integer size, @RequestParam String order_field, @RequestParam SortDirection order_direction) {
        return pricePlanService.getSubsPricePlan(pricePlanName, pricePlanCode, pricePlanType, pricePlanId, spId, order_field, order_direction, page, size);
    }

    @GetMapping("AcctPricePlan/list")
    public ResponseEntity<CustomeResponse> getAcctPricePlan(@RequestParam(required = false) String pricePlanName, @RequestParam(required = false) String pricePlanCode, @RequestParam(required = false) Character pricePlanType, @RequestParam(required = false) Integer pricePlanId, @RequestParam(required = false) Integer spId, @RequestParam Integer page, @RequestParam Integer size, @RequestParam String order_field, @RequestParam SortDirection order_direction) {
        return pricePlanService.getAcctPricePlan(pricePlanName, pricePlanCode, pricePlanType, pricePlanId, spId, order_field, order_direction, page, size);
    }

    @PostMapping(value = "version/add/{offerId}")
    public ResponseEntity<CustomeResponse> addPriceplanVersion(@Validated @RequestBody VersionDto versionDto, @PathVariable Integer offerId) {
        return pricePlanService.addPriceplanVersion(versionDto, offerId);
    }

    @PutMapping(value = "version/edit/{offerId}/{offerVerId}")
    public ResponseEntity<CustomeResponse> editPriceplanVersion(@Validated @RequestBody VersionDto versionDto, @PathVariable Integer offerId, @PathVariable Integer offerVerId) {
        if (versionDto.getEffDate() != null && versionDto.getExpDate() != null && versionDto.getEffDate().isAfter(versionDto.getExpDate())) {
            throw new IllegalArgumentException("Effective date must not be after expiry date.");
        }
        return pricePlanService.editPriceplanVersion(versionDto, offerId, offerVerId);
    }

    @DeleteMapping(value = "version/delete/{offerVerId}")
    public ResponseEntity<CustomeResponse> deletePriceplanVersion(@PathVariable Integer offerVerId) {
        return pricePlanService.deletePricePlanVersion(offerVerId);
    }

  // @GetMapping("copyFrom/list")
  // public ResponseEntity<CustomeResponse> getCopyForm(@RequestParam(defaultValue = "N") String showNotEffOfferver, @RequestParam(defaultValue = "0") Integer spId, @RequestParam(required = false) String PricePlanName) {
  //   return pricePlanService.getCopyFormList(showNotEffOfferver, spId, PricePlanName);
  // }
}