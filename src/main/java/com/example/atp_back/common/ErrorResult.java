package com.example.atp_back.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResult implements BaseResult {
    private boolean isSuccess;
    private Integer code;
    private String message;

    @Override
    public boolean getIsSuccess() {
        return isSuccess;
    }

    @Override
    public void setIsSuccess(boolean success) {
        isSuccess = success;
    }
}
