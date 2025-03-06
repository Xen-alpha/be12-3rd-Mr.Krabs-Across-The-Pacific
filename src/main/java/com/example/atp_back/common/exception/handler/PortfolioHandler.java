package com.example.atp_back.common.exception.handler;

import com.example.atp_back.common.code.BaseErrorCode;
import com.example.atp_back.common.exception.GeneralException;

public class PortfolioHandler extends GeneralException {
    public PortfolioHandler(BaseErrorCode code) {
        super(code);
    }
}
