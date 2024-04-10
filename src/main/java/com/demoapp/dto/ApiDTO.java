package com.demoapp.dto;

public class ApiDTO<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiDTO<T> success(String message, T data) {
        return new ApiDTO<>(true, message, data);
    }

    public static <T> ApiDTO<T> failure(String message) {
        return new ApiDTO<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
