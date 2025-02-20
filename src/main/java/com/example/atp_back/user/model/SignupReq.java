package com.example.atp_back.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupReq {
    @Schema(description="실명(문자열): 필수")
    private String name;
    @Schema(description="이메일(문자열): 필수, 로그인 아이디임")
    private String email;
    @Schema(description="패스워드(문자열): 이건 평문이며, 서버 내에서 암호화")
    private String password;
    @Schema(description="이미지 이름(문자열)")
    private String image;
}
