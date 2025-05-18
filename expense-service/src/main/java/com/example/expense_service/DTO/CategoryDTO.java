package com.example.expense_service.DTO;


import java.util.List;
import java.util.UUID;

public class CategoryDTO {
    private  UUID cateId;
    private String title;
    private String iconId;
    private UUID userId;

    public CategoryDTO() {}

    public CategoryDTO(UUID theCateId,String title, String iconId,UUID userId) {
        this.cateId=theCateId;
        this.title = title;
        this.iconId=iconId;
        this.userId=userId;
    }
    public CategoryDTO(String title, String iconId,UUID userId) {
        this.title = title;
        this.iconId=iconId;
        this.userId=userId;
    }
    public CategoryDTO(String title, String iconId) {
        this.title = title;
        this.iconId=iconId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getCateId() {
        return cateId;
    }

    public void setCateId(UUID cateId) {
        this.cateId = cateId;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}

