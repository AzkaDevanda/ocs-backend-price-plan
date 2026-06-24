package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.TimeSpanDetailId;

@Entity
@Table(name = "TIME_SPAN_DETAIL", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSpanDetail implements Serializable {

  @EmbeddedId
  private TimeSpanDetailId id;

  @Column(name = "CYCLE_BEGIN_DATE")
  private LocalDateTime cycleBeginDate;

  @Column(name = "CYCLE_UNIT")
  private Long cycleUnit;

  @Column(name = "TIME_UNIT", length = 1)
  private String timeUnit;

  @Column(name = "DURATION")
  private Long duration;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "SPLIT_CDR_FLAG", length = 1)
  private String splitCdrFlag;

  @Column(name = "REF_RE_ATTR_FLAG", length = 1)
  private String refReAttrFlag;

  @Column(name = "CYCLE_BEGIN_TIME_RE_ATTR")
  private Long cycleBeginTimeReAttr;

  @Column(name = "TIME_UNIT_RE_ATTR")
  private Long timeUnitReAttr;

  @Column(name = "CYCLE_UNIT_RE_ATTR")
  private Long cycleUnitReAttr;

  @Column(name = "DURATION_RE_ATTR")
  private Long durationReAttr;

  @Column(name = "ADJUST_BEGIN_DATE_MODE", length = 1)
  private String adjustBeginDateMode;

  @Column(name = "BEGIN_DATE_OFFSET")
  private Long beginDateOffset;
}
