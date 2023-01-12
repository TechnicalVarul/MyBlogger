package com.myblogger.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	private String resourseName;
	private String fieldName;
	private Object fieldValue;
	public ResourceNotFoundException(String resourseName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s: %s", resourseName,fieldName,fieldValue));
		this.resourseName = resourseName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
}
