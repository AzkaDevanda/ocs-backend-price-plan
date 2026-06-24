package com.sts.sinorita.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UIP_SERVICE_ROUTING")
public class RoutingService {
    @Id
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "SERVICE_NAME", length = 64)
    private String serviceName;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "COND_EXPR", length = 4000)
    private String condExpr;

    @Column(name = "APP_NAME", nullable = false, length = 128)
    private String appName;

    @Column(name = "ADAPTER_NAME", nullable = false, length = 255)
    private String adapterName;

    @Column(name = "COMMAND_NAME", nullable = false, length = 256)
    private String commandName;

    @Column(name = "COMMENTS", nullable = false, length = 4000)
    private String comments;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "VERSION", nullable = false, length = 32)
    private String version;

    @Column(name = "MODIFIER", nullable = false, length = 32)
    private String modifier;

    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
}
