package com.financialeducation.virtualwallet.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.financialeducation.virtualwallet.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role extends FinancialEducationAndVirtualWalletPersistentObject {

	@NotNull
	@Enumerated(EnumType.STRING)
	private RoleEnum roleEnum;
	@NotBlank(message = "Name may not be blank")
	@Column(length = 1000)
	private String description;
	@ManyToMany(mappedBy = "roles")
	private Set<User> user;

}
