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
@Table(name = "DIRECTORY")
@NoArgsConstructor
public class Directory implements Serializable {

    @Id
    @Column(name = "DIRECTORY_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directory_seq_gen")
    @SequenceGenerator(name = "directory_seq_gen", sequenceName = "DIRECTORY_ID_SEQ", allocationSize = 1)
    private Long directoryId;

    @Column(name = "BELONG_CUST_ID")
    private Long belongCustId;

    @Column(name = "DIRECTORY_NAME", length = 255)
    private String directoryName;

    @Column(name = "PARTY_CODE", length = 60)
    private String partyCode;

    @Column(name = "PARTY_TYPE", length = 10)
    private String partyType;

    @Column(name = "POSITION")
    private Integer position;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "POST_ADDRESS", length = 500)
    private String postAddress;

    @Column(name = "POST_TYPE", length = 60)
    private String postType;
}
