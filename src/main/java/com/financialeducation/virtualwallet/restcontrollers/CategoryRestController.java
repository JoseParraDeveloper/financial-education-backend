package com.financialeducation.virtualwallet.restcontrollers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.financialeducation.virtualwallet.dto.CategoryPersistentObjectDto;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;
import com.financialeducation.virtualwallet.services.ICategoryService;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryRestController {
	@Autowired
	private ICategoryService categoryService;
	@Value("${sizePageCategory}")
	private int sizePageCategory;

	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllCategories() {
		Set<CategoryPersistentObjectDto> listCategories = categoryService.listAllCategory();
		if (listCategories.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no categories.");
		}
		return ResponseEntity.ok(listCategories);
	}

	@GetMapping(value = { "/page/{page}", "/page" })
	public ResponseEntity<?> getCategoriesByPage(@PathVariable(name = "page", required = false) Integer page) {
		if (page == null) {
			page = 1;
		}
		Page<CategoryPersistentObjectDto> pageCategories = categoryService
				.pageCategory(PageRequest.of(page - 1, sizePageCategory));
		if (pageCategories.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no categories in the page: " + page);
		}
		return ResponseEntity.ok(pageCategories);
	}

	@GetMapping(value = "/{categoryID}")
	public ResponseEntity<?> getCategoryById(@PathVariable("categoryID") Long categoryId) {
		try {
			CategoryPersistentObjectDto category = categoryService.getCategoryById(categoryId);
			return ResponseEntity.ok(category);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
		}
	}

	@PostMapping(value = "/create")
	public ResponseEntity<CategoryPersistentObjectDto> createCategory(
			@RequestBody CategoryPersistentObjectDto categoryPersistentObjectDto) {
		return ResponseEntity.ok(categoryService.createCategory(categoryPersistentObjectDto));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Long idCategory) {
		try {
			categoryService.deleteCategoryById(idCategory);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryPersistentObjectDto categoryDto) {
		try {
			CategoryPersistentObjectDto updateCategoryDto = categoryService.updateCategory(categoryDto);
			return new ResponseEntity<>(updateCategoryDto, HttpStatus.OK);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		} catch (BadRequestException badRequestException) {
			return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
