package com.myblogger.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblogger.payloads.CommentDto;
import com.myblogger.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Handler to create comment
	@PostMapping("/posts/{postId}/user/{userId}/comment")
	public ResponseEntity<CommentDto> createComment(@PathVariable("postId") int postId,@PathVariable("userId") int userId,@RequestBody CommentDto commentDto)
	{
		return new ResponseEntity<>(this.modelMapper.map(this.commentService.createComment(commentDto, postId,userId),CommentDto.class),HttpStatus.CREATED);
	}
	
	//Handler to delete comment
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<CommentDto> deleteComment(@PathVariable("commentId") int commentId)
	{
		return new ResponseEntity<CommentDto>(this.commentService.deleteComment(commentId),HttpStatus.OK);
	}
}
