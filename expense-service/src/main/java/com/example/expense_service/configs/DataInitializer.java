//package com.example.expense_service.configs;
//
//import com.example.expense_service.entities.Category;
//import com.example.expense_service.repository.CategoryRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataInitializer {
//    @Bean
//    CommandLineRunner initDatabase(CategoryRepository categoryRepository) {
//        return args -> {
//            if (categoryRepository.count() == 0) {
//                categoryRepository.save(new Category("Ăn uống", "food"));
//                categoryRepository.save(new Category("Mua sắm", "shopping"));
//                categoryRepository.save(new Category("Đi lại", "transport"));
//            }
//        };
//    }
//}