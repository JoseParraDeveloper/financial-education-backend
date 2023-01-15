package com.financialeducation.virtualwallet.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;

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
	@Getter
	private static final Long ROLE_ADMIN = 1L;
	@Getter
	private static final Long ROLE_USER = 2L;
	@Getter
	private static final Long ROLE_INVITADO = 3L;

	@NotBlank(message = "Name may not be blank")
	private String name;
	@NotBlank(message = "Name may not be blank")
	@Column(length = 1000)
	private String description;
	@ManyToMany(mappedBy = "roles")
	private Set<User> user;

}
