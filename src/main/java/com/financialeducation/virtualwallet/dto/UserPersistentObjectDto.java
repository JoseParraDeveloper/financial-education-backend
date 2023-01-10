package com.financialeducation.virtualwallet.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	private List<UserRolePersistentObjectDto> roles;

}
