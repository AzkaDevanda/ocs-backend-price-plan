package com.sts.sinorita.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Information {
  private String name;

  private String displayName;

  private boolean product;

  private boolean kernal;

  private String author;

  private String updateDate;

  private String log;

  private List<String> depends;
}
