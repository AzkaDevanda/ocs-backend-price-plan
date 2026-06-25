package com.ocs.portal.priceplan;

import com.ocs.portal.dto.request.PricePlanTypeDto;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.priceplan.MenuPriceplanTypeAccountReponse;
import com.ocs.portal.entity.PricePlanType;
import com.ocs.portal.enums.EnumRC;
import com.ocs.portal.repository.PricePlanRepository;
import com.ocs.portal.repository.PricePlanTypeRepository;
import com.ocs.portal.validation.ValidationHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PricePlanTypeService {

  @Autowired
  private PricePlanTypeRepository pricePlanTypeRepository;

  @Autowired
  private PricePlanRepository pricePlanRepository;

  public BaseResponseDto getAllPricePlanType () {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    List<PricePlanType> list = pricePlanTypeRepository.findAll();

    if (list.isEmpty()) {
      throw new ValidationHandler(EnumRC.DATA_NOT_FOUND.getMessage());
    }

    List<PricePlanTypeDto> responseList = list.stream().map(obj -> {
      PricePlanTypeDto response = new PricePlanTypeDto();
      response.setId(String.valueOf(obj.getId()));
      response.setPricePlanTypeName(obj.getPricePlanTypeName());
      return response;
    }).toList();

    baseResponseDto.setData(responseList);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    log.info("Completed getAllPricePlanType with response code: {}", baseResponseDto.getCode());
    return baseResponseDto;
  }

  public BaseResponseDto menuList () {

    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<Object[]> result = pricePlanTypeRepository.fetchPricePlanDataSType();

    // ================================
    // 1. SUBSCRIPTION → SEMUA DATA
    // ================================
    List<PricePlanTypeDto> subscriptionList = result.stream()
      .map(row -> new PricePlanTypeDto(
        row[0].toString().charAt(0),
        row[1] != null ? row[1].toString() : ""
      ))
      .collect(Collectors.toList());

    ResponseMenu subscriptionMenu = new ResponseMenu();
    subscriptionMenu.setParentName("Subscription");
    subscriptionMenu.setName("S");
    subscriptionMenu.setList(Collections.singletonList(subscriptionList));

    // ==========================================
    // 2. ACCOUNT → HANYA SYSTEM & DEFAULT (1,2)
    // ==========================================
    List<PricePlanTypeDto> accountList = result.stream()
      .filter(row -> {
        String id = row[0].toString();
        return id.equals("1") || id.equals("3");
      })
      .map(row -> new PricePlanTypeDto(
        row[0].toString().charAt(0),
        row[1].toString()
      ))
      .collect(Collectors.toList());

    ResponseMenu accountMenu = new ResponseMenu();
    accountMenu.setParentName("Account Price Plan");
    accountMenu.setName("A");
    accountMenu.setList(Collections.singletonList(accountList));

    // ================================
    // 3. FINAL RESPONSE
    // ================================
    List<ResponseMenu> finalList = new ArrayList<>();
    finalList.add(subscriptionMenu);
    finalList.add(accountMenu);

    baseResponseDto.setData(finalList);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    return baseResponseDto;
  }


  private MenuPriceplanTypeAccountReponse menuListAccount () {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    MenuPriceplanTypeAccountReponse menuPriceplanTypeAccountReponse = new MenuPriceplanTypeAccountReponse();
    List<PricePlanTypeDto> result = pricePlanTypeRepository.findDefaultAndIndividual('1', '3');
    menuPriceplanTypeAccountReponse.setList(result);
    menuPriceplanTypeAccountReponse.setAccountHeader("Account Price Plan");
    return menuPriceplanTypeAccountReponse;
  }


  @Getter
  @Setter
  private static class ResponseMenu {
    private String parentName;
    private String name;
    private List<Object> list;
  }

}
