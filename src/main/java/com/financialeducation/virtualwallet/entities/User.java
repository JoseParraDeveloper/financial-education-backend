package com.financialeducation.virtualwallet.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@Table(name = "users")
public class User extends EntityFinancialEducationAndVirtualWallet {

	@NotBlank(message = "Surname may not be blank")
	private String surname;
	@NotBlank(message = "Username may not be blank")
	@Column(unique = true)
	private String username;
	@NotBlank(message = "Password may not be blank")
	private String password;
	private boolean enabled;
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	private String image;
	@Transient
	public static final Long ID_USER_ADMIN = 1L;

}
