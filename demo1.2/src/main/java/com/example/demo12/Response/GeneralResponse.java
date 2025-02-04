package com.example.demo12.Response;

public class GeneralResponse {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GeneralResponse(String message) {
        this.message = message;
    }

    String message;
}
