package com.example.expense_service.DTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ExpenseDTO {
    private UUID expenseId;
    private UUID userId;
    private LocalDate date;
    private BigDecimal spend;
    private UUID cateId;

    public ExpenseDTO() {}


    public ExpenseDTO( UUID expenseId,UUID userId, LocalDate date, BigDecimal spend, UUID cateId) {
        this.expenseId=expenseId;
        this.userId = userId;
        this.date = date;
        this.spend = spend;
        this.cateId = cateId;
    }

    public UUID getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(UUID expenseId) {
        this.expenseId = expenseId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getSpend() {
        return spend;
    }

    public void setSpend(BigDecimal spend) {
        this.spend = spend;
    }

    public UUID getCateId() {
        return cateId;
    }

    public void setCateId(UUID cateId) {
        this.cateId = cateId;
    }


}

