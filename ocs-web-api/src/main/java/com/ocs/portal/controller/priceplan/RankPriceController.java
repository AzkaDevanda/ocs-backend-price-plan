package com.ocs.portal.controller.priceplan;


import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.pricever.PricePlanVerService;
import com.ocs.portal.priceplan.RankPriceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "RankPrice")
@RestController
@RequestMapping(value = "api/rankprice")

public class RankPriceController {

    @Autowired
    RankPriceService rankPriceService;

    @Autowired
    PricePlanVerService pricePlanVerService;

    @GetMapping("/resource/list")
    public ResponseEntity<BaseResponseDto> getResources() {
        return ResponseEntity.ok(rankPriceService.getResource());
    }

}
