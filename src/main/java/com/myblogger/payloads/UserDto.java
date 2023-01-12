package com.myblogger.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.myblogger.entities.Role;
import com.myblogger.repos.RoleRepo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min=3,message = "Username must be greater than 3 character !!")
	private String name;
	
	@Email(message = "You enter invalid email !!")
	private String email;
	
	@NotEmpty
	@Size(min=3, max=10, message = "Password must be minimun of 3 characters and maximum of 10 characters !!")
	private String password;
	
	@NotEmpty
	@Size(min=20, max=100, message = "About must be minimun of 20 characters and maximum of 100 characters !!")
	private String about;
	
	private RoleDto roleDto;
}
