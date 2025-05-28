package com.example.expense_service.DTO;

import java.util.List;

public class SuggestRequestDTO {
    private String model;
    private List<MessageDTO> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> message) {
        this.messages = message;
    }
}
