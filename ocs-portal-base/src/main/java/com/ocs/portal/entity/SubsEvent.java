package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "SUBS_EVENT")
public class SubsEvent implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // jika pakai auto-increment, sesuaikan kalau pakai sequence
    @Column(name = "SUBS_EVENT_ID")
    private Long subsEventId;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "STATE_SET")
    private String stateSet;

    @Column(name = "EVENT_CATG")
    private Character eventCatg;

    @Column(name = "OBJ_PROD_STATE")
    private Character objProdState;

    @Column(name = "EVENT_CODE")
    private String eventCode;
}
