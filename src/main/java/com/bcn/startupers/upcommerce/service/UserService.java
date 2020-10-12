package com.bcn.startupers.upcommerce.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.bcn.startupers.upcommerce.dto.UserDto;
import com.bcn.startupers.upcommerce.model.User;
import com.bcn.startupers.upcommerce.payload.SignupRequest;

public interface UserService {	
	Optional<User> getByEmail(String email);	
	boolean existsEmail(String email);
	User save(User user);	
	UserDto create(@Valid SignupRequest signupRequest);
	List<UserDto> list();
	UserDto get(Long id) throws ResourceNotFoundException;     

}
