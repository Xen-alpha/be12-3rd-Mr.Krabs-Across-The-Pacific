package com.example.atp_back.common.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@Builder
public class ErrorReasonDTO{
    HttpStatus httpStatus;
    String code;
    String message;
    boolean isSuccess;
}
