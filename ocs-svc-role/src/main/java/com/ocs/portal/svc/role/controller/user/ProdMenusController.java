package com.ocs.portal.svc.role.controller.user;

import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.svc.role.service.users.ProdMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/prod")
public class ProdMenusController {

    @Autowired
    ProdMenuService prodMenuService;

    @GetMapping(value = {"menus/{menuId}/out/components"})
    ResponseEntity<CustomeResponse>queryUserComponentListByMenuId(@PathVariable Long menuId, @RequestParam List<String> keyPrivCompOutId) {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200,"success", prodMenuService.queryUserComponentListByMenuId(menuId,keyPrivCompOutId)));
    }



}
