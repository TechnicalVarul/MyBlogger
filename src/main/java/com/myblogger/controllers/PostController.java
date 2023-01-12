package com.myblogger.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myblogger.config.AppConstants;
import com.myblogger.payloads.PostDto;
import com.myblogger.payloads.PostResponse;
import com.myblogger.services.FileService;
import com.myblogger.services.PostService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PostController {

	@Autowired
	PostService postService;
	
	@Autowired
	FileService fileService;
	
	@Value("${project.image}")
	String path;
	
	@GetMapping("/")
	public String test()
	{
		return "Hello!! Success... ";
	}
	
	//Handler to create post
	@PostMapping("/category/{catId}/users/{userId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,@PathVariable int catId,@PathVariable int userId) 
	{
		return new ResponseEntity<PostDto>(this.postService.createPost(postDto, catId, userId),HttpStatus.CREATED);
	}
	
	//Handler to delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable int postId)
	{
		PostDto deletedPost = postService.deletePost(postId);
		return ResponseEntity.ok("Post "+ deletedPost.getTitle() +" with Id : "+deletedPost.getPostId()+" deleted successfully...");
	}
	
	//Handler to update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable int postId)
	{
		return new ResponseEntity<PostDto>(this.postService.updatePost(postId, postDto),HttpStatus.OK);
	}
	
	//Handler to get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
												   @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
												   @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
												   @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
	{
		return new ResponseEntity<PostResponse>(this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	//Handler to get post by Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId)
	{
		return new ResponseEntity<PostDto>(this.postService.getPostById(postId),HttpStatus.OK);
	}
	//Handler to get post by user
	@GetMapping("user/{userId}/posts")
	public ResponseEntity<PostResponse> getAllPostByUser(@PathVariable int userId,
														 @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
														 @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
														 @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
														 @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
	{
		return new ResponseEntity<PostResponse>(this.postService.getAllPostByUser(userId,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	//Handler to get post by category
	@GetMapping("category/{catId}/posts")
	public ResponseEntity<PostResponse> getAllPostByCategory(@PathVariable int catId,
			 												 @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
			 												 @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
			 												 @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			 												 @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
	{
		return new ResponseEntity<PostResponse>(this.postService.getAllPostByCategory(catId,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	//Handler to search post
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> search(@PathVariable("keyword") String keyword)
	{
		List<PostDto> postsDtos = this.postService.search(keyword);
		
		return new ResponseEntity<List<PostDto>>(postsDtos,HttpStatus.OK);
	}
	
	// Handler to upload post image
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<String> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable("postId") int postId) throws IOException
	{
		// getting post
		PostDto postDto = this.postService.getPostById(postId);
				
		// uploading image
		String fileName = this.fileService.uploadImage(path, image);
		
		// setting image in post
		postDto.setImageName(fileName);
		
		// updating post in DB
		this.postService.updatePost(postId, postDto);
		
		return new ResponseEntity<>(AppConstants.IMAGE_UPLOADED,HttpStatus.OK);	
	}
	
	// Handler to serve image
	@GetMapping(value = "/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
