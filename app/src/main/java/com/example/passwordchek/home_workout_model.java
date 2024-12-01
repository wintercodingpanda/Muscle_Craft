package com.example.passwordchek;

public class home_workout_model {
    String Title, ImageUrl, Duration, Description;

    public home_workout_model() {
    }

    public home_workout_model(String title, String imageUrl, String duration, String description) {
        Title = title;
        ImageUrl = imageUrl;
        Duration = duration;
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

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
