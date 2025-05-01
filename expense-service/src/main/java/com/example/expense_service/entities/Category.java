package com.example.expense_service.entities;

import jakarta.persistence.*;
import org.hibernate.mapping.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID cateId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String iconId;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

    public UUID getCateId() {
        return cateId;
    }

    public void setCateId(UUID cateId) {
        this.cateId = cateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
    public void add(Expense tempExpense)
    {
        if(expenses==null)
        {
            expenses=new ArrayList<>();
        }
        expenses.add(tempExpense);
        tempExpense.setCategory(this);
    }
}
