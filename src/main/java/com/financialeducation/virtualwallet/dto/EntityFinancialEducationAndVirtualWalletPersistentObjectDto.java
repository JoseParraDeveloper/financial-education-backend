package com.financialeducation.virtualwallet.dto;

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
public class EntityFinancialEducationAndVirtualWalletPersistentObjectDto
		extends FinancialEducationAndVirtualWalletPersistentObjectDto {

	private String name;
	private String email;
	private String telephone;
}
