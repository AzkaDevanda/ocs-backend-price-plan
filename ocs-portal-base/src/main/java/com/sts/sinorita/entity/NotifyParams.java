package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTIFY_PARAMS")
public class NotifyParams implements Serializable {
    @Id
    @Column(name = "NOTIFY_PARAMS_ID")
    private Integer notifyParamsId;

    @Column(name = "NOTIFY_NAME")
    private String notifyName;

    @Column(name = "NOTIFY_TYPE")
    private Character notifyType;

    @Column(name = "NOTIFY_ID")
    private Integer notifyId;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "EFF_DATE")
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "SP_ID")
    private Integer spId;

}
