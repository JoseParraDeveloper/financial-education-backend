package com.financialeducation.virtualwallet.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.financialeducation.virtualwallet.security.models.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	@Value("${jwt.secret}")
	private String ACCESS_TOKEN_SECRET;
	@Value("${jwt.expiration}")
	private Long ACCESS_TOKEN_VALIDITY_SECONDS;

	public String generateToken(Authentication authentication) {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
		Map<String, Object> extra = new HashMap<>();
		extra.put("Name", userDetailsImpl.getName());
		extra.put("Username", userDetailsImpl.getUsername());
		extra.put("Email", userDetailsImpl.getEmail());
		extra.put("authorities", userDetailsImpl.getAuthorities());

		return Jwts.builder().setSubject(userDetailsImpl.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(expirationDate).addClaims(extra)
				.signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_SECRET.getBytes()).compact();
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException malformedJwtException) {
			logger.error("Token mal formed");
		} catch (UnsupportedJwtException unsupportedJwtException) {
			logger.error("Token unsupported");
		} catch (ExpiredJwtException expiredJwtException) {
			logger.error("Token expired");
		} catch (IllegalArgumentException illegalArgumentException) {
			logger.error("Token empty");
		} catch (SignatureException signatureException) {
			logger.error("Fail Signature");
		}
		return false;
	}
}
