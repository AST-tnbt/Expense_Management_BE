package com.example.expense_service.repository;

import com.example.expense_service.entities.Expense;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByUserId(UUID userId);
    // ExpenseRepository.java
    @Query("SELECT e FROM Expense e WHERE e.userId = :userId AND FUNCTION('MONTH', e.date) = :month AND FUNCTION('YEAR', e.date) = :year ORDER BY e.date DESC")
    List<Expense> findTop5ByUserIdAndCurrentMonth(@Param("userId") UUID userId, @Param("month") int month, @Param("year") int year);
    @Query("SELECT COALESCE(SUM(e.spend), 0) FROM Expense e WHERE e.userId = :userId AND FUNCTION('MONTH', e.date) = :month AND FUNCTION('YEAR', e.date) = :year")
    BigDecimal getTotalSpendByUserIdAndMonth(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") int year
    );
    
    @Query("SELECT FUNCTION('MONTH', e.date) as month, COALESCE(SUM(e.spend), 0) as totalAmount " +
           "FROM Expense e " +
           "WHERE e.userId = :userId AND FUNCTION('YEAR', e.date) = :year " +
           "GROUP BY FUNCTION('MONTH', e.date) " +
           "ORDER BY month")
    List<Object[]> getMonthlyExpensesForYear(
            @Param("userId") UUID userId,
            @Param("year") int year
    );
    
    @Query("SELECT e.category.cateId, e.category.title, COALESCE(SUM(e.spend), 0) " +
           "FROM Expense e " +
           "WHERE e.userId = :userId AND FUNCTION('MONTH', e.date) = :month AND FUNCTION('YEAR', e.date) = :year " +
           "GROUP BY e.category.cateId, e.category.title " +
           "ORDER BY COALESCE(SUM(e.spend), 0) DESC")
    List<Object[]> getExpensesByCategory(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") int year
    );
}
