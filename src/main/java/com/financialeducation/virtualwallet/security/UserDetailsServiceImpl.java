package com.financialeducation.virtualwallet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.financialeducation.virtualwallet.entities.User;
import com.financialeducation.virtualwallet.repositories.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOneByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario con username " + username + " no existe"));
		return new UserDetailsImpl(user);
	}

}
