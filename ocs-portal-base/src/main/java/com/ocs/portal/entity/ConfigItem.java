package com.ocs.portal.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CONFIG_ITEM")
public class ConfigItem {

    @Id
    @Column(name = "CONFIG_ITEM_ID", nullable = false)
    private Long configItemId;

    @Column(name = "PARENT_CONFIG_ITEM_ID")
    private Long parentConfigItemId;

    @Column(name = "BUSINESS_MODULE_CODE", nullable = false, length = 30)
    private String businessModuleCode;

    @Column(name = "CONFIG_ITEM_CODE", nullable = false, length = 60)
    private String configItemCode;

    @Column(name = "CONFIG_ITEM_NAME", length = 255)
    private String configItemName;

    @Column(name = "GRID_FLAG", length = 1)
    private String gridFlag;

    @Column(name = "VISIABLE_FLAG", length = 1)
    private String visiableFlag;

    @Column(name = "MODIFY_FLAG", length = 1)
    private String modifyFlag;

    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "COMMENTS", length = 400)
    private String comments;

    @Column(name = "CHINESE_COMMENTS", length = 400)
    private String chineseComments;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "STATE", length = 1)
    private String state = "A"; // Default value in DB

}
