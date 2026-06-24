package com.sts.sinorita.entity;


import com.sts.sinorita.entity.embeddable.SubsUppInstValueId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUBS_UPP_INST_VALUE", schema = "STS")
@Data
public class SubsUppInstValue {

    @EmbeddedId
    private SubsUppInstValueId id;

    @Column(name = "VALUE", nullable = false, length = 255)
    private String value;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "SP_ID", precision = 6)
    private Long spId;

    @Column(name = "ROUTING_ID", precision = 6)
    private Long routingId;

    @Column(name = "UPLOAD_TYPE", length = 1)
    private String uploadType;

    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "NEED_UPLOAD", length = 1)
    private String needUpload;


    public Long getSubsUppInstId() {
        return id != null ? id.getSubsUppInstId() : null;
    }

    public Long getAttrId() {
        return id != null ? id.getAttrId() : null;
    }

    // ===== getter & setter =====


    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        // 1. Audit Dates (Wajib Not Null)
        if (this.createdDate == null) {
            this.createdDate = now;
        }

        if (this.updateDate == null) {
            this.updateDate = now;
        }

        // 3. Effective Date (Wajib Not Null)
        if (this.effDate == null) {
            this.effDate = now;
        }

        if (this.needUpload == null) {
            this.needUpload = "N";
        }
    }

}
