package com.example.atp_back.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse extends BaseResponse {
    private Integer code;
    private String message;

}
