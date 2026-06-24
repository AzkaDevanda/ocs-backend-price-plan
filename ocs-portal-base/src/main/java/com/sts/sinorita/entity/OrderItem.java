package com.sts.sinorita.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/*
,
        indexes = {
                @Index(name = "IDX_ORD_CUST_ORDER", columnList = "CUST_ORDER_ID"),
                @Index(name = "IDX_ORD_ITEM_ACC_NBR", columnList = "ACC_NBR"),
                @Index(name = "IDX_ORD_ITEM_ORDER_ID", columnList = "ORDER_ID"),
                @Index(name = "IDX_ORD_ITEM_ORER_NBR", columnList = "ORDER_NBR"),
                @Index(name = "IDX_ORD_ITEM_SUBS_ID", columnList = "SUBS_ID")
        }
 */

@Entity
@Table(name = "ORDER_ITEM", schema = "STS")
public class OrderItem {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ITEM_ID_GEN")
//    @SequenceGenerator(name = "ORDER_ITEM_ID_GEN", sequenceName = "ORDER_ITEM_ID_SEQ", allocationSize = 1)
    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "ORDER_NBR", length = 60, nullable = false)
    private String orderNbr;

    @Column(name = "CUST_ORDER_ID", nullable = false)
    private Long custOrderId;

    @Column(name = "SUBS_EVENT_ID")
    private Long subsEventId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "SUBS_PLAN_ID")
    private Long subsPlanId;

    @Column(name = "PREFIX", length = 60)
    private String prefix;

    @Column(name = "ACC_NBR", length = 60)
    private String accNbr;

    @Column(name = "ORDER_STATE", length = 1, nullable = false)
    private String orderState;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "BESP_DATE")
    private LocalDateTime bespDate;

    @Column(name = "COMPLETED_DATE")
    private LocalDateTime completedDate;

    @Column(name = "BESP_ADDRESS", length = 1000)
    private String bespAddress;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "ORDER_REASON_ID")
    private Long orderReasonId;

    @Column(name = "ORDER_REASON", length = 255)
    private String orderReason;

    @Column(name = "CANCEL_REASON", length = 255)
    private String cancelReason;

    @Column(name = "CANCEL_PARTY_TYPE", length = 1)
    private String cancelPartyType;

    @Column(name = "CANCEL_PARTY_CODE", length = 30)
    private String cancelPartyCode;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "TIMER_DATE")
    private LocalDateTime timerDate;

    @Column(name = "SURVEY_STATE", length = 1)
    private String surveyState;

    @Column(name = "BESP_NO", length = 30)
    private String bespNo;

    @Column(name = "OFFER_ID")
    private Long offerId;

    @Column(name = "INSTALMENT_TYPE_ID")
    private Long instalmentTypeId;

    @Column(name = "EVENT_PAYMENT_ID")
    private Long eventPaymentId;

    @Column(name = "ORDER_TYPE", length = 1)
    private String orderType;

    @Column(name = "PRIORITY")
    private Long priority;

    @Column(name = "OPP_ID")
    private Long oppId;

    @Column(name = "MK_ORDER_ID")
    private Long mkOrderId;

    @Column(name = "PAYMENT_FLAG", length = 1)
    private String paymentFlag;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "OPERATION_TYPE", length = 1)
    private String operationType;

    @Column(name = "PART_ID", nullable = false, updatable = false)
    private Integer partId;

    @Column(name = "PROMOTION_SEQ")
    private Long promotionSeq;

    @Column(name = "PROMOTION_ITEM_ID")
    private Long promotionItemId;

    // ===== Getter & Setter =====

    @PrePersist
    public void prePersist() {
//        this.stateDate = LocalDateTime.now(
//                ZoneId.of("Asia/Dili")
//        );
    }


    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderNbr() {
        return orderNbr;
    }

    public void setOrderNbr(String orderNbr) {
        this.orderNbr = orderNbr;
    }

    public Long getCustOrderId() {
        return custOrderId;
    }

    public void setCustOrderId(Long custOrderId) {
        this.custOrderId = custOrderId;
    }

    public Long getSubsEventId() {
        return subsEventId;
    }

    public void setSubsEventId(Long subsEventId) {
        this.subsEventId = subsEventId;
    }

    public Long getSubsId() {
        return subsId;
    }

    public void setSubsId(Long subsId) {
        this.subsId = subsId;
    }

    public Long getSubsPlanId() {
        return subsPlanId;
    }

    public void setSubsPlanId(Long subsPlanId) {
        this.subsPlanId = subsPlanId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAccNbr() {
        return accNbr;
    }

    public void setAccNbr(String accNbr) {
        this.accNbr = accNbr;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public LocalDateTime getStateDate() {
        return stateDate;
    }

    public void setStateDate(LocalDateTime stateDate) {
        this.stateDate = stateDate;
    }

    public LocalDateTime getBespDate() {
        return bespDate;
    }

    public void setBespDate(LocalDateTime bespDate) {
        this.bespDate = bespDate;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public String getBespAddress() {
        return bespAddress;
    }

    public void setBespAddress(String bespAddress) {
        this.bespAddress = bespAddress;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getOrderReasonId() {
        return orderReasonId;
    }

    public void setOrderReasonId(Long orderReasonId) {
        this.orderReasonId = orderReasonId;
    }

    public String getOrderReason() {
        return orderReason;
    }

    public void setOrderReason(String orderReason) {
        this.orderReason = orderReason;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelPartyType() {
        return cancelPartyType;
    }

    public void setCancelPartyType(String cancelPartyType) {
        this.cancelPartyType = cancelPartyType;
    }

    public String getCancelPartyCode() {
        return cancelPartyCode;
    }

    public void setCancelPartyCode(String cancelPartyCode) {
        this.cancelPartyCode = cancelPartyCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getTimerDate() {
        return timerDate;
    }

    public void setTimerDate(LocalDateTime timerDate) {
        this.timerDate = timerDate;
    }

    public String getSurveyState() {
        return surveyState;
    }

    public void setSurveyState(String surveyState) {
        this.surveyState = surveyState;
    }

    public String getBespNo() {
        return bespNo;
    }

    public void setBespNo(String bespNo) {
        this.bespNo = bespNo;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getInstalmentTypeId() {
        return instalmentTypeId;
    }

    public void setInstalmentTypeId(Long instalmentTypeId) {
        this.instalmentTypeId = instalmentTypeId;
    }

    public Long getEventPaymentId() {
        return eventPaymentId;
    }

    public void setEventPaymentId(Long eventPaymentId) {
        this.eventPaymentId = eventPaymentId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getOppId() {
        return oppId;
    }

    public void setOppId(Long oppId) {
        this.oppId = oppId;
    }

    public Long getMkOrderId() {
        return mkOrderId;
    }

    public void setMkOrderId(Long mkOrderId) {
        this.mkOrderId = mkOrderId;
    }

    public String getPaymentFlag() {
        return paymentFlag;
    }

    public void setPaymentFlag(String paymentFlag) {
        this.paymentFlag = paymentFlag;
    }

    public Long getSpId() {
        return spId;
    }

    public void setSpId(Long spId) {
        this.spId = spId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public Long getPromotionSeq() {
        return promotionSeq;
    }

    public void setPromotionSeq(Long promotionSeq) {
        this.promotionSeq = promotionSeq;
    }

    public Long getPromotionItemId() {
        return promotionItemId;
    }

    public void setPromotionItemId(Long promotionItemId) {
        this.promotionItemId = promotionItemId;
    }
}
