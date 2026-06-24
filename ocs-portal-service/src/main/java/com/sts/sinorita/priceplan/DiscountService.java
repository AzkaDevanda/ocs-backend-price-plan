package com.sts.sinorita.priceplan;

import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.MappingAccountItemType;
import com.sts.sinorita.dto.request.DisctObjRequestDto;
import com.sts.sinorita.dto.request.priceplan.discount.DiscountRequestDto;
import com.sts.sinorita.dto.request.priceplan.discount.DpRuleRequestDto;
import com.sts.sinorita.dto.request.priceplan.discount.TabDpCondGrpDtListRequestDto;
import com.sts.sinorita.dto.request.priceplan.discount.TabDpDtRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.entity.*;
import com.sts.sinorita.entity.Dp;
import com.sts.sinorita.mapper.offer.OfferMapper;
import com.sts.sinorita.mapper.pricePlan.discount.*;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.repository.offer.OfferAttrRepository;
import com.sts.sinorita.utils.GeneratorXml;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DiscountService {

    @Autowired
    private GeneratorXml generatorXml;
    @Autowired
    private EntityManager entityManager;

    // =====> REPOSITORY <====
    @Autowired
    private DpTypeRepository dpTypeRepository;
    @Autowired
    private DisctCalcMethodRepository disctCalcMethodRepository;
    @Autowired
    private DisctObjTypeRepository disctObjTypeRepository;
    @Autowired
    private SortOperatorRepository sortOperatorRepository;
    @Autowired
    private TabDpTypeRepository tabDpTypeRepository;
    @Autowired
    private DistributeMethodRepository distributeMethodRepository;
    @Autowired
    private DpRefCondRepository dpRefCondRepository;
    @Autowired
    private DpRepository dpRepository;
    @Autowired
    private DpRuleRepository dpRuleRepository;
    @Autowired
    private RefAcctItemTypeRepository refAcctItemTypeRepository;
    @Autowired
    private RefDpRepository refDpRepository;
    @Autowired
    private DisctObjRepository disctObjRepository;
    @Autowired
    private DisctObjAitidRepository disctObjAitidRepository;
    @Autowired
    private TabDpCondGrpRepository tabDpCondGrpRepository;
    @Autowired
    private TabDpCondGrpDtRepository tabDpCondGrpDtRepository;
    @Autowired
    private TabDpRepository tabDpRepository;
    @Autowired
    private DisctObjAitidRepository disctObjAitiesRepository;
    @Autowired
    private TabDpRepository tabDpsRepository;
    @Autowired
    private RefValueRepository refValueRepository;
    @Autowired
    private TabDpDtRepository tabDpDtRepository;
    @Autowired
    private OfferAttrRepository offerAttrRepository;
    @Autowired
    private OfferRepository offerRepository;

    // =====> MAPPER <====
    @Autowired
    private DpTypeMapper dpTypeMapper;
    @Autowired
    private DisctCalcMethodMapper disctCalcMethodMapper;
    @Autowired
    private DisctObjTypeMapper disctObjTypeMapper;
    @Autowired
    private SortOperatorMapper sortOperatorMapper;
    @Autowired
    private DistributeMethodMapper distributeMethodMapper;
    @Autowired
    private DpRefCondMapper dpRefCondMapper;
    @Autowired
    private DpMapper dpMapper;
    @Autowired
    private DisctObjAitidMapper disctObjAitidMapper;
    @Autowired
    private TabDpCondGrpMapper tabDpCondGrpMapper;
    @Autowired
    private TabDpMapper tabDpMapper;
    @Autowired
    private TabDpDetailMapper tabDpDetailMapper;
    @Autowired
    private OfferMapper offerMapper;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> addDp(DiscountRequestDto dto) {
        Dp dp = new Dp();
        Integer priority = dpRepository.findNextPriority();
        dp.setOfferVerId(dto.getOfferVerId());
        dp.setDpType(dto.getDiscountType());
        dp.setDpName(dto.getDiscountName());
        dp.setComments(dto.getRemarks());
        dp.setBillingPlanType(dto.getPromotion());
        dp.setPriority(priority);
        dp.setSpId(0);
        dpRepository.save(dp);

        dto.setDpId(dp.getId());
        if (dto.getReferenceObject() != null) {
            dto.getReferenceObject().setOfferVerId(dp.getOfferVerId());
        }
        if (dto.getCalculationObject() != null) {
            dto.getCalculationObject().setOfferVerId(dp.getOfferVerId());

        }
        if (dto.getApplyingObject() != null) {
            dto.getApplyingObject().setOfferVerId(dp.getOfferVerId());
        }

        if (dto.getDiscountType().equals('E')) {
            if (dto.getDpRule() != null && !dto.getDpRule().getRuleScript().isEmpty()) {
                addDpRule(dp.getId(),dto);
            }
        }
        else if (dto.getDiscountType().equals('T')) {
            // TODO : belom tau dapet rai mana REF_DP nya
//            DynamicDict dictRefDp = dict.getBO("REF_DP");
//            if (dictRefDp != null)
//                addRefDp(dictRefDp, dpId, dict.getLong("SP_ID"));
            if (dto.getTabDiscountType() != null && dto.getDistributeMethod() !=null && dto.getNegativeResult() != null) {
                addTabDpInfo(dto);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    private void modDpName(Integer dpId,DiscountRequestDto dto) {
        if (!dto.getDiscountName().isEmpty()) {
            Optional<Dp> dpDict = dpRepository.selectDp(dpId);
            Dp dp = dpDict.get();
            dp.setOfferVerId(dto.getOfferVerId());
            dp.setDpType(dto.getDiscountType());
            dp.setDpName(dto.getDiscountName());
            dp.setComments(dto.getRemarks());
            dp.setBillingPlanType(dto.getPromotion());
            dp.setSpId(0);
            dpRepository.save(dp);
        }
    }

    private void addTabDpInfo(DiscountRequestDto dto){
        addTabDpDisctObj(dto);
        addTabDpCondGrp(dto);
        if (dto.getResultAccountItemType() == null){
            dto.setResultAccountItemType(-1);
        }
        tabDpRepository.insertTabDp(
                dto.getDpId(),
                dto.getTabDiscountType(),
                dto.getRefDisctObjId(),
                dto.getResultAccountItemType(),
                dto.getDistributeMethod(),
                dto.getTabDpCondGroupId(),
                dto.getCalcDisctObjId(),
                dto.getApplyDisctObjId(),
                dto.getNegativeResult()
        );

        addTabDpDt(dto);
    }

    private void addTabDpDisctObj(DiscountRequestDto dto) {
        DisctObjRequestDto refDisctDict = dto.getReferenceObject();
        DisctObjRequestDto calcDisctDict = dto.getCalculationObject();
        DisctObjRequestDto applyDisctDict = dto.getApplyingObject();

        if (dto.getCalculationObject() != null && dto.getCalculationObject().getObjectName() != null) {
            addDisctObj(calcDisctDict);
            dto.setCalcDisctObjId(calcDisctDict.getDisctObjId());
        } else {
            dto.setCalcDisctObjId(-1);
        }

        if (dto.getReferenceObject() != null && dto.getReferenceObject().getObjectName() != null) {
            addDisctObj(refDisctDict);
            dto.setRefDisctObjId(refDisctDict.getDisctObjId());
        }else {
            dto.setRefDisctObjId(-1);
        }

        if (dto.getApplyingObject() != null && dto.getApplyingObject().getObjectName() != null) {
            addDisctObj(applyDisctDict);
            dto.setApplyDisctObjId(applyDisctDict.getDisctObjId());
        }else {
            dto.setApplyDisctObjId(-1);
        }
    }

    private void addTabDpCondGrp(DiscountRequestDto dto){
        DisctObj disctObj = new DisctObj();
        List<TabDpCondGrpDtListRequestDto> tabDpCondGrpDtList = dto.getInsertDiscountConditionGroup();
        if (!tabDpCondGrpDtList.isEmpty()) {
            TabDpCondGrp tabDpCondGrp = new TabDpCondGrp();
            Integer tabDpCondGrpId = tabDpCondGrpRepository.getMaxValue();
            dto.setTabDpCondGroupId(tabDpCondGrpId);
            // Insert manual pakai query
            tabDpCondGrpRepository.insertTabDpCondGrp(
                    tabDpCondGrpId,
                    dto.getTabDpCondGrpName(),
                    dto.getDpRefCondType().toString()
            );

            for (int i = 0; i < tabDpCondGrpDtList.size(); i++) {
                TabDpCondGrpDtListRequestDto gropCondDict = tabDpCondGrpDtList.get(i);
                RefValue refValue = new RefValue();
                refValue.setRefValueType('1');
                refValue.setValueString(gropCondDict.getRVal());
                refValue.setStaffId(1);
                refValue.setCreatedDate(LocalDate.now());
                refValue.setState('A');
                refValue.setStateDate(LocalDate.now());
                refValue.setCostValueType(2);
                refValue.setCostValueType(0);
                refValue.setOfferVerId(dto.getOfferVerId());
                refValueRepository.save(refValue);

                tabDpCondGrpDtRepository.insertTabDpCondGrpDt(
                        tabDpCondGrpId,
                        gropCondDict.getGrpId(),
                        gropCondDict.getSeqNo(),
                        gropCondDict.getLDpRefCondId(),
                        gropCondDict.getLParam1(),
                        String.valueOf(gropCondDict.getSortOperator()),
                        refValue.getId().toString()
                );
                entityManager.flush();
                entityManager.clear();

                // TODO : Masih nyari ini REF_RIGHT_VALUE dari mana
//                if (null != gropCondDict.getBO("REF_RIGHT_VALUE"))
//                    refValue = (RefValue)BoHelper.boToDto(gropCondDict.getBO("REF_RIGHT_VALUE"), RefValue.class);
//                if (null != refValue) {
//                    refValueMgr.addRefValue(refValue);
//                    gropCondDict.set("R_VAL", refValue.getRefValueId());
//                    refValue = null;
//                }
            }
        } else {
            dto.setTabDpCondGroupId(-1);
        }
    }

    private void addTabDpDt(DiscountRequestDto dto) {
        List<TabDpDtRequestDto> tabDpDtRequestDtoList = dto.getDiscountDetail();
        if (!tabDpDtRequestDtoList.isEmpty()) {
            for (int j = 0; j < tabDpDtRequestDtoList.size(); j++) {
                TabDpDtRequestDto tabDpDtRequestDtoDict = tabDpDtRequestDtoList.get(j);
                tabDpDtRequestDtoDict.setOfferVerId(dto.getOfferVerId());
                if (tabDpDtRequestDtoDict.getDiscountValue().getRefValue() != null && !tabDpDtRequestDtoDict.getDiscountValue().getRefValue().isEmpty()){
                    RefValue refValue = new RefValue();
                    refValue.setRefValueType('1');
                    refValue.setValueString(tabDpDtRequestDtoDict.getDiscountValue().getRefValue());
                    refValue.setStaffId(1);
                    refValue.setCreatedDate(LocalDate.now());
                    refValue.setState('A');
                    refValue.setStateDate(LocalDate.now());
                    refValue.setCostValueType(2);
                    refValue.setCostValueType(0);
                    refValue.setOfferVerId(dto.getOfferVerId());
                    refValueRepository.save(refValue);

                    tabDpDtRequestDtoDict.setCalcVal(refValue.getId());
                } else {
                    tabDpDtRequestDtoDict.setCalcVal(0);
                }
                if (tabDpDtRequestDtoDict.getDiscountValue().getRefCellValue() != null && !tabDpDtRequestDtoDict.getDiscountValue().getRefCellValue().isEmpty()){
                    RefValue refValue = new RefValue();
                    refValue.setRefValueType('1');
                    refValue.setValueString(tabDpDtRequestDtoDict.getDiscountValue().getRefCellValue());
                    refValue.setStaffId(1);
                    refValue.setCreatedDate(LocalDate.now());
                    refValue.setState('A');
                    refValue.setStateDate(LocalDate.now());
                    refValue.setCostValueType(2);
                    refValue.setCostValueType(0);
                    refValue.setOfferVerId(dto.getOfferVerId());
                    refValueRepository.save(refValue);

                    tabDpDtRequestDtoDict.setFVal(refValue.getId());

                } else {
                    tabDpDtRequestDtoDict.setFVal(0);
                }
                if (tabDpDtRequestDtoDict.getDiscountValue().getRefFloorValue() != null && !tabDpDtRequestDtoDict.getDiscountValue().getRefFloorValue().isEmpty()){
                    RefValue refValue = new RefValue();
                    refValue.setRefValueType('1');
                    refValue.setValueString(tabDpDtRequestDtoDict.getDiscountValue().getRefFloorValue());
                    refValue.setStaffId(1);
                    refValue.setCreatedDate(LocalDate.now());
                    refValue.setState('A');
                    refValue.setStateDate(LocalDate.now());
                    refValue.setCostValueType(2);
                    refValue.setCostValueType(0);
                    refValue.setOfferVerId(dto.getOfferVerId());
                    refValueRepository.save(refValue);

                    tabDpDtRequestDtoDict.setCVal(refValue.getId());

                } else {
                    tabDpDtRequestDtoDict.setCVal(0);
                }

                validateDiscountValue(dto.getDiscountDetail());

                dealSValAndEVal(tabDpDtRequestDtoDict);

                tabDpDtRepository.insertTabDpDt(
                        dto.getDpId(),
                        tabDpDtRequestDtoDict.getSeqNo().shortValue(),
                        String.valueOf(tabDpDtRequestDtoDict.getDisctCalcMethod()),
                        tabDpDtRequestDtoDict.getCalcVal(),
                        tabDpDtRequestDtoDict.getFVal(),
                        tabDpDtRequestDtoDict.getCVal(),
                        tabDpDtRequestDtoDict.getSVal(),   // langsung String
                        tabDpDtRequestDtoDict.getEVal()
                );

            }
        }

    }

    private void validateDiscountValue(List<TabDpDtRequestDto> discountDetailList){
        if (discountDetailList == null || discountDetailList.isEmpty()) {
            return;
        }

        // Validasi tiap elemen: sVal <= eVal
        for (TabDpDtRequestDto detail : discountDetailList) {
            int start = Integer.parseInt(detail.getSVal());
            int end   = Integer.parseInt(detail.getEVal());

            if (start > end) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "start value cannot be greater than end value"
                );
            }
        }

        // Validasi overlap antar detail (kalau size > 1)
        List<TabDpDtRequestDto> sorted = discountDetailList.stream()
                .sorted(Comparator.comparingInt(d -> Integer.parseInt(d.getSVal())))
                .toList();

        for (int i = 0; i < sorted.size() - 1; i++) {
            int currStart = Integer.parseInt(sorted.get(i).getSVal());
            int currEnd   = Integer.parseInt(sorted.get(i).getEVal());

            int nextStart = Integer.parseInt(sorted.get(i + 1).getSVal());
            int nextEnd   = Integer.parseInt(sorted.get(i + 1).getEVal());

            if (nextStart <= currEnd) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Range overlap detected: [%d - %d] overlap with [%d - %d]",
                        currStart, currEnd, nextStart, nextEnd)
                );
            }
        }

    }

    private void dealSValAndEVal(TabDpDtRequestDto dto) {
        if (dto.getSVal() != null) {
            RefValue refValue = new RefValue();
            refValue.setRefValueType('1');
            refValue.setValueString(dto.getSVal());
            refValue.setStaffId(1);
            refValue.setCreatedDate(LocalDate.now());
            refValue.setState('A');
            refValue.setStateDate(LocalDate.now());
            refValue.setCostValueType(2);
            refValue.setCostValueType(0);
            refValue.setOfferVerId(dto.getOfferVerId());
            refValueRepository.save(refValue);

            dto.setSVal(refValue.getId().toString());
        }

        if (dto.getEVal() != null) {
            RefValue refValue = new RefValue();
            refValue.setRefValueType('1');
            refValue.setValueString(dto.getEVal());
            refValue.setStaffId(1);
            refValue.setCreatedDate(LocalDate.now());
            refValue.setState('A');
            refValue.setStateDate(LocalDate.now());
            refValue.setCostValueType(2);
            refValue.setCostValueType(0);
            refValue.setOfferVerId(dto.getOfferVerId());
            refValueRepository.save(refValue);

            dto.setEVal(refValue.getId().toString());
        }

    }

    private void addDisctObj(DisctObjRequestDto dto) {
        List<TabDpCondGrpDtListRequestDto> objGropCondList = dto.getInsertDiscountConditionGroup();
        if (!objGropCondList.isEmpty()) {
            TabDpCondGrp tabDpCondGrp = new TabDpCondGrp();
            Integer tabDpCondGrpId = tabDpCondGrpRepository.getMaxValue();
            dto.setTabDpCondGrpId(tabDpCondGrpId);
            tabDpCondGrp.setId(tabDpCondGrpId);
            tabDpCondGrp.setTabDpCondGrpName(dto.getTabDpCondGrpName());
            tabDpCondGrp.setDpRefCondType(dto.getDpRefCondType());
            tabDpCondGrpRepository.save(tabDpCondGrp);
            for (int i = 0; i < objGropCondList.size(); i++) {
                TabDpCondGrpDtListRequestDto gropCondDict = objGropCondList.get(i);
                RefValue refValue = new RefValue();
                refValue.setRefValueType('1');
                refValue.setValueString(gropCondDict.getRVal());
                refValue.setStaffId(1);
                refValue.setCreatedDate(LocalDate.now());
                refValue.setState('A');
                refValue.setStateDate(LocalDate.now());
                refValue.setCostValueType(2);
                refValue.setCostValueType(0);
                refValue.setOfferVerId(dto.getOfferVerId());
                refValueRepository.save(refValue);
                // TODO : Masih nyari ini REF_RIGHT_VAKUE dari mana
//                if (null != gropCondDict.getBO("REF_RIGHT_VALUE"))
//                    refValue = (RefValue)BoHelper.boToDto(gropCondDict.getBO("REF_RIGHT_VALUE"), RefValue.class);
//                if (null != refValue) {
//                    refValueMgr.addRefValue(refValue);
//                    gropCondDict.set("R_VAL", refValue.getRefValueId());
//                    refValue = null;
//                }

                tabDpCondGrpDtRepository.insertTabDpCondGrpDt(
                        tabDpCondGrpId,
                        gropCondDict.getGrpId(),
                        gropCondDict.getSeqNo(),
                        gropCondDict.getLDpRefCondId(),
                        gropCondDict.getLParam1(),
                        String.valueOf(gropCondDict.getSortOperator()),
                        refValue.getId().toString()
                );
                entityManager.flush();
                entityManager.clear();

            }
        } else {
            dto.setTabDpCondGrpId(-1);
        }

        Integer disctObjId = disctObjRepository.getMaxPriority();
        dto.setDisctObjId(disctObjId);

        disctObjRepository.insertDisctObj(
                disctObjId,
                dto.getObjIdentityId() == null ? -1 : dto.getObjIdentityId(),
                dto.getGstSeq() == null ? -1 : dto.getGstSeq(),
                dto.getTabDpCondGrpId(),
                dto.getObjectName(),
                dto.getObjectType(),
                dto.getMemberAlias()
        );

        entityManager.flush();
        entityManager.clear();



        List<MappingAccountItemType> disctObjAitidList = dto.getMappingAccountItemTypes();
        if (!disctObjAitidList.isEmpty()) {
            for (int j = 0; j < disctObjAitidList.size(); j++) {
                DisctObjAitid disctObjAitid = getDisctObjAitid(disctObjAitidList, j, disctObjId);
                disctObjAitidRepository.save(disctObjAitid);

            }
        }
    }

    @NotNull
    private static DisctObjAitid getDisctObjAitid(List<MappingAccountItemType> disctObjAitidList, int j, Integer disctObjId) {
        MappingAccountItemType mappingAccountItemType = disctObjAitidList.get(j);
        DisctObjAitid disctObjAitid = new DisctObjAitid();
        DisctObjAitidId disctObjAitidId = new DisctObjAitidId();

        disctObjAitidId.setDisctObjId(disctObjId);
        disctObjAitidId.setAcctItemTypeId(mappingAccountItemType.getAcctItemTypeId());

        disctObjAitid.setId(disctObjAitidId);
        disctObjAitid.setPriority(mappingAccountItemType.getPriority());
        return disctObjAitid;
    }

    private void addDpRule(Integer dpId, DiscountRequestDto dto) {
        try {
            DpRule dpRule = new DpRule();
            byte[] scriptPage;
            dpRule.setId(dpId);
            dpRule.setScriptTempletId(dto.getDpRule().getScriptTempletId());
            dpRule.setRuleComments(dto.getDpRule().getRemarks());
            if (dto.getDpRule().getJsonScriptPage() == null) {
                dpRule.setRuleScript(dto.getDpRule().getRuleScript());
                dpRule.setScriptPage(new byte[0]);
            } else {
                scriptPage = generatorXml.getXmlScriptPage(dto.getDpRule().getScriptTempletId(), dto.getDpRule().getJsonScriptPage());
                dpRule.setScriptPage(scriptPage);
                dpRule.setRuleScript(generatorXml.getNewScriptRule(dto.getDpRule().getRuleScript(), scriptPage));
            }
            dpRule.setSpId(0);
            dpRule.setAcctItemTypeId(dto.getResultAccountItemType());
            dpRuleRepository.save(dpRule);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ERROR CREATING EXPRESSION PRICE: " + e.getMessage());
        }
    }

    private void delTabDp(Integer dpId) {
        Optional<TabDp> qryTabDpDict = tabDpRepository.selectTabDp(dpId);
        delTabDpDt(dpId);
        tabDpRepository.deleteTabDp(dpId);
        Integer tabDpCondGrpId;
        if (qryTabDpDict.isPresent()){
            Integer refDisctObjId = qryTabDpDict.get().getRefDisctObjId();
            Integer calcDisctObjId = qryTabDpDict.get().getCalcDisctObjId();
            Integer applyDisctObjId = qryTabDpDict.get().getApplyDisctObjId();
            deleteDisctObj(refDisctObjId);
            deleteDisctObj(calcDisctObjId);
            deleteDisctObj(applyDisctObjId);
            tabDpCondGrpId = qryTabDpDict.get().getTabDpCondGrpId();
            if (tabDpCondGrpId != null && !"-1".equals(tabDpCondGrpId.toString())){
                deleteTabDpCondGrpDtRefValue(tabDpCondGrpId);
                tabDpCondGrpDtRepository.deleteTabDpCondGrpDt(tabDpCondGrpId);
                entityManager.flush();
                entityManager.clear();
                deleteTabDpCondGrp(tabDpCondGrpId);
            }
        }
    }

    private void deleteDisctObj(Integer disctObjId) {
        if (disctObjId != null && !"-1".equals(disctObjId.toString())) {
            Optional<DisctObj> disctObjDict = disctObjRepository.selectDisctObject(disctObjId);
            if (disctObjDict.isPresent() &&  disctObjDict.get().getId() != null) {
                Integer tabDpCondGrpId = disctObjDict.get().getTabDpCondGrpId();
                deleteTabDpCondGrpDtRefValue(tabDpCondGrpId);
                disctObjAitidRepository.deleteDisctObjAitid(disctObjId);
                disctObjRepository.deleteDisctObject(disctObjId);
                tabDpCondGrpDtRepository.deleteTabDpCondGrpDt(tabDpCondGrpId);
                deleteTabDpCondGrp(tabDpCondGrpId);
            }
        }
    }

    private void deleteTabDpCondGrp(Integer tabDpCondGrpId) {
        if (tabDpCondGrpId == -1) {
            return;
        }
        tabDpCondGrpRepository.deleteTabDpCondGrp(tabDpCondGrpId);
    }

    private void deleteTabDpCondGrpDtRefValue(Integer tabDpCondGrpId){
        if (tabDpCondGrpId == null){
            return;
        }
        List<TabDpCondGrpDt> tabDpCondGrpDtList = tabDpCondGrpDtRepository.selectTabDpCondGrpDt(tabDpCondGrpId);
        if (!tabDpCondGrpDtList.isEmpty()){
            for (TabDpCondGrpDt tabDpCondGrpDt : tabDpCondGrpDtList) {
                Optional<RefValue> getRefValue = refValueRepository.findById(Integer.valueOf(tabDpCondGrpDt.getRVal()));
                getRefValue.ifPresent(refValue -> refValue.setState('X'));
            }
        }
    }

    private void delTabDpDt(Integer dpId) {
        List<TabDpDt> dpDtList = tabDpDtRepository.selectTabDp(dpId);
        if (!dpDtList.isEmpty()) {
            for (int i = 0; i < dpDtList.size(); i++) {
                TabDpDt dpDt = dpDtList.get(i);
                if (dpDt.getCalcVal() != null && dpDt.getCalcVal() != 0) {
                    Optional<RefValue> getRefValue = refValueRepository.findById(dpDt.getCalcVal());
                    getRefValue.get().setState('X');
                }
                if (dpDt.getFVal() != null && dpDt.getFVal() != 0) {
                    Optional<RefValue> getRefValue = refValueRepository.findById(dpDt.getFVal());
                    getRefValue.get().setState('X');
                }
                if (dpDt.getCVal() != null && dpDt.getCVal() != 0) {
                    Optional<RefValue> getRefValue = refValueRepository.findById(dpDt.getCVal());
                    getRefValue.get().setState('X');
                }
                if (dpDt.getSVal() != null && dpDt.getSVal() != 0) {
                    Optional<RefValue> getRefValue = refValueRepository.findById(dpDt.getSVal().intValue());
                    getRefValue.get().setState('X');
                }
                if (dpDt.getEVal() != null && dpDt.getEVal() != 0) {
                    Optional<RefValue> getRefValue = refValueRepository.findById(dpDt.getEVal().intValue());
                    getRefValue.get().setState('X');
                }
            }
        }
        tabDpDtRepository.deleteTabDpDt(dpId);
    }

    public ResponseEntity<CustomeResponse> getAllDpTypes() {
        var data = dpTypeRepository.findDpTypeNames()
                .stream()
                .map(dpTypeMapper::DpTypeDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).
                body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> qryDisctCalcMethod() {
        var data = disctCalcMethodRepository.qryDisctCalcMethod()
                .stream()
                .map(disctCalcMethodMapper::toQryDisctCalcMethodResponseDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> qryDisctObjType() {
        var data = disctObjTypeRepository.qryDisctObjType()
                .stream()
                .map(disctObjTypeMapper::toQryDisctObjTypeResponseDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> qrySortOperator(String sortOperator) {
        var data = sortOperatorRepository.qrySortOperator(sortOperator)
                .stream()
                .map(sortOperatorMapper::toQrySortOperatorResponseDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> getTabDpTypes(){
        var data = tabDpTypeRepository.findTabDpType()
                .stream()
                .map(dpTypeMapper::TabDpTypeDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> getDistributeMethod(){
        var data = distributeMethodRepository.getDistributeMethod()
                .stream()
                .map(distributeMethodMapper::DistributeMethodDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> getDepRefCond(Integer dpRefCondId, String dpRefCondTypes, Integer spId){
        var data = dpRefCondRepository.getDpRefCond(dpRefCondId, dpRefCondTypes, spId)
                .stream()
                .map(dpRefCondMapper::dpRefCondDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> getDpList(Integer offerVerId,
                                                     String billingPlanType,
                                                     Character billingPlanTypes,
                                                     Integer dpIdSelf,
                                                     Integer spId){
        var data = dpRepository.findDpList(offerVerId, billingPlanType, billingPlanTypes, dpIdSelf, spId)
                .stream()
                .map(dpMapper::dpDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> deleteDp(Integer id){
        Dp dp = dpRepository.findById(id).isPresent() ? dpRepository.findById(id).get() : null;

        if (dp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomeResponse(404, HttpStatusConstant.NOT_FOUND_MESSAGE, null));
        }

        if(dp.getDpType() == 'E'){
            dpRuleRepository.deleteById(id);
        }
        else if(dp.getDpType() == 'T'){
//            refAcctItemTypeRepository.deleteByDpId(id);
//            refDpRepository.deleteByDpId(id);
            delTabDp(id);
        }
        dpRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modDp(Integer id, DiscountRequestDto dto){
        Dp dp = dpRepository.findById(id).orElseThrow(() -> new RuntimeException("Dp not found"));
        if(dp.getDpType() == 'E'){
            modDpRule(id,dto);
        }
        else if(dp.getDpType() == 'T'){
            modTabDpInfo(id,dto);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modTabDpInfo(Integer dpId, DiscountRequestDto dto) {
        dto.setDpId(dpId);
        delTabDp(dpId);
        modDpName(dpId, dto);
        addTabDpInfo(dto);

    }

    @Transactional(rollbackFor = Exception.class)
    public void modDpRule(Integer id, DiscountRequestDto dto) {
        DpRuleRequestDto dpRuleRequestDto = dto.getDpRule();
        DpRule dpRule = dpRuleRepository.findById(id).orElseThrow(() -> new RuntimeException("DpRule not found"));
        try {
            dpRule.setSpId(0);
            dpRule.setAcctItemTypeId(dto.getResultAccountItemType());
            byte[] scriptPage;
            if (dpRuleRequestDto.getScriptTempletId() == null && dpRuleRequestDto.getJsonScriptPage() == null) {
                dpRule.setRuleScript(dpRuleRequestDto.getRuleScript());
                dpRule.setScriptPage(new byte[0]);
            } else {
                scriptPage = generatorXml.getXmlScriptPage(dpRuleRequestDto.getScriptTempletId(), dpRuleRequestDto.getJsonScriptPage());
                dpRule.setScriptPage(scriptPage);
                dpRule.setRuleScript(generatorXml.getNewScriptRule(dpRuleRequestDto.getRuleScript(), scriptPage));
            }
            dpRuleRepository.save(dpRule);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR CREATING EXPRESSION PRICE: " + e.getMessage());
        }
    }

    public ResponseEntity<CustomeResponse> QryDpByPk(Integer dpId, Integer spId){
        var data = dpRepository.QryDpByPk(dpId, spId).stream().map(dpMapper::qryDpByPkDto).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> QryDisctObjInfo(Integer disctObjId, Integer spId){
        var data = disctObjAitidRepository.QryDisctObjInfo(disctObjId, spId).stream().map(disctObjAitidMapper::QryDisctObjInfoDto).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> QryTabDpCondGrpDt(Integer tabDpCondGrpId,Integer spId){
        var data = tabDpCondGrpRepository.QryTabDpCondGrpDt(tabDpCondGrpId,spId).stream().map(tabDpCondGrpMapper::qryTabDpCondGrpDtDto).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> QryTabDpInfo(Integer dpId, Integer spId){
        var data = tabDpRepository.QryTabDpInfo(dpId, spId).stream().map(tabDpMapper::qryTabDpInfoDto).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> QryOfferAttrForDisctObj(Integer spId) {
        var data = offerAttrRepository.qryOfferAttrForDisctObj(spId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> QryTabDpDt(Integer dpId) {
        var data = tabDpDtRepository.qryTabDpDt(dpId).stream().map(tabDpDetailMapper::toQryTabDpDtResponseDto).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    public ResponseEntity<CustomeResponse> QryMemberAlias(){
        var data = offerRepository.qryMemberAlias().stream().map(offerMapper::toQryMemberAliasResponseDto).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

}
