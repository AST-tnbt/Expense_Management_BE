package com.example.expense_service.Controller;

import com.example.expense_service.DTO.CategoryExpenseSummaryDTO;
import com.example.expense_service.Service.ExpenseService;
import com.example.expense_service.Service.RateLimiterService;
import com.example.expense_service.Service.SuggestService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suggestions")
public class SuggestController {
    private static final Logger log = LoggerFactory.getLogger(SuggestController.class);
    
    @Autowired
    private SuggestService suggestService;
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private RateLimiterService rateLimiterService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<String> getSuggestion(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") int year,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getMonthValue()}") int month,
            @RequestParam() BigDecimal expenseTarget) {
        
        // Get the rate limiter bucket for this user
        Bucket bucket = rateLimiterService.resolveBucket(userId);
        
        // Try to consume a token
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        
        // If the request is not allowed due to rate limiting
        if (!probe.isConsumed()) {
            long waitTimeSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000;
            log.warn("Rate limit exceeded for user: {}. Need to wait {} seconds", userId, waitTimeSeconds);
            
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitTimeSeconds))
                    .body("Rate limit exceeded. Try again in " + waitTimeSeconds + " seconds.");
        }
        
        try {
            // Get expense data by category for the specified month
            List<CategoryExpenseSummaryDTO> expensesByCategory = expenseService.getMonthChart(userId, year, month);
            
            // Get AI suggestion based on expense data
            String suggestion = suggestService.getSuggestion(expenseTarget, expensesByCategory);
            
            return ResponseEntity.ok(suggestion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating suggestion: " + e.getMessage());
        }
    }
} 