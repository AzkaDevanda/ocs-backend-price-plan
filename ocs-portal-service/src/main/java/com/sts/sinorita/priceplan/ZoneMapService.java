package com.sts.sinorita.priceplan;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.mapper.pricePlan.QryReAttrByReAttrTypeMapper;
import com.sts.sinorita.mapper.pricePlan.rateplan.QryActiveZoneMapMapper;
import com.sts.sinorita.mapper.pricePlan.rateplan.QryZoneByAllMapping;
import com.sts.sinorita.projection.ZoneProjection;
import com.sts.sinorita.projection.pricePlan.QueryEnumProjection;
import com.sts.sinorita.repository.ReAttrRepository;
import com.sts.sinorita.repository.RpzMappingSrcValueRepository;
import com.sts.sinorita.repository.ZoneMapRepository;
import com.sts.sinorita.repository.ZoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ZoneMapService {
    // ==========> REPOSITORY <==========
    @Autowired
    private ZoneMapRepository zoneMapRepository;
    @Autowired
    private ReAttrRepository reAttrRepository;
    @Autowired
    private RpzMappingSrcValueRepository rpzMappingSrcValueRepository;
    @Autowired
    private ZoneRepository zoneRepository;

    // ==========> MAPPER <==========
    @Autowired
    private QryActiveZoneMapMapper qryActiveZoneMapMapper;
    @Autowired
    private QryReAttrByReAttrTypeMapper qryReAttrByReAttrTypeMapper;
    @Autowired
    private QryZoneByAllMapping qryZoneByAllMapping;

    public ResponseEntity<CustomeResponse> qryActiveZoneMap(Integer zoneMapId, String zoneMapName, Integer spId) {
        var data = zoneMapRepository.qryActiveZoneMap(zoneMapId, zoneMapName, spId)
                .stream()
                .map(qryActiveZoneMapMapper::toDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> qryZoneByAll(Long zoneId, Long parentZoneId, Long zoneMapId, Long spId) {
        List<ZoneProjection> list = zoneMapRepository.qryZoneByAll(zoneId, parentZoneId, zoneMapId, spId);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, list));
    }

    public ResponseEntity<CustomeResponse> listReAttrMapping(Character reAttrType, String reAttrName, Integer spId) {
        var data = reAttrRepository.qryReAttrByReAttrType(reAttrType, reAttrName, spId)
                .stream()
                .map(qryReAttrByReAttrTypeMapper::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> listEnum() {
        List<QueryEnumProjection> list = rpzMappingSrcValueRepository.queryEnum();

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, list));
    }

    public ResponseEntity<CustomeResponse> getZoneByAll(Integer zoneMapId) {
        var data = zoneRepository.qryZoneByAll(zoneMapId)
                .stream()
                .map(qryZoneByAllMapping::toDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }
}
