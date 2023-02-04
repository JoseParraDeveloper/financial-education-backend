package com.financialeducation.virtualwallet.security;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenUtils {

	private static final String ACCESS_TOKEN_SECRET = "4qhp8LrBfYcaRHxhdb9zURb2rf8e7Ud";
	private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

	public static String createToken(String nombre, String username) {

		long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
		Map<String, Object> extra = new HashMap<>();
		extra.put("nombre", nombre);

		return Jwts.builder().setSubject(username).setExpiration(expirationDate).addClaims(extra)
				.signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_SECRET.getBytes()).compact();

	}

	public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).parseClaimsJws(token).getBody();
			String username = claims.getSubject();
			return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
		} catch (JwtException jwtException) {
			return null;
		}

	}
}
