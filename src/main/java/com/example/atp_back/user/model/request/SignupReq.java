package com.example.atp_back.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupReq {
    @Schema(description="실명(문자열): 필수", example = "gildong")
    @NotBlank
    @Pattern(regexp = "[0-9A-Za-z가-힣]+", message="signup wrong name")
    private String name;
    @Schema(description="이메일(문자열): 필수, 로그인 아이디임", example = "example@example.com")
    @NotBlank
    @Email(message = "signup wrong email")
    private String email;
    @Schema(description="패스워드(문자열): 8자 이상 영문, 숫자, 일부 특수문자 사용, 이건 평문이며, 서버 내에서 암호화")
    @NotBlank
    @Pattern(regexp = "[0-9A-Za-z!~$*]{8,}", message="signup wrong pass")
    private String password;
    @Schema(description="이미지 이름(문자열), 공백일 수 있음", example = "/sample/image/url")
    private String image;
}
