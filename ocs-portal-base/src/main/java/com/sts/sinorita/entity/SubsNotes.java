package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.FijiSubsNotesId;
import jakarta.persistence.*;
import lombok.Data;

import java.security.PublicKey;
import java.time.LocalDateTime;


@Entity
@Table(name = "FIJI_SUBS_NOTES", schema = "STS")
@Data
public class SubsNotes {

    @EmbeddedId
    private FijiSubsNotesId id = new FijiSubsNotesId();

    @Column(name = "NOTES", length = 4000)
    private String notes;

    @Column(name = "PARTY_TYPE", columnDefinition = "CHAR(1)")
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    // Untuk LocalDateTime, hapus @Temporal
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ROUTING_ID")
    private Integer routingId;

    // --- Helper Methods ---

    @Transient
    public Long getSubsId() {
        return id != null ? id.getSubsId() : null;
    }

    @Transient
    public Integer getSeq() {
        return id != null ? id.getSeq() : null;
    }

    @Transient
    public void setSubsId(Long subsId) {
        if (this.id == null) this.id = new FijiSubsNotesId();
        this.id.setSubsId(subsId);
    }

    @Transient
    public void setSeq(Integer seq) {
        if (this.id == null) this.id = new FijiSubsNotesId();
        this.id.setSeq(seq);
    }
}
