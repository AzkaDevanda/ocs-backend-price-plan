package com.sts.sinorita.helper;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class SingleLineP6SpyFormatter implements MessageFormattingStrategy {
  @Override
  public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
    if (sql == null || sql.isBlank()) {
      return "";
    }
    String cleanSql = sql.replaceAll("[\\r\\n\\t]+", " ").replaceAll("\\s+", " ").trim();
    return elapsed + " ms | " + category + " | connection " + connectionId + " | " + cleanSql;
  }
}
