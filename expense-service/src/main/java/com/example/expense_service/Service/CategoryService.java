package com.example.expense_service.Service;

import com.example.expense_service.DTO.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDTO> getAllCategory();
    CategoryDTO getCateByID(UUID cateId);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    void deleteCategory(UUID id);
    CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO);
}
