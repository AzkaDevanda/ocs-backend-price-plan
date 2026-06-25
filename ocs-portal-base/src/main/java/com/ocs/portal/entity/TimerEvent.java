package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TIMER_EVENT", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimerEvent {

//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIMER_EVENT_SEQ")
//    @SequenceGenerator(
//            name = "TIMER_EVENT_SEQ",
//            sequenceName = "STS.TIMER_EVENT_ID_SEQ",
//            allocationSize = 1
//    )
    @Id
    @Column(name = "TIMER_EVENT_ID", nullable = false)
    private Long timerEventId;

    @Column(name = "OBJ_SUBS_EVENT_ID")
    private Long objSubsEventId;

    @Column(name = "SRC_SUBS_EVENT_ID")
    private Long srcSubsEventId;

    @Column(name = "SRC_ORDER_ITEM_ID")
    private Long srcOrderItemId;

    @Column(name = "OBJ_ORDER_ITEM_ID")
    private Long objOrderItemId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "EXP_DATE", nullable = false)
    private LocalDateTime expDate;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Lob
    @Column(name = "EXT_ATTR")
    private byte[] extAttr;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "CUST_ORDER_ID")
    private Long custOrderId;

    @Column(name = "ACC_NBR", length = 30)
    private String accNbr;

    @Column(name = "PARENT_TIMER_EVEN_ID")
    private Long parentTimerEvenId;

    @Column(name = "REVERSED_TIMER_EVENT_ID")
    private Long reversedTimerEventId;

    @Column(name = "ERROR_MESSAGE", length = 4000)
    private String errorMessage;
}
