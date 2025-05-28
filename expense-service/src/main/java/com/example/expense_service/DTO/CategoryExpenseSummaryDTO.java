package com.example.expense_service.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class CategoryExpenseSummaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private UUID categoryId;
    private String categoryTitle;
    private BigDecimal totalAmount;

    public CategoryExpenseSummaryDTO() {
    }

    public CategoryExpenseSummaryDTO(UUID categoryId, String categoryTitle, BigDecimal totalAmount) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.totalAmount = totalAmount;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
} 