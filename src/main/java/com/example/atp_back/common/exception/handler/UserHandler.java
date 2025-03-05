package com.example.atp_back.common.exception.handler;


import com.example.atp_back.common.code.BaseErrorCode;
import com.example.atp_back.common.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
