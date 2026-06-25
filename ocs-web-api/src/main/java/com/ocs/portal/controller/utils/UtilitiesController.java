package com.ocs.portal.controller.utils;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocs.portal.constant.HttpStatusConstant;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.enums.EnumSelected;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/utilities")
@Tag(name = "Utilities", description = "Utilities APIs")

public class UtilitiesController {
  @GetMapping("selectedType")
  public ResponseEntity<CustomeResponse> getSelectedType() {
    var data = Arrays.stream(EnumSelected.values())
                     .map(EnumSelected::getLabel)
                     .toList();
                     
    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200,HttpStatusConstant.SUCCESS_MESSAGE, data));
  }
}
