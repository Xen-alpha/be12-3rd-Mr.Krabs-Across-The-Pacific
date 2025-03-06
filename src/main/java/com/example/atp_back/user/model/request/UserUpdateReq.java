package com.example.atp_back.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Tag(name = "유저 정보 갱신", description="자신의 정보 갱신 요청용 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(description="유저 정보 갱신 요청 body")
public class UserUpdateReq {
    @Schema(description="실명, 필수", example = "GilDongHong")
    @NotBlank
    private String name;
    @Schema(description="이메일(문자열): 필수, 로그인 아이디임", example = "example@example.com")
    @NotBlank
    @Email(message = "wrong email format")
    private String email;
    @Schema(description="패스워드(문자열): 8자 이상 영문, 숫자, 일부 특수문자 사용, 이건 평문이며, 서버 내에서 암호화")
    @NotBlank
    private String password;
    @Schema(description="이미지 이름(문자열), 공백일 수 있음", example = "/sample/image/url")
    private String image;
}
