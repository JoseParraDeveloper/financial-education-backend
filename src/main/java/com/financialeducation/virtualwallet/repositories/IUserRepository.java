package com.financialeducation.virtualwallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialeducation.virtualwallet.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

}
