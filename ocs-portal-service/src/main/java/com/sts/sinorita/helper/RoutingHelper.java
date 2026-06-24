package com.sts.sinorita.helper;

import com.sts.sinorita.dto.AcctIdentifyDto;
import com.sts.sinorita.mapper.acct.AcctMapper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AcctIdentifyRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.StringParser;
import com.sts.sinorita.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RoutingHelper {

  // ====>
  @Autowired
  private ConfigItemRepository configItemRepository;

//  @Autowired
//  private SubsIdentifyRepository subsIdentifyRepository;

  @Autowired
  private AcctIdentifyRepository acctIdentifyRepository;

  // ====> Mapper
//  @Autowired
//  private SubsIdentifyMapper subsIdentifyMapper;

  @Autowired
  private AcctMapper acctMapper;

  // LOGGER
  Logger logger = LoggerFactory.getLogger(this.getClass());



  public Long getRoutingIdByAcctId(Long acctId) {
        AssertUtil.isNotNull(acctId, "acctId is null");
        AcctIdentifyDto qryDto = new AcctIdentifyDto();
        qryDto.setAcctId(acctId);
        qryDto.setState("A");
        AcctIdentifyDto acctIdentifyDto = getAcctIdentifyDtoByParam(qryDto);
        if (acctIdentifyDto != null)
            return acctIdentifyDto.getRoutingId();
        return null;
  }

    public AcctIdentifyDto getAcctIdentifyDtoByParam(AcctIdentifyDto qryDto) {
        List<AcctIdentifyDto> acctIdentifyDtos = getAcctIdentifyListByParam(qryDto);
        if (acctIdentifyDtos != null && !acctIdentifyDtos.isEmpty())
            return acctIdentifyDtos.get(0);
        return null;
    }


    public List<AcctIdentifyDto> getAcctIdentifyListByParam(AcctIdentifyDto qryDto) {
        if (qryDto.getAcctId() == null && StringUtil.isEmpty(qryDto.getIdentifyValue()))
            return null;
        String identifyType = qryDto.getIdentifyType();
        if (StringUtil.isEmpty(identifyType))
            identifyType = "A";
        boolean fromQmdb = isReadRoutingIdxFromQmdb(identifyType).booleanValue();
        String isNewTransForQryQMDB = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "IS_NEW_TRANS_4_QRY_QMDB").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
        List<AcctIdentifyDto> acctIdentifyDtos = null;
        // IRoutingHelperDAO routingHelperDAO = null;
        if (fromQmdb && "Y".equals(isNewTransForQryQMDB)) {
            // Session session = null;
            try {
                // session = SessionContext.newSession();
                // session.beginTrans();
                // routingHelperDAO = (IRoutingHelperDAO)DAOFactory.createModuleDAORouteMaster("RoutingHelper", "bizcommon.coreapi", null, JdbcUtil4BC.getDbIdentifier(), fromQmdb);
                acctIdentifyDtos = acctIdentifyRepository.qryAcctIdentifyList(qryDto.getAcctId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getRoutingId(), qryDto.getSpId()).stream().map(acctMapper::toAcctIdentifyDto).toList();
                // session.commitTrans();
            } finally {
                // if (session != null)
                //   session.releaseTrans();
            }
        } else {
            // routingHelperDAO = (IRoutingHelperDAO)DAOFactory.createModuleDAORouteMaster("RoutingHelper", "bizcommon.coreapi", null, JdbcUtil4BC.getDbIdentifier(), fromQmdb);
            acctIdentifyDtos = acctIdentifyRepository.qryAcctIdentifyList(qryDto.getAcctId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getRoutingId(), qryDto.getSpId()).stream().map(acctMapper::toAcctIdentifyDto).toList();
        }
        if (fromQmdb && isReadOracleAfterQMDB().booleanValue() && (acctIdentifyDtos == null || acctIdentifyDtos.isEmpty())) {
            // routingHelperDAO = (IRoutingHelperDAO)DAOFactory.createModuleDAORouteMaster("RoutingHelper", "bizcommon.coreapi", null, JdbcUtil4BC.getDbIdentifier(), false);
            acctIdentifyDtos = acctIdentifyRepository.qryAcctIdentifyList(qryDto.getAcctId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getRoutingId(), qryDto.getSpId()).stream().map(acctMapper::toAcctIdentifyDto).toList();
        }
        return acctIdentifyDtos;
    }

    public Boolean isReadOracleAfterQMDB() {
        String isReadOracleAfterQMDB = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "IS_READ_ORACLE_AFTER_QMDB").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
        if ("Y".equalsIgnoreCase(isReadOracleAfterQMDB))
            return Boolean.valueOf(true);
        return Boolean.valueOf(false);
    }

    public Boolean isReadRoutingIdxFromQmdb() {
        String readType4IdxTable = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "READ_TYPE_4_INDEX_TABLE").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
        Boolean fromQmdb = Boolean.valueOf(true);
        if ("ORACLE".equals(readType4IdxTable))
            fromQmdb = Boolean.valueOf(false);
        return fromQmdb;
    }

    public Boolean isReadRoutingIdxFromQmdb(String identifyTypes) {
        String identifyTypes4QMDBLoad = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "IDENTIFY_TYPES_4_QMDB_LOAD").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
        Boolean fromQmdb = isReadRoutingIdxFromQmdb();
        if (StringUtil.isEmpty(identifyTypes4QMDBLoad) || StringUtil.isEmpty(identifyTypes))
            return fromQmdb;
        if (fromQmdb.booleanValue()) {
            String[] identifyTypeArr = identifyTypes.split(",");
            for (String identifyType : identifyTypeArr) {
                if (StringParser.checkValueInValueStr(identifyTypes4QMDBLoad, identifyType, null))
                    return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    }

//  public Long getRoutingIdBySubsId(Long subsId) {
//    AssertUtil.isNotNull(subsId, "subsId is null");
//
//    SubsIdentifyDto qryDto = new SubsIdentifyDto();
//    qryDto.setSubsId(subsId);
//    qryDto.setState("A");
//    SubsIdentifyDto subsIdentifyDto = getSubsIdentifyDtoByParam(qryDto);
//    if (subsIdentifyDto != null)
//      return subsIdentifyDto.getRoutingId();
//    return null;
//  }
//
//  @Transactional
//  public SubsIdentifyDto getSubsIdentifyDtoByParam(SubsIdentifyDto qryDto) {
//    List<SubsIdentifyDto> subsIdentifyDtos = getSubsIdentifyListByParam(qryDto);
//    if (subsIdentifyDtos != null && !subsIdentifyDtos.isEmpty())
//      return subsIdentifyDtos.get(0);
//    return null;
//  }
//
//  public List<SubsIdentifyDto> getSubsIdentifyListByParam(SubsIdentifyDto qryDto) {
//    if (qryDto.getSubsId() == null && StringUtil.isEmpty(qryDto.getIdentifyValue()))
//      return null;
//
//    String identifyType = qryDto.getIdentifyType();
//    if (StringUtil.isEmpty(identifyType))
//      identifyType = "U,M,E,I,S,V,P,D";
//
//    boolean fromQmdb = isReadRoutingIdxFromQmdb(identifyType).booleanValue();
//    String isNewTransForQryQMDB = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "IS_NEW_TRANS_4_QRY_QMDB").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
//    List<SubsIdentifyDto> subsIdentifyDtos = null;
//    if (fromQmdb && "Y".equals(isNewTransForQryQMDB)) {
//      try {
//        subsIdentifyDtos = subsIdentifyRepository.qrySubsIdentifyList(qryDto.getSubsId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getUpdateDate(), qryDto.getSubsId()).stream().map(subsIdentifyMapper::toSubsIdentifyDto).toList();
//      } finally {
//        log.debug("finally on function getSubsIdentifyListByParam");
//      }
//    } else {
//      subsIdentifyDtos = subsIdentifyRepository.qrySubsIdentifyList(qryDto.getSubsId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getUpdateDate(), qryDto.getSubsId()).stream().map(subsIdentifyMapper::toSubsIdentifyDto).toList();
//    }
//    if (fromQmdb && isReadOracleAfterQMDB().booleanValue() && (subsIdentifyDtos == null || subsIdentifyDtos.isEmpty())) {
//      subsIdentifyDtos = subsIdentifyRepository.qrySubsIdentifyList(qryDto.getSubsId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getUpdateDate(), qryDto.getSubsId()).stream().map(subsIdentifyMapper::toSubsIdentifyDto).toList();
//    }
//    return subsIdentifyDtos;
//  }
//
//  public Boolean isReadRoutingIdxFromQmdb(String identifyTypes) {
//    String identifyTypes4QMDBLoad = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "IDENTIFY_TYPES_4_QMDB_LOAD").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
//    Boolean fromQmdb = isReadRoutingIdxFromQmdb();
//    if (StringUtil.isEmpty(identifyTypes4QMDBLoad) || StringUtil.isEmpty(identifyTypes))
//      return fromQmdb;
//    if (fromQmdb.booleanValue()) {
//      String[] identifyTypeArr = identifyTypes.split(",");
//      for (String identifyType : identifyTypeArr) {
//        if (StringParser.checkValueInValueStr(identifyTypes4QMDBLoad, identifyType, null))
//          return Boolean.valueOf(true);
//      }
//    }
//    return Boolean.valueOf(false);
//  }
//
//  public Boolean isReadRoutingIdxFromQmdb() {
//    String readType4IdxTable = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "READ_TYPE_4_INDEX_TABLE").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
//    Boolean fromQmdb = Boolean.valueOf(true);
//    if ("ORACLE".equals(readType4IdxTable))
//      fromQmdb = Boolean.valueOf(false);
//    return fromQmdb;
//  }
//
//  public Boolean isReadOracleAfterQMDB() {
//    String isReadOracleAfterQMDB = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "IS_READ_ORACLE_AFTER_QMDB").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
//    if ("Y".equalsIgnoreCase(isReadOracleAfterQMDB))
//      return Boolean.valueOf(true);
//    return Boolean.valueOf(false);
//  }
//
//  public Object getCurrentRoutingId() {
//    // Object routingId = DbRoutingEnvUtil.getEnvFristRoutingID();
//    // if (routingId == null)
//    //   routingId = DbRoutingCfg.getDefaultDBRoutingID();
//    Object routingId = null;
//    if (routingId == null)
//      routingId = Long.valueOf(0L);
//    return routingId;
//  }
//
//  public Long getRoutingIdByAcctId(Long acctId) {
//    AssertUtil.isNotNull(acctId, "acctId is null");
//    AcctIdentifyDto qryDto = new AcctIdentifyDto();
//    qryDto.setAcctId(acctId);
//    qryDto.setState("A");
//    AcctIdentifyDto acctIdentifyDto = getAcctIdentifyDtoByParam(qryDto);
//    if (acctIdentifyDto != null)
//      return acctIdentifyDto.getRoutingId();
//    return null;
//  }
//
//  public Long getRoutingIdByAccNbr(String accNbr) {
//    SubsIdentifyDto qryDto = new SubsIdentifyDto();
//    qryDto.setIdentifyType("M");
//    qryDto.setIdentifyValue(accNbr);
//    qryDto.setState("A");
//    if (StringUtil.isEmpty(accNbr))
//      return null;
//    SubsIdentifyDto subsIdentifyDto = getSubsIdentifyDtoByParam(qryDto);
//    if (subsIdentifyDto != null)
//      return subsIdentifyDto.getRoutingId();
//    return null;
//  }
//
//  public AcctIdentifyDto getAcctIdentifyDtoByParam(AcctIdentifyDto qryDto) {
//    List<AcctIdentifyDto> acctIdentifyDtos = getAcctIdentifyListByParam(qryDto);
//    if (acctIdentifyDtos != null && !acctIdentifyDtos.isEmpty())
//      return acctIdentifyDtos.get(0);
//    return null;
//  }
//
//  public List<AcctIdentifyDto> getAcctIdentifyListByParam(AcctIdentifyDto qryDto) {
//    if (qryDto.getAcctId() == null && StringUtil.isEmpty(qryDto.getIdentifyValue()))
//      return null;
//    String identifyType = qryDto.getIdentifyType();
//    if (StringUtil.isEmpty(identifyType))
//      identifyType = "A";
//    boolean fromQmdb = isReadRoutingIdxFromQmdb(identifyType).booleanValue();
//    String isNewTransForQryQMDB = configItemRepository.findConfigItem("CUSTOMER_CARE", "DISTRIBUTED_SYSTEM_PARAM", "IS_NEW_TRANS_4_QRY_QMDB").map(ConfigItemParamProjection::getDefaultValue).orElse(null);
//    List<AcctIdentifyDto> acctIdentifyDtos = null;
//    // IRoutingHelperDAO routingHelperDAO = null;
//    if (fromQmdb && "Y".equals(isNewTransForQryQMDB)) {
//      // Session session = null;
//      try {
//        // session = SessionContext.newSession();
//        // session.beginTrans();
//        // routingHelperDAO = (IRoutingHelperDAO)DAOFactory.createModuleDAORouteMaster("RoutingHelper", "bizcommon.coreapi", null, JdbcUtil4BC.getDbIdentifier(), fromQmdb);
//        acctIdentifyDtos = acctIdentifyRepository.qryAcctIdentifyList(qryDto.getAcctId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getRoutingId(), qryDto.getSpId()).stream().map(acctMapper::toAcctIdentifyDto).toList();
//        // session.commitTrans();
//      } finally {
//        // if (session != null)
//        //   session.releaseTrans();
//      }
//    } else {
//      // routingHelperDAO = (IRoutingHelperDAO)DAOFactory.createModuleDAORouteMaster("RoutingHelper", "bizcommon.coreapi", null, JdbcUtil4BC.getDbIdentifier(), fromQmdb);
//      acctIdentifyDtos = acctIdentifyRepository.qryAcctIdentifyList(qryDto.getAcctId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getRoutingId(), qryDto.getSpId()).stream().map(acctMapper::toAcctIdentifyDto).toList();
//    }
//    if (fromQmdb && isReadOracleAfterQMDB().booleanValue() && (acctIdentifyDtos == null || acctIdentifyDtos.isEmpty())) {
//      // routingHelperDAO = (IRoutingHelperDAO)DAOFactory.createModuleDAORouteMaster("RoutingHelper", "bizcommon.coreapi", null, JdbcUtil4BC.getDbIdentifier(), false);
//      acctIdentifyDtos = acctIdentifyRepository.qryAcctIdentifyList(qryDto.getAcctId(), qryDto.getIdentifyType(), qryDto.getIdentifyValue(), qryDto.getState(), qryDto.getRoutingId(), qryDto.getSpId()).stream().map(acctMapper::toAcctIdentifyDto).toList();
//    }
//    return acctIdentifyDtos;
//  }
//
//  public Long getDefaultRoutingId() {
//    // String routingId = DbRoutingCfg.getDefaultDBRoutingID();
//    String routingId = null;
//    if (StringUtil.isEmpty(routingId)) {
//      logger.debug("get default routingId occur exception:Default Routing Id is null.");
//      return Long.valueOf("0");
//    }
//    return Long.valueOf(routingId);
//  }
}
