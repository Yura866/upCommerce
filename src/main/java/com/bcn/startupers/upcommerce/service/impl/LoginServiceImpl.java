package com.bcn.startupers.upcommerce.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bcn.startupers.upcommerce.exception.AppException;
import com.bcn.startupers.upcommerce.exception.BadRequestException;
import com.bcn.startupers.upcommerce.exception.ResourceNotFoundException;
import com.bcn.startupers.upcommerce.model.Role;
import com.bcn.startupers.upcommerce.model.RoleName;
import com.bcn.startupers.upcommerce.model.User;
import com.bcn.startupers.upcommerce.payload.JwtResponse;
import com.bcn.startupers.upcommerce.payload.JwtSocialRequest;
import com.bcn.startupers.upcommerce.payload.LoginRequest;
import com.bcn.startupers.upcommerce.security.UserDetailsImpl;
import com.bcn.startupers.upcommerce.security.jwt.JwtProvider;
import com.bcn.startupers.upcommerce.service.LoginService;
import com.bcn.startupers.upcommerce.service.RolService;
import com.bcn.startupers.upcommerce.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;


@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);	
	private static final String GOOGLE_USER_NAME="google";

	@Value("${google.clientId}")
	private String googleClientId;

	@Value("${bcn.upcommerce.secretPsw}")
	private String secretPsw;


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserService userService;	 

	@Autowired
	private RolService rolService;


	@Override
	public JwtResponse generateGoogleTokenResponse(JwtSocialRequest jwtSocialRequest) {			
		User user = new User();
		try {			
			NetHttpTransport transport = new NetHttpTransport();
			JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
			GoogleIdTokenVerifier.Builder verifier =
					new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
					.setAudience(Collections.singletonList(googleClientId));			
			GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), jwtSocialRequest.getValue());	
			GoogleIdToken.Payload payload = googleIdToken.getPayload();
			if(userService.existsEmail(payload.getEmail()))					
				user = userService.getByEmail(payload.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + payload.getEmail()));
			else
				user = saveSocialUser(GOOGLE_USER_NAME, payload.getEmail());	
		} catch (IOException e) {				
			logger.error("generateAuthenticateResponse: error while authentication on google: " + e);
			throw new AppException("Error occurs at the user authentication, please retry later");			
		}				
		return login(user);
	}
	

	/**
	 * FIXME: make attention while doing the frontend
	 * The retrieving users information needs verification before, if the email doesn't have the public permission and is not verified  
	 * by facebook user it doesn't fetch from there. 
	 */
	@Override
	public JwtResponse generateFacebookTokenResponse(JwtSocialRequest jwtSocialRequest) {	
		User user = new User();
		try {
			Facebook facebook = new FacebookTemplate(jwtSocialRequest.getValue());
			String [] fields = {"id","first_name","last_name", "name", "email"};
			org.springframework.social.facebook.api.User userFacebook = 
					facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);		
			
			if(userService.existsEmail(userFacebook.getEmail())) 				
				user = userService.getByEmail(userFacebook.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + userFacebook.getEmail()));	
			else
				user = saveSocialUser(userFacebook.getName(), userFacebook.getEmail());
		} catch (Exception e) {
			logger.error("generateFacebookTokenResponse:  error at the authentication on facebook: " + e);
			throw new AppException("Error occurs at the authentication, please retry later ");			
		}
		return login(user);
	}

	@Override
	public JwtResponse generateAuthenticateResponse(LoginRequest loginRequest) {		
		JwtResponse authResponse = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
					);    		
			SecurityContextHolder.getContext().setAuthentication(authentication);
			authResponse = createJwtResponse(authentication);    		
		} catch (BadRequestException e) {
			logger.error("generateAuthenticateResponse: Invalid credentials: " + e);
			throw new BadRequestException("Error occurs at the authentication, please retry later or introduce another credentials ");
		}		
		return authResponse;		
	}	

	private JwtResponse login(User user) {	
		JwtResponse jwtResponse = null;		
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(), secretPsw)
					);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);	
			jwtResponse = createJwtResponse(authentication);
		} catch (AuthenticationException e) {
			logger.error("login: login error");
			throw new BadRequestException("Error occurs at the authentication, please retry later " + e);
		}		
		return jwtResponse;
	}

	private JwtResponse createJwtResponse(Authentication authentication) {		
		String jwt = jwtProvider.generateToken(authentication);	
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());   
		return new JwtResponse (
				jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles);	
	}	
	/**
	 * the user registered by any social network always has the role "user".
	 * @param name
	 * @param email
	 * @return User
	 */
	private User saveSocialUser(String name, String email) {			
		User user= new User(name, email, passwordEncoder.encode(secretPsw));		
		Role rolUser = rolService.getByRolName(RoleName.ROLE_USER).orElse(new Role(RoleName.ROLE_USER));		
		Set<Role> roles = new HashSet<>();		
		roles.add(rolUser);
		user.setRoles(roles);			
		return userService.save(user);	

	}
}
