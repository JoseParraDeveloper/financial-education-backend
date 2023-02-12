package com.financialeducation.virtualwallet.dto;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	@NotBlank(message = "Name may not be blank")
	@Size(min = 3, message = "Name should not be less than 3 characters")
	private String name;
	@Email(message = "Email should be valid")
	@Column(unique = true)
	private String email;
	private String telephone;
}
