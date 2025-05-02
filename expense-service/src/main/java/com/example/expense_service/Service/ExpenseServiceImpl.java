package com.example.expense_service.Service;

import com.example.expense_service.DTO.ExpenseDTO;
import com.example.expense_service.entities.Category;
import com.example.expense_service.entities.Expense;
import com.example.expense_service.repository.CategoryRepository;
import com.example.expense_service.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

    @Service
    public class ExpenseServiceImpl implements ExpenseService {

        @Autowired
        private ExpenseRepository expenseRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Override
        public List<ExpenseDTO> getAllExpenses() {
            return expenseRepository.findAll().stream()
                    .map(expense -> new ExpenseDTO(
                            expense.getExpenseId(),
                            expense.getUserId(),
                            expense.getDate(),
                            expense.getSpend(),
                            expense.getCategory().getCateId()
                    ))
                    .collect(Collectors.toList());
        }

        @Override
        public ExpenseDTO getExpenseById(UUID id) {
            Expense expense = expenseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Expense not found"));

            return new ExpenseDTO(
                    expense.getExpenseId(),
                    expense.getUserId(),
                    expense.getDate(),
                    expense.getSpend(),
                    expense.getCategory().getCateId()
            );
        }

        @Override
        public ExpenseDTO createExpense(ExpenseDTO dto) {
            Category category = categoryRepository.findById(dto.getCateId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Expense expense = new Expense(
                    dto.getUserId(),
                    dto.getDate(),
                    dto.getSpend()
            );
            expense.setCategory(category);

            Expense saved = expenseRepository.save(expense);

            return new ExpenseDTO(
                    saved.getExpenseId(),
                    saved.getUserId(),
                    saved.getDate(),
                    saved.getSpend(),
                    saved.getCategory().getCateId()
            );
        }

        @Override
        public void deleteExpense(UUID id) {
            expenseRepository.deleteById(id);
        }


        @Override
        public ExpenseDTO updateExpense(UUID id, ExpenseDTO dto) {
            Expense existing = expenseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Expense not found"));

            existing.setUserId(dto.getUserId());
            existing.setDate(dto.getDate());
            existing.setSpend(dto.getSpend());

            Category category = categoryRepository.findById(dto.getCateId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existing.setCategory(category);

            Expense updated = expenseRepository.save(existing);

            return new ExpenseDTO(
                    updated.getExpenseId(),
                    updated.getUserId(),
                    updated.getDate(),
                    updated.getSpend(),
                    updated.getCategory().getCateId()
            );
        }

}
