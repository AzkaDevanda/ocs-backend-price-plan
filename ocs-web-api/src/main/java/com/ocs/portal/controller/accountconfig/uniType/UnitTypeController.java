// package com.ocs.portal.controller.accountconfig.uniType;

// import com.ocs.portal.accountconfig.UnitTypeService;
// import com.ocs.portal.dto.response.BaseResponseDto;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("api/unitType")

// @Tag(name = "UnitType")
// public class UnitTypeController {

//     @Autowired
//     private UnitTypeService unitTypeService;

//     @GetMapping("/list")
//     public ResponseEntity<BaseResponseDto> getUnitType(){
//         return ResponseEntity.ok(unitTypeService.getAllUnitType());
//     }

// }
