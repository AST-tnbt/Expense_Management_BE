package com.example.expense_service.Controller;


import com.example.expense_service.DTO.CategoryDTO;
import com.example.expense_service.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable UUID id) {
        return categoryService.getCateByID(id);
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
