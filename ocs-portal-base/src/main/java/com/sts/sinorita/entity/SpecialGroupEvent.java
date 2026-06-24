package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SPECIAL_GROUP_EVENT", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialGroupEvent {
    @Id
    @Column(name = "EVENT_CODE", length = 30, nullable = false)
    private String eventCode;

    @Column(name = "EVENT_NAME", length = 255, nullable = false)
    private String eventName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;
}