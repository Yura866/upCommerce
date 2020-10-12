package com.bcn.startupers.upcommerce.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bcn.startupers.upcommerce.api.RestApiResponse;
import com.bcn.startupers.upcommerce.dto.UserDto;
import com.bcn.startupers.upcommerce.exception.UserAlreadyExistException;
import com.bcn.startupers.upcommerce.payload.SignupRequest;
import com.bcn.startupers.upcommerce.service.UserService;

/**
 * 
 * @author yhuzo
 *
 */

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

	@Autowired 
	private UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> list() {
		List<UserDto> users = userService.list();
		return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> userById(@PathVariable(value = "id") Long id) {
		UserDto user = userService.get(id);
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}

	@PostMapping ("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){		
		if (userService.existsEmail(signupRequest.getEmail())) {
			throw new UserAlreadyExistException();
		}	
		UserDto userCreated = userService.create(signupRequest);		
		return new ResponseEntity<>(new RestApiResponse(userCreated), HttpStatus.CREATED);
	}	
}
