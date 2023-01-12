package com.myblogger.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

	private int catId;
	
	@NotEmpty
	@Size(min = 5,max=20,message = "Title must be minimum of 5 characters and maximum of 20 characters")
	private String catTitle;
	
	@NotEmpty
	@Size(min = 5,max=100,message = "Description must be minimum of 5 characters and maximum of 100 characters")
	private String catDiscription;
	
}
