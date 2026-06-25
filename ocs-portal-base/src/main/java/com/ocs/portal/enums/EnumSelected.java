package com.ocs.portal.enums;

import lombok.Getter;

@Getter
public enum EnumSelected {
  SINGLE_SELECT("Single Select"),
  MULTI_SELECT("Multi Select"),
  SELECT_ALL("Select All");

  private final String label;

  EnumSelected(String label) {
    this.label = label;
  }
}
