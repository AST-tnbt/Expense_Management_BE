package com.example.expense_service.DTO;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ExpenseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private UUID expenseId;
    private UUID userId;
    private LocalDate date;
    private BigDecimal spend;
    private UUID cateId;
    private String Title;
    private String IconId;

    public ExpenseDTO() {}


    public ExpenseDTO( UUID expenseId,UUID userId, LocalDate date, BigDecimal spend, UUID cateId) {
        this.expenseId=expenseId;
        this.userId = userId;
        this.date = date;
        this.spend = spend;
        this.cateId = cateId;
    }
    public ExpenseDTO(UUID expenseId,LocalDate date, BigDecimal spend, String Title, String iconId) {
        this.expenseId=expenseId;
        this.date = date;
        this.spend = spend;
        this.Title = Title;
        this.IconId=iconId;
    }
    public ExpenseDTO(UUID expenseId,LocalDate date, BigDecimal spend, String Title, String iconId,UUID cateId) {
        this.expenseId=expenseId;
        this.date = date;
        this.spend = spend;
        this.Title = Title;
        this.IconId=iconId;
        this.cateId=cateId;
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIconId() {
        return IconId;
    }

    public void setIconId(String iconId) {
        IconId = iconId;
    }
}

