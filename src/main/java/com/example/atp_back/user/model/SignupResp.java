package com.example.atp_back.user.model;

import com.example.atp_back.common.BaseResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupResp implements BaseResponse {
    private boolean isSuccess;
    @Override
    public boolean getIsSuccess() {
        return isSuccess;
    }

    @Override
    public void setIsSuccess(boolean success) {
        this.isSuccess = success;
    }
}
