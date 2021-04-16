package com.bcn.startupers.upcommerce.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	@Email
    private String email;
	@NotBlank
    private String password;
    
    public LoginRequest() {}
    
	public LoginRequest(String email, String password) {		
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

}
