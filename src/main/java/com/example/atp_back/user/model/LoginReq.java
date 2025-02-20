package com.example.atp_back.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginReq {
    @Schema(description="ID(문자열): 이메일임, 필수")
    private String id;
    @Schema(description="패스워드(문자열), 필수")
    private String password;
}
