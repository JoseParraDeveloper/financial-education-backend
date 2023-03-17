package com.financialeducation.virtualwallet.dto;

import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	@NotBlank(message = "Surname may not be blank")
	private String surname;
	@NotBlank(message = "Surname may not be blank")
	@Size(min = 4, message = "Username should not be less than 4 characters")
	@Column(unique = true)
	private String username;
	@NotBlank(message = "Surname may not be blank")
	@Size(min = 4, message = "Password should not be less than 4 characters")
	private String password;
	private boolean enabled;
	@JsonIgnore
	private Set<RolePersistentObjectDto> roles;
	private String image;

}
