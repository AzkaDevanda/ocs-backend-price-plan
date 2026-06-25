package com.ocs.portal.controller.event.event;

import com.google.gson.Gson;
import com.ocs.portal.dto.request.InsertUsageEventRequest;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.dto.response.priceplan.UsageEvent;
import com.ocs.portal.priceplan.SubscriptionEventService;
import com.ocs.portal.priceplan.UsageEventService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "EventPrice")
@RestController
@RequestMapping("api/event")

public class EventController {

    @Autowired
    private UsageEventService usageEventService;

    @Autowired
    private SubscriptionEventService subscriptionEventService;

    private static final Logger log = LoggerFactory.getLogger(EventController.class);


    @GetMapping("/{reType}")
    public ResponseEntity<BaseResponseDto> getAllUsageEvent(@PathVariable Character reType) {
        log.info("::Request getAllUsageEvent :: {}", new Gson().toJson(new UsageEvent()));
        return ResponseEntity.ok(usageEventService.getALlUsageEvent(reType));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponseDto> getUsageEventByOfferId(@RequestParam Integer offerVerId,@RequestParam Character reType) {
        log.info("::Request getUsageEventByOfferId :: {}", new Gson().toJson(new UsageEvent()));
        return ResponseEntity.ok(usageEventService.getUsageEventByOfferVerId(offerVerId,reType));
    }

    @GetMapping("subsciption/list")
    public ResponseEntity<CustomeResponse> getSubscriptionEventByOfferId(@Schema(description = "ex : 401") @RequestParam Integer offerVerId, @Schema(description = "ex : 0") @RequestParam Integer spId, @Schema(description = "ex : 'C'")@RequestParam Character notReType) {
        return usageEventService.qryProdSubsReEventForSubs(offerVerId,spId,notReType);
    }

    @PostMapping("create")
    public ResponseEntity<BaseResponseDto> insertUsageEvent(@RequestBody InsertUsageEventRequest usageEvent) {
        log.info("::Request insertUsageEvent :: {}", new Gson().toJson(usageEvent));
        return ResponseEntity.ok(usageEventService.insertUsageEvent(usageEvent));
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<CustomeResponse> deleteUsageEvent(@RequestParam Integer offerVerId , @RequestParam Integer usageEventId) {
        return usageEventService.deleteUsageEvent(offerVerId,usageEventId);
    }

}
