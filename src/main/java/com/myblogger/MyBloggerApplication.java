package com.myblogger;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.myblogger.config.AppConstants;
import com.myblogger.entities.Role;
import com.myblogger.repos.RoleRepo;

@SpringBootApplication
public class MyBloggerApplication implements CommandLineRunner {

	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(MyBloggerApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setRoleId(AppConstants.ADMIN_ROLE_CODE);
			role.setRoleName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setRoleId(AppConstants.USER_ROLE_CODE);
			role1.setRoleName("ROLE_USER");
			
			
			roleRepo.saveAll(List.of(role,role1));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

}
