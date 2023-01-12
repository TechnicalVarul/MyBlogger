package com.myblogger.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.myblogger.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private int postId;
	
	@NotEmpty
	@Size(min = 5,max=20,message = "Title must be minimum of 5 characters and maximum of 20 characters")
	private String title;
	
	@NotEmpty
	@Size(min = 10,max=10000,message = "Content must be minimum of 10 characters and maximum of 10000 characters")
	private String content;
	
	private Date postDate;
	
	private String imageName;
	
	private CategoryDto category;

	private UserDto user;
	
	private  Set<CommentDto> comments = new HashSet<>();

}
