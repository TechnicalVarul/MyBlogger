package com.myblogger.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogger.entities.Category;
import com.myblogger.entities.Post;
import com.myblogger.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	Page<Post> findByUser(User user,Pageable pageable);
	Page<Post> findByCategory(Category cat,Pageable pageable);
	List<Post> findByTitleContaining(String keyword);
	
}
