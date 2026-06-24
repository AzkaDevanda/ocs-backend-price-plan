package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_APPLY_ORG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferApplyOrg {
	@Id
	@Column(name = "OFFER_ID", nullable = false)
	private Integer id;

	@Column(name = "ORG_ID")
	private Integer orgId;

	@Column(name = "SP_ID")
	private Integer spId;

	@Column(name = "EXCLUDE_FLAG")
	private Character excludeFlag;
}
