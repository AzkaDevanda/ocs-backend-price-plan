package com.ocs.portal.controller.validator;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.pricever.PricePlanVerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/validator")

@Tag(name = "Validator")
public class ValidatorController {


    @Autowired
    private PricePlanVerService pricePlanVerService;
    private static final Logger log = LoggerFactory.getLogger(ValidatorController.class);


    @GetMapping("price-version/{ratePlanId}")
    public ResponseEntity<CustomeResponse> validatePricePlanVer(@PathVariable Integer ratePlanId) {
        return pricePlanVerService.validatePriceVersionExpDate(ratePlanId);
    }

}
