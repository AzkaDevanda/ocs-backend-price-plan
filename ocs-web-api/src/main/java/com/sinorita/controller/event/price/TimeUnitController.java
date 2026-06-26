package com.sinorita.controller.event.price;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.price.PricePlanVerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Time Unit")
@RestController
@RequestMapping("api/time-unit")
public class TimeUnitController {

    @Autowired
    private PricePlanVerService pricePlanVerService;

    @GetMapping("list")
    public ResponseEntity<CustomeResponse> getAllTimeUnit(@RequestParam String notExact) {
        return pricePlanVerService.doGetTimeUnitList(notExact);
    }
}
