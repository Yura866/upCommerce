package com.bcn.startupers.upcommerce.security.jwt;


import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.bcn.startupers.upcommerce.security.UserDetailsImpl;
import io.jsonwebtoken.*;


@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${bcn.upcommerce.jwt.secret}")
	private String secret;

	@Value("${bcn.upcommerce.jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication){
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		return Jwts.builder().setSubject(userDetailsImpl.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public String getEmailFromToken(String token){
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token){
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		}catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException | SignatureException e){
			logger.error("validateToken - Error validating the token : " +e);			
		}
		return false;
	}

}
