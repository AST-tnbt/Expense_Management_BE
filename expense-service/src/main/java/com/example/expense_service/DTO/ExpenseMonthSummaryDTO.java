package com.example.expense_service.DTO;

import java.math.BigDecimal;

public class ExpenseMonthSummaryDTO {
    private int month;
    private BigDecimal totalAmount;

    public ExpenseMonthSummaryDTO() {
    }

    public ExpenseMonthSummaryDTO(int month, BigDecimal totalAmount) {
        this.month = month;
        this.totalAmount = totalAmount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
} 