package com.bcn.startupers.upcommerce.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.persistence.JoinColumn;


@Entity
@Table(name="users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id ;	

	@Column(length=30)
	private String name;
	
	@Column(length=30)
	private String lastName;

	@NotBlank
	@Column(length = 60)	
	private String password;

	@NotBlank
	@Column(unique = true, length=100)
	@Email
	private String email;	
	
	private Boolean enabled;	
	
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))	 
	@ManyToMany(cascade = {CascadeType.ALL} ,fetch = FetchType.LAZY)	
	private Set <Role> roles = new HashSet<>();
	
	
	public User() {	}		

	public User(String name, String email, String password) {		
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(Long id, String name, String lastName, String password, String email, Boolean enabled,
			Set<Role> roles) {		
		this.id = id;		
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.roles = roles;
	}

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



}
