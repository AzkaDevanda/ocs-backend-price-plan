package com.sts.sinorita.priceplan;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.InsertRecurringEventRequest;
import com.sts.sinorita.dto.request.InsertUsageEventRequest;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.priceplan.UsageEvent;
import com.sts.sinorita.dto.response.re.GetReResponseTree;
import com.sts.sinorita.dto.response.re.GetUsageEventByOfferIdResponse;
import com.sts.sinorita.entity.*;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.projection.pricePlan.QryRecurringReTypeListByVerIdProjection;
import com.sts.sinorita.projection.pricePlan.price.QryProdSubsReEventForSubsProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.validation.ValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UsageEventService {

  @Autowired
  private ReRepository reRepository;
  @Autowired
  private RePricePlanRepository rePricePlanRepository;
  @Autowired
  private RecurringReTypeRepository recurringReTypeRepository;
  @Autowired
  private OfferVerRepository offerVerRepository;
  @Autowired
  private RePpRecurringRepository rePpRecurringRepository;
  @Autowired
  private OfferRepository offerRepository;
  @Autowired
  private RatePlanRepository ratePlanRepository;
  @Autowired
  private RatePlanMappingRepository ratePlanMappingRepository;
  @Autowired
  private PriceVerRepository priceVerRepository;
  @Autowired
  private PriceRepository priceRepository;
  @Autowired
  private EventBenefitRepository eventBenefitRepository;

  public BaseResponseDto getALlUsageEvent (Character reType) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    List<Object[]> list = reRepository.getEvent(reType);

    if (reType.equals('1')) {
      List<Object[]> rawData = reRepository.getEvent('1');
      List<GetReResponseTree> flatList = mapToUsageEvents(rawData);
      List<GetReResponseTree> tree = buildTree(flatList);

      baseResponseDto.setData(tree);
      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
      return baseResponseDto;
    }

    if (list.isEmpty()) {
      baseResponseDto.setData(list);
      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
      return baseResponseDto;
    }

    List<UsageEvent> responseList = list.stream().map(obj -> {
      UsageEvent response = new UsageEvent();
      response.setParentId(obj[0] != null ? (Integer) obj[0] : null);
      response.setReId((Integer) obj[1]);
      response.setReType(((Character) obj[2]));
      response.setReName((String) obj[3]);
      response.setComments((String) obj[4]);
      response.setSpId(obj[5] != null ? (Integer) obj[5] : null);
      response.setReCode((String) obj[6]);
      response.setReAttr(obj[7] != null ? (Integer) obj[7] : null);
      return response;
    }).toList();

    baseResponseDto.setData(responseList);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    log.info("Completed getAllUsageEvent with response code: {}", baseResponseDto.getCode());

    return baseResponseDto;
  }

  public List<GetReResponseTree> mapToUsageEvents (List<Object[]> rows) {
    List<GetReResponseTree> events = new ArrayList<>();

    for (Object[] row : rows) {
      GetReResponseTree event = new GetReResponseTree();
      event.setParentId(toBigDecimal(row[0]));
      event.setReId(toBigDecimal(row[1]));
      event.setReType((Character) row[2]);
      event.setReName((String) row[3]);
      event.setComments((String) row[4]);
      event.setSpId(toBigDecimal(row[5]));
      event.setReCode((String) row[6]);
      event.setReAttr(toBigDecimal(row[7]));

      events.add(event);
    }

    return events;
  }

  private BigDecimal toBigDecimal (Object obj) {
    return obj != null ? new BigDecimal(obj.toString()) : null;
  }

  public List<GetReResponseTree> buildTree (List<GetReResponseTree> flatList) {
    Map<BigDecimal, GetReResponseTree> idToNode = new HashMap<>();
    List<GetReResponseTree> rootNodes = new ArrayList<>();

    for (GetReResponseTree event : flatList) {
      idToNode.put(event.getReId(), event);
    }

    for (GetReResponseTree event : flatList) {
      if (event.getParentId() == null) {
        rootNodes.add(event); // Root node
      } else {
        GetReResponseTree parent = idToNode.get(event.getParentId());
        if (parent != null) {
          parent.getChildren().add(event);
        } else {
          // fallback, if parent not found treat as root
          rootNodes.add(event);
        }
      }
    }

    return rootNodes;
  }

  public BaseResponseDto getUsageEventByOfferVerId (Integer offerVerId, Character reType) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<Object[]> responseList = reRepository.getUsageEventByOfferVerId(offerVerId, reType);

    if (responseList.isEmpty()) {
      baseResponseDto.setData(responseList);
      baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
      baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
      return baseResponseDto;
    }

    List<GetUsageEventByOfferIdResponse> list = responseList.stream().map(obj -> {
      GetUsageEventByOfferIdResponse response = new GetUsageEventByOfferIdResponse();
      response.setReId((Integer) obj[0]);
      response.setOfferVerId((Integer) obj[1]);
      response.setReName((String) obj[2]);
      return response;
    }).toList();

    baseResponseDto.setData(list);
    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

    return baseResponseDto;
  }

  public BaseResponseDto RecurringReTypeList (Integer offerVerId, String reName) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();
    List<QryRecurringReTypeListByVerIdProjection> recurringReTypeName = recurringReTypeRepository
      .qryRecurringReTypeListByVerId(offerVerId, reName);

    if (recurringReTypeName.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data not found!");
    }

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    baseResponseDto.setData(recurringReTypeName);
    return baseResponseDto;

  }

  public ResponseEntity<CustomeResponse> qryProdSubsReEventForSubs (Integer offerVerId, Integer spId,
                                                                    Character notReType) {

    List<QryProdSubsReEventForSubsProjection> data1 = reRepository.qryProdSubsReEventForSubs(offerVerId, spId,
      notReType);

    return ResponseEntity.ok(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data1));
  }

  public BaseResponseDto insertEventRecurring (InsertRecurringEventRequest dto) {
    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<Character> recurringReType = dto.getRecurringReType();
    String pricePlanName = offerVerRepository.findOfferNameByOfferVerId(dto.getOfferVerId());

    for (Character eventName : recurringReType) {
      String recurringReTypeName = recurringReTypeRepository.findNameByRecurringReType(eventName);
      String reName = String.format("%s ( %s )", recurringReTypeName, pricePlanName);

      Re re = new Re();
      re.setReName(reName);
      re.setReType('9');
      re.setSpId(0);
      reRepository.save(re);

      RePpRecurring rePpRecurring = new RePpRecurring();
      RePpRecurringId rePpRecurringId = new RePpRecurringId();
      rePpRecurringId.setReId(re.getId());
      rePpRecurringId.setPricePlanId(dto.getPricePlanId());
      rePpRecurring.setId(rePpRecurringId);
      rePpRecurring.setRecurringReType(eventName);
      rePpRecurring.setSpId(0);
      rePpRecurring.setState('A');
      rePpRecurringRepository.save(rePpRecurring);

      RePricePlanId rePricePlanId = new RePricePlanId();
      rePricePlanId.setOfferVerId(dto.getOfferVerId());
      rePricePlanId.setReId(re.getId());

      if (rePricePlanRepository.findById(rePricePlanId).isPresent()) {
        baseResponseDto.setCode(EnumRC.BAD_REQUEST.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage("Event already exist");
        return baseResponseDto;
      }

      RePricePlan rePricePlan = new RePricePlan();
      rePricePlan.setId(rePricePlanId);
      rePricePlan.setSpId(0);
      rePricePlanRepository.save(rePricePlan);

    }

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;
  }

  public BaseResponseDto insertUsageEvent (InsertUsageEventRequest dto) {

    BaseResponseDto baseResponseDto = new BaseResponseDto();

    List<Integer> usageEvents = dto.getReId();

    for (Integer event : usageEvents) {
      RePricePlanId rePricePlanId = new RePricePlanId();
      rePricePlanId.setOfferVerId(dto.getOfferVerId());
      rePricePlanId.setReId(event);

      if (rePricePlanRepository.existsById(rePricePlanId)) {
        continue;
      }

      RePricePlan rePricePlan = new RePricePlan();
      rePricePlan.setId(rePricePlanId);
      rePricePlan.setSpId(0);
      rePricePlanRepository.save(rePricePlan);
    }

    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
    return baseResponseDto;
  }

  @Transactional
  public ResponseEntity<CustomeResponse> deleteUsageEvent (Integer offerVerId, Integer reId) {
    if (offerVerId == null || reId == null) {
      throw new ValidationHandler(EnumRC.BAD_REQUEST.getMessage());
    }

    rePricePlanRepository.callDeleteRePricePlanProcedure(reId, offerVerId);

    return ResponseEntity.status(HttpStatus.CREATED)
      .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }
}
