package com.financialeducation.virtualwallet.security.rescontroller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialeducation.virtualwallet.security.dto.JwtDto;
import com.financialeducation.virtualwallet.security.jwt.JwtProvider;
import com.financialeducation.virtualwallet.security.models.AuthCredentials;

@RestController
@RequestMapping("/api/authenticate/v1")
@CrossOrigin
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthCredentials authCredentials, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Wrong credentials", HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authCredentials.getUsername(), authCredentials.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
}
