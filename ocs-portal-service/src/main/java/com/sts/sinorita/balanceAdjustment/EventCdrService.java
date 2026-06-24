package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.helper.BillingSeqHelper;
import com.sts.sinorita.mapper.balanceAdjustment.CcEventTypeMapper;
import com.sts.sinorita.mapper.balanceAdjustment.CdrTemplateMapper;
import com.sts.sinorita.mapper.balanceAdjustment.CdrTemplateRuleMapper;
import com.sts.sinorita.mapper.balanceAdjustment.SubsMapper;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.util.DataFormatter;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.EqualsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

@Service
public class EventCdrService {
	private static final Logger logger = LoggerFactory.getLogger(EventCdrService.class);

	private static final String regExp = "(\\w+)=\\${([^$]+)}";
	private static final String defExp = ",DF=([^}]*)";
	// private static Pattern pattern = null;
	// private static Pattern defPattern = null;
	// private static PatternCompiler orocom = null;

	static {
		try {
			// orocom = (PatternCompiler) new Perl5Compiler();
			// pattern = orocom.compile(regExp);
			// defPattern = orocom.compile(defExp);
		} catch (Exception t) {
			logger.warn(" init static variables error.", t);
		}
	}

	@Autowired
	private CdrTemplateRepository cdrTemplateRepository;
	@Autowired
	private CdrTemplateRuleRepository cdrTemplateRuleRepository;
	@Autowired
	private CcEventTypeRepository ccEventTypeRepository;
	@Autowired
	private CcEventRepository ccEventRepository;
	@Autowired
	private SubsRepository subsRepository;

	@Autowired
	private CdrTemplateMapper cdrTemplateMapper;
	@Autowired
	private CdrTemplateRuleMapper cdrTemplateRuleMapper;
	@Autowired
	private CcEventTypeMapper ccEventTypeMapper;
	@Autowired
	private SubsMapper subsMapper;

	@Autowired
	private BillingSeqHelper billingSeqHelper;

	public static boolean absEquals(Object a, Object b) {
		if (a == null)
			return true;
		return a.equals(b);
	}

	public int perform(CdrDict dict) {
		logger.debug("Call EventCdrService the param is [{}]", dict);
		try {
			// Boolean isNeedCdr = dict.getBoolean("IS_NEED_CDR");
			Boolean isNeedCdr = dict.getIsNeedCdr();
			if (isNeedCdr != null && !isNeedCdr) {
				logger.debug("No need to record cdr.");
				return 0;
			}
			CdrTemplateRuleExDto ruleDto = matchTemplateRule(dict);
			if (ruleDto == null) {
				logger.debug("Cdr config not found.");
				// dict.set("NO_CONFIG", Boolean.TRUE);
				dict.setNoConfig(true);
				return 0;
			}
			if (ruleDto.getEventFormatId() == null) {
				logger.debug("EVENT_FORMAT_ID does not exist. ruleId = {}.", ruleDto.getCdrTemplateRuleId());
				return 0;
			}
			CdrTemplateDto cdrTemplateDto = cdrTemplateRepository.selectCdrTemplate(ruleDto.getCdrTemplateId())
					.map(cdrTemplateMapper::toCdrTemplateDto)
					.orElse(null);
			if (cdrTemplateDto == null) {
				logger.debug("CDR_TEMPLATE does not exist. templateId = {}.", ruleDto.getCdrTemplateRuleId());
				return 0;
			}
			String eventParam = createCdrParam(dict, cdrTemplateDto.getCdrContent());
			saveEventCdr(dict, ruleDto, eventParam);
		} catch (Exception ex) {
			try {
				// dict.set("IS_FAIL", Boolean.TRUE);
				dict.setIsFail(true);
			} catch (Exception e) {
				logger.warn(" 'dict.set('IS_FAIL',Boolean.TRUE)' error.", e);
			}
			logger.warn(" perform error.", ex);
		}
		return 0;
	}

	private CdrTemplateRuleExDto matchTemplateRule(CdrDict dict) {
		// String eventCode = dict.getString("CDR_SERVICE_EVENT_CODE", true);
		String eventCode = dict.getCdrServiceEventCode();
		CcEventTypeDto ccEventType = ccEventTypeRepository.getCcEventTypeByCode(eventCode)
				.map(ccEventTypeMapper::toCcEventTypeDto)
				.orElse(null);
		if (ccEventType == null) {
			logger.debug("No ccEventType found by code [{}].", eventCode);
			return null;
		}
		CdrTemplateRuleMatchDto matchDto = new CdrTemplateRuleMatchDto();
		matchDto.setEventType(ccEventType.getEventType());
		// matchDto.setPaymentMethod(dict.getLong("PAYMENT_METHOD_ID"));
		matchDto.setPaymentMethod(dict.getPaymentMethodId());
		// matchDto.setSubsEventId(dict.getLong("SUBS_EVENT_ID"));
		matchDto.setSubsEventId(dict.getSubsEventId());
		// matchDto.setContactChannelId(dict.getLong("CONTACT_CHANNEL_ID"));
		matchDto.setContactChannelId(dict.getContactChannelId());
		return matchTemplateRule(matchDto, dict);
	}

	private CdrTemplateRuleExDto matchTemplateRule(CdrTemplateRuleMatchDto matchDto, CdrDict dict) {
		List<CdrTemplateRuleExDto> ruleDtoList = cdrTemplateRuleRepository.selectAllCdrTemplateRule()
				.stream().map(cdrTemplateRuleMapper::toCdrTemplateRuleExDto).collect(Collectors.toList());
		logger.debug("Match template rule by eventCode, paymentmethod, subsEvent, contactChannelId, spId.");
		CdrTemplateRuleExDto ruleDto = matchTemplateRule(ruleDtoList, matchDto, dict, new RuleMatcher() {
			public boolean match(CdrTemplateRuleExDto ruleDto, CdrTemplateRuleMatchDto matchDto, CdrDict dict) {
				return (EqualsUtil.equals(ruleDto.getEventType(), matchDto.getEventType()) &&
						EqualsUtil.equals(ruleDto.getPaymentMethod(), matchDto.getPaymentMethod()) &&
						EqualsUtil.equals(ruleDto.getSubsEventId(), matchDto.getSubsEventId()) &&
						EqualsUtil.equals(ruleDto.getContactChannelId(), matchDto.getContactChannelId()) &&
						EqualsUtil.equals(ruleDto.getSpId(), EventCdrService.this.getSpIdByDict(dict, ruleDto)));
			}
		});
		if (ruleDto == null) {
			logger.debug("Match template rule by eventCode, paymentmethod, subsEvent.");
			ruleDto = matchTemplateRule(ruleDtoList, matchDto, dict, new RuleMatcher() {
				public boolean match(CdrTemplateRuleExDto ruleDto, CdrTemplateRuleMatchDto matchDto, CdrDict dict) {
					return (EqualsUtil.equals(ruleDto.getEventType(), matchDto.getEventType()) &&
							EqualsUtil.equals(ruleDto.getPaymentMethod(), matchDto.getPaymentMethod()) &&
							EqualsUtil.equals(ruleDto.getSubsEventId(), matchDto.getSubsEventId()) &&
							EqualsUtil.equals(ruleDto.getContactChannelId(), matchDto.getContactChannelId()) &&
							EventCdrService.absEquals(ruleDto.getSpId(), EventCdrService.this.getSpIdByDict(dict, ruleDto)));
				}
			});
		}
		if (ruleDto == null) {
			logger.debug("Match template rule by eventCode, paymentmethod, subsEvent.");
			ruleDto = matchTemplateRule(ruleDtoList, matchDto, dict, new RuleMatcher() {
				public boolean match(CdrTemplateRuleExDto ruleDto, CdrTemplateRuleMatchDto matchDto, CdrDict dict) {
					return (EqualsUtil.equals(ruleDto.getEventType(), matchDto.getEventType()) &&
							EqualsUtil.equals(ruleDto.getPaymentMethod(), matchDto.getPaymentMethod()) &&
							EqualsUtil.equals(ruleDto.getSubsEventId(), matchDto.getSubsEventId()) &&
							EventCdrService.absEquals(ruleDto.getContactChannelId(), matchDto.getContactChannelId()) &&
							EventCdrService.absEquals(ruleDto.getSpId(), EventCdrService.this.getSpIdByDict(dict, ruleDto)));
				}
			});
		}
		if (ruleDto == null) {
			logger.debug("Match template rule by eventCode, spId.");
			ruleDto = matchTemplateRule(ruleDtoList, matchDto, dict, new RuleMatcher() {
				public boolean match(CdrTemplateRuleExDto ruleDto, CdrTemplateRuleMatchDto matchDto, CdrDict dict) {
					return (EqualsUtil.equals(ruleDto.getEventType(), matchDto.getEventType()) &&
							EventCdrService.absEquals(ruleDto.getPaymentMethod(), matchDto.getPaymentMethod()) &&
							EventCdrService.absEquals(ruleDto.getSubsEventId(), matchDto.getSubsEventId()) &&
							EventCdrService.absEquals(ruleDto.getContactChannelId(), matchDto.getContactChannelId()) &&
							EqualsUtil.equals(ruleDto.getSpId(), EventCdrService.this.getSpIdByDict(dict, ruleDto)));
				}
			});
		}
		if (ruleDto == null) {
			logger.debug("Match template rule by eventCode, paymentmethod.");
			ruleDto = matchTemplateRule(ruleDtoList, matchDto, dict, new RuleMatcher() {
				public boolean match(CdrTemplateRuleExDto ruleDto, CdrTemplateRuleMatchDto matchDto, CdrDict dict) {
					return (EqualsUtil.equals(ruleDto.getEventType(), matchDto.getEventType()) &&
							EqualsUtil.equals(ruleDto.getPaymentMethod(), matchDto.getPaymentMethod()) &&
							EventCdrService.absEquals(ruleDto.getSubsEventId(), matchDto.getSubsEventId()) &&
							EventCdrService.absEquals(ruleDto.getContactChannelId(), matchDto.getContactChannelId()) &&
							EventCdrService.absEquals(ruleDto.getSpId(), EventCdrService.this.getSpIdByDict(dict, ruleDto)));
				}
			});
		}
		if (ruleDto == null) {
			logger.debug("Match template rule by eventCode.");
			ruleDto = matchTemplateRule(ruleDtoList, matchDto, dict, new RuleMatcher() {
				public boolean match(CdrTemplateRuleExDto ruleDto, CdrTemplateRuleMatchDto matchDto, CdrDict dict) {
					return (EqualsUtil.equals(ruleDto.getEventType(), matchDto.getEventType()) &&
							EventCdrService.absEquals(ruleDto.getPaymentMethod(), matchDto.getPaymentMethod()) &&
							EventCdrService.absEquals(ruleDto.getSubsEventId(), matchDto.getSubsEventId()) &&
							EventCdrService.absEquals(ruleDto.getContactChannelId(), matchDto.getContactChannelId()) &&
							EventCdrService.absEquals(ruleDto.getSpId(), EventCdrService.this.getSpIdByDict(dict, ruleDto)));
				}
			});
		}
		return ruleDto;
	}

	private CdrTemplateRuleExDto matchTemplateRule(List<CdrTemplateRuleExDto> ruleDtoList,
			CdrTemplateRuleMatchDto matchDto, CdrDict dict, RuleMatcher matcher) {
		if (ruleDtoList != null && !ruleDtoList.isEmpty())
			for (CdrTemplateRuleExDto ruleDto : ruleDtoList) {
				if (matcher.match(ruleDto, matchDto, dict))
					return ruleDto;
			}
		return null;
	}

	protected String createCdrParam(CdrDict dict, String contentConfig) {
		String eventParamStr = "";
		if (contentConfig.isEmpty())
			return eventParamStr;
		try {
			StringBuilder eventParam = new StringBuilder(512);
			MatchResult result = null;
			// PatternMatcherInput matcherInput = new PatternMatcherInput(contentConfig);
			// Perl5Matcher matcher = new Perl5Matcher();
			// while (matcher.contains(matcherInput, pattern)) {
			// 	result = matcher.getMatch();
			// 	if (!eventParam.isEmpty())
			// 		eventParam.append("|");
			// 	eventParam.append(result.group(1)).append("=").append(formatValue(result.group(2), dict));
			// }
			eventParamStr = eventParam.toString();
		} catch (Exception ex) {
			// ExceptionHandler.publish("S-CNT-01022", ex);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-CNT-01022"));
		}
		return eventParamStr;
	}

	private String formatValue(String param, CdrDict dict) {
		int pos = -1;
		String paramName = null;
		String paramType = null;
		String paramValue = "";
		paramName = param;
		paramType = "S";
		pos = param.indexOf(':');
		if (pos > -1) {
			paramName = param.substring(0, pos);
			paramType = param.substring(pos + 1, pos + 2);
		} else if (param.indexOf(',') > -1) {
			pos = param.indexOf(',');
			paramName = param.substring(0, pos);
			paramType = "S";
		} else {
			paramName = param;
			paramType = "S";
		}
		// paramValue = dict.getString(paramName);
		paramValue = paramName;
		if (!paramValue.isEmpty()) {
			if (paramType.equals("N")) {
				paramValue = DataFormatter.formatNumber(param, paramValue);
			} else if (paramType.equals("B")) {
				paramValue = DataFormatter.formatNumber(param, paramValue);
				if (Double.parseDouble(paramValue) < 0.0D) {
					paramValue = paramValue.substring(1);
				} else if (Double.parseDouble(paramValue) > 0.0D) {
					paramValue = "-" + paramValue;
				}
			} else if (paramType.equals("D")) {
				paramValue = DataFormatter.formatDate(param, paramValue);
			}
			ParamNode paramNode = ParamNode.parseParamExp(param);
			if (paramNode.getAttrMap().containsKey("P")) {
				param = String.valueOf(paramNode.getAttrMap().get("P"));
				if (param.toUpperCase().contains("DECODE")) {
					pos = param.toUpperCase().indexOf("DECODE");
					param = param.substring(pos + 7, param.length() - 1);
					Map<String, String> paramMap = parseDecodeParamExp(param);
					if (paramMap.containsKey(paramValue))
						paramValue = String.valueOf(paramMap.get(paramValue));
				}
			}
		} else {
			paramValue = getDefaultValue(param);
		}
		return (paramValue != null) ? paramValue : "";
	}

	private Map<String, String> parseDecodeParamExp(String paramExp) {
		Map<String, String> paramNode = new HashMap<>();
		while (paramExp != null && paramExp.contains(";")) {
			paramExp = paramExp.substring(paramExp.indexOf(";") + 1);
			String strOper = "";
			if (paramExp.contains(";")) {
				strOper = paramExp.substring(0, paramExp.indexOf(";"));
			} else {
				strOper = paramExp;
			}
			String key = strOper.substring(0, strOper.indexOf("="));
			String value = strOper.substring(strOper.indexOf("=") + 1);
			paramNode.put(key, value);
		}
		return paramNode;
	}

	private String getDefaultValue(String param) {
		MatchResult result = null;
		// PatternMatcherInput matcherInput = new PatternMatcherInput(param);
		// Perl5Matcher matcher = new Perl5Matcher();
		// if (matcher.contains(matcherInput, defPattern)) {
		// 	result = matcher.getMatch();
		// 	return result.group(1);
		// }
		return "";
	}

	protected void saveEventCdr(CdrDict dict, CdrTemplateRuleExDto ruleDto, String eventParam) {
		try {
			LocalDateTime dateTimeNow = DateUtil.GetDBDateTime();
			CcEventDto ccEventDto = new CcEventDto();
			ccEventDto.eventFormatId = ruleDto.getEventFormatId();
			List<CdrTemplateRuleParamDto> ruleParamDtoList = ruleDto.getParamList();
			if (ruleParamDtoList != null && !ruleParamDtoList.isEmpty())
				for (CdrTemplateRuleParamDto paramDto : ruleParamDtoList) {
					if ("SUBS_ID_PATH".equals(paramDto.getParamCode())) {
						// ccEventDto.subsId = dict.getLong(paramDto.getParamPath());
						ccEventDto.subsId = Long.valueOf(paramDto.getParamPath());
						continue;
					}
					if ("PREFIX_PATH".equals(paramDto.getParamCode())) {
						// ccEventDto.prefix = dict.getString(paramDto.getParamPath());
						ccEventDto.prefix = paramDto.getParamPath();
						continue;
					}
					if ("ACC_NBR_PATH".equals(paramDto.getParamCode()))
						// ccEventDto.accNbr = dict.getString(paramDto.getParamPath());
						ccEventDto.accNbr = paramDto.getParamPath();
				}
			if (ccEventDto.subsId == null)
				// ccEventDto.subsId = dict.getLong("SUBS_ID");
				ccEventDto.subsId = dict.getSubsId();
			if (ccEventDto.prefix == null)
				// ccEventDto.prefix = dict.getString("PREFIX");
				ccEventDto.prefix = dict.getPrefix();
			if (ccEventDto.accNbr == null)
				// ccEventDto.accNbr = dict.getString("ACC_NBR");
				ccEventDto.accNbr = dict.getAccNbr();
			ccEventDto.eventId = billingSeqHelper.getNextVal("EVENT_ID_SEQ");
			ccEventDto.createdDate = dateTimeNow;
			ccEventDto.state = "A";
			ccEventDto.stateDate = dateTimeNow;
			ccEventDto.eventParam = eventParam;
			// Long spId = dict.getLong("SP_ID");
			Long spId = dict.getSpId();
			if (spId == null)
				spId = getSpId(ccEventDto);
			ccEventDto.spId = spId;
			// ICcEventDAO ccEventDAO = (ICcEventDAO) DAOFactory.createModuleDAO("CcEvent",
			// "common",
			// JdbcUtil4BC.getDbBackService());
			String eventParam1 = "";

			if (eventParam != null && eventParam.length() > 4000) {
				eventParam1 = eventParam.substring(4000);
				eventParam = eventParam.substring(0, 4000);
			}

			ccEventRepository.insertCcEvent(ccEventDto.getEventId(), ccEventDto.getEventFormatId(), ccEventDto.getSubsId(),
					ccEventDto.getPrefix(), ccEventDto.getAccNbr(), ccEventDto.getCreatedDate(), ccEventDto.getState(),
					ccEventDto.getStateDate(), eventParam, ccEventDto.getComments(), ccEventDto.getSpId(), eventParam1);
		} catch (Exception ex) {
			// ExceptionHandler.publish("S-CNT-01024", ex);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-CNT-01024"));
		}
	}

	private Long getSpId(CcEventDto ccEventDto) {
		Long spId = null;
		try {
			SubsDto subsDto = null;
			// ISubsDAO subsDAO = (ISubsDAO) DAOFactory.create("Subs",
			// JdbcUtil4BC.getDbBackService());
			if (ccEventDto.subsId != null) {
				subsDto = subsRepository.selectSubs(ccEventDto.subsId)
						.map(subsMapper::toSubsDto)
						.orElse(null);
			} else if (!ccEventDto.getPrefix().isEmpty() && !ccEventDto.getAccNbr().isEmpty()) {
				subsDto = subsRepository.selectLastSubs(ccEventDto.getPrefix(), ccEventDto.getAccNbr())
						.map(subsMapper::toSubsDtoFromSelectLastSubs)
						.orElse(null);
			}
			if (subsDto != null)
				spId = subsDto.getSpId();
		} catch (Exception ex) {
			// ExceptionHandler.publish("S-CNT-01024", ex);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-CNT-01024"));
		}
		return spId;
	}

	private Long getSpIdByDict(CdrDict dict, CdrTemplateRuleExDto ruleDto) {
		// Long spId = dict.getLong("SP_ID");
		Long spId = dict.getSpId();
		if (spId == null)
			try {
				Long subsId = null;
				String prefix = null;
				String accNbr = null;
				List<CdrTemplateRuleParamDto> ruleParamDtoList = ruleDto.getParamList();
				if (ruleParamDtoList != null && !ruleParamDtoList.isEmpty())
					for (CdrTemplateRuleParamDto paramDto : ruleParamDtoList) {
						if ("SUBS_ID_PATH".equals(paramDto.getParamCode())) {
							// subsId = dict.getLong(paramDto.getParamPath());
							subsId = Long.valueOf(paramDto.getParamPath());
							continue;
						}
						if ("PREFIX_PATH".equals(paramDto.getParamCode())) {
							// prefix = dict.getString(paramDto.getParamPath());
							prefix = paramDto.getParamPath();
							continue;
						}
						if ("ACC_NBR_PATH".equals(paramDto.getParamCode()))
							// accNbr = dict.getString(paramDto.getParamPath());
							accNbr = paramDto.getParamPath();
					}
				if (subsId == null)
					// subsId = dict.getLong("SUBS_ID");
					subsId = dict.getSubsId();
				if (prefix == null)
					// prefix = dict.getString("PREFIX");
					prefix = dict.getPrefix();
				if (accNbr == null)
					// accNbr = dict.getString("ACC_NBR");
					accNbr = dict.getAccNbr();
				SubsDto subsDto = null;
				// ISubsDAO subsDAO = (ISubsDAO) DAOFactory.create("Subs",
				// JdbcUtil4BC.getDbBackService());
				if (subsId != null) {
					subsDto = subsRepository.selectSubs(subsId)
							.map(subsMapper::toSubsDto)
							.orElse(null);
				} else if (!prefix.isEmpty() && !accNbr.isEmpty()) {
					subsDto = subsRepository.selectLastSubs(prefix, accNbr)
							.map(subsMapper::toSubsDtoFromSelectLastSubs)
							.orElse(null);
				}
				if (subsDto != null)
					spId = subsDto.getSpId();
			} catch (Exception ex) {
				// ExceptionHandler.publish("S-CNT-01024", ex);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-CNT-01024"));
			}
		return spId;
	}

	private abstract class RuleMatcher {
		private RuleMatcher() {
		}

		public abstract boolean match(CdrTemplateRuleExDto param1CdrTemplateRuleExDto,
				CdrTemplateRuleMatchDto param1CdrTemplateRuleMatchDto, CdrDict param1DynamicDict);
	}
}
