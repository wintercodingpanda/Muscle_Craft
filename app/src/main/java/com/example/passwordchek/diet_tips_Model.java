package com.example.passwordchek;

public class diet_tips_Model {
    String Title, ImageUrl, Description;

    diet_tips_Model(){

    }
    public diet_tips_Model(String title, String imageUrl, String description) {
        Title = title;
        ImageUrl = imageUrl;
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
