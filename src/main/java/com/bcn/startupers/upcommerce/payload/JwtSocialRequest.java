package com.bcn.startupers.upcommerce.payload;

public class JwtSocialRequest {
	
	String value;

	public JwtSocialRequest () {}
	
	public JwtSocialRequest (String token) {
		this.value=token;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
