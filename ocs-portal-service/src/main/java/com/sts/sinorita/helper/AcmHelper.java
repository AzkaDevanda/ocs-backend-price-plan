package com.sts.sinorita.helper;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.AcmInstData;
import com.sts.sinorita.dto.request.balanceAdjustment.QryAcmDict;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsAcmInstData;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsAcmProdData;
import com.sts.sinorita.dto.response.balanceAdjustment.AcmCycleTypeDto;
import com.sts.sinorita.dto.response.balanceAdjustment.RatableResourceDto;
import com.sts.sinorita.mapper.balanceAdjustment.AcmCycleTypeMapper;
import com.sts.sinorita.mapper.balanceAdjustment.RatableResourceMapper;
import com.sts.sinorita.projection.balanceAdjustment.AcmValueProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectCurBillingCycleIDByAcctIdProjection;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.util.DateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public final class AcmHelper {
  static Logger logger = LoggerFactory.getLogger(AcmHelper.class);
  private static SubsAcmCycleRepository subsAcmCycleRepository;
  private static SubsAcmDailyRepository subsAcmDailyRepository;
  private static SubsAcmRepository subsAcmRepository;
  private static AcctAcmCycleRepository acctAcmCycleRepository;
  private static AcctAcmRepository acctAcmRepository;
  private static AcctAcmDailyRepository acctAcmDailyRepository;
  private static SubsAcmProdRepository subsAcmProdRepository;
  private static SubsAcmInstRepository subsAcmInstRepository;
  private static RatableResourceRepository ratableResourceRepository;
  private static RatableResourceMapper ratableResourceMapper;
  private static AcmCycleTypeRepository acmCycleTypeRepository;
  private static AcmCycleRepository acmCycleRepository;
  private static ConfigItemRepository configItemRepository;
  private static BillingCycleRepository billCycleRepository;
  private static AcmCycleTypeMapper acmCycleTypeMapper;
  @PersistenceContext
  private static EntityManager em;

  public static void insertAcm(AcmInstData acmInst, Long routingId) {
    // AssertUtil.isNotNull(acmInst, "acmInst is null");
    // AssertUtil.isNotNull(acmInst);
    // AssertUtil.isNotNull(acmInst.getResourceId());
    // fillUpAcmType(acmInst);
    // AssertUtil.isNotNull(acmInst.getAcmType());
    // AssertUtil.isNotNull(acmInst.getAcmValue());

    String acmType = acmInst.getAcmType();
    Long value = acmInst.getAcmValue();

    switch (acmType) {

      case "1":
      case "J":
        subsAcmCycleRepository.insertSubsAcmCycle(acmInst.getSubsId(), acmInst.getResourceId(),
            acmInst.getBillingCycleId(), value);
        break;

      case "2":
        subsAcmRepository.insertSubsAcm(acmInst.getSubsId(), acmInst.getResourceId(), value);
        break;

      case "3":
        acctAcmCycleRepository.insertAcctAcmCycle(acmInst.getAcctId(), acmInst.getResourceId(),
            acmInst.getBillingCycleId(), value);
        break;

      case "4":
        acctAcmRepository.insertAcctAcm(acmInst.getAcctId(), acmInst.getResourceId(), value);
        break;

      case "7":
        subsAcmDailyRepository.insertSubsAcmDaily(acmInst.getSubsId(), acmInst.getResourceId(), acmInst.getDateStamp(),
            value);
        break;

      case "8":
        acctAcmDailyRepository.insertAcctAcmDaily(acmInst.getAcctId(), acmInst.getResourceId(), acmInst.getDateStamp(),
            value);
        break;

      case "5":
      case "6":
      case "9":
        logger.warn("Not support ACM type = {}", acmType);
        break;

      case "A":
        insertAcmCycleDynamic(acmInst);
        break;

      default:
        logger.warn("Unknown ACM type = {}", acmType);
    }
  }

  public static int updateAcm(com.sts.sinorita.dto.request.balanceAdjustment.AcmInstData acmInst, Long routingId) {
    // AssertUtil.isNotNull(acmInst, "acmInst is null");
    String type = acmInst.getAcmType();
    // IAcmHelperDAO acmHelperDAO = (IAcmHelperDAO)
    // DAOFactory.createModuleDAO("AcmHelper", "billing.coreapi",
    // JdbcUtil4BC.getDbCache(routingId), routingId);
    int result = 0;
    Long subsId = acmInst.getSubsId();
    if (subsId == null) {
      logger.debug("subsId of AcmInstData is null, not need to deal SubsAcmProd and SubsAcmInst");
      if ("1".equals(type) || "J".equals(type)) {
        result = subsAcmCycleRepository.updateSubsAcmCycle(acmInst.getAcmValue(), acmInst.getSubsId(),
            acmInst.getResourceId(), acmInst.getBillingCycleId());
      }
      if ("2".equals(type)) {
        result = subsAcmRepository.updateSubsAcm(acmInst.getAcmValue(), acmInst.getSubsId(), acmInst.getResourceId());
      }
      if ("3".equals(type)) {
        result = acctAcmCycleRepository.updateAcctAcmCycle(acmInst.getAcmValue(), acmInst.getAcctId(),
            acmInst.getResourceId(), acmInst.getBillingCycleId());
      }
      if ("4".equals(type)) {
        result = acctAcmRepository.updateAcctAcm(acmInst.getAcmValue(), acmInst.getAcctId(), acmInst.getResourceId());
      }
      if ("7".equals(type)) {
        result = subsAcmDailyRepository.updateSubsAcmDaily(acmInst.getAcmValue(), acmInst.getSubsId(),
            acmInst.getResourceId(), acmInst.getDateStamp());
      }
      if ("8".equals(type)) {
        result = acctAcmDailyRepository.updateAcctAcmDaily(acmInst.getAcmValue(), acmInst.getAcctId(),
            acmInst.getResourceId(), acmInst.getDateStamp());
      }
    }
    String prodIds = acmInst.getProdIds();
    if (!prodIds.isEmpty()) {
      String[] prodIdList = prodIds.split("\\|");
      SubsAcmProdData subsAcmProdData = null;
      int prodAcmResult = 0;
      for (String prodId : prodIdList) {
        subsAcmProdData = new SubsAcmProdData();
        subsAcmProdData.setProdId(Long.parseLong(prodId));
        subsAcmProdData.setResourceId(acmInst.getResourceId());
        subsAcmProdData.setSubsId(acmInst.getSubsId());
        subsAcmProdData.setValue(acmInst.getAcmValue());
        prodAcmResult = subsAcmProdRepository.updateSubsAcmProd(subsAcmProdData.getValue(), subsAcmProdData.getSubsId(),
            subsAcmProdData.getResourceId(), subsAcmProdData.getProdId());
        if (prodAcmResult == 0)
          subsAcmProdRepository.insertSubsAcmProd(subsAcmProdData.getSubsId(), subsAcmProdData.getResourceId(),
              subsAcmProdData.getProdId(), subsAcmProdData.getValue());
      }
    }
    String subsUppInstIds = acmInst.getSubsUppInstId();
    if (!subsUppInstIds.isEmpty()) {
      String[] subsUppInstIdList = subsUppInstIds.split("\\|");
      SubsAcmInstData subsAcmInstData = null;
      int subsUppInstAcmResult = 0;
      for (String subsUppInstId : subsUppInstIdList) {
        subsAcmInstData = new SubsAcmInstData();
        subsAcmInstData.setInstId(Long.parseLong(subsUppInstId));
        subsAcmInstData.setResourceId(acmInst.getResourceId());
        subsAcmInstData.setSubsId(acmInst.getSubsId());
        subsAcmInstData.setValue(acmInst.getAcmValue());
        subsUppInstAcmResult = subsAcmInstRepository.updateSubsAcmInst(subsAcmInstData.getValue(),
            subsAcmInstData.getSubsId(), subsAcmInstData.getResourceId(), subsAcmInstData.getInstId());
        if (subsUppInstAcmResult == 0)
          subsAcmInstRepository.insertSubsAcmInst(subsAcmInstData.getSubsId(), subsAcmInstData.getResourceId(),
              subsAcmInstData.getInstId(), subsAcmInstData.getValue());
      }
    }
    return result;
  }

  public static void insertAcmCycleDynamic(AcmInstData data) {

    // AssertUtil.isNotNull(data.getCycleBeginDate());
    // AssertUtil.isNotNull(data.getCycleEndDate());

    String tableName = getAcmTypeTableNameByAcmType(data.getResourceId());

    String sql = """
            INSERT INTO %s
            (SUBS_ID, ACM_CYCLE_TYPE_ID, CYCLE_BEGIN_DATE, CYCLE_END_DATE, VALUE)
            VALUES (?, ?, ?, ?, ?)
        """.formatted(tableName);

    Long begin = Long.valueOf(
        DateUtil.date2String(data.getCycleBeginDate(), "yyyyMMddHHmmss"));

    Long end = Long.valueOf(
        DateUtil.date2String(data.getCycleEndDate(), "yyyyMMddHHmmss"));

    em.createNativeQuery(sql)
        .setParameter(1, data.getSubsId())
        .setParameter(2, data.getResourceId())
        .setParameter(3, begin)
        .setParameter(4, end)
        .setParameter(5, data.getAcmValue())
        .executeUpdate();
  }

  private static String getAcmTypeTableNameByAcmType(Long resourceId) {
    // AssertUtil.isNotNull(resourceId, "resourceId is missing.");
    // AcmCycleTypeDto acmCycleTypeDto = BcCache.getAcmCycleTypeById(resourceId);
    // AssertUtil.isNotNull(acmCycleTypeDto, "acmCycleTypeDto is missing.");
    String acmTableName = "ACM_CYCLE";
    // if ("D".equals(acmCycleTypeDto.getRefType()))
    // acmTableName = "ACM_CYCLE_BFE";
    return acmTableName;
  }

  public static void qryAcmResult(QryAcmDict dict) {
    // Long routingId = dict.getRoutingId();
    Long acctId = dict.getAcctId();
    Long resourceId = dict.getResourceId();
    LocalDateTime begDate = null;
    String acmType = dict.getAcmType();
    // AssertUtil.isNotNull(resourceId, "ResourceId can not be null");
    // AssertUtil.isNotNull(acctId, "AcctId can not be null");
    if (acmType.isEmpty()) {
      RatableResourceDto ratableResourceDto = ratableResourceRepository.selectRatableResource(resourceId)
          .map(ratableResourceMapper::toRatableResourceDto)
          .orElse(null);
      if (ratableResourceDto == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00423"));
      dict.setAcmType(ratableResourceDto.getAcmType());
    }
    AcmCycleTypeDto acmCycleTypeDto = acmCycleTypeRepository.getAcmCycleTypeById(resourceId)
        .map(acmCycleTypeMapper::toAcmCycleTypeDto)
        .orElse(null);
    if (acmCycleTypeDto != null && acmCycleTypeDto.getBeginDate() != null) {
      begDate = acmCycleTypeDto.getBeginDate();
      Optional<ConfigItemParamProjection> findIsNeedSecond = configItemRepository.findConfigItem("ACCT",
          "ACCOUNT_PUBLIC", "IS_NEED_SECOND_FOR_ACM_TYPE_SUBS_CYCLE");
      String isNeedSecond = findIsNeedSecond
          .map(ConfigItemParamProjection::getDefaultValue)
          .orElse("");
      if ("Y".equals(isNeedSecond) && "C".equals(acmCycleTypeDto.getRefType())) {
        dict.setBeginDate(DateUtil.date2String(begDate, "yyyyMMddHHmmss"));
        // dict.set("BEGIN_DATE", DateUtil.date2String(begDate, "yyyyMMddHHmmss"));
      } else {
        dict.setBeginDate(DateUtil.date2String(begDate, "yyyyMMdd"));
      }
    } else {
      Long curBillincgCycleId = billCycleRepository.selectCurBillingCycleId(acctId)
          .map(SelectCurBillingCycleIDByAcctIdProjection::getBillingCycleId)
          .orElse(null);
      if (curBillincgCycleId == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00077"));
      dict.setBillingCycleId(curBillincgCycleId);
      // dict.set("BILLING_CYCLE_ID", curBillingCycleId);
    }
    // IAcmHelperDAO acmHelperDAO = (IAcmHelperDAO)
    // DAOFactory.createModuleDAO("AcmHelper", "billing.coreapi",
    // JdbcUtil4BC.getDbCache(routingId), routingId);
    AcmInstData data = getAcmValue(dict);
    if (data != null)
      dict.setAcmValue(data.getAcmValue());
  }

  // public static void setSubsAcmCycleRepository(SubsAcmCycleRepository subsAcmCycleRepository) {
  //   AcmHelper.subsAcmCycleRepository = subsAcmCycleRepository;
  // }

  // public static void setSubsAcmDailyRepository(SubsAcmDailyRepository subsAcmDailyRepository) {
  //   AcmHelper.subsAcmDailyRepository = subsAcmDailyRepository;
  // }

  // public static void setSubsAcmRepository(SubsAcmRepository subsAcmRepository) {
  //   AcmHelper.subsAcmRepository = subsAcmRepository;
  // }

  // public static void setAcctAcmCycleRepository(AcctAcmCycleRepository acctAcmCycleRepository) {
  //   AcmHelper.acctAcmCycleRepository = acctAcmCycleRepository;
  // }

  // public static void setAcctAcmRepository(AcctAcmRepository acctAcmRepository) {
  //   AcmHelper.acctAcmRepository = acctAcmRepository;
  // }

  // public static void setAcctAcmDailyRepository(AcctAcmDailyRepository acctAcmDailyRepository) {
  //   AcmHelper.acctAcmDailyRepository = acctAcmDailyRepository;
  // }

  // public static void setSubsAcmProdRepository(SubsAcmProdRepository subsAcmProdRepository) {
  //   AcmHelper.subsAcmProdRepository = subsAcmProdRepository;
  // }

  // public static void setSubsAcmInstRepository(SubsAcmInstRepository subsAcmInstRepository) {
  //   AcmHelper.subsAcmInstRepository = subsAcmInstRepository;
  // }

  // public static void setRatableResourceRepository(RatableResourceRepository ratableResourceRepository) {
  //   AcmHelper.ratableResourceRepository = ratableResourceRepository;
  // }

  // public static void setRatableResourceMapper(RatableResourceMapper ratableResourceMapper) {
  //   AcmHelper.ratableResourceMapper = ratableResourceMapper;
  // }

  // public static void setAcmCycleTypeRepository(AcmCycleTypeRepository acmCycleTypeRepository) {
  //   AcmHelper.acmCycleTypeRepository = acmCycleTypeRepository;
  // }

  // public static void setAcmCycleTypeMapper(AcmCycleTypeMapper acmCycleTypeMapper) {
  //   AcmHelper.acmCycleTypeMapper = acmCycleTypeMapper;
  // }

  // public static ConfigItemRepository getConfigItemRepository() {
  //   return configItemRepository;
  // }

  // public static void setConfigItemRepository(ConfigItemRepository configItemRepository) {
  //   AcmHelper.configItemRepository = configItemRepository;
  // }

  public static AcmInstData getAcmValue(QryAcmDict dict) {

    String acmType = dict.getAcmType();
    Long subsId = dict.getSubsId();
    Long acctId = dict.getAcctId();
    Long resourceId = dict.getResourceId();
    Long billingCycleId = dict.getBillingCycleId();
    Long beginDate = dict.getBeginDate() != null ? Long.valueOf(dict.getBeginDate()) : null;

    Long today = Long.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

    Optional<AcmValueProjection> result = Optional.empty();

    switch (acmType) {

      case "A" -> result = acmCycleRepository.getAcmTypeA(subsId, resourceId, beginDate);
      case "1" -> result = subsAcmCycleRepository.getAcmType1(subsId, resourceId, billingCycleId);
      case "2" -> result = subsAcmRepository.getAcmType2(
          subsId,
          resourceId);

      case "7" -> result = subsAcmDailyRepository.getAcmType7(
          subsId,
          resourceId,
          today);

      case "3" -> result = acctAcmCycleRepository.getAcmType3(
          acctId,
          resourceId,
          billingCycleId);

      case "4" -> result = acctAcmRepository.getAcmType4(
          acctId,
          resourceId);

      case "8" -> result = acctAcmDailyRepository.getAcmType8(
          acctId,
          resourceId,
          today);
    }

    // convert hasil ke AcmInstData (sama seperti kode lama)
    AcmInstData data = new AcmInstData();
    result.ifPresent(r -> data.setAcmValue(r.getValue()));

    return data;
  }

}
