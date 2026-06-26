package com.ocs.portal.controller.accountconfig;

import com.ocs.portal.accountconfig.AccountBalanceTypeService;
import com.ocs.portal.constant.SortDirection;
import com.ocs.portal.dto.request.accountConfig.AccountBalanceTypeRequestDto;
import com.ocs.portal.dto.request.common.PagingRequestDto;
import com.ocs.portal.dto.response.CustomeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AccountBalanceType")
@RestController
@RequestMapping("api/account-balance")
public class AccountBalanceTypeController {

    @Autowired
    private AccountBalanceTypeService accountBalanceTypeService;

    @PostMapping("/add-acct-res")
    public ResponseEntity<CustomeResponse> addAcctRes(@RequestBody AccountBalanceTypeRequestDto dto) {
        return accountBalanceTypeService.addAcctRes(dto);
    }

    @PutMapping("/mod-acct-res/{acctResId}")
    public ResponseEntity<CustomeResponse> updateAcctRes(@PathVariable Long acctResId, @RequestBody AccountBalanceTypeRequestDto dto) {
        return accountBalanceTypeService.modAcctRes(acctResId, dto);
    }

    @GetMapping("/balance-type-with-mvno")
    public ResponseEntity<CustomeResponse> qryBalanceTypeWithMVNO(@RequestParam(required = false) Integer acctResId,
                                                                  @RequestParam(required = false) String acctResName,
                                                                  @RequestParam(required = false) Integer balType,
                                                                  @RequestParam(required = false) String exceptBalTypes,
                                                                  @RequestParam(required = false) String refillable,
                                                                  @RequestParam(required = false) Integer spId,
                                                                  @RequestParam int page,
                                                                  @RequestParam int size,
                                                                  @RequestParam String order_field,
                                                                  @RequestParam SortDirection order_direction) {
        return accountBalanceTypeService.qryBalanceTypeWithMVNO(acctResId, acctResName, balType, exceptBalTypes, refillable, spId, page, size, order_field, order_direction);
    }

    @DeleteMapping("/del-acct-res/{acctResId}")
    public ResponseEntity<CustomeResponse> delAcctRes(@PathVariable Long acctResId) {
        return accountBalanceTypeService.delAcctRes(acctResId);
    }


    // ==========> DROPDOWN LIST API <==========
    @GetMapping("/acct-res-list")
    public ResponseEntity<CustomeResponse> qryAcctResList(@RequestParam(required = false) String acctResName,
                                                          @RequestParam(required = false) Character isCurrency,
                                                          @RequestParam(required = false) Character refillable,
                                                          @RequestParam(required = false) Integer balType,
                                                          @RequestParam(required = false) String stdCode,
                                                          @RequestParam(required = false) Integer acctResIds,
                                                          @RequestParam(defaultValue = "0") Integer spId) {
        return accountBalanceTypeService.qryAcctResList(acctResName, isCurrency, refillable, balType, stdCode, acctResIds, spId);
    }

    @GetMapping("/bal-type")
    public ResponseEntity<CustomeResponse> qryBalType(@RequestParam(required = false) Integer balType,
                                                      @RequestParam(required = false) Integer balTypeFlag,
                                                      @RequestParam(required = false) Integer spId) {
        return accountBalanceTypeService.qryBalType(balType, balTypeFlag, spId);
    }

    @GetMapping("type-list")
    public ResponseEntity<CustomeResponse> getBalanceTypeList(
            @ModelAttribute PagingRequestDto pagingRequest,
            @RequestParam(required = false) String acctResName) {
        return accountBalanceTypeService.getBalanceTypeList(pagingRequest, acctResName);
    }
}

