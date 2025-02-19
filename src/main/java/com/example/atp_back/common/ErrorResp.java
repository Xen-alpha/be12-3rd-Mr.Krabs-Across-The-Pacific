package com.example.atp_back.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResp<T> extends BaseResp<T> {
    private Integer code;
    private String message;
}
