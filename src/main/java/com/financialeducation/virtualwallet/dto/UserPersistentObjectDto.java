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
	private boolean active;
	private String listRolesString;
	private Set<RolePersistentObjectDto> roles;

	public String getListRolesString() {
		StringBuilder stringBuiler = new StringBuilder();
		for (RolePersistentObjectDto rolePersistentObjectDto : roles) {
			stringBuiler.append(rolePersistentObjectDto.getName() + ", ");
		}
		this.listRolesString = stringBuiler.substring(0, stringBuiler.length() - 2) + ".";
		return listRolesString;
	}

}
