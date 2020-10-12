package com.bcn.startupers.upcommerce.service.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bcn.startupers.upcommerce.dto.UserDto;
import com.bcn.startupers.upcommerce.exception.ResourceNotFoundException;
import com.bcn.startupers.upcommerce.model.Role;
import com.bcn.startupers.upcommerce.model.RoleName;
import com.bcn.startupers.upcommerce.model.User;
import com.bcn.startupers.upcommerce.payload.SignupRequest;
import com.bcn.startupers.upcommerce.repository.RoleRepository;
import com.bcn.startupers.upcommerce.repository.UserRepository;
import com.bcn.startupers.upcommerce.service.UserService;

/**
 * 
 * @author yhuzo
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;	

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Optional<User> getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean existsEmail(String email) {
		return userRepository.existsByEmail(email);
	}	

	public User save(User user) {		
		return userRepository.save(user);
	}
	/**
	 * create a new user
	 */
	@Override
	public UserDto create(SignupRequest signupRequest) {			
		User user = new User();		
		user.setName(signupRequest.getUsername());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(encoder.encode(signupRequest.getPassword()));	
		user.setRoles(assignRole(signupRequest));			
		user.setEnabled(true);
		return convertToDto(userRepository.save(user));
	}
	/**
	 * assign the role to the user
	 * @param userDto
	 * @return
	 */
	private Set<Role> assignRole(SignupRequest req) {
		Set<Role> roles = new HashSet<>();		
		Optional.ofNullable(req.getRoles()).ifPresentOrElse((r) ->  r.forEach(role -> {
			switch(role) {
			case "ROLE_ADMIN":
				Role adminRole = roleRepository.findByRoleName(RoleName.ROLE_ADMIN)
				.orElseThrow(() -> new ResourceNotFoundException("Error: Role "+RoleName.ROLE_ADMIN+" is not found."));
				roles.add(adminRole);	
				break;				
				case "ROLE_MODERATOR":
					Role modRole = roleRepository.findByRoleName(RoleName.ROLE_MODERATOR)
					.orElseThrow(() -> new ResourceNotFoundException("Error: Role "+RoleName.ROLE_MODERATOR+" is not found."));
					roles.add(modRole);	
					break;					
					default:
						Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
						.orElseThrow(() -> new ResourceNotFoundException("Error: Role "+RoleName.ROLE_USER+" is not found."));
						roles.add(userRole);					
			}
		}),
				() -> {					
					roles.add(new Role(RoleName.ROLE_USER));
		});				
		return roles;
	}

	/**
	 * get a user by id
	 */
	@Override
	public UserDto get(Long userId){				
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on user id : " + userId));						
		return convertToDto(user);		
	}

	/**
	 * get the all users
	 */
	@Override
	public List<UserDto> list() {				
		return userRepository.findAll().stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());

	}
	private User convertToEntity(UserDto userDto) {			
		return modelMapper.map(userDto, User.class);	
	}

	private UserDto convertToDto(User user) {		
		return modelMapper.map(user, UserDto.class);		
	}

	@SuppressWarnings("unused")
	private User convertToUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(encoder.encode(userDto.getPassword()));	
		user.setEnabled(true);
		return user;
	}
}
