package com.example.expense_service.Service;

import com.example.expense_service.DTO.CategoryDTO;
import com.example.expense_service.entities.Category;
import com.example.expense_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository theCategoryRepository)
    {
        this.categoryRepository=theCategoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(
                        category.getCateId(),
                        category.getTitle(),
                        category.getIconId()
                )).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCateByID(UUID cateId) {
      Category category= categoryRepository.findById(cateId).orElseThrow(()-> new RuntimeException("Category not found"));
        return new CategoryDTO(category.getCateId(),category.getTitle(),category.getIconId());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
            Category category = new Category(categoryDTO.getTitle(), categoryDTO.getIconId());
            Category saved = categoryRepository.save(category);
            return new CategoryDTO(saved.getCateId(),saved.getTitle(),saved.getIconId());
            }

    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setTitle(categoryDTO.getTitle());
        Category updated = categoryRepository.save(existing);

        return new CategoryDTO(updated.getCateId(),updated.getTitle(), updated.getIconId());
    }

    }
