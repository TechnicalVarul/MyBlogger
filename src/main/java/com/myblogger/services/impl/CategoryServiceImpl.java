package com.myblogger.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblogger.entities.Category;
import com.myblogger.exceptions.ResourceNotFoundException;
import com.myblogger.payloads.CategoryDto;
import com.myblogger.repos.CategoryRepo;
import com.myblogger.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto catDto) {
		
		return this.CatToDto(this.categoryRepo.save(this.dtoToCat(catDto)));

	}

	@Override
	public CategoryDto getCategoryById(int catId) {
		
		//fetch data from DB if not found then throws exception
		Category cat = this.categoryRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", catId));
		
		return this.CatToDto(cat);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		List<Category> categories = this.categoryRepo.findAll();
		
		// converting all cat obj to dtos 
		List<CategoryDto> dtos = categories.stream().map(e->this.CatToDto(e)).collect(Collectors.toList());
		
		return dtos;
	}

	@Override
	public CategoryDto deleteCategory(int catId) {
		
		//fetch data from DB if not found then throws exception
		Category cat = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", catId));
		
		// delete the category from DB
		this.categoryRepo.delete(cat);
		
		return this.CatToDto(cat);
	}

	@Override
	public CategoryDto updateCategory(int catId, CategoryDto catDto) {
		
		//fetch data from DB if not found then throws exception
		Category catDB = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", catId));
		
		// updating values
		catDB.setCatTitle(catDto.getCatTitle());
		catDB.setCatDiscription(catDto.getCatDiscription());
		
		//return updated values
		return this.CatToDto(this.categoryRepo.save(catDB));
	}
	
	
	//Method to convert Entity to Dto
	public CategoryDto CatToDto(Category cat)
	{
		return this.modelMapper.map(cat, CategoryDto.class);
	}
	
	//Method to convert Dto to Entity
	public Category dtoToCat(CategoryDto catDto)
	{
		return this.modelMapper.map(catDto, Category.class);
	}

}
