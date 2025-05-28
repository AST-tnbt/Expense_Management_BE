package com.example.expense_service.configs;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RateLimitInterceptor.class);
    
    // Create a global bucket for all suggestion requests (as an additional safety measure)
    private final Bucket globalBucket;
    
    public RateLimitInterceptor() {
        // Allow 10 tokens per minute globally (across all users)
        // This is just an additional safety layer on top of per-user limits
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
        this.globalBucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Only apply to suggestion API endpoints
        if (request.getRequestURI().contains("/suggestions")) {
            if (!globalBucket.tryConsume(1)) {
                log.warn("Global rate limit exceeded for suggestion API");
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too many requests. Please try again later.");
                return false;
            }
        }
        return true;
    }
} 