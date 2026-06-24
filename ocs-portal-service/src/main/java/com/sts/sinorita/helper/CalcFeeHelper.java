package com.sts.sinorita.helper;

import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.ConfigItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CalcFeeHelper {

  private static final Logger logger = LoggerFactory.getLogger(CalcFeeHelper.class);
  private static ConfigItemRepository configItemRepository;

  public CalcFeeHelper (ConfigItemRepository repository) {
    configItemRepository = repository;
  }

  /**
   * Ambil nilai param tertentu dari attribute 916 di refAttr.
   */
  public static String getExtParamFromRefAttr (String refAttr, String paramName) {
    logger.debug("getExtParamFromRefAttr, refAttr=[{}], paramName=[{}]", refAttr, paramName);

    if (!StringUtils.hasText(refAttr) || !StringUtils.hasText(paramName)) {
      return "";
    }

    String extParam = getValFromKeyValueStr(refAttr, "916", ";", "=");
    if (!StringUtils.hasText(extParam)) {
      logger.debug("No 916 attribute found in input refAttr.");
      return "";
    }

    return getValFromKeyValueStr(extParam, paramName, ",", ":");
  }

  /**
   * Ambil nilai dari string key-value dengan konfigurasi split dan key-value separator.
   * Menggunakan flag 'IS_REMOVE_SPACE_4_EXT_CAL_FEE_PARAM_916' dari ConfigItemRepository.
   */
  public static String getValFromKeyValueStr (String inputStr, String key, String spliter, String keyValueSpliter) {
    Optional<ConfigItemParamProjection> optConfig =
            configItemRepository.findConfigItem("ACCT", "FEE", "IS_REMOVE_SPACE_4_EXT_CAL_FEE_PARAM_916");

    boolean isRemoveSpace = optConfig
            .map(ConfigItemParamProjection::getDefaultValue)
            .map(value -> !"N".equalsIgnoreCase(value))
            .orElse(true);

    return getValFromKeyValueStr(inputStr, key, spliter, keyValueSpliter, isRemoveSpace);
  }

  /**
   * Ambil nilai dari key-value string dengan flag remove-space.
   */
  public static String getValFromKeyValueStr (String inputStr, String key, String spliter, String keyValueSpliter, boolean isRemoveSpace) {
    if (!StringUtils.hasText(inputStr) || !StringUtils.hasText(key)) {
      return null;
    }

    String value;
    spliter = StringUtils.hasText(spliter) ? spliter : ";";
    keyValueSpliter = StringUtils.hasText(keyValueSpliter) ? keyValueSpliter : "=";

    inputStr = isRemoveSpace ? inputStr.replace(" ", "") : inputStr.trim();

    String longKey = spliter + key + keyValueSpliter;
    String mediumKey = key + keyValueSpliter;

    int pos = getKeyPosition(inputStr, longKey, mediumKey);
    if (pos < 0) {
      longKey = spliter + '\'' + key + '\'' + keyValueSpliter;
      mediumKey = '\'' + key + '\'' + keyValueSpliter;
      pos = getKeyPosition(inputStr, longKey, mediumKey);
      if (pos < 0) return null;
    }

    int endIndex = inputStr.indexOf(spliter, pos);
    value = (endIndex < 0) ? inputStr.substring(pos) : inputStr.substring(pos, endIndex);
    value = value.trim();

    if (",".equals(spliter) && ":".equals(keyValueSpliter)) {
      value = unEscapeCalFeeStr(value);
    }

    return value.replaceFirst("^'([^']+)'$", "$1");
  }

  /**
   * Cari posisi kunci di dalam input string.
   */
  private static int getKeyPosition (String inputStr, String longKey, String mediumKey) {
    int pos = inputStr.indexOf(longKey);
    if (pos >= 0) return pos + longKey.length();

    pos = inputStr.indexOf(mediumKey);
    if (pos == 0) return pos + mediumKey.length();

    String mediumKey916 = "916=" + mediumKey;
    pos = inputStr.indexOf(mediumKey916);
    return (pos < 0) ? -1 : pos + mediumKey916.length();
  }

  /**
   * Decode karakter escape khusus pada string fee.
   * Bergantung pada konfigurasi 'IS_NEED_ESCAPE' di ConfigItemRepository.
   */
  public static String unEscapeCalFeeStr (String inputString) {
    if (!StringUtils.hasText(inputString)) return inputString;

    Optional<ConfigItemParamProjection> optConfig =
            configItemRepository.findConfigItem("ACCT", "FEE", "IS_NEED_ESCAPE");

    boolean isNeedEscape = optConfig
            .map(ConfigItemParamProjection::getDefaultValue)
            .map("Y"::equalsIgnoreCase)
            .orElse(false);

    if (!isNeedEscape) return inputString;

    return inputString
            .replace("&{2c}", ",")
            .replace("&{20}", " ")
            .replace("&{7c}", "|")
            .replace("&{3b}", ";")
            .replace("&{3a}", ":")
            .replace("&{3d}", "=");
  }

  /**
   * Tambahkan parameter ke buffer dalam format key=value;key2=value2.
   */
  public static StringBuffer appendParam (StringBuffer buffer, String key, Object value, String spliter, String keyValueSpliter) {
    if (value == null) value = "";
    spliter = StringUtils.hasText(spliter) ? spliter : ";";
    keyValueSpliter = StringUtils.hasText(keyValueSpliter) ? keyValueSpliter : "=";

    if (buffer.length() > 0) {
      buffer.append(spliter);
    }

    buffer.append(key).append(keyValueSpliter).append(value);
    return buffer;
  }
}
