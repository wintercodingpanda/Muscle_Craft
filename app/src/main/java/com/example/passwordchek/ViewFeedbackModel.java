package com.example.passwordchek;

public class ViewFeedbackModel {
    String Email,Message,Name,Subject;

    public ViewFeedbackModel() {
    }

    public ViewFeedbackModel(String email, String message, String name, String subject) {
        Email = email;
        Message = message;
        Name = name;
        Subject = subject;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
