package com.example.expense_service.Service;

import com.example.expense_service.DTO.CategoryExpenseSummaryDTO;
import com.example.expense_service.DTO.ExpenseDTO;
import com.example.expense_service.DTO.ExpenseMonthSummaryDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses();
    ExpenseDTO getExpenseById(UUID id);
    ExpenseDTO createExpense(ExpenseDTO expenseDTO);
    void deleteExpense(UUID id);
    ExpenseDTO updateExpense(UUID id, ExpenseDTO expenseDTO);
    List<ExpenseDTO>getAllExpensesByUserId(UUID userId);
    List<ExpenseDTO> getTop5RecentExpensesInCurrentMonth(UUID userId);
    BigDecimal getMonthlyTotalSpend(UUID userId); // Không cần truyền month/year nữa
    List<ExpenseMonthSummaryDTO> getExpenseYearChart(UUID userId, int year);
    List<CategoryExpenseSummaryDTO> getMonthChart(UUID userId, int year, int month);
}
