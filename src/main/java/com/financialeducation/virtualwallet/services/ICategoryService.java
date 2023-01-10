package com.financialeducation.virtualwallet.services;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financialeducation.virtualwallet.dto.CategoryPersistentObjectDto;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;

public interface ICategoryService {

	public Set<CategoryPersistentObjectDto> listAllCategory();

	public Page<CategoryPersistentObjectDto> pageCategory(Pageable pageable);

	public CategoryPersistentObjectDto getCategoryById(Long idCategory) throws ResourceNotFoundException;

	public CategoryPersistentObjectDto createCategory(CategoryPersistentObjectDto categoryPersistentObjectDto);

	public CategoryPersistentObjectDto updateCategory(CategoryPersistentObjectDto categoryPersistentObjectDto)
			throws ResourceNotFoundException, BadRequestException;

	public void deleteCategoryById(Long idCategory) throws ResourceNotFoundException;
}
