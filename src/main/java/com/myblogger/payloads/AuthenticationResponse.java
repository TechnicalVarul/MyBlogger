package com.myblogger.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {

	private String token;

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}
	
	
}
