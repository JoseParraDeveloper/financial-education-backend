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
public class RolePersistentObjectDto extends FinancialEducationAndVirtualWalletPersistentObjectDto {
	private String name;
	private String description;
	@JsonIgnore
	private List<UserRolePersistentObjectDto> user;
}
