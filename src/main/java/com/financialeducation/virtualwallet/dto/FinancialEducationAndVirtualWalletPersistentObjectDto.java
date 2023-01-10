package com.financialeducation.virtualwallet.dto;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class FinancialEducationAndVirtualWalletPersistentObjectDto {
	private Long id;
	private Integer version;
	private Date modificationTimestamp;
	private Date creationTimestamp;
}
