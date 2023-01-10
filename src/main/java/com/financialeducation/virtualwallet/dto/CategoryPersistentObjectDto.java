package com.financialeducation.virtualwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPersistentObjectDto extends FinancialEducationAndVirtualWalletPersistentObjectDto {

	private String title;
	private String description;

}
