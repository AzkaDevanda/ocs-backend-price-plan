package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.AdviceTypeLangId;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ADVICE_TYPE_LANG", schema = "STS")
public class AdviceTypeLang {
  @EmbeddedId
  private AdviceTypeLangId id;

  @Column(name = "SUBJECT_DEFINE", length = 4000)
  private String subjectDefine;

  @Lob
  @Column(name = "MSG_DEFINE")
  private String msgDefine; // Oracle CLOB → String

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "SENDER_PARAM", length = 1000)
  private String senderParam;
}
