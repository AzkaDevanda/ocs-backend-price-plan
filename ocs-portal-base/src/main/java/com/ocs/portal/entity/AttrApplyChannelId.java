package com.ocs.portal.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AttrApplyChannelId {
    private Integer attrId;
    private Integer contactChannelId;

    public AttrApplyChannelId(Integer attrId, Integer contactChannelId) {
        this.attrId = attrId;
        this.contactChannelId = contactChannelId;
    }

    public AttrApplyChannelId() {

    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public Integer getContactChannelId() {
        return contactChannelId;
    }

    public void setContactChannelId(Integer contactChannelId) {
        this.contactChannelId = contactChannelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttrApplyChannelId)) return false;
        AttrApplyChannelId that = (AttrApplyChannelId) o;
        return Objects.equals(attrId, that.attrId) &&
                Objects.equals(contactChannelId, that.contactChannelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attrId, contactChannelId);
    }
}
