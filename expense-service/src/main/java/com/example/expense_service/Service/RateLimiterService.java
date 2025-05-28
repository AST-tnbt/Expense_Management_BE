package com.example.expense_service.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {
    
    // Store buckets by user ID
    private final Map<UUID, Bucket> userBuckets = new ConcurrentHashMap<>();
    
    /**
     * Get a rate limiter bucket for a specific user
     * @param userId the user's ID
     * @return a bucket configured with 1 request per minute
     */
    public Bucket resolveBucket(UUID userId) {
        return userBuckets.computeIfAbsent(userId, this::createNewBucket);
    }
    
    /**
     * Create a new bucket with a limit of 1 request per minute
     * @param userId the user's ID (not used in the current implementation, but could be for different limits per user)
     * @return a new rate limiter bucket
     */
    private Bucket createNewBucket(UUID userId) {
        // Allow 1 token per minute
        Bandwidth limit = Bandwidth.classic(1, Refill.intervally(1, Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
} 