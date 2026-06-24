package com.sts.sinorita.svc.role.projection;

public interface AttrDefDtoProjection {

    String getTableName();

    String getColumnName();

    String getExtCode();

    Long getValueId();

    String getInputType();

    Long getMinLength();

    Long getMaxLength();

    String getNullAble();

    String getDisplayName();

    String getColumnType();

    String getComments();

    Long getSpId();

    Long getDisplayOrder();

    String getIsSyncAttr();

    String getDisplayAble();

    String getSrc();  // Enum bisa dikembalikan sebagai String
}

