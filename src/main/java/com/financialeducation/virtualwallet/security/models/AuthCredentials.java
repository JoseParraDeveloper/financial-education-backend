package com.financialeducation.virtualwallet.security.models;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthCredentials {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
