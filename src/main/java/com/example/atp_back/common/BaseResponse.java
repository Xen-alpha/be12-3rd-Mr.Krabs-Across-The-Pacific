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

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public static<T> BaseResponse<T> success(T result) {
        BaseResponse<T> resp = new BaseResponse<>();
        resp.isSuccess = true;
        resp.result = result;
        return resp;
    }
}