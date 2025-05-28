package com.example.expense_service.Service;

import com.example.expense_service.DTO.CategoryExpenseSummaryDTO;
import com.example.expense_service.DTO.MessageDTO;
import com.example.expense_service.DTO.SuggestRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SuggestServiceImpl implements SuggestService {
    private static final String API_URL = "https://api.together.xyz/v1/chat/completions";
    private static final Logger log = LoggerFactory.getLogger(SuggestServiceImpl.class);
    @Value("${togetherai.api.key}")
    private String API_KEY;
    private WebClient webClient;

    @PostConstruct
    void init() {
        var client = HttpClient.create().responseTimeout(Duration.ofSeconds(45));
        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl(API_URL)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Bearer " + API_KEY)
                .build();
    }

    @Override
    public String getSuggestion(BigDecimal expenseTarget, List<CategoryExpenseSummaryDTO> expenseByCategory) {
        String prompt = "Response with only text, using Vietnamese. " +
                "The format of response include: percentage of expense by categories " +
                "and suggest the expense plan for next month. ";
        StringBuilder contentValue = new StringBuilder("Expense information: ");
        String target = ". Expense target: " + expenseTarget.toString() + "VNĐ";
        List<CategoryExpenseSummaryDTO> expenseList = new ArrayList<>(expenseByCategory);
        for (CategoryExpenseSummaryDTO item : expenseList) {
            contentValue.append(item.getCategoryTitle()).append(": ").append(item.getTotalAmount()).append("; ");
        }
        StringBuilder fullyPrompt = new StringBuilder();
        fullyPrompt.append(prompt).append(contentValue).append(target);
        
        SuggestRequestDTO request = new SuggestRequestDTO();
        request.setModel("meta-llama/Llama-3.3-70B-Instruct-Turbo-Free");
        MessageDTO message = new MessageDTO();
        message.setRole("user");
        message.setContent(fullyPrompt.toString());
        request.setMessages(Collections.singletonList(message));

        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("Sending request payload: {}", mapper.writeValueAsString(request));
        } catch (Exception e) {
            log.error("Failed to serialize request", e);
        }
        
        try {
            Map<String, Object> response = webClient.post()
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Map.class)
            .block();
            
            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, Object> messageResponse = (Map<String, Object>) choice.get("message");
                    if (messageResponse != null && messageResponse.containsKey("content")) {
                        return (String) messageResponse.get("content");
                    }
                }
            }
            return "No suggestion available at this time.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting suggestion: " + e.getMessage();
        }
    }
}
