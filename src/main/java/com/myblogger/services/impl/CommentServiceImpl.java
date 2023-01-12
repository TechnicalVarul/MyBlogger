package com.myblogger.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblogger.entities.Comment;
import com.myblogger.entities.Post;
import com.myblogger.entities.User;
import com.myblogger.exceptions.ResourceNotFoundException;
import com.myblogger.payloads.CommentDto;
import com.myblogger.payloads.PostDto;
import com.myblogger.payloads.UserDto;
import com.myblogger.repos.CommentRepo;
import com.myblogger.services.CommentService;
import com.myblogger.services.PostService;
import com.myblogger.services.UserService;

@Service
public class CommentServiceImpl implements CommentService {

	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
		PostDto postDto = postService.getPostById(postId);
		UserDto userDto = userService.getUserById(userId);
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(modelMapper.map(postDto, Post.class));
		comment.setUser(modelMapper.map(userDto, User.class));
		
		return modelMapper.map(this.commentRepo.save(comment), CommentDto.class);
	}

	@Override
	public CommentDto deleteComment(int commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","Id",commentId));
		
		this.commentRepo.delete(comment);
		
		return this.modelMapper.map(comment, CommentDto.class);
	}

}
