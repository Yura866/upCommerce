package com.bcn.startupers.upcommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcn.startupers.upcommerce.model.User;
import com.bcn.startupers.upcommerce.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 User user = userService.getByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + email));
	        return UserPrincipalFactory.build(user);
	}

}
