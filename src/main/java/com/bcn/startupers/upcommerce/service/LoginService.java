package com.bcn.startupers.upcommerce.service;

import com.bcn.startupers.upcommerce.payload.JwtResponse;
import com.bcn.startupers.upcommerce.payload.JwtSocialRequest;
import com.bcn.startupers.upcommerce.payload.LoginRequest;

public interface LoginService {
	
	JwtResponse generateGoogleTokenResponse(JwtSocialRequest jwtSocialRequest);
	JwtResponse generateFacebookTokenResponse(JwtSocialRequest jwtSocialRequest);	
	JwtResponse generateAuthenticateResponse(LoginRequest loginRequest);
}
