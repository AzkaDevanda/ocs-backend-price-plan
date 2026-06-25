package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TFM_SERVICES", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TfmServices {
    @Id
    @Column(name = "SERVICE_NAME", nullable = false, length = 64)
    private String serviceName;

    @Column(name = "ENV_ID", length = 16)
    private String envId;

    @Column(name = "ENV_DESC", length = 255)
    private String envDesc;

    @Column(name = "ENV_NAME", length = 32)
    private String envName;

    @Column(name = "ENV_TYPE", length = 1)
    private String envType;

    @Column(name = "SERVICE_DESC", nullable = false, length = 255)
    private String serviceDesc;

    @Column(name = "SERVICE_TYPE", nullable = false, length = 1)
    private String serviceType;

    @Column(name = "DEFINITION", length = 128)
    private String definition;

    @Column(name = "METHOD_DEF", length = 128)
    private String methodDef;

    @Lob
    @Column(name = "SERVICE_DEF_XML")
    private byte[] serviceDefXml;

    @Column(name = "CACHE_FLAG", length = 1)
    private String cacheFlag;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "PROJECT_CODE", length = 255)
    private String projectCode;

    @Column(name = "BASE_SERVICE", length = 64)
    private String baseService;

    @Column(name = "MODULE_NAME", length = 32)
    private String moduleName;

    @Column(name = "VERSION", nullable = false, length = 32)
    private String version;

    @Column(name = "MODIFIER", length = 32)
    private String modifier;

    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
}