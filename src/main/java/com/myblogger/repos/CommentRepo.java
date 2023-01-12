package com.myblogger.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogger.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,  Integer>{

}
