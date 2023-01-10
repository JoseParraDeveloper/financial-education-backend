package com.financialeducation.virtualwallet.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.financialeducation.virtualwallet.dto.CategoryPersistentObjectDto;
import com.financialeducation.virtualwallet.entities.Category;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;
import com.financialeducation.virtualwallet.repositories.ICategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private ICategoryRepository categoryRepository;
	@Autowired
	@Qualifier("modelMapper")
	private ModelMapper modelMapper;

	@Override
	public Set<CategoryPersistentObjectDto> listAllCategory() {
		List<Category> listCategory = categoryRepository.findAll();
		return listCategory.stream().map(category -> modelMapper.map(category, CategoryPersistentObjectDto.class))
				.collect(Collectors.toSet());
	}

	@Override
	public Page<CategoryPersistentObjectDto> pageCategory(Pageable pageable) {
		Page<Category> pageCategory = categoryRepository.findAll(pageable);
		List<CategoryPersistentObjectDto> listCategoryDto = pageCategory.stream()
				.map(category -> modelMapper.map(category, CategoryPersistentObjectDto.class))
				.collect(Collectors.toList());
		return new PageImpl<>(listCategoryDto);

	}

	@Override
	public CategoryPersistentObjectDto getCategoryById(Long idCategory) throws ResourceNotFoundException {
		Optional<Category> optionalCategory = categoryRepository.findById(idCategory);
		return modelMapper.map(
				optionalCategory
						.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", idCategory.toString())),
				CategoryPersistentObjectDto.class);
	}

	@Override
	public CategoryPersistentObjectDto createCategory(CategoryPersistentObjectDto categoryPersistentObjectDto) {

		Category newCategoryEntity = modelMapper.map(categoryPersistentObjectDto, Category.class);
		newCategoryEntity = categoryRepository.save(newCategoryEntity);
		return modelMapper.map(newCategoryEntity, CategoryPersistentObjectDto.class);
	}

	@Override
	public CategoryPersistentObjectDto updateCategory(CategoryPersistentObjectDto categoryPersistentObjectDto)
			throws ResourceNotFoundException, BadRequestException {
		Long idCategoryUpdate = categoryPersistentObjectDto.getId();
		if (idCategoryUpdate != null) {
			Optional<Category> optionalCategory = categoryRepository.findById(idCategoryUpdate);
			if (!optionalCategory.isPresent()) {
				throw new ResourceNotFoundException("Category", "ID", idCategoryUpdate.toString());
			}
		} else {
			throw new BadRequestException("id " + idCategoryUpdate);
		}
		return this.createCategory(categoryPersistentObjectDto);

	}

	@Override
	public void deleteCategoryById(Long idCategory) throws ResourceNotFoundException {
		Optional<Category> optionalCategory = categoryRepository.findById(idCategory);
		optionalCategory.ifPresentOrElse(category -> categoryRepository.deleteById(category.getId()), () -> {
			throw new ResourceNotFoundException("Category", "ID", idCategory.toString());
		});
	}

}
