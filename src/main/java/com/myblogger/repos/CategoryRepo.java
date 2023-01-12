package com.myblogger.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogger.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
