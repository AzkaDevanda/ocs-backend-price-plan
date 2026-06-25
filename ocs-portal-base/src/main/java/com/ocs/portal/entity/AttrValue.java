package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "ATTR_VALUE")
public class AttrValue implements Serializable {
    @EmbeddedId
    private AttrValueId id;

    @Column(name = "VALUE_MARK", nullable = false, length = 60)
    private String valueMark;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "PARENT_ATTR_VALUE_ID")
    private Integer parentAttrValueId;

    @Column(name = "PARENT_ATTR_ID")
    private Integer parentAttrId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SEQ")
    private Integer seq;

    public AttrValueId getId() {
        return id;
    }

    public void setId(AttrValueId id) {
        this.id = id;
    }

    public String getValueMark() {
        return valueMark;
    }

    public void setValueMark(String valueMark) {
        this.valueMark = valueMark;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getParentAttrValueId() {
        return parentAttrValueId;
    }

    public void setParentAttrValueId(Integer parentAttrValueId) {
        this.parentAttrValueId = parentAttrValueId;
    }

    public Integer getParentAttrId() {
        return parentAttrId;
    }

    public void setParentAttrId(Integer parentAttrId) {
        this.parentAttrId = parentAttrId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

}