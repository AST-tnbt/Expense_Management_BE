package com.example.expense_service.Service;

import com.example.expense_service.DTO.ExpenseDTO;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses();
    ExpenseDTO getExpenseById(UUID id);
    ExpenseDTO createExpense(ExpenseDTO expenseDTO);
    void deleteExpense(UUID id);
    ExpenseDTO updateExpense(UUID id, ExpenseDTO expenseDTO);
    List<ExpenseDTO>getAllExpensesByUserId(UUID userId);

}
