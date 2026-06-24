package com.sts.sinorita.entity;

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
@Table(name = "DP_REF_COND")
public class DpRefCond {

        @Id
        @Column(name = "DP_REF_COND_ID", nullable = false)
        private Integer dpRefCondId;

        @Column(name = "DP_REF_COND_TYPE", nullable = false, length = 1)
        private String dpRefCondType;

        @Column(name = "DP_REF_COND_NAME", nullable = false, length = 255)
        private String dpRefCondName;

        @Column(name = "DP_REF_COND_PARAM_NUM", nullable = false)
        private Integer dpRefCondParamNum;

        @Column(name = "DATA_TYPE", length = 1)
        private String dataType;

        @Column(name = "COMMENTS")
        private String comments;

        @Column(name = "SP_ID")
        private Integer spId;
}
