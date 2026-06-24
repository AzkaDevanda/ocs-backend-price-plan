package com.sts.sinorita.controller.priceplan;


import com.sts.sinorita.dto.request.*;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.priceplan.PricePlanVerService;
import com.sts.sinorita.priceplan.RankPriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
