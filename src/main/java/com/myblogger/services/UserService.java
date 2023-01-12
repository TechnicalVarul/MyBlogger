package com.myblogger.services;

import java.util.List;

import com.myblogger.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,int userId);
	
	UserDto getUserById(int userId);
	
	UserDto getUserByEmail(String userEmail);
	
	List<UserDto> getAllUser();
	
	UserDto deleteUser(int userId);
	
	
}
