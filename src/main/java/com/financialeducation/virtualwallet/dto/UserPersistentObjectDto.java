package com.financialeducation.virtualwallet.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPersistentObjectDto extends EntityFinancialEducationAndVirtualWalletPersistentObjectDto {

	private String surname;
	private String username;
	private String password;
	private Set<RolePersistentObjectDto> roles;

}
