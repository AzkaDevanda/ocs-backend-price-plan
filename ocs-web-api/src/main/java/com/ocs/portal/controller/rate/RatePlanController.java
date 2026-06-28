package com.ocs.portal.controller.rate;


import com.google.gson.Gson;
import com.ocs.portal.dto.request.ModRePricePlanDto;
import com.ocs.portal.dto.request.RatePlanDto;
import com.ocs.portal.dto.request.UpdateRatePlanDto;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.rateplan.RatePlanService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rate-plan")
@Tag(name = "RatePlan")

public class RatePlanController {

    @Autowired
    private RatePlanService ratePlanService;

    private static final Logger log = LoggerFactory.getLogger(RatePlanController.class);


    @PostMapping("/create")
    public ResponseEntity<BaseResponseDto> addRatePlan(@Validated @RequestBody RatePlanDto ratePlanDto) {
        log.info("::Request addRatePlan :: {} ", new Gson().toJson(ratePlanDto));
        return ResponseEntity.ok(ratePlanService.addRatePlan(ratePlanDto));
    }
    @PutMapping("/priority")
    public ResponseEntity<CustomeResponse> updatePriorityRatePlan(@Schema @RequestParam Integer ratePlanId, @RequestParam Integer priority) {
        return ratePlanService.updatePriorityRatePlan(ratePlanId, priority);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomeResponse> deleteRatePlan(@Schema(description = "ratePlan id") @PathVariable Integer id) {
        return ratePlanService.deleteRatePlan(id);
    }

    @PostMapping("mod-re-price-plan")
    public ResponseEntity<CustomeResponse> modRePricePlan(@RequestBody ModRePricePlanDto modRePricePlanDto) {
        return ratePlanService.modRePricePlan(modRePricePlanDto);
    }


    @GetMapping("/list")
    public ResponseEntity<CustomeResponse> getRatePlanByOfferVerId(@RequestParam Integer offerVerId, @RequestParam Integer reId, @RequestParam(required = false) Integer spId, @RequestParam(required = false) String ratePlanName) {
        return ratePlanService.getRatePlanByOfferVerId(offerVerId, reId, spId, ratePlanName);
    }


    @GetMapping("detail/{id}")
    public ResponseEntity<BaseResponseDto> getDetailRatePlanById(@Schema(description = "ratePlan id") @PathVariable Integer id) {
        return ResponseEntity.ok(ratePlanService.getRatePlanById(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CustomeResponse> updateRatePlan(@Schema(description = "ratePlan id") @PathVariable Integer id, @Validated @RequestBody UpdateRatePlanDto ratePlanDto) {
        log.info("::Request updateRatePlan :: {} ", new Gson().toJson(ratePlanDto));
        return ratePlanService.updateRatePlan(id, ratePlanDto);
    }

}


