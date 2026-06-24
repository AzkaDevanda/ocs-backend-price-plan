package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "ADVICE_EVENT")
public class AdviceEvent {

    @Id
    @Column(name = "ADVICE_EVENT_ID", nullable = false)
    private Integer id;

    @Column(name = "ADVICE_EVENT_CODE", length = 60)
    private String adviceEventCode;

    @Column(name = "ADVICE_EVENT_NAME")
    private String adviceEventName;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "STATE")
    private Character state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "SP_ID")
    private Integer spId;
}
