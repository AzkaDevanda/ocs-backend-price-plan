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



  // @GetMapping("copyFrom/list")
  // public ResponseEntity<CustomeResponse> getCopyForm(@RequestParam(defaultValue = "N") String showNotEffOfferver, @RequestParam(defaultValue = "0") Integer spId, @RequestParam(required = false) String PricePlanName) {
  //   return pricePlanService.getCopyFormList(showNotEffOfferver, spId, PricePlanName);
  // }
}