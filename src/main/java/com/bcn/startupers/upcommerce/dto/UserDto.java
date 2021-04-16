package com.bcn.startupers.upcommerce.dto;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import com.bcn.startupers.upcommerce.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto implements Serializable{

	private Long id ;	
	
	private String name;	
	
	private String lastName;

	@NotNull
	@JsonIgnore
	private String password;

	@NotNull	
	private String email;	
	
	private Boolean enabled;
	
	private Set <Role> roles ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Set <Role> getRoles() {
		return roles;
	}

	public void setRoles(Set <Role> roles) {
		this.roles = roles;
	}
	
}
