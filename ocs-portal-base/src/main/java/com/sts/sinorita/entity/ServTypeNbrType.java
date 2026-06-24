package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.ServTypeNbrTypeId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "SERV_TYPE_NBR_TYPE")
public class ServTypeNbrType implements Serializable {
    @EmbeddedId
    private ServTypeNbrTypeId id;

    @Column(name = "SP_ID")
    private Long spId;
}
