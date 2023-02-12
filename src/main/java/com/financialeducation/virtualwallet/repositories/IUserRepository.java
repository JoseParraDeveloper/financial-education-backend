package com.financialeducation.virtualwallet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialeducation.virtualwallet.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByUsername(String username);

	public boolean existsByUsername(String username);

	public boolean existsByEmail(String email);

}
