package com.myblogger.config;

public class AppConstants {

	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "5";
	public static final String SORT_BY = "postId";
	public static final String SORT_DIR = "acs";
	public static final String IMAGE_UPLOADED = "Image Uploaded Successfully";
	public static final String COMMENT_DELETED = "Comment Deleted Successfully";
	public static final String ACCESS_DENIED = "Access Denied !!";
	public static final String JWT_REQUEST_KEY = "Authorization";
	public static final String JWT_TOKEN_STARTER = "Bearer";
	public static final String JWT_TOKEN_ERROR = "Token Not Starts with Bearer";
	public static final String TOKEN_NOT_FOUNND = "Unable to find token !!";
	public static final String EXPIRED_TOKEN = "Token has expired !!";
	public static final String INVALID_TOKEN = "Invalid Token !!";
	public static final String BAD_CARDENTIALS = "Invalid Username or Password !!"; 
	public static final Integer ADMIN_ROLE_CODE = 1001;
	public static final Integer USER_ROLE_CODE = 1002;
	public static final String[] PUBLIC_PATH = {
			"/api/",
			"/api/v1/auth/login",
			"/api/users/",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
			
	};
	
	
}
