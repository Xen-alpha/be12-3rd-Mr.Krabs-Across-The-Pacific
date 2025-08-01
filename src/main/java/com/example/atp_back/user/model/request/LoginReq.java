package com.example.atp_back.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description="로그인 요청 Body")
public class LoginReq {
    @Schema(description="ID(문자열): 이메일임, 필수", required = true,  example = "example@example.com")
    @NotBlank
    @Email(message = "signup")
    private String id;
    @Schema(description="패스워드(문자열), 필수", required = true, example = "7276sefds")
    @NotBlank
    private String password;
}
