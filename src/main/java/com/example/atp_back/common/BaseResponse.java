package com.example.atp_back.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private boolean isSuccess;
    private String code;
    private String message;
    private T result;

    public static<T> BaseResponse<T> success(T result) {
        return BaseResponse.<T>builder()
                .isSuccess(true)
                .result(result)
                .build();
    }

    public BaseResponse error(String code, String message) {
        isSuccess = false;
        this.code = code;
        this.message = message;
        return this;
    }
}