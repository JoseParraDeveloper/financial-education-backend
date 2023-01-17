package com.financialeducation.virtualwallet.enums;

public enum RoleEnum {

	ROLE_ADMIN(1L), ROLE_USER(2L), ROLE_GUEST(3L);

	private Long id;

	private RoleEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
