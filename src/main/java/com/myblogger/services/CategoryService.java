package com.myblogger.services;

import java.util.List;

import com.myblogger.payloads.CategoryDto;

public interface CategoryService {

	public CategoryDto createCategory(CategoryDto catDto);
	
	public CategoryDto getCategoryById(int catId);
	
	public List<CategoryDto> getAllCategory();
	
	public CategoryDto deleteCategory(int catId);
	
	public CategoryDto updateCategory(int catId, CategoryDto catDto);
}
