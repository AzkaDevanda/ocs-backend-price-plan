package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "DIRECTORY_NBR")
@NoArgsConstructor
public class DirectoryNbr implements Serializable {

    @Id
    @Column(name = "DIRECTORY_NBR_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directory_nbr_seq_gen")
    @SequenceGenerator(name = "directory_nbr_seq_gen", sequenceName = "DIRECTORY_NBR_ID_SEQ", allocationSize = 1)
    private Long directoryNbrId;

    @Column(name = "DIRECTORY_ID")
    private Long directoryId;

    @Column(name = "ACC_NBR_ID")
    private Long accNbrId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "LINE_NUMBER", length = 60)
    private String lineNumber;

    @Column(name = "PARTY_CODE", length = 60)
    private String partyCode;

    @Column(name = "PARTY_TYPE", length = 10)
    private String partyType;

    @Column(name = "POSITION")
    private Integer position;

    @Column(name = "PUBLISH_STATE", length = 1)
    private String publishState;

    @Column(name = "PUBLISH_TYPE", length = 10)
    private String publishType;

    @Column(name = "TOWN_ADDRESS", length = 255)
    private String townAddress;

    @Column(name = "TOWN_ADDRESS_VALUE", length = 500)
    private String townAddressValue;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}
