package com.sts.sinorita.projection.trigger;

import java.time.LocalDateTime;

public interface AdviceEventProjection {
    Long getAdviceEventId();
    String getAdviceEventCode();
    String getAdviceEventName();
    String getComments();
    String getState();
    LocalDateTime getStateDate(); // Atau java.util.Date tergantung tipe di DB
    Long getSpId();
}
