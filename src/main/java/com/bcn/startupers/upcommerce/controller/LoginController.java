package com.bcn.startupers.upcommerce.controller;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bcn.startupers.upcommerce.payload.JwtResponse;
import com.bcn.startupers.upcommerce.payload.JwtSocialRequest;
import com.bcn.startupers.upcommerce.payload.LoginRequest;
import com.bcn.startupers.upcommerce.service.LoginService;

/**
 * 
 * @author yhuzo
 *
 */

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {
	
    @Autowired
    LoginService loginService;

    
    @PostMapping("/google")
    public ResponseEntity<JwtResponse> google(@Valid @RequestBody JwtSocialRequest jwtSocialRequest) throws IOException {        
    	JwtResponse jwtResponse = loginService.generateGoogleTokenResponse(jwtSocialRequest);          
    	return ResponseEntity.ok(jwtResponse);
    }
    
    @PostMapping("/facebook")
    public ResponseEntity<JwtResponse> facebook(@Valid @RequestBody JwtSocialRequest jwtSocialRequest) throws IOException {    	
    	JwtResponse jwtResponse = loginService.generateFacebookTokenResponse(jwtSocialRequest);      
    	return ResponseEntity.ok(jwtResponse);
    } 
    
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) throws IOException {     	
    	JwtResponse jwtResponse = loginService.generateAuthenticateResponse(loginRequest);   
        return ResponseEntity.ok(jwtResponse);
    }        
}
