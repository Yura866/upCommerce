package com.bcn.startupers.upcommerce.security;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bcn.startupers.upcommerce.model.User;

public class UserPrincipalFactory {
	
	 public static UserDetailsImpl build(User user){
	        List<GrantedAuthority> authorities =
	        		user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRoleName().name())).collect(Collectors.toList());
	        return new UserDetailsImpl(user.getId(), user.getName(), user.getEmail(), user.getPassword(), authorities);
	    }

}