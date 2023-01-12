package com.myblogger.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblogger.payloads.UserDto;
import com.myblogger.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;
	
	
	// Handler To save user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user)
	{
		UserDto createdUser = userService.createUser(user);
		
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
	}
	
	//Handler to get a single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable Integer userId)
	{
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	//Handler to get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	
	// Handler to delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer userId)
	{
		UserDto deletedUser =this.userService.deleteUser(userId);
		
		return ResponseEntity.ok("Details of "+deletedUser.getName()+"deleted successfully...");
	}
	
	// Handler to update user details
	@PutMapping("/")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user, Principal principal)
	{
		
		return ResponseEntity.ok(this.userService.updateUser(user,userService.getUserByEmail(principal.getName()).getId()));
	}
}
