package com.ocs.portal.controller.accountconfig;

import com.ocs.portal.dto.request.common.PagingRequestDto;
import com.ocs.portal.accountconfig.AcctItemTypeService;
import com.ocs.portal.dto.response.CustomeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AccountItemType")
@RestController
@RequestMapping("api/account-item-type")
public class AcctItemTypeController {
  @Autowired
  private AcctItemTypeService acctItemTypeService;

  @GetMapping(value = "name/list")
  public ResponseEntity<CustomeResponse> getAcctItemType(@ModelAttribute PagingRequestDto pagingRequest, @RequestParam(required = false) String acctItemTypeName, @RequestParam(required = false) Long acctResId, @RequestParam(required = false) String balType, @RequestParam(required = false) Long spId) {
    return acctItemTypeService.getAllAcctItemType(pagingRequest, acctItemTypeName, acctResId, balType, spId);
  }

  @GetMapping("bal-type/list")
  public ResponseEntity<CustomeResponse> getBalType() {
    return acctItemTypeService.getBalType();
  }

  @GetMapping("bal-type/child")
  public ResponseEntity<CustomeResponse> getBalTypeChild(@ModelAttribute PagingRequestDto pagingRequestDto, @RequestParam(defaultValue = "0") Integer spId) {
    return acctItemTypeService.getBalTypeAcctRes(pagingRequestDto, spId);
  }

  @GetMapping("add-list")
  public ResponseEntity<CustomeResponse> getAcctItemTypeForAdd(@ModelAttribute PagingRequestDto pagingRequest, @RequestParam(required = false) Integer acctItemTypeId, @RequestParam(required = false) Integer acctResId, @RequestParam(required = false) Integer nullFlag, @RequestParam(required = false) Integer parentId, @RequestParam(required = false) Integer priceVerId, @RequestParam(required = false) Integer priceVerIdExists, @RequestParam(required = false) Integer priceVerIdEx, @RequestParam(required = false) Integer curPriceId, @RequestParam(required = false) Integer spId, @RequestParam(required = false) String usageType, @RequestParam(required = false) Integer defaultAcctResId, @RequestParam(required = false) String acctItemTypeName) {
    return acctItemTypeService.getAcctItemTypeForAdd(pagingRequest, acctItemTypeId, acctResId, nullFlag, parentId, priceVerId, priceVerIdExists, priceVerIdEx, curPriceId, spId, usageType, defaultAcctResId, acctItemTypeName);
  }

  @GetMapping("update-list")
  public ResponseEntity<CustomeResponse> getAcctItemTypeForUpdate(@ModelAttribute PagingRequestDto pagingRequest, @RequestParam(required = false) Integer acctItemTypeId, @RequestParam(required = false) Integer acctResId, @RequestParam(required = false) Integer nullFlag, @RequestParam(required = false) Integer parentId, @RequestParam(required = false) Integer priceVerId, @RequestParam(required = false) Integer priceVerIdExists, @RequestParam(required = false) Integer priceVerIdEx, @RequestParam(required = false) Integer curPriceId, @RequestParam(required = false) Integer spId, @RequestParam(required = false) String usageType, @RequestParam(required = false) Integer defaultAcctResId, @RequestParam(required = false) String acctItemTypeName) {
    return acctItemTypeService.getAcctItemTypeForUpdate(pagingRequest, acctItemTypeId, acctResId, nullFlag, parentId, priceVerId, priceVerIdExists, priceVerIdEx, curPriceId, spId, usageType, defaultAcctResId, acctItemTypeName);
  }

  @GetMapping("gst-type")
  public ResponseEntity<CustomeResponse> getAllGstType() {
    return acctItemTypeService.getAllGstType();
  }

  @GetMapping("QryFirstLevelAcctItemType")
  public ResponseEntity<CustomeResponse> getQryFirstLevelAcctItemType(@ModelAttribute PagingRequestDto pagingRequest, @RequestParam(required = false) Integer spId, @RequestParam(required = false) Integer acctResId) {
    return acctItemTypeService.getAcctItemType(pagingRequest, spId, acctResId);
  }
}
