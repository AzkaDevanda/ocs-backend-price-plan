package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

import lombok.*;

@Entity
@Table(name = "BWF_COND_GROUP", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BwfCondGroup implements Serializable {
	@Id
	@Column(name = "COND_GROUP_ID", nullable = false)
	private Integer id;

	@Column(name = "STEP_ID", nullable = false)
	private Integer stepId;

	@Column(name = "SP_ID")
	private Integer spId;
}