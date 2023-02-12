package com.financialeducation.virtualwallet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialeducation.virtualwallet.entities.Role;
import com.financialeducation.virtualwallet.enums.RoleEnum;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

	public Optional<Role> findByRoleEnum(RoleEnum roleEnum);

}
