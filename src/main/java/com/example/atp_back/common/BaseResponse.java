package com.example.atp_back.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})  //getter 수정안해도 isSuccess 받음
public class BaseResponse<T> {
    private boolean isSuccess;
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)  //result null인 경우에 result 필드를 제외 시킴
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

    // 실패한 경우 응답 생성
    public static <T> BaseResponse<T> onFailure(String code, String message, T data){
        return new BaseResponse<>(true, code, message, data);
    }
}