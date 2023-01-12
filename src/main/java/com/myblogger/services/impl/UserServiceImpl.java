package com.myblogger.services.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.myblogger.config.AppConstants;
import com.myblogger.entities.Role;
import com.myblogger.entities.User;
import com.myblogger.exceptions.ResourceNotFoundException;
import com.myblogger.payloads.RoleDto;
import com.myblogger.payloads.UserDto;
import com.myblogger.repos.RoleRepo;
import com.myblogger.repos.UserRepo;
import com.myblogger.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		Role role = roleRepo.findById(AppConstants.ADMIN_ROLE_CODE).orElseThrow(() -> new ResourceNotFoundException("Role", "roleId", AppConstants.USER_ROLE_CODE));
		System.out.println("Role " + role.getRoleId()+role.getRoleName());
		
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleId(role.getRoleId());
		roleDto.setRoleName(role.getRoleName());
		System.out.println("RoleDto " + roleDto.getRoleId()+roleDto.getRoleName());
		
		userDto.setRoleDto(roleDto);
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		System.out.println("UserDto "+ userDto.getRoleDto());
		return this.userToDto(userRepo.save(this.dtoToUser(userDto)));
	}

	@Override
	public UserDto updateUser(UserDto user, int userId) {
		//fetch user from DB
		User userDb = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		//updating values
		userDb.setName(user.getName());
		userDb.setPassword(passwordEncoder.encode(userDb.getPassword()));
		userDb.setAbout(user.getAbout());
		
		
		// again save user to DB
		return this.userToDto(userRepo.save(userDb));
	}

	@Override
	public UserDto getUserById(int userId) {
		
		//fetch data from DB
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		// Convert it into DTO and return 
		return this.userToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String userEmail) {
		
		//fetch data from DB
		User user = userRepo.findByEmail(userEmail).orElseThrow(()-> new ResourceNotFoundException("User", "Email",userEmail));
		
		// Convert it into DTO and return 
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		//fetch data from DB
		List<User> users = userRepo.findAll();
		
		//converting list to DTO Object
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public UserDto deleteUser(int userId) {

		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		//delete data from DB
		userRepo.delete(user);
		
		return this.userToDto(user);
	}
	
	
	//Method to convert Dto to Entity
	public User dtoToUser(UserDto userDto)
	{
		// Converting Dto to User Object by Model mapper
		User user = modelMapper.map(userDto, User.class);
		
		// return user
		return user;
	}
	
	// Method to convert entity to Dto
	public UserDto userToDto(User user)
	{
		// Converting User to Dto Object
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		// return user object
		return userDto;
	}

}
