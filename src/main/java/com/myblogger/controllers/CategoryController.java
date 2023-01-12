package com.myblogger.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblogger.payloads.CategoryDto;
import com.myblogger.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	// Handler to add new category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto)
	{
		CategoryDto savedCat = this.categoryService.createCategory(catDto);
		
		return new ResponseEntity<CategoryDto>(savedCat,HttpStatus.CREATED);
	}
	
	// Handler to update category
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto,@PathVariable Integer catId)
	{
		return ResponseEntity.ok(this.categoryService.updateCategory(catId, catDto));
	}
	
	// Handler to delete category
	@DeleteMapping("/{catId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer catId)
	{
		CategoryDto deletedCat = this.categoryService.deleteCategory(catId);
		
		return ResponseEntity.ok("Category " + deletedCat.getCatTitle() + " with Id : "+deletedCat.getCatId()+"deleted successfully...");
	}
	
	// Handler to get category by catId
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId)
	{
		return ResponseEntity.ok(this.categoryService.getCategoryById(catId));
	}
	
	// Handler to get all category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		return ResponseEntity.ok(this.categoryService.getAllCategory());
	}
}
