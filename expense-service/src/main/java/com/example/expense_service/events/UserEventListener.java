package com.example.expense_service.events;

import com.example.expense_service.Service.CategoryService;
import com.example.expense_service.configs.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);
    private final CategoryService categoryService;
    
    public UserEventListener(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_QUEUE)
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        logger.info("Received user created event for user: {}", event.getUsername());
        
        try {
            // Create default categories for the new user
            categoryService.createDefaultCategoriesForUser(event.getUserId());
            logger.info("Default categories created for user: {}", event.getUserId());
        } catch (Exception e) {
            logger.error("Error creating default categories for user: {}", event.getUserId(), e);
        }
    }
} 