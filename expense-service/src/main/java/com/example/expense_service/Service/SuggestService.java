package com.example.expense_service.Service;

import com.example.expense_service.DTO.CategoryExpenseSummaryDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SuggestService {
    String getSuggestion(BigDecimal expenseTarget, List<CategoryExpenseSummaryDTO> expenseByCategory);
}
