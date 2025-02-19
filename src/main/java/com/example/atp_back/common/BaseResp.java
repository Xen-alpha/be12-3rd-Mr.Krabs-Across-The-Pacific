package com.example.atp_back.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseResp<T> {
    private boolean isSuccess;
    private T result;
}
