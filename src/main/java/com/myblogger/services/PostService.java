package com.myblogger.services;

import java.util.List;

import com.myblogger.entities.Post;
import com.myblogger.payloads.CommentDto;
import com.myblogger.payloads.PostDto;
import com.myblogger.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto,int catId,int userId);
	
	PostDto updatePost(int postId,PostDto postDto);
	
	PostDto deletePost(int postId);
	
	PostDto getPostById(int postId);
	
	PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	PostResponse getAllPostByUser(int userId,int pageNumber,int pageSize,String sortBy,String sortDir);
	
	PostResponse getAllPostByCategory(int catId,int pageNumber,int pageSize,String sortBy,String sortDir);
	
	List<PostDto> search(String keyword);
	
}
