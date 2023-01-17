package com.financialeducation.virtualwallet.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
public abstract class EntityFinancialEducationAndVirtualWallet
		extends FinancialEducationAndVirtualWalletPersistentObject {
	@NotBlank(message = "Name may not be blank")
	private String name;
	@Email(message = "Email is not valid")
	@NotEmpty(message = "Email cannot be empty")
	@Column(unique = true)
	private String email;
	private String telephone;

}
