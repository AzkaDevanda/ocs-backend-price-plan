package com.sts.sinorita.priceplan;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.*;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.mapping.FlatRecordMapping;
import com.sts.sinorita.dto.response.mapping.MappingResponseDto;
import com.sts.sinorita.dto.response.mapping.PriceMappingResponse;
import com.sts.sinorita.dto.response.mapping.PriceVerMappingResponseDto;
import com.sts.sinorita.dto.response.rateplan.GetListEventFeature;
import com.sts.sinorita.entity.*;
import com.sts.sinorita.enums.EnumRC;
import com.sts.sinorita.mapper.pricePlan.rateplan.QryMappingMapper;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.validation.ValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MappingService {
    private static final Logger logger = LoggerFactory.getLogger(MappingService.class);

    @Autowired
    private ReAttrRepository reAttrRepository;

    @Autowired
    private RatePlanZoneRepository ratePlanZoneRepository;

    @Autowired
    private ZoneMapRepository zoneMapRepository;

    @Autowired
    private MappingDesTypeMasterRepository mappingDesTypeMasterRepository;

    @Autowired
    private MappingSrcTypeMasterRepository mappingSrcTypeMasterRepository;

    @Autowired
    private MappingRepository mappingRepository;

    @Autowired
    private MappingUnitRepository mappingUnitRepository;

    @Autowired
    private QryMappingMapper qryMappingMapper;

    @Autowired
    MessageService messageService;

    @NotNull
    private static MappingUnit getMappingUnit(AddMappingRequest addMappingRequest, int j, Mapping mapping) {
        AddMappingUnitRequest mappingUnitRequest = addMappingRequest.getMappingUnit().get(j);
        MappingUnitId mappingUnitId = new MappingUnitId();
        mappingUnitId.setMappingId(mapping.getId());

        mappingUnitId.setRatePlanZoneId(mappingUnitRequest.getRatePlanZoneId());

        MappingUnit mappingUnit = new MappingUnit();
        mappingUnit.setId(mappingUnitId);
        mappingUnit.setMappingType(mappingUnitRequest.getMappingType());
        mappingUnit.setMappingValue(mappingUnitRequest.getMappingValue());
        mappingUnit.setMappingMatchType(mappingUnitRequest.getMappingMatchType());
        mappingUnit.setSpId(0);
        return mappingUnit;
    }

    @NotNull
    private static MappingUnit updateMappingUnit(UpdateMappingRequest addMappingRequest, int j, Mapping mapping) {
        UpdateMappingUnitRequest mappingUnitRequest = addMappingRequest.getMappingUnit().get(j);
        MappingUnitId mappingUnitId = new MappingUnitId();
        mappingUnitId.setMappingId(mapping.getId());

        mappingUnitId.setRatePlanZoneId(mappingUnitRequest.getRatePlanZoneId());

        MappingUnit mappingUnit = new MappingUnit();
        mappingUnit.setId(mappingUnitId);
        mappingUnit.setMappingType(mappingUnitRequest.getMappingType());
        mappingUnit.setMappingValue(mappingUnitRequest.getMappingValue());
        mappingUnit.setMappingMatchType(mappingUnitRequest.getMappingMatchType());
        mappingUnit.setSpId(0);
        return mappingUnit;
    }

    public ResponseEntity<CustomeResponse> addMapping(AddMappingRequest addMappingRequest) {
        Integer priority = mappingRepository.getMaxPriority();

        Mapping mapping = new Mapping();
        mapping.setRatePlanId(addMappingRequest.getRatePlanId());
        mapping.setMappingName(addMappingRequest.getMappingName());
        mapping.setMappingExit('N');
        mapping.setPriority(priority);
        mapping.setSpId(0);
        mappingRepository.save(mapping);
        logger.info("::: MAPPING successfully saved into database ::");

        if (addMappingRequest.getMappingUnit() != null && !addMappingRequest.getMappingUnit().isEmpty()) {
            for (int i = 0; i < addMappingRequest.getMappingUnit().size(); i++) {
                MappingUnit mappingUnit = getMappingUnit(addMappingRequest, i, mapping);
                mappingUnitRepository.save(mappingUnit);
                logger.info("::: MAPPING UNIT successfully saved into database ::");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional
    public ResponseEntity<CustomeResponse> updateMapping(Integer mappingId, UpdateMappingRequest updateMappingRequest) {
        Optional<Mapping> optionalMapping = mappingRepository.findById(mappingId);
        if (optionalMapping.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(404, HttpStatusConstant.NOT_FOUND_MESSAGE, null));
        }

        Mapping mapping = optionalMapping.get();

        // update mapping
        mapping.setMappingName(updateMappingRequest.getMappingName());
        mapping.setMappingExit('N');
        mapping.setSpId(0);
        mappingRepository.save(mapping);

        logger.info("::: MAPPING updated :::");

        // 1. Ambil data existing dari DB
        List<MappingUnit> existingUnits = mappingUnitRepository.findByMappingId(mappingId);
        Map<MappingUnitId, MappingUnit> existingMap = existingUnits.stream()
                .collect(Collectors.toMap(MappingUnit::getId, Function.identity()));

        // 2. Siapkan data baru dari request
        List<MappingUnit> incomingUnits = new ArrayList<>();
        if (updateMappingRequest.getMappingUnit() != null) {
            for (int i = 0; i < updateMappingRequest.getMappingUnit().size(); i++) {
                MappingUnit unit = updateMappingUnit(updateMappingRequest, i, mapping);
                incomingUnits.add(unit);
            }
        }
        Map<MappingUnitId, MappingUnit> incomingMap = incomingUnits.stream()
                .collect(Collectors.toMap(MappingUnit::getId, Function.identity()));

        // 3. Insert or Update
        for (MappingUnitId id : incomingMap.keySet()) {
            MappingUnit incoming = incomingMap.get(id);
            if (existingMap.containsKey(id)) {
                MappingUnit existing = existingMap.get(id);
                if (!existing.equals(incoming)) {
                    existing.setMappingType(incoming.getMappingType());
                    existing.setMappingValue(incoming.getMappingValue());
                    existing.setMappingMatchType(incoming.getMappingMatchType());
                    existing.setSpId(0);
                    mappingUnitRepository.save(existing);
                    logger.info("::: MAPPING UNIT updated :::");
                }
            } else {
                mappingUnitRepository.save(incoming);
                logger.info("::: MAPPING UNIT inserted :::");
            }
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    public List<MappingResponseDto> getMappingPriceVerAndPrice(Integer ratePlanId) {
        List<FlatRecordMapping> flatRecordMappings = mappingRepository.getMappingPriceVerAndPrice(ratePlanId);
        return getMappingPriceVerAndPrice(flatRecordMappings);
    }

    private List<MappingResponseDto> getMappingPriceVerAndPrice(List<FlatRecordMapping> flatRecordMappings) {
        Map<Integer, MappingResponseDto> mappingMap = new LinkedHashMap<>();

        for (FlatRecordMapping flatRecordMapping : flatRecordMappings) {
            MappingResponseDto mapping = mappingMap.computeIfAbsent(flatRecordMapping.getMappingId(), id -> {
                MappingResponseDto m = new MappingResponseDto();
                m.setMappingId(flatRecordMapping.getMappingId());
                m.setMappingName(flatRecordMapping.getMappingName());
                m.setPriceVer(new ArrayList<>());
                return m;
            });

            PriceVerMappingResponseDto priceVer = mapping.getPriceVer().stream()
                    .filter(pv -> pv.getPriceVerId() == flatRecordMapping.getPriceVerId())
                    .findFirst()
                    .orElseGet(() -> {
                        PriceVerMappingResponseDto pv = new PriceVerMappingResponseDto();
                        pv.setPriceVerId(flatRecordMapping.getPriceVerId());
                        pv.setEffDate(String.valueOf(flatRecordMapping.getEffDate()));
                        pv.setExpDate(String.valueOf(flatRecordMapping.getExpDate()));
                        pv.setPrice(new ArrayList<>());
                        mapping.getPriceVer().add(pv);
                        return pv;
                    });

            // Pastikan bahwa priceVer.getPrice() tidak null
            if (priceVer.getPrice() == null) {
                priceVer.setPrice(new ArrayList<>()); // Inisialisasi ulang jika null
            }

            PriceMappingResponse price = new PriceMappingResponse();
            price.setPriceId(flatRecordMapping.getPriceId());
            price.setPriceName(flatRecordMapping.getPriceName());
            price.setAcctItemTypeName(flatRecordMapping.getAcctItemTypeName());
            price.setValueString(flatRecordMapping.getValueString());
            price.setPrice(flatRecordMapping.getRum());
            price.setReAttrName(flatRecordMapping.getReAttrName());

            priceVer.getPrice().add(price);
        }

        return new ArrayList<>(mappingMap.values());
    }

    public BaseResponseDto getEventFeature(Integer ratePlanId) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        List<Object[]> list = ratePlanZoneRepository.findByRatePlanId(ratePlanId);

        if (list.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        List<GetListEventFeature> responseList = list.stream().map(obj -> {
            GetListEventFeature response = new GetListEventFeature();
            response.setPriority((Integer) obj[0]);
            response.setSourceType((String) obj[1]);
            response.setSourceTypeValue((String) obj[2]);
            response.setDesType((String) obj[3]);
            response.setDesTypeValue((String) obj[4]);
            response.setLabelShow((String) obj[5]);
            return response;
        }).toList();

        baseResponseDto.setData(responseList);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

        return baseResponseDto;
    }

    public ResponseEntity<CustomeResponse> getMappingList(Integer ratePlanId, Integer spId) {
        var data = mappingRepository.qryMapping(ratePlanId, spId)
                .stream()
                .map(qryMappingMapper::toDto);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

//    public BaseResponseDto getZoneMap() {
//        BaseResponseDto baseResponseDto = new BaseResponseDto();
//        List<ZoneMap> list = zoneMapRepository.findAll(Sort.by(Sort.Direction.ASC, "zoneMapName"));
//
//        if (list.isEmpty()) {
//            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
//        }
//
//        List<ZoneMapResponse> responseList = list.stream().map(obj -> {
//            ZoneMapResponse response = new ZoneMapResponse();
//            response.setId(obj.getId());
//            response.setZoneMapName(obj.getZoneMapName());
//            return response;
//        }).toList();
//
//        baseResponseDto.setData(responseList);
//        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
//        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
//
//        return baseResponseDto;
//    }

    public BaseResponseDto getMappingDesType() {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        List<MappingDesTypeMaster> list = mappingDesTypeMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "mappingDesType"));

        if (list.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        baseResponseDto.setData(list);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());

        return baseResponseDto;
    }

    public BaseResponseDto getMappingSrcType() {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        List<MappingSrcTypeMaster> mappingSrcTypeMaster = mappingSrcTypeMasterRepository.findAll();

        if (mappingSrcTypeMaster.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        baseResponseDto.setData(mappingSrcTypeMaster);
        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        return baseResponseDto;

    }

    public BaseResponseDto eventFeatureList() {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        List<ReAttrDto> reAttr = reAttrRepository.findByReAttrName();

        if (reAttr.isEmpty()) {
            throw new ValidationHandler(EnumRC.NOT_FOUND.getMessage());
        }

        baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE().toString());
        baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
        baseResponseDto.setData(reAttr);
        return baseResponseDto;

    }


    @Transactional
    public ResponseEntity<CustomeResponse> delMapping(Integer mappingId) {
        mappingRepository.findById(mappingId)
                .ifPresent(mapping -> mappingRepository.callDeleteMappingProcedure(mapping.getId()));

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional
    public ResponseEntity<CustomeResponse> modMappingPriority(Integer mappingId, Integer newPriority) {
        Mapping selectedMapping= mappingRepository.findById(mappingId)
                .orElseThrow(() -> new ValidationHandler(EnumRC.NOT_FOUND.getMessage()));

        Integer oldPriority = selectedMapping.getPriority();

        if (oldPriority.equals(newPriority)) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, messageService.getMessage("S-RES-70005"), null));
        }

        if (newPriority < oldPriority) {
            // Geser item lain ke atas
            mappingRepository.shiftPriorityUp(newPriority, oldPriority);
        } else {
            // Geser item lain ke bawah
            mappingRepository.shiftPriorityDown(oldPriority, newPriority);
        }

        // Update priority item utama
        selectedMapping.setPriority(newPriority);
         mappingRepository.save(selectedMapping);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }
}
