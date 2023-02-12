package com.financialeducation.virtualwallet.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.financialeducation.virtualwallet.entities.User;
import com.financialeducation.virtualwallet.repositories.IUserRepository;
import com.financialeducation.virtualwallet.security.models.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
				"El usuario con username " + username.toUpperCase() + " no existe"));
		return UserDetailsImpl.build(user);
	}

}
