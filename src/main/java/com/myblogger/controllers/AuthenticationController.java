package com.myblogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblogger.config.AppConstants;
import com.myblogger.payloads.AuthenticationRequest;
import com.myblogger.payloads.AuthenticationResponse;
import com.myblogger.security.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request) throws Exception
	{
		this.authenticate(request.getUsername(),request.getPassword());
		
		String tokenString = this.jwtUtil.generateToken(this.userDetailsService.loadUserByUsername(request.getUsername()));
		
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(tokenString),HttpStatus.CREATED); 
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			throw new Exception(AppConstants.BAD_CARDENTIALS);
		}
	}
}
