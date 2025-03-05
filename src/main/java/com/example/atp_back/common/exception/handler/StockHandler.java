package com.example.atp_back.common.exception.handler;

import com.example.atp_back.common.code.BaseErrorCode;
import com.example.atp_back.common.exception.GeneralException;

public class StockHandler extends GeneralException {
    public StockHandler(BaseErrorCode code) {
        super(code);
    }
}
