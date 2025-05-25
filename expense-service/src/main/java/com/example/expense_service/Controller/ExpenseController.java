package com.example.expense_service.Controller;


import com.example.expense_service.DTO.ExpenseDTO;
import com.example.expense_service.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<ExpenseDTO> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public List<ExpenseDTO> getAllExpensesByUserId(@PathVariable UUID id) {
        return expenseService.getAllExpensesByUserId(id);
    }

    @PostMapping
    public ExpenseDTO createExpense(@RequestBody ExpenseDTO expenseDTO) {
        return expenseService.createExpense(expenseDTO);
    }

    @PutMapping("/{id}")
    public ExpenseDTO updateExpense(@PathVariable UUID id, @RequestBody ExpenseDTO expenseDTO) {
        return expenseService.updateExpense(id, expenseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/recent/{userId}")
    public List<ExpenseDTO> getRecentExpensesThisMonth(@PathVariable UUID userId) {
        return expenseService.getTop5RecentExpensesInCurrentMonth(userId);
    }
    @GetMapping("/monthly-total/{userId}")
    public ResponseEntity<BigDecimal> getMonthlyTotalSpend(@PathVariable UUID userId) {
        try {
            BigDecimal totalSpend = expenseService.getMonthlyTotalSpend(userId);
            return ResponseEntity.ok(totalSpend);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

