package com.example.expense_service.Service;

import com.example.expense_service.DTO.CategoryExpenseSummaryDTO;
import com.example.expense_service.DTO.ExpenseDTO;
import com.example.expense_service.DTO.ExpenseMonthSummaryDTO;
import com.example.expense_service.entities.Category;
import com.example.expense_service.entities.Expense;
import com.example.expense_service.repository.CategoryRepository;
import com.example.expense_service.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

        @Override
        public List<ExpenseDTO> getAllExpensesByUserId(UUID userId) {
            List<Expense> expenses = expenseRepository.findByUserId(userId);

            return expenses.stream()
                    .map(expense -> new ExpenseDTO(
                            expense.getExpenseId(),
                            expense.getDate(),
                            expense.getSpend(),
                            expense.getCategory().getTitle(),
                            expense.getCategory().getIconId(),
                            expense.getCategory().getCateId()
                    ))
                    .collect(Collectors.toList());
        }
        @Override
        public List<ExpenseDTO> getTop5RecentExpensesInCurrentMonth(UUID userId) {
            // Lấy tháng và năm hiện tại
            LocalDate now = LocalDate.now();
            int currentMonth = now.getMonthValue();
            int currentYear = now.getYear();

            List<Expense> expenses = expenseRepository.findTop5ByUserIdAndCurrentMonth(userId, currentMonth, currentYear);

            return expenses.stream()
                    .limit(5) // đảm bảo chỉ lấy 5 dòng (nếu query chưa giới hạn)
                    .map(expense -> new ExpenseDTO(
                            expense.getExpenseId(),
                            expense.getDate(),
                            expense.getSpend(),
                            expense.getCategory().getTitle(),
                            expense.getCategory().getIconId(),
                            expense.getCategory().getCateId()
                    ))
                    .collect(Collectors.toList());
        }
        @Override
        public BigDecimal getMonthlyTotalSpend(UUID userId) {
            LocalDate today = LocalDate.now();
            int currentMonth = today.getMonthValue();
            int currentYear = today.getYear();

            return expenseRepository.getTotalSpendByUserIdAndMonth(userId, currentMonth, currentYear);
        }

        @Override
        public List<ExpenseMonthSummaryDTO> getExpenseYearChart(UUID userId, int year) {
            List<Object[]> results = expenseRepository.getMonthlyExpensesForYear(userId, year);
            
            // Convert the raw results to DTOs
            List<ExpenseMonthSummaryDTO> monthlySummaries = new ArrayList<>();
            for (Object[] result : results) {
                int month = ((Number) result[0]).intValue();
                BigDecimal totalAmount = (BigDecimal) result[1];
                monthlySummaries.add(new ExpenseMonthSummaryDTO(month, totalAmount));
            }
            
            // Fill in missing months with zero values
            List<ExpenseMonthSummaryDTO> completeYearData = new ArrayList<>();
            for (int month = 1; month <= 12; month++) {
                final int currentMonth = month;
                ExpenseMonthSummaryDTO existingData = monthlySummaries.stream()
                        .filter(summary -> summary.getMonth() == currentMonth)
                        .findFirst()
                        .orElse(new ExpenseMonthSummaryDTO(currentMonth, BigDecimal.ZERO));
                
                completeYearData.add(existingData);
            }
            
            return completeYearData;
        }
        
        @Override
        public List<CategoryExpenseSummaryDTO> getMonthChart(UUID userId, int year, int month) {
            List<Object[]> results = expenseRepository.getExpensesByCategory(userId, month, year);
            
            List<CategoryExpenseSummaryDTO> categoryExpenses = new ArrayList<>();
            for (Object[] result : results) {
                UUID categoryId = (UUID) result[0];
                String categoryTitle = (String) result[1];
                BigDecimal totalAmount = (BigDecimal) result[2];
                
                categoryExpenses.add(new CategoryExpenseSummaryDTO(categoryId, categoryTitle, totalAmount));
            }
            
            return categoryExpenses;
        }
    }
