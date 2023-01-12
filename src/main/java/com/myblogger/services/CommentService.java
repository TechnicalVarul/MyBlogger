package com.myblogger.services;

import com.myblogger.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,int postId,int userId);
	
	CommentDto deleteComment(int commentId);
}
