package com.financialeducation.virtualwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRolePersistentObjectDto extends FinancialEducationAndVirtualWalletPersistentObjectDto {

	private UserPersistentObjectDto user;
	private RolePersistentObjectDto role;

}
