package com.myblogger.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myblogger.entities.Category;
import com.myblogger.entities.Comment;
import com.myblogger.entities.Post;
import com.myblogger.entities.User;
import com.myblogger.exceptions.ResourceNotFoundException;
import com.myblogger.payloads.CategoryDto;
import com.myblogger.payloads.CommentDto;
import com.myblogger.payloads.PostDto;
import com.myblogger.payloads.PostResponse;
import com.myblogger.payloads.UserDto;
import com.myblogger.repos.CommentRepo;
import com.myblogger.repos.PostRepo;
import com.myblogger.services.CategoryService;
import com.myblogger.services.PostService;
import com.myblogger.services.UserService;

import net.bytebuddy.asm.Advice.This;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepo postRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryService categoryServive;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,int catId,int userId) {
	
		UserDto userDto = this.userService.getUserById(userId);
		CategoryDto catDto = this.categoryServive.getCategoryById(catId);
		
		Post post = new Post();
		
		//setting values
		post.setTitle(postDto.getTitle());
		post.setCategory(modelMapper.map(catDto, Category.class));
		post.setContent(postDto.getContent());
		post.setImageName("default.png");
		post.setPostDate(new Date());
		post.setUser(modelMapper.map(userDto, User.class));
		
		//saving and returning value
		return this.postToDto(this.postRepo.save(post));
	}

	@Override
	public PostDto updatePost(int postId, PostDto postDto) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		
		
		return this.postToDto(this.postRepo.save(post));
	}

	@Override
	public PostDto deletePost(int postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));

		this.postRepo.delete(post);
		
		return this.postToDto(post);
	}

	@Override
	public PostDto getPostById(int postId) {
		
		return this.postToDto(this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId)));
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Page<Post> pagePost = this.postRepo.findAll(PageRequest.of(pageNumber, pageSize,(sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy)));
		
		List<PostDto> posts = pagePost.getContent().stream().map(post->this.postToDto(post)).collect(Collectors.toList());
		
		PostResponse resp = new PostResponse();
		
		resp.setContent(posts);
		resp.setPageNumber(pagePost.getNumber());
		resp.setPageSize(pagePost.getSize());
		resp.setTotalElements(pagePost.getTotalElements());
		resp.setTotalPage(pagePost.getTotalPages());
		resp.setLastPage(pagePost.isLast());
		
		
		return resp;
	}

	@Override
	public PostResponse getAllPostByUser(int userId,int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		User user = this.modelMapper.map(this.userService.getUserById(userId), User.class);
		
		Page<Post> pagePost = this.postRepo.findByUser(user, PageRequest.of(pageNumber, pageSize,(sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy)));
		
		List<PostDto> posts = pagePost.getContent().stream().map(post->this.postToDto(post)).collect(Collectors.toList());
		
		PostResponse resp = new PostResponse();
		
		resp.setContent(posts);
		resp.setPageNumber(pagePost.getNumber());
		resp.setPageSize(pagePost.getSize());
		resp.setTotalElements(pagePost.getTotalElements());
		resp.setTotalPage(pagePost.getTotalPages());
		resp.setLastPage(pagePost.isLast());
		
		return resp;
	}

	@Override
	public PostResponse getAllPostByCategory(int catId,int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Category cat = this.modelMapper.map(this.categoryServive.getCategoryById(catId), Category.class);
		
		Page<Post> pagePost = this.postRepo.findByCategory(cat, PageRequest.of(pageNumber, pageSize,(sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy)));
		
		List<PostDto> posts = pagePost.getContent().stream().map(post->this.postToDto(post)).collect(Collectors.toList());
		
		PostResponse resp = new PostResponse();
		
		resp.setContent(posts);
		resp.setPageNumber(pagePost.getNumber());
		resp.setPageSize(pagePost.getSize());
		resp.setTotalElements(pagePost.getTotalElements());
		resp.setTotalPage(pagePost.getTotalPages());
		resp.setLastPage(pagePost.isLast());
		
		return resp;
	}
	
	//Method to convert Dto to Entity
	public Post dtoToPost(PostDto postDto)
	{
		// Converting Dto to Post Object by Model mapper
		Post post = modelMapper.map(postDto, Post.class);
		
		// return post
		return post;
	}
	
	// Method to convert entity to Dto
	public PostDto postToDto(Post post)
	{
		// Converting User to Dto Object
		PostDto postDto = modelMapper.map(post, PostDto.class);
		
		// return user object
		return postDto;
	}

	@Override
	public List<PostDto> search(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> allPosts = posts.stream().map((e)->this.modelMapper.map(e,PostDto.class)).collect(Collectors.toList());
		
		return allPosts;
	}

}
